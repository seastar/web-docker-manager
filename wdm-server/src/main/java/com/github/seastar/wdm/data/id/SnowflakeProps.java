package com.github.seastar.wdm.data.id;

import com.github.seastar.wdm.exps.PropsException;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@ConfigurationProperties(prefix = "wdm.snowflake")
public record SnowflakeProps(
        long serviceId,
        long machineId
) {

    private static final long MaxServiceId = 31L;
    private static final long MaxMachineId = 31L;

    public void checkPropsValues() throws PropsException {
        if (serviceId > MaxServiceId || serviceId < 0) {
            throw new PropsException("Snowflake abnormal service id: " + serviceId);
        }
        if (machineId > MaxMachineId || machineId < 0) {
            throw new PropsException("Snowflake abnormal machine id: " + machineId);
        }
    }
}