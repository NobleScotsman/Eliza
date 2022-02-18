import java.util.ArrayList;

public class KeywordList {
    protected ArrayList<Keywords> keyList = new ArrayList<Keywords>();

    public KeywordList(){}

    public void add(ArrayList<String> keys, ArrayList<String> recomp) {
        // Adds a new keyword to the keylist with the values of both ArrayLists
        keyList.add(new Keywords(keys, recomp));
    }

    public ArrayList<Keywords> getKeyList() {
        return keyList;
    }
}
