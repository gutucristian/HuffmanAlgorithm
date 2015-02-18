import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Compression class handles essential tasks for any data encoding algorithm.
 * A subclass of Compression benefits from common tasks like reading input from files,
 * building a <Code>dictionary</Code> from input, encoding, decoding, and printing codes.
 */

/*
    File with helper functions

    Programming language: Java
    Version: Java 1.8
    Project language level: 7.0
    Development framework: Intellij IDEA 13.1.5 Community Edition

    Note: if problems appear with "Project language level" check this video on how to
          change the level: https://www.youtube.com/watch?v=6svfLUxK2nA

          Example of such a problem is "java: strings in switch are not supported in -source 1.5"
*/

public class Compression{
    protected ArrayList<String> originalAlphabetOrder = new ArrayList<String>();
    protected HashMap<String, Code> dictionary = new HashMap<String, Code>();

    /**
     * Builds <Code>dictionary</Code> from <Code>alphabet</Code>. First <Code>alphabet</Code> is split by "," and stores result in array.
     * It then loops through this result and splits each element by "=". This results in a new array with the key in index position [0] and
     * the frequency in index position [1]. Knowing the key and the frequency a new element is placed in
     * <Code>dictionary</Code>. A similar process is continued for the entire alphabet.
     *
     * @param alphabet <Code>String</Code> with filtered alphabet. This means that keys are separated from frequencies by an "=" sign.
     *                 Similarly, each key and frequency together are separated ",".
     *                 <p><b>Note:</b> the last key and frequency in the alphabet should not have a ","
     *                 <p><b>Example:</b> a=41,b=32,c=27,d=19,e=4
     */

    public void setupDictionaryFromString(String alphabet){
        alphabet = alphabet.replaceAll(" ", "");
        // Split input by "," signs into an array
        String[] splitAlphabet = alphabet.split(",");

        // Loop through the "splitAlphabet"
        for(int i = 0; i < splitAlphabet.length; i++){
            // Split current element in "splitAlphabet" by the "=" sign
            String[] splitAlphabetElement = splitAlphabet[i].split("="); // The first element in "splitAlphabetElement" is the key and the second is the frequency
            String key = splitAlphabetElement[0];
            long frequency =  Long.parseLong(splitAlphabetElement[1]);
            // Place the key and the frequency into the "dictionary"
            dictionary.put(key, new Code(key, frequency, i, null)); // Note: "i" is the position of the key in the given alphabet and "null" is the "parentCode"
            originalAlphabetOrder.add(splitAlphabetElement[0]); // Save order as "HashMap" places elements with its own hashing algorithm ultimately loosing original order
        }
    }

    /**
     * Builds <Code>dictionary</Code> from a file. The <Code>dictionary</Code> is built according to the file contents. If <Code>isAlphabetRawText == true</Code>
     * the file contains raw text such as a Shakespearean play or a book. This being the case the file is filtered of non-letter symbols and all symbols are converted to lowercase.
     * Additionally every symbol would be counted so each key would have a frequency. On the contrary if <Code>isAlphabetRawText == false</Code> then the file's content's is an alphabet
     * which has every key and frequency separated by a "=" and similarly where each key and frequency together is separated by a ",".
     * <p><b>Example of "alphabet":</b> a=34,b=23,c=9,d=7
     *
     * <p><b>Note:</b>
     * <ul>
     * <li>key = certain symbol
     * <li>frequency  = how many times a symbol is encountered
     * </ul>
     *
     * @param fileName          name of the file
     * @param isAlphabetRawText indicates whether or not the file's contents is raw text
     *
     * <p><b>Precondition:</b> fileName has ".txt" extension. Example: if fileName is "alphabet" pass as "alphabet.txt"
     */

    public void setupDictionaryFromFile(String fileName, boolean isAlphabetRawText){
        dictionary = new HashMap<String, Code>();
        originalAlphabetOrder = new ArrayList<String>();

        if(isAlphabetRawText){
            // Find frequency of each letter by incrementing its frequencies every time its met in the input
            String input = getInputFromFile(fileName);
            input = removeNonLetterSymbols(input, "");
            for(int i = 0; i < input.length(); i++){
                // If dictionary has this key increase its frequency by one otherwise add it to the dictionary
                String key = input.substring(i, i+1);
                if(dictionary.containsKey(key)){
                    dictionary.get(key).frequency++;
                }else{
                    dictionary.put(key, new Code(key, 1, i, null));
                    originalAlphabetOrder.add(key); // Remember original order
                }
            }
        }else{
            String input = removeNonLetterSymbols(getInputFromFile(fileName), "0-9=,") ;
            setupDictionaryFromString(input);
        }
    }

