package com.example.mymoney.enums;

import java.util.stream.Stream;

public enum Command {
    ALLOCATE, SIP, CHANGE, BALANCE, REBALANCE;

    public static Command getCommand(String cmd) {
        return Stream.of(Command.values()).filter(value -> value.name().equals(cmd)).findFirst().get();
    }

}
