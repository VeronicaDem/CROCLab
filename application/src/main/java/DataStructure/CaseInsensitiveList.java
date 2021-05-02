package DataStructure;

import java.util.ArrayList;

public class CaseInsensitiveList extends ArrayList<String> {
    @Override
    public boolean contains(Object o) {

        String inputString = (String)o;
        for (String string : this){
            if (inputString.equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
}
