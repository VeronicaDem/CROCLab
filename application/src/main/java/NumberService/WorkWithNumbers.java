package NumberService;

public class WorkWithNumbers {
    // <= 10
    public static final String edinitsi[] = {
         "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять"
    };
    // > 10 < 20
    public static final String nadsat[] = {
            "один", "две", "три", "четыр", "пят", "шест", "сем", "восем", "девят"
    };
    // >= 20 <40
    public static final String dsat[] = {
      "два", "три"
    };
    // 40 - исключение
    // >=50 < 90
    public static final String desyat[] = {
            "пять", "шесть", "cемь", "восемь"
    };
    // 90 - исключение
    // 100 - исключение, 200 - исключение
    // >=300 <=400
    public static final String sta[] = {
            "три", "четыре"
    };
    // >=500 <1000
    public static final String sot[] = {
            "пять", "шесть", "семь", "восемь", "девять"
    };
    // 1000 - исключение
    // >=2000 <5000
    public static final String tasyachi[] = {
      "две", "три", "четыре"
    };
    // остальное - тысяч, >=5000 <1e6
    // оканчивающиеся на один
    public static final String others[] = {
            "миллион", "миллиард", "триллион", "квадриллион"
    };
    // оканчивающиеся на надцать, дцат, десят, сто, тысяч, тысячи - ов
    // остальные - а




   /* public static final String sot [] = {
            "сто", "двести", "триста", "четыреста", "пят", "шест", "сем", "восем", "девят"
    };
    public static final String desiat [] = {
            " двадцать"," тридцать"," сорок"," пят"," шест",
            " сем"," восем"," девяносто"
    };
    public static final String edm[] = {
            ""," один"," два"," три"," четыре"," пять"," шесть",
            " семь"," восемь",
            " девять"," десять"," один"," две"," три",
            " четыр"," пят"," шест"," сем"," восем"," девят"
    };
    public static final String edw[] = {
            " одна"," две"
    };
    public static final String nmtr[] = {
            " тысяч"," миллион"," миллиард", " триллион",
            " квадриллион", " квинтиллион", " сектиллион", " септаллион", null
    };
    public static final String nokm[] = {
            "а","ов"
    };
    public static final String nokt[] = {
            "а", "и", ""
    };*/
   /* public static long getSklon(long dd) {
        long n,r;
        r = 2;
        n = dd % 100;
        if (n > 4 && n < 20) return r;
        n = n % 10;
        if (n == 1) r = 0;
        else if (n > 1 && n < 5) r = 1;
        return r;
    }*/
}
