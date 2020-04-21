package com.example.admin.simplecalculaterapp;

public class Calculater {

    public static double setData(double allprice, double weight, double use_weight){
        return Math.round(allprice*use_weight/weight*10)/10;
    }
}
