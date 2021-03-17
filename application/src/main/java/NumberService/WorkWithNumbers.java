package NumberService;

public class WorkWithNumbers {
        // <= 10
        public static final String edinitsi[] = {
                "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять"
        };
        // > 10 < 20
        public static final String nadsat[] = {
                "один", "две", "три", "четыр", "пять", "шест", "сем", "восемь", "девять"
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

}
