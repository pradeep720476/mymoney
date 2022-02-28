package com.example.mymoney.enums;

import java.util.stream.Stream;

public enum Month {
    JANUARY(0), FEBRUARY(1), MARCH(2), APRIL(3), MAY(4), JUNE(5), JULY(6), AUGUST(7), SEPTEMBER(8), OCTOBER(9),
    NOVEMBER(10), DECEMBER(11);

    private int index;

    private Month(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public int getMonth() {
        return this.index;
    }

    public static Month asMonth(String month) {
        return Stream.of(Month.values()).filter(m -> m.name().equals(month)).findFirst().get();
    }

    public static Month previousMonth(Month month) {
        if (month == Month.JANUARY)
            return Month.JANUARY;
        return Month.values()[month.getMonth() - 1];
    }

    public static Month nextMonth(Month month) {
        if (month == Month.JUNE)
            return Month.JUNE;
        else
            return Month.values()[month.getMonth() + 1];
    }
}
