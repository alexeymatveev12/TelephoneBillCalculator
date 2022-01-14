package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill implements TelephoneBillCalculator {

    private final double highTariiff = 5.0;
    private final double lowTariiff = 0.5;
    private final double moreThen5minutes = 0.2;

    @Override
    public BigDecimal calculate(String phoneLog) {

        phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n";

        String[] lines = phoneLog.split("\n");
        System.out.println(lines.toString());


        return null;
    }

    public static void main(String[] args) {
//        String S = "00:01:07,400-234-090\n" +
//                "00:05:01,701-080-080\n" +
//                "00:05:00,400-234-090";
//        Bill bill = new Bill();
//
//        BigDecimal bill2 = bill.calculate(S);
//
//        System.out.println(bill2);



        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n";

        String[] lines = phoneLog.split("\n");
        for (String a : lines) {
            System.out.println(a);
        }

        String[] onePhoneLine = phoneLog.split(",");

        for (String a : onePhoneLine) {
            System.out.println(a);
        }

        String phoneNumber = onePhoneLine[0];
        String dateStartString = onePhoneLine[1];
        String dateFinishString = onePhoneLine[2];
        System.out.println(phoneNumber);


        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dateStart = null;
        Date dateFinish = null;
        try {
            dateStart = dateFormat.parse(dateStartString);
            dateFinish = dateFormat.parse(dateFinishString);
        } catch (Exception ex) {
        }

        int hoursStart = dateStart.getHours();
        int hoursFinish = dateFinish.getHours();


        int seconds = (int) (dateFinish.getTime() - dateStart.getTime()) / 1000;
        System.out.println(Math.toIntExact(seconds));

        long cost = 0;
        int minutes = seconds / 60;
        System.out.println(minutes);
        if ((hoursStart > 8) & (hoursFinish < 16)) {
//            cost = seconds / 60 *

        }
    }

}
