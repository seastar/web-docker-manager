package com.github.seastar.wdm.utils.ip;

import com.github.seastar.wdm.io.WdmBytesIO;
import jakarta.servlet.http.HttpServletRequest;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Date: 2022/12/28
 * Author: hchery
 * home: https://github.com/hchery
 */
public abstract class IpTracker {

    private static final byte[] xdb = new WdmBytesIO("ip2region.xdb").read();
    private static final String LOCAL_IP_V4 = "127.0.0.1";
    private static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";
    private static final String[] IP_HEADERS = {"X-Real-IP", "x-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
    private static final Predicate<String> VALID_IP = ip -> isNotBlank(ip) && !equalsIgnoreCase("unknown", ip);

    public static IpValue.Addr getIpAddr(HttpServletRequest request) {
        var ip = request.getRemoteAddr();
        for (String header : IP_HEADERS) {
            var realIp = request.getHeader(header);
            if (VALID_IP.test(realIp)) {
                ip = realIp;
                break;
            }
        }
        var idx = ip.indexOf(',');
        if (idx >= 0) {
            ip = ip.substring(0, idx);
        }
        if (LOCAL_IP_V6.equalsIgnoreCase(ip)) {
            ip = LOCAL_IP_V4;
        }
        return new IpValue.Addr(ip);
    }

    public static IpValue.Region getIpRegion(IpValue.Addr ipAddr) {
        var region = "未知IP归属地";
        if (!ipAddr.hasValue()) {
            return new IpValue.Region(region);
        }
        try (var searcher = new AutoCloseSearcher(xdb)) {
            region = searcher.search(ipAddr.val());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        var spRegion = region.split("\\|");
        region = switch (spRegion[0]) {
            case "中国" -> isBlank(spRegion[3]) ? spRegion[2] : (spRegion[2] + " " + spRegion[3]);
            case "0" -> isBlank(spRegion[3]) ? region : spRegion[3];
            default -> isBlank(spRegion[0]) ? region : spRegion[0];
        };
        return new IpValue.Region(region);
    }

    public static IpValue.Value getIpAddrAndRegion(HttpServletRequest request) {
        var ipAddr = getIpAddr(request);
        var ipRegion = getIpRegion(ipAddr);
        return new IpValue.Value(ipAddr, ipRegion);
    }

    public static IpValue.Addr localIp() {
        try {
            var nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                var ni = nis.nextElement();
                if (!ni.isLoopback() && !ni.isVirtual() && ni.isUp()) {
                    var addr = ni.getInetAddresses();
                    while (addr.hasMoreElements()) {
                        var ip = addr.nextElement();
                        if (ip instanceof Inet4Address) {
                            return new IpValue.Addr(ip.getHostAddress());
                        }
                    }
                }
            }
            throw new UnknownHostException("Cannot get host ip addr.");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}