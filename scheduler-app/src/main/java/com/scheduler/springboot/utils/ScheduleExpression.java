package com.scheduler.springboot.utils;

import lombok.Getter;

@Getter
public class ScheduleExpression {

    private String dayOfMonth = "*";
    private String dayOfWeek = "*";
    private String hour = "0";
    private String minute = "0";
    private String month = "*";
    private String second = "0";
    private String year = "*";

    public ScheduleExpression dayOfMonth(int d) {
        dayOfMonth = Integer.toString(d);
        return this;
    }

    public ScheduleExpression dayOfMonth(String d) {
        dayOfMonth = d;
        return this;
    }

    public ScheduleExpression dayOfWeek(int d) {
        dayOfWeek = Integer.toString(d);
        return this;
    }

    public ScheduleExpression dayOfWeek(String d) {
        dayOfWeek = d;
        return this;
    }

    public ScheduleExpression hour(int h) {
        hour = Integer.toString(h);
        return this;
    }

    public ScheduleExpression hour(String h) {
        hour = h;
        return this;
    }

    public ScheduleExpression minute(int m) {
        minute = Integer.toString(m);
        return this;
    }

    public ScheduleExpression minute(String m) {
        minute = m;
        return this;
    }

    public ScheduleExpression month(int m) {
        month = Integer.toString(m);
        return this;
    }

    public ScheduleExpression month(String m) {
        month = m;
        return this;
    }

    public ScheduleExpression second(int s) {
        second = Integer.toString(s);
        return this;
    }

    public ScheduleExpression second(String s) {
        second = s;
        return this;
    }

    public ScheduleExpression year(int y) {
        year = Integer.toString(y);
        return this;
    }

    public ScheduleExpression year(String y) {
        year = y;
        return this;
    }


}
