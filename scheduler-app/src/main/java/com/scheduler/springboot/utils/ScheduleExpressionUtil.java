package com.scheduler.springboot.utils;

public class ScheduleExpressionUtil {

    public static ScheduleExpression parseCron(String cronString){

        if(cronString.equals("")) return null;

        final String[] parts = cronString.split(" ");

        if (parts.length < 5) {
                throw new IllegalArgumentException(cronString + " doesn't have 5 or 6 segments as expected");

        } else if (parts.length == 6) { // return cron with seconds segment
            return new ScheduleExpression()
                    .second(parts[0])
                    .minute(parts[1])
                    .hour(parts[2])
                    .dayOfMonth(parts[3])
                    .month(parts[4])
                    .dayOfWeek(parts[5]);
        }

        return new ScheduleExpression()
                .minute(parts[0])
                .hour(parts[1])
                .dayOfMonth(parts[2])
                .month(parts[3])
                .dayOfWeek(parts[4]);

    }
}
