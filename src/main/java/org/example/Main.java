package org.example;

import java.util.function.Function;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int squreByOldMethod(int n){
        return n*n;
    }
    public static void main(String[] args) {
        Function<Integer,Integer> squre=n->n*n;
        System.out.println(squre.apply(5));

        System.out.println(squreByOldMethod(7));


    }
}