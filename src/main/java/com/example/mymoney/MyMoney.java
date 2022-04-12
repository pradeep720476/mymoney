package com.example.mymoney;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

import com.example.mymoney.manager.CommandManager;
import com.example.mymoney.manager.Commander;

public class MyMoney {

    public static void main(String[] args) {
        String path = args[0];
        BufferedReader bufferedReader;
        Commander money = new CommandManager();
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

}