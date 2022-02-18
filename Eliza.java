import java.lang.reflect.Array;
import java.util.*;

public class Eliza {
    public static void main(String[] args) {

        String script;
        // Checks if script parameter is passed in and if not runs eliza
        if (args.length == 0) {
            script = "eliza";
            System.out.println("No script given, running eliza");
        } else {
            script = args[0];
            System.out.println("Running " + script);
        }
        // Loads all the elements of the script
        LoadScript loadScript = new LoadScript("./scripts/" + script);

        // Creates dice to allow for responses
        Random dice = new Random();
        System.out.println(loadScript.getStartMessages().get(dice.nextInt(loadScript.startMessages.size())));

        boolean running = true;

        Scanner scan = new Scanner(System.in);
        ArrayList<Keywords> keywords = loadScript.getKeywords().getKeyList();
        int counter = 0;
        String oldInput = "";

        // Loops until a terminating message is input
        while (running) {
            String input = scan.nextLine().toUpperCase();

            // Checks if input is a terminating message is passed
            if (loadScript.getTerminatingMessages().contains(input.toUpperCase())) {
                System.out.println(loadScript.getEndMessages().get(dice.nextInt(loadScript.getEndMessages().size())));
                running = false;
                break;
            }

            // Checks for repeat messages
            if (input.equals(oldInput)) {
                counter++;
                // Checks if message is repeated more than 3 time and prints a conversion starter
                if (counter == 3) {
                    System.out.println(loadScript.getConvoStarters().get(dice.nextInt(loadScript.getConvoStarters().size())));
                    counter = 0;
                } else {
                    counter++;
                    System.out.println(loadScript.getRepeatMessages().get(dice.nextInt(loadScript.getRepeatMessages().size())));
                }
            } else {
                // If not a repeat message attempts to find a keyword
                boolean foundResponse = false;
                String response = "";

                if (!foundResponse) {
                    // Loops through keywords ArrayList and then get all the keywords in that list
                    for (Keywords keyword : keywords) {
                        ArrayList<String> keys = keyword.getKeywords();
                        for (String key : keys) {
                            // "Cleans" the keyword for processing
                            String cleankey = key.replaceAll("#", "");
                            // If keyword is found get the response and passes all to the processResponse method
                            if (input.contains(cleankey)) {
                                response = keyword.getRecompList().get(dice.nextInt(keyword.getRecompList().size()));
                                processResponse(input, response, key, loadScript.getConjugation());
                                foundResponse = true;
                                break;
                            }
                        }
                        // If response is founds breaks
                        if (foundResponse) {
                            break;
                        }
                    }
                    // If no keyword is matched prints default message
                    if (!foundResponse) {
                        System.out.println(loadScript.getDefaultMessages().get(dice.nextInt(loadScript.getDefaultMessages().size())));
                    }
                }
            }
            // Sets oldinput for checking of repeat message
            oldInput = input;
        }
    }

    // Transforms the string using the conjugation from the script
    public static String tranform(String sentence, HashMap<String, String> map) {
        StringBuilder newSentence = new StringBuilder();
        String[] splitSentence = sentence.split(" ");

        for (String words : splitSentence) {
            if (map.containsKey(words.toUpperCase())) {
                newSentence.append(map.get(words.toUpperCase())).append(" ");
            } else {
                newSentence.append(words.toUpperCase()).append(" ");
            }
        }
        return newSentence.toString();
    }

    // Processes the output using the input, response, key and conjugations
    public static void processResponse(String input, String response, String key, HashMap<String, String> conjugation) {
        String tempVal;

        // Checks if the response contains a key symbol and doesn't contain another
        if (response.contains("@") && !response.contains("*")) {
            response = response.replace("@", tranform(key.replaceAll("#","").trim(), conjugation));
            System.out.println(response);
        } else if (key.contains("#") && response.contains("*")) {
            // Checks if key and response contain there respective symbols and inserts the specified key into the response
            if (response.contains("@")) {
                response = response.replace("@", key.replaceAll("#","").trim());
            }
            tempVal = input.substring(input.indexOf(key.replace("#", "").trim()) + key.length() - 1);
            tempVal = tranform(tempVal, conjugation);
            response = response.replace("*", tempVal.trim());
            System.out.println(response);
        } else {
            // Prints the response if none of the conditions are valid
            System.out.println(response);
        }
    }
}
