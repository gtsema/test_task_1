package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Price {

    private final String product_code;
    private final int number;
    private final int depart;
    private final Date begin;
    private final Date end;
    private final long value;

    private final int hashCode;

    public Price(String product_code, int number, int depart, Date begin, Date end, long value) {
        this.product_code = product_code;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;

        hashCode = calcHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if(object == this) {
            return true;
        }
        if(object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Price price = (Price) object;

        return Objects.equals(product_code, price.product_code) &&
               number == price.number &&
               depart == price.depart &&
               Objects.equals(begin, price.begin) &&
               Objects.equals(end, price.end) &&
               value == price.value;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private int calcHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (product_code == null ? 0 : product_code.hashCode());
        result = prime * result + number;
        result = prime * result + depart;
        result = prime * result + (begin == null ? 0 : begin.hashCode());
        result = prime * result + (end == null ? 0 : end.hashCode());
        result = prime * result + (int)value;

        return result;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("dd.MM.y HH:mm:ss");

        return new StringBuffer().append("Product Code: ").append(product_code).append('\t')
                                 .append("Number: ").append(number).append('\t')
                                 .append("Depart: ").append(depart).append('\t')
                                 .append("Begin: ").append(format.format(begin)).append('\t')
                                 .append("End: ").append(format.format(end)).append('\t')
                                 .append("value: ").append(value).toString();
    }

    public String getProduct_code() {
        return product_code;
    }

    public int getNumber() {
        return number;
    }

    public int getDepart() {
        return depart;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public long getValue() {
        return value;
    }
}
