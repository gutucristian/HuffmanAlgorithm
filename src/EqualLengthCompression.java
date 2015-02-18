/**
 * Subclass of Compression class which builds an alphabet with equal-length codes.
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

public class EqualLengthCompression extends Compression{

    /**
     * Builds equal-length codes for an alphabet. The amount of binary numbers per symbol is
     * determined by the entropy equation of an equal-length alphabet.
     * <p><b>Let n = size of alphabet</b>
     * <p><b>Entropy for equal-length alphabet = Math.ceil( log(n) / log(2) )</b>
     *
     * @param alphabet alphabet to be built into dictionary
     */

    public void setupDictionaryFromString(String alphabet){
        int index = 0;
        String[] splitAlphabet = alphabet.split(",");
        int codeLength = (int)Math.ceil(Math.log(splitAlphabet.length)/Math.log(2));

        while (index < splitAlphabet.length){
            String key = splitAlphabet[index];
            String code = padString(Integer.toBinaryString(index), codeLength);

            dictionary.put(key, new Code(key, 0, 0, null)); // Put key in dictionary
            dictionary.get(key).code = code; // Puts key code in dictionary
            originalAlphabetOrder.add(key);
            index++;
        }
    }

    /**
     * Builds equal-length codes for an alphabet. After reading file's contents builds codes with
     * <Code>buildEqualLengthCodesFromString()</Code>
     *
     * @param fileName the file name
     *
     *<p><b>Precondition:</b> fileName has ".txt" extension. Example: if fileName is "alphabet" pass as "alphabet.txt"
     */

    public void setupDictionaryFromFile(String fileName){
        setupDictionaryFromString(getInputFromFile(fileName));
    }

    /**
     * Pads <Code>text</Code> with 0's to account for place holder 0's in equal-length encoding
     *
     * @param text      text to be padded with 0's
     * @param padAmount how much to pad
     * @return          text padded with 0's
     */

    private String padString(String text, int padAmount){
        // Pads text by adding "0"s to front
        return String.format("%" + padAmount + "s", text).replaceAll(" ", "0");
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