package ProcessingServices.DateServices;

import java.util.regex.Matcher;

public interface Action {
    abstract String exec(Matcher s);
}
