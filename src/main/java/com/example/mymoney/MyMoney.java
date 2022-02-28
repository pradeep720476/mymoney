package com.example.mymoney;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.mymoney.enums.Command;
import com.example.mymoney.enums.Month;
import com.example.mymoney.pojo.Portfolio;
import com.example.mymoney.utils.Converter;

public class MyMoney {

    private Portfolio portfolio = new Portfolio();

    public static void main(String[] args) {
        String path = args[0];
        BufferedReader bufferedReader;
        MyMoney money = new MyMoney();
        try {
            bufferedReader = new BufferedReader(
                    new FileReader(new File(path)));

            Stream<String> streams = bufferedReader.lines();
            streams.forEach((cmd) -> {
                money.command(cmd);
            });

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found in given path" + fileNotFoundException.getMessage());
        }

    }

    private void command(String cmd) {

        String[] cmds = cmd.split(" ");
        switch (Command.getCommand(cmds[0])) {
            case ALLOCATE:
                this.portfolio.allocate(
                        Stream.of(cmds).skip(1).map(amount -> new BigDecimal(amount)).collect(Collectors.toList()),
                        Month.JANUARY);

                break;
            case CHANGE:
                List<Float> percentChange = Stream.of(cmds).skip(1).limit(cmds.length - 2)
                        .map(Converter::percentInInt)
                        .collect(Collectors.toList());
                this.portfolio.change(percentChange, Month.asMonth(cmds[cmds.length - 1]));
                break;
            case SIP:
                this.portfolio.sip(
                        Stream.of(cmds).skip(1).map(amount -> new BigDecimal(amount)).collect(Collectors.toList()),
                        Month.FEBRUARY);
                break;
            case BALANCE:
                this.portfolio.balance(Month.asMonth(cmds[cmds.length - 1]))
                        .forEach(asset -> System.out.print(asset.getAmount() + " "));
                System.out.println();
                break;
            case REBALANCE:

                if (this.portfolio.canRebalance()) {

                    if (this.portfolio.getStatment().containsKey(Month.JUNE)) {
                        this.portfolio.rebalance(
                                Month.JUNE);
                        this.portfolio.balance(Month.JUNE)
                                .forEach(asset -> System.out.print(asset.getAmount() + " "));
                        System.out.println();
                        ;
                    } else if (this.portfolio.getStatment().containsKey(Month.DECEMBER)) {
                        this.portfolio.rebalance(
                                Month.DECEMBER);
                        this.portfolio.balance(Month.DECEMBER)
                                .forEach(asset -> System.out.print(asset.getAmount() + " "));
                        System.out.println();
                    }

                } else {
                    System.out.println("CANNOT_REBALANCE");
                }
                break;
            default:
                break;
        }
    }

}