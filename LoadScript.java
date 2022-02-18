import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class LoadScript {

    protected ArrayList<String> startMessages = new ArrayList<String>();
    protected ArrayList<String> endMessages = new ArrayList<String>();
    protected ArrayList<String> terminatingMessages = new ArrayList<String>();
    protected ArrayList<String> defaultMessages = new ArrayList<String>();
    protected ArrayList<String> convoStarters = new ArrayList<String>();
    protected HashMap<String, String> conjugation = new HashMap<String, String>();
    protected ArrayList<String> repeatMessages = new ArrayList<String>();
    protected KeywordList keys = new KeywordList();

    // Uses constructor to initialise the script
    public LoadScript(String fileName) {
        initialiseScript(fileName);
    }

    public void initialiseScript(String fileName) {
        // Uses try catch to determine if file exists
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));
            String tempLine = reader.readLine();
            // Loops until the file end
            // Checks the first character is one of the key letters and adds to respectice ArrayList
            while (tempLine != null) {
                if (tempLine.startsWith("S")) {
                    startMessages.add(tempLine.substring(1));
                } else if (tempLine.startsWith("T")) {
                    terminatingMessages.add(tempLine.substring(1));
                } else if (tempLine.startsWith("E")) {
                    endMessages.add(tempLine.substring(1));
                } else if (tempLine.startsWith("C")) {
                    String[] temp = tempLine.substring(1).split(" ");
                    conjugation.put(temp[0], temp[1]);
                } else if (tempLine.startsWith("D")) {
                    defaultMessages.add(tempLine.substring(1));
                } else if (tempLine.startsWith("A")) {
                    convoStarters.add(tempLine.substring(1));
                } else if (tempLine.startsWith("P")) {
                    repeatMessages.add(tempLine.substring(1));
                } else if (tempLine.startsWith("K")) {
                    /* Adds the initial line to the currKeys and then loops
                    until the break character "$" is reached
                    If the line starts with a "K" it is added to the currKeys
                    and if it starts with a "R" adds to currRecomp
                    */
                    ArrayList<String> currKeys = new ArrayList<>();
                    ArrayList<String> currRecomp = new ArrayList<>();
                    currKeys.add(tempLine.substring(1));
                    tempLine = reader.readLine();
                    boolean tempBool = true;
                    while (tempBool || tempLine != null) {
                        if (tempLine.startsWith("K")) {
                            currKeys.add(tempLine.substring(1));
                        } else if (tempLine.startsWith("R")) {
                            currRecomp.add(tempLine.substring(1));
                        } else if (tempLine.startsWith("$")) {
                            // Ends the loop and adds currKeys and CurrRecomp to keys
                            tempBool = false;
                            keys.add(currKeys, currRecomp);
                            // Clears the arrays for next keyword
                            currKeys = new ArrayList<>();
                            currRecomp = new ArrayList<>();
                        }
                        tempLine = reader.readLine();
                    }
                }
                tempLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getStartMessages() {
        return this.startMessages;
    }

    public ArrayList<String> getTerminatingMessages() {
        return terminatingMessages;
    }

    public ArrayList<String> getEndMessages() {
        return this.endMessages;
    }

    public HashMap<String, String> getConjugation() {
        return this.conjugation;
    }

    public ArrayList<String> getDefaultMessages() {
        return defaultMessages;
    }

    public ArrayList<String> getConvoStarters() {
        return convoStarters;
    }

    public ArrayList<String> getRepeatMessages() {
        return repeatMessages;
    }

    public KeywordList getKeywords() {
        return keys;
    }
}
