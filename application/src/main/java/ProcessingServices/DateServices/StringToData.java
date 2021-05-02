package ProcessingServices.DateServices;

import NumberService.NumberHandler;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToData {
    // TODO сделать обработку двузначных годов
    static final public String year = "([1-9][0-9]{3})";
    static final public String month = "(0[1-9]|1[012])";
    static final public String day = "(0[1-9]|1[0-9]|2[0-9]|3[01]|[1-9])";
    static final public String monthWords = "(январ[яьюе]|" +
            "январ[её]м|" +
            "феврал[яьюе]|" +
            "февр[её]м|" +
            "март[ауе]|" +
            "март|" +
            "мартом|" +
            "апрел[еьяю]|" +
            "апрелем|" +
            "ма[йяюе]|" +
            "маем|" +
            "июн[ьяюе]|" +
            "июнем|" +
            "июл[ьюяе]|" +
            "июлем|" +
            "август|" +
            "августом|" +
            "август[ауе]|" +
            "сентябр[ьяюе]|" +
            "сентябр[её]м|" +
            "октябр[яьюе]|" +
            "октябр[её]м|" +
            "ноябр[ьяюе]|" +
            "ноябр[её]м|" +
            "декабр[ьяюе]|" +
            "декабр[её]м)";
    static final public String separators = "\\/|\\.|-";
    static final public String space = "?\\s*";
    static final public String postfix = "(год[аыуе]|годов|лет|годам|годами|годах|год|г\\.|г)*";
    static final public String exactPostfix = "(год[аыуе]|годов|лет|годам|годами|годах|год|г\\.|г)";
    static final private PriorityQueue<MyPattern> patterns = new PriorityQueue<MyPattern>(new PatternComparator());
    public static String s(String number) {
        if(number.endsWith("один ")) {
            int first = number.lastIndexOf("один ");
            int second = first + 4;
            number = number.substring(0, first) + " первого" + number.substring(second);
        }
        else if (number.endsWith("два")) {
            int first = number.lastIndexOf("два");
            int second = first + 3;
            number = number.substring(0, first) + " второго" + number.substring(second);
        }
        else if(number.endsWith("три ")) {
            int first = number.lastIndexOf("три ");
            int second = first + 3;
            number = number.substring(0, first) + " третьего" + number.substring(second);
        }
        else if(number.endsWith("восемь ")) {
            int first = number.lastIndexOf("восемь ");
            int second = first + 7;
            number = number.substring(0, first) + "восьмого" + number.substring(second);
        }
        else if(number.endsWith("семь ")) {
            int first = number.lastIndexOf("семь ");
            int second = first + 5;
            number = number.substring(0, first) + " седьмого " + number.substring(second);
        }
        else {
            int first = number.lastIndexOf("ый");
            if(first != -1) {
                int second = first + 2;
                number = number.substring(0, first) + "ого" + number.substring(second);
            }
            first = number.lastIndexOf("ь");
            if(first != -1) {
                int second  = first + 1;
                number = number.substring(0, first) + "ого" + number.substring(second);
            }
        }
        return number;
    }
    public static String in(long number) {
        String res = NumberHandler.numberToSymbol(number,number);

        if(res.endsWith("один ")) {
            int first = res.lastIndexOf("один ");
            int second = first + 4;
            res = res.substring(0, first) + " первом" + res.substring(second);
        }
        else if (res.endsWith("два")) {
            int first = res.lastIndexOf("два");
            int second = first + 3;
            res = res.substring(0, first) + " втором" + res.substring(second);
        }
        else if(res.endsWith("три ")) {
            int first = res.lastIndexOf("три ");
            int second = first + 3;
            res = res.substring(0, first) + " третьем" + res.substring(second);
        }
        else if(res.endsWith("восемь ")) {
            int first = res.lastIndexOf("восемь ");
            int second = first + 7;
            res = res.substring(0, first) + "восьмом" + res.substring(second);
        }
        else if(res.endsWith("семь ")) {
            int first = res.lastIndexOf("семь ");
            int second = first + 5;
            res = res.substring(0, first) + " седьмом" + res.substring(second);
        }
        else {
            int first = res.lastIndexOf("ый");
            if (first != -1) {
                int second = first + 2;
                res = res.substring(0, first) + "ом" + res.substring(second);
            }
            first = res.lastIndexOf("ь");
            if (first != -1) {
                int second = first + 1;
                res = res.substring(0, first) + "ом" + res.substring(second);
            }
        }
        return res;
    }
    public static String getMonth(long month) {
        switch((int) month) {
            case 1:
                return "январь";
            case 2:
                return "февраль";
            case 3:
                return "март";
            case 4:
                return "апрель";
            case 5:
                return "май";
            case 6:
                return "июнь";
            case 7:
                return "июль";
            case  8:
                return "август";
            case 9:
                return "сентябрь";
            case 10:
                return "октябрь";
            case 11:
                return "ноябрь";
            case 12:
                return "декабрь";
        }
        return "";
    }

    public static String po(String number) {
        if(number.split(" ").length >= 2) {
            if(number.endsWith("один ")) {
                return s(number);
            }
            else if(number.endsWith("восемнадцать ")) {
                return s(number);
            }
        }
        if(number.endsWith("один ")) {
            int first = number.lastIndexOf("один ");
            int second = first + 4;
            number = number.substring(0, first) + "первое" + number.substring(second);
        }
        else if (number.endsWith("два")) {
            int first = number.lastIndexOf("два");
            int second = first + 3;
            number = number.substring(0, first) + " второе" + number.substring(second);
        }
        else if(number.endsWith("три ")) {
            int first = number.lastIndexOf("три ");
            int second = first + 3;
            number = number.substring(0, first) + " третье" + number.substring(second);
        }
        else if(number.endsWith("восемь ")) {
            int first = number.lastIndexOf("восемь ");
            int second = first + 7;
            number = number.substring(0, first) + "восьмое" + number.substring(second);
        }
        else if(number.endsWith("семь ")) {
            int first = number.lastIndexOf("семь ");
            int second = first + 4;
            number = number.substring(0, first) + "седьмое" + number.substring(second);
        }
        else if(number.endsWith("девять ")) {
            int first = number.lastIndexOf("девять ");
            int second = first + 6;
            number = number.substring(0, first) + "девятому" + number.substring(second);
        }
        else {
            int first = number.lastIndexOf("ый");
            if (first != -1) {
                int second = first + 2;
                number = number.substring(0, first) + "ое" + number.substring(second);
            }
            first = number.lastIndexOf("ь");
            if (first != -1) {
                int second = first + 1;
                number = number.substring(0, first) + "ое" + number.substring(second);
            }
        }
        return number;
    }
    public static String ot(long day1, long month1, long year1) {
        String yearString1 = s(NumberHandler.numberToSymbol(year1, year1));
        String monthString1 = s_date(getMonth(month1));
        String dayString1 = s(NumberHandler.numberToSymbol(day1,day1));

        return " " + dayString1 + " " + monthString1 + " " + yearString1 + " года ";
    }
    public static String ot(long day1, long month1) {
        String monthString1 = s_date(getMonth(month1));
        String dayString1 = s(NumberHandler.numberToSymbol(day1,day1));
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String ot(long day1, String month1, long year1) {
        String yearString1 = s(NumberHandler.numberToSymbol(year1, year1));
        String monthString1 = month1;
        String dayString1 = s(NumberHandler.numberToSymbol(day1,day1));
        return " " + dayString1 + monthString1 + " " + yearString1 + "года ";
    }
    public static String ot(long day1, String month1) {
        String monthString1 = month1;
        String dayString1 = s(NumberHandler.numberToSymbol(day1,day1));
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String s_date(String month) {
        if(month.equals("март")) {
            return "марта";
        }
        else if(month.equals("май")){
            return "мая";
        }
        else if(month.equals("август")){
            return "августа";
        }
        else {
            return month.replace("ь", "я");
        }
    }
    public static String s_string(long day1, String month1, long year1) {
        String yearString1 = s(NumberHandler.numberToSymbol(year1, year1));
        String monthString1 = month1;
        String dayString1 = s(NumberHandler.numberToSymbol(day1, day1));
        return " " + dayString1 + " " + monthString1 + " " + yearString1 + " года ";
    }
    public static String po_string(long day1, long month1, long year1) {
        String yearString1 = NumberHandler.numberToSymbol(year1, year1);
        String monthString1 = s_date(getMonth(month1));
        String dayString1 = NumberHandler.numberToSymbol(day1, day1);

        dayString1 = po(dayString1);
        yearString1 = po(yearString1);
        return " " + dayString1 + " " + monthString1 + " " + yearString1+ "года";
    }
    public static String s_string(long day1, long month1, long year1) {
        String yearString1 = s(NumberHandler.numberToSymbol(year1, year1));
        String monthString1 = s_date((getMonth(month1)));
        String dayString1 = s(NumberHandler.numberToSymbol(day1, day1));
        return " " + dayString1 + " " + monthString1 + " " + yearString1 + "года ";
    }
    public static String s_string(long day1, long month1) {
        String monthString1 = s_date(getMonth(month1));
        String dayString1 = s(NumberHandler.numberToSymbol(day1, day1));
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String s_string(long day1, String month1) {
        String monthString1 = month1;
        String dayString1 = s(NumberHandler.numberToSymbol(day1, day1));
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String po_string(long day1, String month1, long year1) {
        String yearString1 = NumberHandler.numberToSymbol(year1, year1);
        String monthString1 = month1;
        String dayString1 = NumberHandler.numberToSymbol(day1, day1);

        dayString1 = po(dayString1);
        yearString1 = po(yearString1);
        return " " + dayString1 + " " + monthString1 + yearString1+ "года";
    }
    public static String po_string(long day1, String month1) {
        String dayString1 = po(NumberHandler.numberToSymbol(day1, day1));
        String monthString1 = month1;
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String po_string(long day1, long month1) {
        String dayString1 = po(NumberHandler.numberToSymbol(day1, day1));
        String monthString1 = s_date(NumberHandler.numberToSymbol(month1, month1));
        return " " + dayString1 + " " + monthString1 + " ";
    }
    public static String classic(String number) {
        if(number.endsWith("два ")) {
            int first = number.lastIndexOf("два ");
            int second = first + 3;
            number = number.substring(0, first) + "второе" + number.substring(second);
        }
        else if(number.endsWith("девять ")) {
            int first = number.lastIndexOf("девять ");
            int second = first + 6;
            number = number.substring(0, first) + "девятое" + number.substring(second);
        }
        else {
            number = po(number);
        }
        return number;
    }
    public static String classic_year(String year) {
        if(year.endsWith("один ")) {
            int first = year.lastIndexOf("один ");
            int second = first + 4;
            year = year.substring(0, first) + "первый" + year.substring(second);
        }
        else if(year.endsWith("два ")) {
            int first = year.lastIndexOf("два ");
            int second = first + 3;
            year = year.substring(0, first) + "второй" + year.substring(second);
        }
        else if(year.endsWith("три ")) {
            int first = year.lastIndexOf("три ");
            int second = first + 3;
            year = year.substring(0, first) + "третий" + year.substring(second);
        }
        else if(year.endsWith("четыре ")) {
            int first = year.lastIndexOf("четыре ");
            int second = first + 6;
            year = year.substring(0, first) + "четвертый" + year.substring(second);
        }
        else if(year.endsWith("семь ")) {
            int first = year.lastIndexOf("семь ");
            int second = first + 4;
            year = year.substring(0, first) + "седьмой" + year.substring(second);
        }
        else {
            int first = year.lastIndexOf("ть");
            if (first != -1) {
                int second = first + 2;
                year = year.substring(0, first) + "тый" + year.substring(second);
            }
        }
        return year;
    }
    public static String classic_string(long day1, long month1, long year1) {

        String dayString1 = classic(NumberHandler.numberToSymbol(day1,day1));
        String monthString1 = s_date(getMonth(month1));
        String yearString1 = classic_year(NumberHandler.numberToSymbol(year1,year1));

        return dayString1 + " " + monthString1 + " " + yearString1 + " год ";
    }
    public static String classic_string(long day1, long month1) {

        String dayString1 = classic(NumberHandler.numberToSymbol(day1,day1));
        String monthString1 = s_date(getMonth(month1));


        return dayString1 + " " + monthString1 + " ";
    }

    public static String classic_string(long day1, String month1, long year1) {
        String dayString1 = classic(NumberHandler.numberToSymbol(day1,day1));

        String monthString1 = month1;
        String yearString1 = classic_year(NumberHandler.numberToSymbol(year1,year1));


        return dayString1 + " " + monthString1 + " " + yearString1 + " год ";
    }
    public static String classic_string(long day1, String month1) {

        String dayString1 = classic(NumberHandler.numberToSymbol(day1,day1));
        String monthString1 = s_date(month1);


        return dayString1 + " " + monthString1 + " ";
    }
    public static void generatePatternsQueue() {
        int order = 1;
        // самый высокий приоритет
        patterns.add(new MyPattern(" - " + day + "[" + separators + "]" + month, order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(1));
            long month1 = Long.parseLong(matcher.group(2));



            return "по" + po_string(day1, month1);
        }));
        // с-по
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + year + "[" + separators + "]" + space + month +  "[" + separators + "]" + space + day,
                order++, (matcher)-> {
            long year1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + year + "[" + separators + "]" + space + monthWords + "[" + separators + "]"
                + space + day,
                order++, (matcher)-> {
            long year1 = Long.parseLong(matcher.group(2));
            String month1 = matcher.group(3);
            long day1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле)  " + day + "[" + separators + "]" + month +
                "[" + separators + "]" + year + "[" + separators + "]" + exactPostfix,
                order++, (matcher)-> {

            long day1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле)  " + day + "[" + separators + "]" + month +
                "[" + separators + "]" + year,
                order++, (matcher)-> {

            long day1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day+ "[" + separators + "]" + space +
                monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix,
                order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(2));
            String month1 = matcher.group(3);
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );

        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day+ "[" + separators + "]" + space +
                monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix,
                order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(2));
            String month1 = matcher.group(3);
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );

        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day+ "[" + separators + "]" + space +
                monthWords + "[" + separators + "]" + space + year,
                order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(2));
            String month1 = matcher.group(3);
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );

        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + year + "[" + separators + "]" + month + "[" +separators + "]" + day, order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long day1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);
                }
        ));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year + "[" + separators + "]" + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);

                }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);

                }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + year + "[" + separators + "|" + space + "]" + monthWords +
                "[" + separators + "|" + space + "]" + day,
                order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long day1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + space + monthWords+ "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);
                }
        ));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + space + monthWords+ "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + ot(day1,
                            month1,
                            year1);
                }
        ));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + month + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(2));
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++, (matcher)-> {
            String month1 = matcher.group(2);
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(2));
            long year1 = Long.parseLong(matcher.group(4));
            long day1 = Long.parseLong(matcher.group(3));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(2));
            long year1 = Long.parseLong(matcher.group(4));
            long day1 = Long.parseLong(matcher.group(3));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле)  " + monthWords + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++, (matcher)-> {
            String month1 = matcher.group(2);
            long year1 = Long.parseLong(matcher.group(4));
            long day1 = Long.parseLong(matcher.group(3));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле)  " + monthWords + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++, (matcher)-> {
            String month1 = matcher.group(2);
            long year1 = Long.parseLong(matcher.group(4));
            long day1 = Long.parseLong(matcher.group(3));
            String prefix = matcher.group(1);
            return prefix + ot(
                    day1,
                    month1,
                    year1
            );
        }));
       /*
       patterns.add(new MyPattern("с " + year + "[" + separators + "]" + month + "[" +separators + "]"
               + day,
               order++, (matcher)-> {
           long year1 = Long.parseLong(matcher.group(1));
           long month1 = Long.parseLong(matcher.group(2));
           long day1 = Long.parseLong(matcher.group(3));
           return s_string(day1,
                   month1,
                   year1
           );
       }));*/
        patterns.add(new MyPattern("(к|по) " + year + "[" + separators + "]" +  month + "[" + separators +
                "]" + day, order++, (matcher)-> {
            long year1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + po_string(day1,
                    month1,
                    year1
            );
        }));
       /*
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + month + "[" + separators + "]" + year
                                        ,
                                       order++, (matcher)-> {
                                          long day1 = Long.parseLong(matcher.group(1));
                                          long month1 = Long.parseLong(matcher.group(2));
                                          long year1 = Long.parseLong(matcher.group(3));
                                           return s_string(day1,
                                                      month1,
                                                      year1
                                                   );
                                   }));
        */
        patterns.add(new MyPattern(
                "(к|по) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year + "[" + separators + "]" + exactPostfix,
                order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + po_string(day1,
                    month1,
                    year1);
        }));
        patterns.add(new MyPattern(
                "(к|по) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year,
                order++, (matcher)-> {
            long day1 = Long.parseLong(matcher.group(2));
            long month1 = Long.parseLong(matcher.group(3));
            long year1 = Long.parseLong(matcher.group(4));
            String prefix = matcher.group(1);
            return prefix + po_string(day1,
                    month1,
                    year1);
        }));
       /*
       patterns.add(new MyPattern("с " + year + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + day,
                                        order++, (matcher)-> {
                                 long day1 = Long.parseLong(matcher.group(1));
                                 String month1 = matcher.group(2);
                                 long year1 = Long.parseLong(matcher.group(3));
                                 return s_string(day1,
                                            month1,
                                            year1
                                            );
                                         }));
        */
        patterns.add(new MyPattern(
                "(к|по) " + year + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space  + day, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
       /*
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + space + monthWords
               + "[" + separators + "]" + space + year,
                                       order++, (matcher)-> {
                                      long day1 = Long.parseLong(matcher.group(1));
                                      String month1 = matcher.group(2);
                                      long year1 = Long.parseLong(matcher.group(3));
                                      return s_string(day1,
                                                  month1,
                                                  year1);
       }));
        */
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + space + monthWords +
                "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + space + monthWords +
                "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
       /*
       patterns.add(new MyPattern("с " + year + "[" + separators + "]" + month + "[" + separators + "]" + day ,
               order++,
                                      (matcher)-> {
                                       long year1 = Long.parseLong(matcher.group(1));
                                       long month1 = Long.parseLong(matcher.group(2));
                                       long day1 = Long.parseLong(matcher.group(3));
                                       return s_string(day1,
                                                  month1,
                                                  year1
                                                );
                                       }));
        */
        patterns.add(new MyPattern("(к|по) " + year + "[" + separators + "]" + month + "[" + separators + "]" + day,
                order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long day1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
       /*
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + month + "[" + separators + "]" +
                year ,
               order++,
               (matcher)-> {
                     long day1 = Long.parseLong(matcher.group(1));
                     long month1 = Long.parseLong(matcher.group(2));
                     long year1 = Long.parseLong(matcher.group(3));
                   return s_string(day1,
                           month1,
                           year1
                           );
               }));
        */
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year + "[" + separators + "]" + exactPostfix,
                order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + month + "[" + separators + "]" + year,
                order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
       /*
       patterns.add(new MyPattern("с " + year +  "[" + separators + "]" + space + monthWords+ "[" + separators + "]" + space + day ,
                                       order++, (matcher)-> {
                                          long year1 = Long.parseLong(matcher.group(1));
                                          String month1 = matcher.group(2);
                                          long day1 = Long.parseLong(matcher.group(3));
                                         return s_string(day1,
                                                    month1,
                                                     year1
                                                     );
       }));
        */
        patterns.add(new MyPattern("(к|по) " + year + "[" + separators + "]" + space +
                monthWords + "[" + separators + "]" + space + day, order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long day1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
       /*
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + space + monthWords
               + "[" + separators + "]" + space + year, order++,
               (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));
                   return s_string(day1,
                           month1,
                           year1
                           );
               }));
        */
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1,
                            year1
                    );
                }));
       /*
       patterns.add(new MyPattern("c " + month + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(3));
                   long year1 = Long.parseLong(matcher.group(2));
                   long month1 = Long.parseLong(matcher.group(1));
                   return s_string(day1,
                                month1,
                              year1);
               }));
        */
        patterns.add(new MyPattern("(к|по) " + month + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(4));
                    long year1 = Long.parseLong(matcher.group(3));
                    long month1 = Long.parseLong(matcher.group(2));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
       /*
       patterns.add(new MyPattern("c " + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(3));
                   String  month1 = matcher.group(1);
                   long year1 = Long.parseLong(matcher.group(2));
                   return s_string(day1,
                           month1,
                           year1);
               }));
        */
        patterns.add(new MyPattern("(к|по) " + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(4));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
        /*
       patterns.add(new MyPattern("c " + month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(2));
                   long year1 = Long.parseLong(matcher.group(3));
                   long month1 = Long.parseLong(matcher.group(1));
                   return s_string(day1,
                           month1,
                           year1);
               }));
         */
        patterns.add(new MyPattern("(к|по) " + month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    long month1 = Long.parseLong(matcher.group(2));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern("(к|по) " + month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(3));
                    long year1 = Long.parseLong(matcher.group(4));
                    long month1 = Long.parseLong(matcher.group(2));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
       /*
       patterns.add(new MyPattern("c " + monthWords + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(2));
                   String  month1 = matcher.group(1);
                   long year1 = Long.parseLong(matcher.group(3));
                   return s_string(day1,
                           month1,
                           year1);
               }));
        */
        patterns.add(new MyPattern("(к|по) " + monthWords + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(3));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern("(к|по) " + monthWords + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(3));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(4));
                    String prefix = matcher.group(1);
                    return prefix + po_string(day1,
                            month1,
                            year1);
                }));

        // classic
        patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators +"]" + year  + "[" + separators +"]" + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators +"]" + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators +"]" + space + year + "[" + separators +"]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators +"]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(year + "[" + separators + "]" + month +
                "[" + separators + "]" + day, order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long day1 = Long.parseLong(matcher.group(3));
                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(year + exactPostfix + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + day, order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long day1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(year + "[" + separators + "]" + month + "[" + separators + "]" + day, order++,
                (matcher)-> {

                    long year1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long day1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators + "]" + year + "[" + separators + "]" + exactPostfix,
                order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators + "]" + year,
                order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(year + exactPostfix + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + day, order++,
                (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long day1 = Long.parseLong(matcher.group(3));

                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));
                    return classic_string(day1,
                            month1,
                            year1);
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords + "[" + separators + "]" + space + year, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));
                    return classic_string(day1,
                            month1,
                            year1);
                }));

        patterns.add(new MyPattern(month + "[" + separators + "]" + space + year + "[" + separators + "]" + space + day, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(1));
            long year1 = Long.parseLong(matcher.group(2));
            long day1 = Long.parseLong(matcher.group(3));
            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));

        patterns.add(new MyPattern(monthWords + "[" + separators + "]" + space + year
                + "[" + separators + "]" + space + day, order++, (matcher)-> {
            String month1 = matcher.group(1);
            long year1 = Long.parseLong(matcher.group(2));
            long day1 = Long.parseLong(matcher.group(3));

            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern(month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year + "[" + separators + "]" + space + exactPostfix, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(1));
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(2));
            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern(month + "[" + separators + "]" + space + day + "[" + separators + "]" + space + year, order++, (matcher)-> {
            long month1 = Long.parseLong(matcher.group(1));
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(2));
            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));

        patterns.add(new MyPattern(monthWords + "[" + separators + "]" + space + day
                + "[" + separators + "]" + space + year  + "[" + separators + "]" + space +  exactPostfix, order++, (matcher)-> {
            String month1 = matcher.group(1);
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(2));

            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));
        patterns.add(new MyPattern(monthWords + "[" + separators + "]" + space + day
                + "[" + separators + "]" + space + year, order++, (matcher)-> {
            String month1 = matcher.group(1);
            long year1 = Long.parseLong(matcher.group(3));
            long day1 = Long.parseLong(matcher.group(2));

            return classic_string(
                    day1,
                    month1,
                    year1
            );
        }));



        // с-по
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + month, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    String prefix = matcher.group(1);
                    return prefix + ot(
                            day1,
                            month1
                    );
                }));
        patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + space + monthWords, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    String prefix = matcher.group(1);
                    return prefix + ot(
                            day1,
                            month1
                    );
                }));
       /*
       patterns.add(new MyPattern("(от|до|из|без|у|для|около|с|вокруг|после|возле) " + day + "[" + separators + "]" + month, order++, (matcher)-> {
                 long day1 = Long.parseLong(matcher.group(1));
                 long month1 = Long.parseLong(matcher.group(2));
                 return s_string(day1,
                             month1
                             );
       }));*/
        patterns.add(new MyPattern(
                "(к|по) " + day + "[" + separators + "]" + month, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    long month1 = Long.parseLong(matcher.group(3));
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1
                    );
                }));
       /*patterns.add(new MyPattern("с " + day + "[" + separators + "]" + space +
               monthWords, order++,
               (matcher)-> {
                     long day1 = Long.parseLong(matcher.group(1));
                     String month1 = matcher.group(2);
                   return s_string(day1,
                           month1
                          );
               }));*/
        patterns.add(new MyPattern("(к|по) " + day + "[" + separators + "]" + space + monthWords, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(2));
                    String month1 = matcher.group(3);
                    String prefix = matcher.group(1);

                    return prefix + po_string(day1,
                            month1
                    );
                }));
        // от


        patterns.add(new MyPattern(day + "[" +separators + "]" + month, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));

                    return classic_string(
                            day1,
                            month1
                    );
                }));
        patterns.add(new MyPattern(day + "[" + separators + "]" + space + monthWords, order++,
                (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    return classic_string(
                            day1,
                            month1
                    );
                }));
        patterns.add(new MyPattern("в " + year + "\\s*" + exactPostfix, order++, (matcher)-> {
            String year1 = in(Long.parseLong(matcher.group(1)));
            String postfix = matcher.group(2);
            return "в " + year1 + " " + postfix;
        }));
        patterns.add(new MyPattern("во " + year + "\\s*" + exactPostfix, order++, (matcher)-> {
            String year1 = in(Long.parseLong(matcher.group(1)));
            String postfix = matcher.group(2);
            return "во " + year1 + " " + postfix;

        }));
    }
    public static String isPatterned(String string) {
        //generatePatternsQueue();
        Iterator itr = patterns.iterator();

        String res = "";
        String before = "";
        String unProcessed = string;
        while (itr.hasNext()) {
            MyPattern myPattern = (MyPattern) itr.next();
            Pattern pattern = Pattern.compile("\\b" + myPattern.getPattern());
            Matcher matcher;
            while ((matcher = pattern.matcher(unProcessed)).find()) {
                int end = matcher.start() - 1;
                if (end >= 0) {
                    before += unProcessed.substring(0, end);
                    before = isPatterned(before);
                    res += before + " " + myPattern.action.exec(matcher) + " ";
                    before = "";

                } else {
                    res += myPattern.action.exec(matcher) + " ";
                }
                if (unProcessed.length() < matcher.end() + 1) unProcessed = "";
                else unProcessed = unProcessed.substring(matcher.end() + 1);
            }
        }
        if(res.endsWith(unProcessed + " ")) {
            return res;
        }

        res = res + unProcessed;

        /*
        Pattern pattern = Pattern.compile("(.*)" + exactPostfix + "\\s+" + exactPostfix + "(.*)");
        Matcher matcher = pattern.matcher(res);
        while(matcher.find()) {
            res = matcher.group(1) + matcher.group(2) + matcher.group(4);
            matcher = pattern.matcher(res);
        }
         */
        return res.replaceAll("\\s+", " ");
    }
}