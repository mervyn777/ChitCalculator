package com.banjo.chitcalculator;

public class ChitfundUtil {

    public static double getMaturityAmount(long installment, double rate, int actualTenure) {
        return installment * ((Math.pow(1 + rate, actualTenure + 1) - 1 - rate) / rate);
    }

    public static double getAPR(double rate) {
        return rate * 1200;
    }

    public static double getRate(long chitAmount, int originalTenure, int actualTenure) {

        long installment = chitAmount / originalTenure;
        long actualAmount = installment * actualTenure;

        long maturityAmount = actualAmount;

        double rateMin = 0.001; //1.2% APR
        double rateMax = 0.1;  //120% APR
        double rate = 0.01;

        while (Math.abs(maturityAmount - chitAmount) > 1) {
            rate = (rateMax - rateMin) / 2 + rateMin;
            maturityAmount = (long) (getMaturityAmount(installment, rate, actualTenure) + 0.5f);
            if (maturityAmount > chitAmount) {
                rateMax = rate;
            } else {
                rateMin = rate;
            }
        }

        return rate;
    }

    private ChitfundUtil() {

    }
}
