package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PriceExecutor {

    public static Price getPrice(String product_code, int number, int depart, String startDate, String endDate, long value) {

        if(product_code == null || product_code.isEmpty()) throw new IllegalArgumentException("Illegal product code.");
        if(startDate == null || startDate.isEmpty()) throw new IllegalArgumentException("Illegal start date.");
        if(endDate == null || endDate.isEmpty()) throw new IllegalArgumentException("Illegal end date.");

        DateFormat format = new SimpleDateFormat("dd.MM.y HH:mm:ss");
        Date begin = null;
        Date end = null;

        try {
            begin = format.parse(startDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal start date.");
        }

        try {
            end = format.parse(endDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal end date.");
        }

        if(begin.after(end)) throw new IllegalArgumentException("Illegal date. Start date more end date.");

        return new Price(product_code, number, depart, begin, end, value);
    }

    public static List<Price> combine(List<Price> oldPrices, List<Price> newPrices) {
        List<Price> copyOldPrices = new ArrayList<>(oldPrices);
        List<Price> addBuffer = new ArrayList<>();
        List<Price> delBuffer = new ArrayList<>();

        for(Price newPrice : newPrices) {
            for(Price oldPrice : copyOldPrices) {
                if(match(newPrice, oldPrice) && overlap(newPrice, oldPrice)) {
                    if(oldPrice.getBegin().before(newPrice.getBegin()) && oldPrice.getEnd().before(newPrice.getEnd())) {
                        Date end = (Date) newPrice.getBegin().clone();
                        end.setTime(end.getTime() - 1000L);
                        addBuffer.add(new Price(oldPrice.getProduct_code(),
                                                oldPrice.getNumber(),
                                                oldPrice.getDepart(),
                                                oldPrice.getBegin(),
                                                end,
                                                oldPrice.getValue()));
                    } else if(oldPrice.getBegin().before(newPrice.getBegin()) && oldPrice.getEnd().after(newPrice.getEnd())) {
                        Date end = (Date) newPrice.getBegin().clone();
                        end.setTime(end.getTime() - 1000L);
                        addBuffer.add(new Price(oldPrice.getProduct_code(),
                                                oldPrice.getNumber(),
                                                oldPrice.getDepart(),
                                                oldPrice.getBegin(),
                                                end,
                                                oldPrice.getValue()));
                        Date start = (Date) newPrice.getEnd().clone();
                        start.setTime(start.getTime() + 1000L);
                        addBuffer.add(new Price(oldPrice.getProduct_code(),
                                                oldPrice.getNumber(),
                                                oldPrice.getDepart(),
                                                start,
                                                oldPrice.getEnd(),
                                                oldPrice.getValue()));
                    } else if(oldPrice.getBegin().after(newPrice.getBegin()) && oldPrice.getEnd().after(newPrice.getEnd())) {
                        Date start = (Date) newPrice.getEnd().clone();
                        start.setTime(start.getTime() + 1000L);
                        addBuffer.add(new Price(oldPrice.getProduct_code(),
                                                oldPrice.getNumber(),
                                                oldPrice.getDepart(),
                                                start,
                                                oldPrice.getEnd(),
                                                oldPrice.getValue()));
                    }
                    delBuffer.add(oldPrice);
                }
            }
            copyOldPrices.add(newPrice);
            copyOldPrices.removeAll(delBuffer);
            delBuffer.clear();
            copyOldPrices.addAll(addBuffer);
            addBuffer.clear();
        }

        boolean join = true;
        while (join) {
            join = false;
            for(int i = 0; i < copyOldPrices.size(); i++) {
                if(join) break;
                for(int j = i + 1; j < copyOldPrices.size(); j++) {
                    Price p1 = copyOldPrices.get(i);
                    Price p2 = copyOldPrices.get(j);
                    if(match(p1, p2) && p1.getValue() == p2.getValue()) {
                        if(p1.getEnd().equals(p2.getBegin())) {
                            join = true;
                            copyOldPrices.remove(p1);
                            copyOldPrices.remove(p2);
                            copyOldPrices.add(new Price(p1.getProduct_code(), p1.getNumber(), p1.getDepart(), p1.getBegin(), p2.getEnd(), p1.getValue()));
                            break;
                        } else if(p2.getEnd().equals(p1.getBegin())) {
                            join = true;
                            copyOldPrices.remove(p1);
                            copyOldPrices.remove(p2);
                            copyOldPrices.add(new Price(p1.getProduct_code(), p1.getNumber(), p1.getDepart(), p2.getBegin(), p1.getEnd(), p1.getValue()));
                            break;
                        }
                    }
                }
            }
        }

        return copyOldPrices;
    }

    private static boolean match(Price p1, Price p2) {
        return p1.getProduct_code().equals(p2.getProduct_code()) &&
               p1.getNumber() == p2.getNumber() &&
               p1.getDepart() == p2.getDepart();
    }

    private static boolean overlap(Price p1, Price p2) {
        return !(p1.getEnd().before(p2.getBegin()) || p2.getEnd().before(p1.getBegin()));
    }
}
