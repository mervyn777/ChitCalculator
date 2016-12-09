package com.banjo.chitcalculator;

import android.util.Log;

public class ChitfundUtil {

    public static double getAPR(double rate) {
        return rate * 1200;
    }

    public static double getRate(int originalTenure, int actualTenure) {

        double maturityAmount = 0;

        double rateMin = 0; //0% APR
        double rateMax = 1;  //120% APR
        double rate = 0;

        int count = 0;

        while (Math.abs(maturityAmount - originalTenure) > 0.0001 && count < 25) {
            rate = (rateMax - rateMin) / 2 + rateMin;
            maturityAmount = getMaturityAmount(rate, actualTenure);

            Log.d("TAG", String.format("%.10f %.3f", Math.abs(maturityAmount - originalTenure), rate*1200));
            if (maturityAmount > originalTenure) {
                rateMax = rate;
            } else if (maturityAmount < originalTenure) {
                rateMin = rate;
            }
            count++;
        }

        Log.d("TAG", "count " + count);

        return rate;
    }

    private static double getMaturityAmount(double rate, int actualTenure) {
        return (Math.pow(1 + rate, actualTenure + 1) - 1 - rate) / rate;
    }

    /*
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

     */

    private ChitfundUtil() {

    }
}
