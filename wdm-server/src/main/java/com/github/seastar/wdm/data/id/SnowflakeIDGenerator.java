package com.github.seastar.wdm.data.id;

import com.github.seastar.wdm.exps.ClockException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@Component
@RequiredArgsConstructor
public class SnowflakeIDGenerator implements IDGenerator, InitializingBean {

    private static final long StartTimeStamp = 1640966400000L;
    private static final long MachineIdBits = 5L;
    private static final long ServiceIdBits = 5L;
    private static final long SeqBits = 12L;
    private static final long MachineIdShift = SeqBits;
    private static final long ServiceIdShift = SeqBits + MachineIdBits;
    private static final long TimeStampShift = SeqBits + MachineIdBits + ServiceIdBits;
    private static final long SeqMask = ~(-1L << SeqBits);

    private final SnowflakeProps snowflakeProps;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    @Override
    public synchronized Long next() {
        var ts = timestamp();
        if (ts < this.lastTimestamp) {
            throw new ClockException("Clock moved backwards.  Refusing to generate id for %d milliseconds"
                    .formatted(this.lastTimestamp - ts)
            );
        }
        if (ts == this.lastTimestamp) {
            this.sequence = (this.sequence + 1) & SeqMask;
            if (this.sequence == 0L) {
                ts = nextMillis();
            }
        } else {
            this.sequence = 0L;
        }
        this.lastTimestamp = ts;
        return ((ts - StartTimeStamp) << TimeStampShift)
                | (snowflakeProps.serviceId() << ServiceIdShift)
                | (snowflakeProps.machineId() << MachineIdShift)
                | sequence;
    }

    private long nextMillis() {
        var ts = timestamp();
        while (ts <= lastTimestamp) {
            ts = timestamp();
        }
        return ts;
    }

    private long timestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        snowflakeProps.checkPropsValues();
    }
}