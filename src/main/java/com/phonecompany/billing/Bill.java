package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bill implements TelephoneBillCalculator {

    private final double highTariiff = 1.0;
    private final double lowTariiff = 0.5;
    private final double more5minutes = 0.2;

    @Override
    public BigDecimal calculate(String phoneLog) {

        HashMap<String, BigDecimal> phoneResult = new HashMap<>();
        Map<String, Long> phoneFrequency = new HashMap<>();
        //HashSet<String> phoneNumbers = new HashSet<>();
        List<String> phoneNumbersList = new ArrayList<>();

        BigDecimal costOneCall = BigDecimal.valueOf(0);
        BigDecimal costAllCalls = BigDecimal.valueOf(0);

        String[] phoneLines = phoneLog.split("\n");
//        for (String a : phoneLines) {
//            System.out.println(a);
//        }

        for (int i = 0; i < phoneLines.length; i++) {

            String[] onePhoneLine = phoneLines[i].split(",");

//            for (String a : onePhoneLine) {
//                System.out.println(a);
//            }

            String phoneNumber = onePhoneLine[0];
            String dateStartString = onePhoneLine[1];
            String dateFinishString = onePhoneLine[2];

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date dateStart = null;
            Date dateFinish = null;
            try {
                dateStart = dateFormat.parse(dateStartString);
                dateFinish = dateFormat.parse(dateFinishString);
            } catch (Exception ex) {
            }

            int hoursStart;
            int minutesStart;

            Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
            calendar.setTime(dateStart);
            hoursStart = calendar.get(Calendar.HOUR_OF_DAY);
            minutesStart = calendar.get(Calendar.MINUTE);

            int seconds = (int) (dateFinish.getTime() - dateStart.getTime()) / 1000;
            int minutesPhoneLong = seconds / 60 + 1;

            for (int j = 0; j < minutesPhoneLong; j++) {

                if (j < 5) {
                    if ((hoursStart >= 8) && (hoursStart < 16)) {
                        costOneCall = costOneCall.add(new BigDecimal(highTariiff));
                    } else {
                        costOneCall = costOneCall.add(new BigDecimal(lowTariiff));
                    }
                } else {
                    costOneCall = costOneCall.add(new BigDecimal(more5minutes));
                }
                calendar.add(Calendar.SECOND, 60);
                //System.out.println("cost temp: " + costOneCall);
            }

//list for sorting
            phoneNumbersList.add(phoneNumber);
//hashmaps of numbers+cost
            phoneResult.put(phoneNumber, costOneCall);

            phoneFrequency = phoneNumbersList.stream()
                    .collect(Collectors.groupingBy(Function.identity(),
                            Collectors.counting()));


            for (String name : phoneFrequency.keySet()) {
                String key = name.toString();
                Long value = phoneFrequency.get(name);
                //System.out.println(key + " " + value);

            }

            for (String name : phoneResult.keySet()) {
                String key = name.toString();
                BigDecimal value = phoneResult.get(name);
                //System.out.println(key + " " + value);

                costAllCalls = costAllCalls.add(value);
            }
        }

        return costAllCalls;
    }


    public static void main(String[] args) {

        String phoneLog0 = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n";

        String phoneLog = "420776562353,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n" + "4209999999,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "4209999999,18-01-2020 08:59:20,18-01-2020 19:10:00\n";
        Bill bill = new Bill();

        BigDecimal money = bill.calculate(phoneLog);

        System.out.println(money);

    }
}
