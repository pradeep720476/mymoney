package com.example.mymoney.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.mymoney.enums.Command;
import com.example.mymoney.enums.Month;
import com.example.mymoney.utils.Converter;

public class CommandManager implements Commander {

    public Portfolio portfolio = new PortfolioManager();

    @Override
    public void command(String cmd) {

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

                    if (this.portfolio.getStatment().containsKey(Month.DECEMBER)) {
                        this.portfolio.balance(Month.DECEMBER)
                                .forEach(asset -> System.out.print(asset.getAmount() + " "));
                        System.out.println();
                        ;
                    } else if (this.portfolio.getStatment().containsKey(Month.JUNE)) {
                        this.portfolio.balance(Month.JUNE)
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