    /**
     * Reads input from a file.
     *
     * @param  fileName the file name
     * @return          all file contents
     *
     * <p><b>Precondition:</b> fileName has ".txt" extension. Example: if fileName is "alphabet" pass as "alphabet.txt"
     * <p><b>Note:</b> returned <Code>String</Code> is not filtered of non-letter symbols
     */

    protected String getInputFromFile(String fileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String result = "";
            String currentLine = "";

            while ((currentLine = reader.readLine()) != null){
                result += currentLine.toLowerCase().replaceAll(" ", "");
            }

            reader.close();
            return result;
        }catch (IOException e){
            System.out.println(e);
        }

        return "";
    }

    /**
     * Removes all non-letter symbols (<Code>additionalCharactersToKeep</Code> are kept) using regex.
     *
     * @param input                      text to be filtered
     * @param additionalCharactersToKeep characters that should not be removed
     * @return                           <Code>input</Code> filtered of all non-letter symbols and made lower case
     */

    public String removeNonLetterSymbols(String input, String additionalCharactersToKeep){
        return input.replaceAll("[^a-zA-Z"  + additionalCharactersToKeep + "]", "").toLowerCase(); // Uses regex to remove unwanted characters
    }

    /**
     * Encodes <Code>input</Code> using key's values from <Code>dictionary</Code> HashMap.
     * Encoding is accomplished by retrieving every character's binary code from <Code>dictionary</Code>
     * and appending it to result using <Code>StringBuilder</Code>.
     *
     * @param input <Code>input</Code> to be encoded
     * @return      <Code>input</Code> encoded with binary codes from <Code>dictionary</Code> HashMap
     */

    public StringBuilder encodeInput(String input){
        StringBuilder encodedInput = new StringBuilder();
        int codeCounter = 0; // Counts amount of codes printed. Used to separate result into lines.
        int index = 0;
        input = removeNonLetterSymbols(input, ""); // Remove unwanted symbols

        // Loops through every character in input
        while(index < input.length()){
            String temp = input.substring(index, index + 1); // Takes character at index
            // Wrap code in lines every 15 characters for readability
            if(codeCounter == 15){
                encodedInput.append("\r\n");
                codeCounter = 0;
            }
            encodedInput.append(dictionary.get(temp).code); // Appends character code to result
            codeCounter++;
            index++;
        }

        return encodedInput;
    }

    /**
     * Decodes <Code>input</Code> using key's values from <Code>dictionary</Code> HashMap.
     * First, in order to decode loop through <Code>dictionary</Code>. While looping get
     * the <Code>substring</Code> of the current key's length from our <Code>input</Code>. If the substring
     * matches the current key's value then add the current key to the result and continue this process.
     * This is guaranteed to work as no two Huffman codes have the same prefix.
     *
     * @param input input to be decoded
     * @return      <Code>input</Code> decoded with binary codes from <Code>dictionary</Code> HashMap
     *
     * @throws Exception if code is not found in <Code>dictionary</Code>
     */

    public StringBuilder decodeInput(String input) throws Exception{
        StringBuilder result = new StringBuilder();
        boolean codeExistsInDictionary = true; // Flag that indicates if code is in dictionary
        int newLineCounter = 0; // Counts amount of words printed. Used to separate result into lines.

        while ((input.length() > 0) && codeExistsInDictionary){
            for(Map.Entry<String, Code> entry: dictionary.entrySet()){
                String entryCode = entry.getValue().code;
                if((input.length() >= entryCode.length()) && (input.substring(0, entryCode.length()).equals(entryCode))){
                    if(newLineCounter == 50){
                        result.append("\r\n");
                        newLineCounter = 0;
                    }
                    result.append(entry.getKey());
                    input = input.substring(entryCode.length());
                    codeExistsInDictionary = true;
                    break;
                }
                // If matching code is not found set "codeExistsInDictionary" flag to false so we avoid an infinite loop
                codeExistsInDictionary = false;
            }
            if(codeExistsInDictionary == false){
                System.out.println("Verify that input is valid as codes do not correspond to dictionary.");
            }
            newLineCounter++;
        }

        return result;
    }

    /**
     * Prints dictionary's keys and codes
     *
     * @throws Exception if <Code>originalAlphabetOrder</Code> is null
     */

    public void printCodes() throws Exception{
        if(originalAlphabetOrder != null){
            for(String entry: originalAlphabetOrder){
                System.out.println("Key: " + entry + " Code: " + dictionary.get(entry).code);
            }
        }else{
            throw new Exception("\"originalAlphabetOrder\" array is null");
        }
    }
}
