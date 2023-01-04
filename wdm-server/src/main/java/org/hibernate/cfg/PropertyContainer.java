package org.hibernate.cfg;

/**********************************
 * Date: 2023/1/3
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/

import java.util.*;

import org.hibernate.AnnotationException;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Target;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.cfg.annotations.HCANNHelper;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.CollectionHelper;

import org.jboss.logging.Logger;

import jakarta.persistence.Access;
import jakarta.persistence.Basic;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

/**
 * A helper class to keep the {@code XProperty}s of a class ordered by access type.
 *
 * @author Hardy Ferentschik
 */
class PropertyContainer {

    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());

    /**
     * The class for which this container is created.
     */
    private final XClass xClass;
    private final XClass entityAtStake;

    /**
     * Holds the AccessType indicated for use at the class/container-level for cases where persistent attribute
     * did not specify.
     */
    private final AccessType classLevelAccessType;

    private final List<XProperty> persistentAttributes;

    PropertyContainer(XClass clazz, XClass entityAtStake, AccessType defaultClassLevelAccessType) {
        this.xClass = clazz;
        this.entityAtStake = entityAtStake;

        if ( defaultClassLevelAccessType == AccessType.DEFAULT ) {
            // this is effectively what the old code did when AccessType.DEFAULT was passed in
            // to getProperties(AccessType) from AnnotationBinder and InheritanceState
            defaultClassLevelAccessType = AccessType.PROPERTY;
        }

        AccessType localClassLevelAccessType = determineLocalClassDefinedAccessStrategy();
        assert localClassLevelAccessType != null;

        this.classLevelAccessType = localClassLevelAccessType != AccessType.DEFAULT
                ? localClassLevelAccessType
                : defaultClassLevelAccessType;
        assert classLevelAccessType == AccessType.FIELD || classLevelAccessType == AccessType.PROPERTY;


        final List<XProperty> fields = xClass.getDeclaredProperties( AccessType.FIELD.getType() );
        final List<XProperty> getters = xClass.getDeclaredProperties( AccessType.PROPERTY.getType() );

        preFilter( fields, getters );

        final Map<String,XProperty> persistentAttributesFromGetters = new HashMap<>();

        final LinkedHashMap<String, XProperty> localAttributeMap = new LinkedHashMap<>();
        collectPersistentAttributesUsingLocalAccessType(
                xClass,
                localAttributeMap,
                persistentAttributesFromGetters,
                fields,
                getters
        );
        collectPersistentAttributesUsingClassLevelAccessType(
                xClass,
                classLevelAccessType,
                localAttributeMap,
                persistentAttributesFromGetters,
                fields,
                getters
        );
        this.persistentAttributes = verifyAndInitializePersistentAttributes( xClass, localAttributeMap );
    }

    private void preFilter(List<XProperty> fields, List<XProperty> getters) {
        Iterator<XProperty> propertyIterator = fields.iterator();
        while ( propertyIterator.hasNext() ) {
            final XProperty property = propertyIterator.next();
            if ( mustBeSkipped( property ) ) {
                propertyIterator.remove();
            }
        }

        propertyIterator = getters.iterator();
        while ( propertyIterator.hasNext() ) {
            final XProperty property = propertyIterator.next();
            if ( mustBeSkipped( property ) ) {
                propertyIterator.remove();
            }
        }
    }

    private static void collectPersistentAttributesUsingLocalAccessType(
            XClass xClass,
            LinkedHashMap<String, XProperty> persistentAttributeMap,
            Map<String,XProperty> persistentAttributesFromGetters,
            List<XProperty> fields,
            List<XProperty> getters) {

        // Check fields...
        Iterator<XProperty> propertyIterator = fields.iterator();
        while ( propertyIterator.hasNext() ) {
            final XProperty xProperty = propertyIterator.next();
            final Access localAccessAnnotation = xProperty.getAnnotation( Access.class );
            if ( localAccessAnnotation == null
                    || localAccessAnnotation.value() != jakarta.persistence.AccessType.FIELD ) {
                continue;
            }

            propertyIterator.remove();
            persistentAttributeMap.put( xProperty.getName(), xProperty );
        }

        // Check getters...
        propertyIterator = getters.iterator();
        while ( propertyIterator.hasNext() ) {
            final XProperty xProperty = propertyIterator.next();
            final Access localAccessAnnotation = xProperty.getAnnotation( Access.class );
            if ( localAccessAnnotation == null
                    || localAccessAnnotation.value() != jakarta.persistence.AccessType.PROPERTY ) {
                continue;
            }

            propertyIterator.remove();

            final String name = xProperty.getName();

            // HHH-10242 detect registration of the same property getter twice - eg boolean isId() + UUID getId()
            final XProperty previous = persistentAttributesFromGetters.get( name );
            if ( previous != null ) {
                throw new org.hibernate.boot.MappingException(
                        LOG.ambiguousPropertyMethods(
                                xClass.getName(),
                                HCANNHelper.annotatedElementSignature( previous ),
                                HCANNHelper.annotatedElementSignature( xProperty )
                        ),
                        new Origin( SourceType.ANNOTATION, xClass.getName() )
                );
            }

            persistentAttributeMap.put( name, xProperty );
            persistentAttributesFromGetters.put( name, xProperty );
        }
    }

    private static void collectPersistentAttributesUsingClassLevelAccessType(
            XClass xClass,
            AccessType classLevelAccessType,
            LinkedHashMap<String, XProperty> persistentAttributeMap,
            Map<String,XProperty> persistentAttributesFromGetters,
            List<XProperty> fields,
            List<XProperty> getters) {
        if ( classLevelAccessType == AccessType.FIELD ) {
            for ( XProperty field : fields ) {
                if ( persistentAttributeMap.containsKey( field.getName() ) ) {
                    continue;
                }

                persistentAttributeMap.put( field.getName(), field );
            }
        }
        else {
            for ( XProperty getter : getters ) {
                final String name = getter.getName();

                // HHH-10242 detect registration of the same property getter twice - eg boolean isId() + UUID getId()
                final XProperty previous = persistentAttributesFromGetters.get( name );
                if ( previous != null ) {
                    throw new org.hibernate.boot.MappingException(
                            LOG.ambiguousPropertyMethods(
                                    xClass.getName(),
                                    HCANNHelper.annotatedElementSignature(previous),
                                    HCANNHelper.annotatedElementSignature(getter)
                            ),
                            new Origin( SourceType.ANNOTATION, xClass.getName() )
                    );
                }

                if ( persistentAttributeMap.containsKey( name ) ) {
                    continue;
                }

                persistentAttributeMap.put( getter.getName(), getter );
                persistentAttributesFromGetters.put( name, getter );
            }
        }
    }

    public XClass getEntityAtStake() {
        return entityAtStake;
    }

    public XClass getDeclaringClass() {
        return xClass;
    }

    public AccessType getClassLevelAccessType() {
        return classLevelAccessType;
    }

    public Iterable<XProperty> propertyIterator() {
        return persistentAttributes;
    }

    private static List<XProperty> verifyAndInitializePersistentAttributes(XClass xClass, Map<String, XProperty> localAttributeMap) {
        ArrayList<XProperty> output = new ArrayList<>( localAttributeMap.size() );
        for ( XProperty xProperty : localAttributeMap.values() ) {
            if ( !xProperty.isTypeResolved() && !discoverTypeWithoutReflection( xClass, xProperty ) ) {
                String msg = "Property '" + StringHelper.qualify( xClass.getName(), xProperty.getName() ) +
                        "' has an unbound type and no explicit target entity (resolve this generics usage issue" +
                        " or set an explicit target attribute with '@OneToMany(target=)' or use an explicit '@Type')";
                throw new AnnotationException( msg );
            }
            output.add( xProperty );
        }
        return CollectionHelper.toSmallList( output );
    }

    private AccessType determineLocalClassDefinedAccessStrategy() {
        AccessType classDefinedAccessType = AccessType.DEFAULT;
        Access access = xClass.getAnnotation( Access.class );
        if ( access != null ) {
            classDefinedAccessType = AccessType.getAccessStrategy( access.value() );
        }
        return classDefinedAccessType;
    }

    private static boolean discoverTypeWithoutReflection(XClass clazz, XProperty property) {
        if ( property.isAnnotationPresent( OneToOne.class ) && !property.getAnnotation( OneToOne.class )
                .targetEntity()
                .equals( void.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( OneToMany.class ) && !property.getAnnotation( OneToMany.class )
                .targetEntity()
                .equals( void.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( ManyToOne.class ) && !property.getAnnotation( ManyToOne.class )
                .targetEntity()
                .equals( void.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( ManyToMany.class ) && !property.getAnnotation( ManyToMany.class )
                .targetEntity()
                .equals( void.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( org.hibernate.annotations.Any.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( ManyToAny.class ) ) {
            if ( !property.isCollection() && !property.isArray() ) {
                throw new AnnotationException( "Property '" + StringHelper.qualify( clazz.getName(), property.getName() )
                        + "' annotated '@ManyToAny' is neither a collection nor an array" );
            }
            return true;
        }
        else if ( property.isAnnotationPresent( Basic.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( Type.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( JavaType.class ) ) {
            return true;
        }
        else if ( property.isAnnotationPresent( Target.class ) ) {
            return true;
        }
        return false;
    }

    private static boolean mustBeSkipped(XProperty property) {
        //TODO make those hardcoded tests more portable (through the bytecode provider?)
        return property.isAnnotationPresent( Transient.class )
                || "net.sf.cglib.transform.impl.InterceptFieldCallback".equals( property.getType().getName() );
    }
}