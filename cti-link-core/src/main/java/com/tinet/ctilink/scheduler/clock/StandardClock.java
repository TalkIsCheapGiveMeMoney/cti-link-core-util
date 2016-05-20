package com.tinet.ctilink.scheduler.clock;

import java.util.Calendar;
import java.util.Timer;

public class StandardClock implements Clock {

    @Override
    public Calendar now() {
        return Calendar.getInstance();
    }
}
