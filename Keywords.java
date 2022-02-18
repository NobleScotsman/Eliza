import java.util.ArrayList;

public class Keywords {
    public ArrayList<String> keywords = new ArrayList<String>();
    protected ArrayList<String> recompList = new ArrayList<String>();

    // Default constructor adds the values to the ArrayLists
    public Keywords(ArrayList<String> keys, ArrayList<String> recomp) {
        this.keywords = keys;
        this.recompList = recomp;
    }

    public ArrayList<String> getKeywords() {
        return this.keywords;
    }

    public ArrayList<String> getRecompList() {
        return recompList;
    }
}
