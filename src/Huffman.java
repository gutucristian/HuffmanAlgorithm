import java.util.ArrayList;
import java.util.Map;

/**
 * Huffman is a class that constructs Huffman codes based on any given alphabet or text.
 * Being that the Huffman class is a subclass of <Code>Compression</Code> it allows the re-use of many essential
 * methods for encoding, decoding, reading, and initializing input.
 * <p>
 * Having all codes built a Huffman object retains the ability to calculate the ratio
 * between equal-length encoding and Huffman code encoding. Further, using known entropy
 * equations, a Huffman object allows to calculate the total alphabet entropy and find
 * deviations from any given "optimal" alphabet.
 *
 * <p><b>Let n = size of alphabet</b>
 * <br>
 * <b>Let p = probability of a given symbol</b>
 * <ul>
 * <li> Entropy for equal-length alphabet = Math.ceil( log(n) / log(2) )
 * <li> Entropy for individual symbol = -p * ( log(p) / log(2) )
 * </ul>
 *
 * <p><b>Building codes:</b>
 * <p><b>Step 1:</b> instantiate Huffman class
 * <p><Code>Huffman huffmanDictionary = new Huffman();</Code>
 * <p><b>Step 2:</b> setup algorithm <Code>dictionary</Code> from file
 * <p><Code>huffmanDictionary.setupDictionaryFromFile("fileName.txt", false);</Code>
 * <p><b>Note:</b> in <Code>huffmanDictionary.setupDictionaryFromFile(String fileName, boolean isAlphabetRawText)</Code>
 * <Code>fileName</Code> is the name of the file plus a ".txt" extension. Also the boolean <Code>isAlphabetRawText</Code> should be
 * set <Code>true</Code> only when the file is actual text. If the file is an alphabet like "a=20, b=13, c=4" then alphabet
 * is not raw text, so <Code>isAlphabetRawText</Code> should be <Code>false</Code>.
 * <p><b>Step 3:</b> call <Code>setupHuffmanCodes()</Code> to build the codes
 * <p><Code>huffmanDictionary.setupHuffmanCodes();</Code>
 *
 * <p><b>Printing codes:</b>
 * <p><Code>huffmanDictionary.printCodes();</Code>
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

public class Huffman extends Compression{

    /**
     * Once <Code>dictionary</Code> from <Code>Compression</Code> class is initialized from
     * input this method is called to build the Huffman codes. Specifically, an {@code ArrayList<Code>} containing
     * all symbols and frequencies is initialized from which a Huffman tree is built.
     * Having this Huffman tree recursion is  used to derive the codes for every symbol.
     * <p>
     * <b>Precondition</b>: dictionary is initialized from a valid file using <Code>setupDictionaryFromFile(String fileName, boolean isAlphabetRawText)</Code>
     */

    public void setupHuffmanCodes(){
        if(dictionary == null){
            System.out.println("Dictionary is null");
        }else{
            ArrayList<Code> nodeList = buildNodeList();
            nodeList = buildHuffmanTree(nodeList);
            buildHuffmanCodes(nodeList);
        }
    }

    /**
     * Uses initialized <Code>dictionary</Code> from <Code>Compression</Code> class to copy all <Code>Code</Code>'s
     * keys and frequencies and places them in {@code ArrayList<Code>}. This <Code>ArrayList</Code> is later used to
     * build a Huffman tree by combining nodes correspondingly and forming new parent nodes.
     *
     * @return {@code ArrayList<Code>} with all <Code>dictionary</Code>'s keys and values
     */

    private ArrayList<Code> buildNodeList(){
        ArrayList<Code> nodeList = new ArrayList<Code>();

        for(Map.Entry<String, Code> entry: dictionary.entrySet()){
            nodeList.add(new Code(entry.getKey(), entry.getValue().frequency, entry.getValue().leftPosition, null));
        }

        return nodeList;
    }

    /**
     * Builds Huffman codes by merging nodeList values. This is done by looping through every element in the <Code>dictionary</Code> while
     * checking keys that match with other keys from <Code>nodeList</Code>. If a match is found the entry's Huffman code is
     * built recursively by <Code>mergeNodeValues()</Code>. Checking for a match is necessary as the <Code>nodeList</Code> contains
     * parent nodes in addition to the original nodes. Because a parent node is a combination of two parentless nodes
     * and does not provide access to it's child nodes values we must filter them out. Passing a parent node to <Code>mergeNodeValues()</Code>
     * will cut off all child nodes and ultimately produce incorrect codes.
     *
     * @param nodeList  {@code ArrayList<Code>} containing <Code>Code</Code>s with their symbol, frequency, and parent <Code>Code</Code>
     */

    private void buildHuffmanCodes(ArrayList<Code> nodeList){
        // loop through every key in dictionary
        for (Map.Entry<String, Code> entry: dictionary.entrySet()){
            for(int i = 0; i < nodeList.size(); i++){
                // "mergeNodeValues" recursively appends the code of this node's parents to form a Huffman code
                if(entry.getKey().equals(nodeList.get(i).key)){
                    entry.getValue().code = mergeNodeValues(nodeList.get(i));
                    break;
                }
            }
        }
    }

    /**
     * This method recursively appends each node's parent code to a <Code>String</Code> to form a Huffman code.
     *
     * @param  node  a base <Code>Code</Code> which is not a parent of any other <Code>Code</Code>
     * @return       <Code>String</Code> with all parent's codes appended to the original <Code>code</Code>'s value that was passed
     */

    private String mergeNodeValues(Code node){
        String huffmanCode = node.code;

        // base case
        if(node.parentCode == null){
            return "";
        }else{
            // calls "mergeNodeValues" with the node's "parentCode" and appends it to the current node's code (huffmanCode).
            return mergeNodeValues(node.parentCode) + huffmanCode;
        }
    }

    /**
     * Builds a Huffman tree from an <Code>nodeList</Code>. Precisely, the Huffman tree is built through the process of locating two symbols with the smallest frequencies. Once these
     * two symbols are found they are combined into a "parent node." This "parent node" contains the sum of the two symbol's frequencies
     * and their concatenated symbols. Note that when concatenating the symbols the parent node puts the symbol earliest in the given alphabet before the other symbol. Additionally, if their frequencies are not equal,
     * the symbol with the smaller frequency receives a value of "0" and the symbol with the larger frequency receives a value of "1". If the symbols frequencies are equal
     * then the symbol whose first character came earlier in the original alphabet gets the "0" and the symbol whose first character came later in the alphabet gets a "1".
     * This process continues recursively until only one parentless node is left and the Huffman tree is completed.
     * <p>
     * <b>Note: a symbol and its frequency is a <Code>Code</Code>.</b>
     *
     * @param  nodeList  {@code ArrayList<Code>} with prebuilt codes from the <Code>buildNodeList()</Code> method
     * @return           an {@code ArrayList<Code>} that has all the original <Code>Code</Code>s from
     *                   the <Code>dictionary</Code> plus the new parent codes
     */

    private ArrayList<Code> buildHuffmanTree(ArrayList<Code> nodeList){
        Code firstMinNode = getMinNode(nodeList, null);
        Code secondMinNode = getMinNode(nodeList, firstMinNode);

        if((firstMinNode != null) && (secondMinNode != null)){
            Code newParent = new Code(null, firstMinNode.frequency + secondMinNode.frequency, 0, null);

            // If the frequencies are the same the node with the leftmost symbol gets a '0' and the other gets a '1' ->
            // -> otherwise "firstMinNode" gets a "0" as is has the smallest frequency
            if(firstMinNode.frequency == secondMinNode.frequency){
                // Note: first element in split() array is the left most symbol
                // Gets first symbol from firstMinNode
                String firstCharacter = firstMinNode.key.split("-")[0]; // split by "-" as that is how symbols are separated
                // Gets first symbol from secondMinNode
                String secondCharacter = secondMinNode.key.split("-")[0];

                if(dictionary.get(firstCharacter).leftPosition < dictionary.get(secondCharacter).leftPosition){
                    firstMinNode.code = "0";
                    secondMinNode.code = "1";
                    newParent.key = firstMinNode.key + "-" + secondMinNode.key;
                }else{
                    firstMinNode.code = "1";
                    secondMinNode.code = "0";
                    newParent.key = secondMinNode.key + "-" + firstMinNode.key;
                }
            }else{
                firstMinNode.code = "0";
                secondMinNode.code = "1";
                // Adding "-" helps retrieve symbols (especially binomials) when comparing "leftPosition"'s with equal nodes value
                newParent.key = firstMinNode.key + "-" + secondMinNode.key;
            }

            // Update nodes with the new parent node
            firstMinNode.parentCode = newParent;
            secondMinNode.parentCode = newParent;
            nodeList.add(newParent);

            // recursively call "buildNodesTree" until we do not have two smallest nodes
            buildHuffmanTree(nodeList);
        }

        return nodeList;
    }

    // NOTE: HELPER METHODS BELLOW

    /**
     * Finds the smallest <Code>Code</Code> in <Code>nodeList</Code>. Note that if <Code>firstMinNode == null</Code> then it is simply ignored and the smallest <Code>Code</Code> is returned
     * else <Code>firstMinNode</Code> is overlooked and the second smallest node is returned
     *
     * @param  nodeList        {@code ArrayList<Code>} with prebuilt codes from the <Code>buildNodeList()</Code> method
     * @param  firstMinNode    <Code>Code</Code> to overlook. This allows to find second smallest <Code>Code</Code>
     * @return                 minNode
     */

    private Code getMinNode(ArrayList<Code> nodeList, Code firstMinNode){
        Code minNode = null;
        for(Code node: nodeList){
            // If a node has a "parentNode" that means its been previously combined with another node so therefore we avoid it.
            if(node.parentCode == null){
                if(firstMinNode == null){
                    if((minNode == null) || (node.frequency <= minNode.frequency)){
                        minNode = node;
                    }
                }else{
                    if(firstMinNode != node){
                        if(minNode == null){
                            minNode = node;
                        }
                        if((node.frequency <= minNode.frequency)){
                            minNode = node;
                        }
                    }
                }
            }
        }

        return minNode;
    }


    /**
     * This method calculates ratio of space needed for encoding a given amount of letters
     * using fixed-length encoding to the space expected to be taken by Huffman code encoding.
     * The ratio for any amount of letters is found by calculating the factor and using it to increase
     * the lengths proportionally.
     * <p>
     * <b>Factor: totalTextSize / actual code size</b>
     *
     * @param totalTextSize  amount of letters to be compared in ratio. If totalTextSize is -1 then calculate ratio as is
     * @return               ratio of space needed for encoding a given amount of letters using fixed-length encoding to to the space expected to be taken by Huffman code encoding
     * @throws Exception     if <Code>dictionary</Code> is <Code>null</Code>
     */

    public double calculateRatio(int totalTextSize) throws Exception{

        if(dictionary == null){
            throw new Exception("dictionary is null");
        }else{
            // Note: "finalCodeSize" could equal 3000 the ratio will still be the same
            double actualCodeSize = 0;

            // Loop through dictionary and multiply the frequency by the length to find the total length ->
            // -> of that specific letter throughout the dictionary. Then add all the lengths to obtain the "actualCodeSize".
            for(Map.Entry<String, Code> entry: dictionary.entrySet()){
                actualCodeSize += entry.getValue().frequency * entry.getKey().length();
            }

            // Factor which helps us adjust actualCodeSize
            double factor = totalTextSize == -1 ? 1 : totalTextSize / actualCodeSize;

            // Using entropy equation -p(log(p)/log(2)) and applying Math.ceil() we find how much ->
            // -> binary numbers it take to encode a fixed length code
            int fixedLengthCodeSize = (int)Math.ceil(Math.log(dictionary.size()) / Math.log(2));

            double totalLengthWithHuffmanCodes = 0;
            double totalLengthWithFixedLengthCodes = 0;

            // Loop trough dictionary and find the total amount of code needed for equal-length encoding and Huffman code encoding
            for(Map.Entry<String, Code> entry: dictionary.entrySet()){
                // NOTE: unlike fixed length codes Huffman codes vary so when we loop through the dictionary ->
                // we multiply the key frequency by the current code's length
                totalLengthWithHuffmanCodes += entry.getValue().frequency * factor * entry.getValue().code.length();
                // Here since fixed-length codes are all of equal length we just multiply the frequencies by the "fixedLengthCodeSize"
                totalLengthWithFixedLengthCodes += entry.getValue().frequency * factor * fixedLengthCodeSize;
            }

            return (totalLengthWithFixedLengthCodes / totalLengthWithHuffmanCodes);
        }
    }

    /**
     * Finds deviation from the optimal encoding by calculating percent change between the total <Code>comparison</Code>
     * entropy and the total entropy of the object calling this method.
     * <p>
     * <b>Note: the optimal encoding is the total entropy of the object calling this method</b>
     *
     * @param comparison  object who's entropy is to be compared to
     * @return            percent change between this object's and the <Code>comparison</Code>'s total entropy
     * @throws Exception  if <Code>dictionary</Code> is <Code>null</Code>
     */

    private double calculateDeviationFromOptimalEncoding(Huffman comparison) throws Exception{
        double callerObjectEntropy = calculateTotalAlphabetEntropy(false); // total alphabet entropy of the object that calls this method (in problem 11 that would be the object with the english alphabet)
        double comparisonEntropy = comparison.calculateTotalAlphabetEntropy(false);
        // "deviation" is the percent change from the "comparisonEntropy" to the "callerObjectEntropy" ->
        // "callerObjectEntropy" is considered to be the optimal entropy (For example "callerObjectEntropy" = ~4.18 if its the english alphabet)
        double deviation = (((comparisonEntropy / callerObjectEntropy) * 100) - 100);
        return deviation;
    }

    /**
     * Prints out the deviation from the optimal encoding in a readable form with an explanation as to what it means.
     *
     * @param comparison object who's entropy is to be compared to
     * @param name       name of file from which <Code>comparison</Code>'s alphabet comes from
     * @throws Exception if <Code>dictionary</Code> is <Code>null</Code>
     */

    public void displayDeviationFromOptimalEncoding(Huffman comparison, String name) throws Exception{
        double deviationFromOptimalEncoding = calculateDeviationFromOptimalEncoding(comparison);
        if(deviationFromOptimalEncoding > 0){
            System.out.println("If the text \"" + name + "\" were to be encoded with its own Huffman codes it would require about " + String.format("%4f", deviationFromOptimalEncoding) +
                    "% more binary numbers (per character) than it would if it were encoded with the \"optimal\" English Huffman codes.");
        }else{
            System.out.println("If the text \"" + name + "\" were to be encoded with its own Huffman codes it would require about " + String.format("%4f", deviationFromOptimalEncoding).replace("-", "") +
                    "% less binary numbers (per character) than it would if it were encoded with the \"optimal\" English Huffman codes.");
        }
    }

    /**
     * Finds total alphabet entropy by summing up the entropy of each individual symbol throughout the alphabet.
     * <p>
     * <b>Note: entropy for individual symbol in a given alphabet is found by -p * ( log(p) / log(2) ) when p is probability for that symbol</b>
     *
     * @param printComputations if true computations are printed else they are not
     * @return                  the total entropy of this alphabet
     * @throws Exception        if <Code>dictionary</Code> is <Code>null</Code>
     */

    public double calculateTotalAlphabetEntropy(boolean printComputations) throws Exception{
        double sumOfFrequencies = calculateSumOfFrequencies(printComputations);

        double totalAlphabetEntropy = 0;
        // Loop through dictionary
        for(Map.Entry<String, Code> entry: dictionary.entrySet()){

            double probability = entry.getValue().frequency / sumOfFrequencies; // Probability of current character (based on frequencies)
            double characterEntropy = -(probability) * (Math.log(probability) / Math.log(2)); // Equation to find entropy of individual frequency
            totalAlphabetEntropy += characterEntropy; // By adding the entropy for each character in the dictionary we get the total alphabet entropy

            if(printComputations){
                System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                System.out.println("Key: " + entry.getKey() + " \t\tFrequency: " + entry.getValue().frequency);
                System.out.println("Probability = " + entry.getValue().frequency + " / " + sumOfFrequencies + " = " + probability);
                System.out.println("Character Entropy = " + (-probability) + " * (Math.log(" + probability + ") / Math.log(2))" + " = " + characterEntropy);
            }
        }

        if(printComputations){
            System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
            System.out.println("Total Entropy: " + totalAlphabetEntropy);
        }

        return totalAlphabetEntropy;
    }

    /**
     * Finds total sum of alphabet's symbol frequencies.
     *
     * @param printComputations if true computations are printed else they are not
     * @return                  sum of alphabet's symbol frequencies
     * @throws Exception        if <Code>dictionary</Code> is <Code>null</Code>
     */

    public double calculateSumOfFrequencies(boolean printComputations) throws Exception{
        double sumOfFrequencies = 0;
        if(dictionary == null){
            throw new Exception("dictionary is null");
        }else{
            // Loop through dictionary and add up all frequencies
            for (Map.Entry<String, Code> entry: dictionary.entrySet()){
                sumOfFrequencies += entry.getValue().frequency;
            }
        }

        if(printComputations) {
            System.out.println("Total alphabet frequency: " + sumOfFrequencies);
        }

        return sumOfFrequencies;
    }

    /**
     * Finds the best binomial(s) by testing how each would affect the total alphabet entropy.
     *
     * @param alphabetFile      name of while in which alphabet is located
     * @param binomialFileName  name of while in which binomial is located
     * @param findBestBinomial  if true finds single best binomial else finds all best binomials that together reduce the total entropy
     * @param printComputations if true computations are printed else they are not
     * @return                  best binomial(s) found that help reduce entropy of alphabet
     * @throws Exception        if <Code>dictionary</Code> is <Code>null</Code>
     * <p>
     * <b>Precondition</b>: <Code>alphabetFile</Code> and <Code>binomialFileName</Code> have ".txt" extension. Example: if file name is "alphabet" pass as "alphabet.txt"
     */

    public ArrayList<String> findBestBinomials(String alphabetFile, String binomialFileName, boolean findBestBinomial, boolean printComputations) throws Exception{
        ArrayList<String> bestBinomials = new ArrayList<String>();

        // If "findBestBinomial" was previously true then "dictionary" was altered at exit of method. Because of this rebuild Huffman codes to have an original version of the dictionary
        this.setupDictionaryFromFile(alphabetFile, false);
        this.setupHuffmanCodes();

        double minEntropy = 0;
        long bestBinomialFrequency = 0;

        Huffman binomialFile = new Huffman();
        binomialFile.setupDictionaryFromFile(binomialFileName, false);

        double originalHuffmanDictionaryEntropy = calculateTotalAlphabetEntropy(false);

        // Loop through binomial dictionary
        for (Map.Entry<String, Code> entry: binomialFile.dictionary.entrySet()){

            String firstLetterOfBinomial = entry.getKey().substring(0, 1);
            String secondLetterOfBinomial = entry.getKey().substring(1);

            // Account for binomial by subtracting its frequency from the monogram's frequencies that make it up
            long firstLetterStartValue = dictionary.get(firstLetterOfBinomial).frequency;
            dictionary.get(firstLetterOfBinomial).frequency = dictionary.get(firstLetterOfBinomial).frequency - entry.getValue().frequency;
            long firstLetterEndValue = dictionary.get(firstLetterOfBinomial).frequency;

            long secondLetterStartValue = dictionary.get(secondLetterOfBinomial).frequency;
            dictionary.get(secondLetterOfBinomial).frequency = dictionary.get(secondLetterOfBinomial).frequency - entry.getValue().frequency;
            long secondLetterEndValue = dictionary.get(secondLetterOfBinomial).frequency;

            // Put binomial in dictionary
            dictionary.put(entry.getKey(), new Code(entry.getKey(), entry.getValue().frequency, dictionary.size(), null));
            setupHuffmanCodes();

            double newAlphabetEntropy = calculateTotalAlphabetEntropy(false);

            if(printComputations){
                System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                System.out.println("Key: [" + entry.getKey() + "]\tFrequency: " + entry.getValue().frequency);
                System.out.println("  First letter: [" + firstLetterOfBinomial + "] (frequency: " + firstLetterStartValue + ")");
                System.out.println("     Subtract frequency of binomial from first letter: " + firstLetterStartValue + " - " + entry.getValue().frequency + " = " + firstLetterEndValue);
                System.out.println("  Second letter: [" + secondLetterOfBinomial + "] (frequency: " + secondLetterStartValue+ ")");
                System.out.println("     Subtract frequency of binomial from second letter: " + secondLetterStartValue + " - " + entry.getValue().frequency + " = " + secondLetterEndValue);
                System.out.println("\n  - Add binomial to dictionary . . .");
                System.out.println("  - Compute total alphabet entropy . . . \n");
                System.out.println("  Original English alphabet entropy: " + originalHuffmanDictionaryEntropy + "\n  " + "Alphabet entropy with binomial: " + newAlphabetEntropy);
            }

            if(findBestBinomial){

                if(minEntropy == 0){
                    minEntropy = newAlphabetEntropy;
                    bestBinomials.add(entry.getKey());
                    bestBinomialFrequency = entry.getValue().frequency;
                }

                if(newAlphabetEntropy < originalHuffmanDictionaryEntropy && minEntropy > newAlphabetEntropy){
                    bestBinomials.clear();
                    bestBinomials.add(entry.getKey());
                    bestBinomialFrequency = entry.getValue().frequency;
                }

                // reset dictionary to initial state
                dictionary.get(secondLetterOfBinomial).frequency = secondLetterStartValue;
                dictionary.get(firstLetterOfBinomial).frequency = firstLetterStartValue;
                dictionary.remove(entry.getKey());

            }else {

                if(newAlphabetEntropy < originalHuffmanDictionaryEntropy){
                    bestBinomials.add(entry.getKey());

                    if(printComputations){
                        System.out.println("  New alphabet entropy is smaller so add \"" + entry.getKey() + "\" to dictionary");
                    }

                }else{

                    if(printComputations){
                        System.out.println("\n  Original alphabet has a smaller entropy so remove \"" + entry.getKey() + "\" from dictionary and reset letter frequencies:");
                        System.out.println("\t" + "Frequency of second letter (" + secondLetterOfBinomial + ") = " + secondLetterEndValue + " + " + entry.getValue().frequency + " = " + secondLetterStartValue);
                        System.out.println("\t" + "Frequency of first letter (" + firstLetterOfBinomial  + ") = " + firstLetterEndValue + " + " + entry.getValue().frequency + " = " + firstLetterStartValue);
                    }

                    // Reset frequencies for letters and remove binomial
                    dictionary.get(secondLetterOfBinomial).frequency = secondLetterStartValue;
                    dictionary.get(firstLetterOfBinomial).frequency = firstLetterStartValue;
                    dictionary.remove(entry.getKey());
                }

            }

        }

        if(findBestBinomial){
            String firstLetterOfBinomial = bestBinomials.get(0).substring(0, 1);
            String secondLetterOfBinomial = bestBinomials.get(0).substring(1);

            dictionary.get(firstLetterOfBinomial).frequency = dictionary.get(firstLetterOfBinomial).frequency - bestBinomialFrequency;
            dictionary.get(secondLetterOfBinomial).frequency = dictionary.get(secondLetterOfBinomial).frequency - bestBinomialFrequency;
            dictionary.put(bestBinomials.get(0), new Code(bestBinomials.get(0), bestBinomialFrequency, dictionary.size(), null));
        }

        return bestBinomials;
    }
}
