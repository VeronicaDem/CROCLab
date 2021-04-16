package ProcessingServices.DateServices;

import java.util.Comparator;

public class PatternComparator implements Comparator<MyPattern> {
    @Override
    public int compare(MyPattern pattern1, MyPattern pattern2) {
        return pattern1.getOrder() - pattern2.getOrder();
    }
}
