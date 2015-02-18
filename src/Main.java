import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

// NOTE: read "README" text file within "README" folder

/**
    All problem's objects and outputs are created here.
 */

/*
    Problem numbers in file: 2,3,4,5,7,8,9,10,11,13,14,15

    Programming language: Java
    Version: Java 1.8
    Project language level: 7.0
    Development framework: Intellij IDEA 13.1.5 Community Edition

    Note: if problems appear with "Project language level" check this video on how to
          change the level: https://www.youtube.com/watch?v=6svfLUxK2nA

          Example of such a problem is "java: strings in switch are not supported in -source 1.5"
 */

public class Main {
    public static void main(String[] args) throws Exception{
        Huffman huffmanDictionary = null;
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            System.out.println("- - - - - - - - - - - - - - - -");
            System.out.println("Console Menu:");
            System.out.println("\tTo run problem Nr.2 type \t2");
            System.out.println("\tTo run problem Nr.3 type \t3");
            System.out.println("\tTo run problem Nr.4 type \t4");
            System.out.println("\tTo run problem Nr.5 type \t5");
            System.out.println("\tTo run problem Nr.7 type \t7");
            System.out.println("\tTo run problem Nr.8 type \t8");
            System.out.println("\tTo run problem Nr.9 type \t9");
            System.out.println("\tTo run problem Nr.10 type \t10");
            System.out.println("\tTo run problem Nr.11 type \t11");
            System.out.println("\tTo run problem Nr.13 type \t13");
            System.out.println("\tTo run problem Nr.14 type \t14");
            System.out.println("\tTo run problem Nr.15 type \t15");
            System.out.println("- - - - - - - - - - - - - - - -");

            input = reader.readLine();

            if(input == null) {
                break;
            }

            // Each case is a different problem identified by the number in the case.
            // For instance case "2" is problem 2, case "3" is problem 3, and so on
            switch (input){
                case "2":
                    EqualLengthCompression problem2 = new EqualLengthCompression();
                    problem2.setupDictionaryFromFile("problem2Alphabet.txt");

                    System.out.println("Equal-length codes:\n\r");
                    problem2.printCodes();

                    String textToEncode = problem2.getInputFromFile("problem2InputA.txt");
                    System.out.println("\n\rText to encode:\n\r" + problem2.removeNonLetterSymbols(textToEncode, ""));
                    System.out.println("\n\rResult: " + problem2.encodeInput(textToEncode));

                    textToEncode = problem2.getInputFromFile("problem2InputB.txt");
                    System.out.println("\n\rText to encode:\n\r" + problem2.removeNonLetterSymbols(textToEncode, ""));
                    System.out.println("\n\rResult: " + problem2.encodeInput(textToEncode));
                    break;
                case "3":
                    EqualLengthCompression problem3 = new EqualLengthCompression();
                    problem3.setupDictionaryFromFile("problem2Alphabet.txt");

                    System.out.println("Decoded input: " + problem3.decodeInput(problem3.getInputFromFile("problem3Input.txt")));
                    break;
                case "4":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem4Alphabet.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    System.out.println("\"ace\" encoded: " + huffmanDictionary.encodeInput("ace"));
                    System.out.println("\"add\" encoded: " + huffmanDictionary.encodeInput("add"));
                    System.out.println("\"101011\" decoded: " + huffmanDictionary.decodeInput("101011"));
                    System.out.println("\"0010101\" decoded: " + huffmanDictionary.decodeInput("0010101"));
                    break;
                case "5":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem4Alphabet.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    double totalLengthWithHuffmanCodes = 0;
                    for (Map.Entry<String, Code> entry: huffmanDictionary.dictionary.entrySet()){
                        totalLengthWithHuffmanCodes += entry.getValue().code.length() * entry.getValue().frequency;
                    }

                    System.out.println("We need "  + (huffmanDictionary.calculateSumOfFrequencies(false) * 3) + " 0's and 1's with equal-length encoding.");
                    System.out.println("We need "  + totalLengthWithHuffmanCodes + " 0's and 1's with Huffman code encoding.");
                    System.out.println("The ratio between equal-length encoding and Huffman encoding is " + huffmanDictionary.calculateRatio(100));
                    break;
                case "7":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem7AlphabetA.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    System.out.println("\nGenerated codes from file \"problem7AlphabetA.txt\":");
                    huffmanDictionary.printCodes();

                    huffmanDictionary.setupDictionaryFromFile("problem7AlphabetB.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    System.out.println("\nGenerated codes from file \"problem7AlphabetB.txt\":");
                    huffmanDictionary.printCodes();
                    break;
                case "8":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem7AlphabetA.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    System.out.println("Generated codes from file \"problem7AlphabetA.txt\":");
                    huffmanDictionary.printCodes();

                    System.out.println("\nWords encoded with these Huffman codes:");
                    System.out.println("\"cab\": " + huffmanDictionary.encodeInput("cab"));
                    System.out.println("\"bad\": " + huffmanDictionary.encodeInput("bad"));
                    System.out.println("\"caged\": " + huffmanDictionary.encodeInput("caged"));
                    System.out.println("\nWords decoded with these Huffman codes:");
                    System.out.println("\"111001000\": " + huffmanDictionary.decodeInput("111001000"));
                    System.out.println("\"11000110\": " + huffmanDictionary.decodeInput("11000110"));
                    break;
                case "9":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem4Alphabet.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    System.out.println("Input file \"problem4Alphabet.txt\"");
                    System.out.println("Ratio using fixed-length codes to Huffman codes: " + huffmanDictionary.calculateRatio(1000));
                    break;
                case "10":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem10Alphabet.txt", false);
                    huffmanDictionary.setupHuffmanCodes();

                    // "getInputFromFile" and "removeNonLetterSymbols"
                    String problem10TextInput = huffmanDictionary.removeNonLetterSymbols(huffmanDictionary.getInputFromFile("problem10TextInput.txt"), "");
                    System.out.println("\"problem10TextInput.txt\" encoded with Huffman codes:\n\n" + huffmanDictionary.encodeInput(problem10TextInput));

                    // Test data to see that we get same decoded text as the one we encoded. If you un-comment you will see that decoding will result in the given input.
                    //System.out.println("\n" + huffmanDictionary.decodeInput(huffmanDictionary.getInputFromFile("test.txt")));

                    String problem10BinaryInput = huffmanDictionary.getInputFromFile("problem10BinaryInput.txt");
                    System.out.println("\n\"problem10BinaryInput.txt\" decoded with the same Huffman codes:\n\n" + huffmanDictionary.decodeInput(problem10BinaryInput));
                    break;
                case "11":
                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem11Alphabet.txt", false);
                    huffmanDictionary.setupHuffmanCodes();
                    System.out.println("NOTE: check \"problem11Explanations\" in \"explanations\" folder for additional explanations and the exact source of the texts.");

                    System.out.println("\nOptimal entropy (English alphabet): " + huffmanDictionary.calculateTotalAlphabetEntropy(false) + "\n");

                    Huffman problem11InputA = new Huffman();
                    problem11InputA.setupDictionaryFromFile("problem11InputA.txt", true);
                    problem11InputA.setupHuffmanCodes();
                    System.out.println("Total entropy of \"problem11InputA.txt\": " + problem11InputA.calculateTotalAlphabetEntropy(false));
                    huffmanDictionary.displayDeviationFromOptimalEncoding(problem11InputA, "problem11InputA.txt");
                    System.out.println("Source URL: http://www.fa-kuan.muc.de/LUCEAFA.HTML#eng" + "\n");

                    Huffman problem11InputB = new Huffman();
                    problem11InputB.setupDictionaryFromFile("problem11InputB.txt", true);
                    problem11InputB.setupHuffmanCodes();
                    System.out.println("Total entropy of \"problem11InputB.txt\": " + problem11InputB.calculateTotalAlphabetEntropy(false));
                    huffmanDictionary.displayDeviationFromOptimalEncoding(problem11InputB, "problem11InputB.txt");
                    System.out.println("Source URL: http://shakespeare.mit.edu/measure/full.html" + "\n");

                    Huffman problem11InputC = new Huffman();
                    problem11InputC.setupDictionaryFromFile("problem11InputC.txt", true);
                    problem11InputC.setupHuffmanCodes();
                    System.out.println("Total entropy of \"problem11InputC.txt\": " + problem11InputC.calculateTotalAlphabetEntropy(false));
                    huffmanDictionary.displayDeviationFromOptimalEncoding(problem11InputC, "problem11InputC.txt");
                    System.out.println("Source URL: https://github.com/libgdx/libgdx/wiki/A-simple-game");
                    break;
                case "13":
                    System.out.println("NOTE: check \"problem13Explanations\" in \"explanations\" folder for explanations.\n");

                    Huffman problem13InputA = new Huffman();
                    problem13InputA.setupDictionaryFromFile("problem13InputA.txt", false);
                    problem13InputA.setupHuffmanCodes();
                    System.out.println("If one letter has 50% frequency and the other seven are equally distributed" +
                            "\nthen the ratio of equal-length to Huffman code length is " + problem13InputA.calculateRatio(-1) + "\n");

                    Huffman problem13InputB = new Huffman();
                    problem13InputB.setupDictionaryFromFile("problem13InputB.txt", false);
                    problem13InputB.setupHuffmanCodes();
                    System.out.println("If four letters have 20% frequency each and the remaining four have 5%" +
                            "\nfrequency each then the ratio of equal-length to Huffman code length is " + problem13InputB.calculateRatio(-1) + "\n");

                    Huffman problem13InputC = new Huffman();
                    problem13InputC.setupDictionaryFromFile("problem13InputC.txt", false);
                    problem13InputC.setupHuffmanCodes();
                    System.out.println("If the frequencies are, in percentages: 50,25,12.5,6.25,3.125,1.5625,0.78125,0.78125\n" +
                            "then the ratio of equal-length to Huffman code length is " + problem13InputC.calculateRatio(-1) + "\n");

                    Huffman problem13InputD = new Huffman();
                    problem13InputD.setupDictionaryFromFile("problem13InputD.txt", false);
                    problem13InputD.setupHuffmanCodes();
                    System.out.println("An 8-letter alphabet with Huffman encoding more efficient than the best case above\n" +
                            "is: a=93,b=1,c=1,d=1,e=1,f=1,g=1,h=1 \nThe ratio of equal-length to Huffman code length for this" +
                            " alphabet is " + problem13InputD.calculateRatio(-1));
                    break;
                case "14":
                    Huffman finnish = new Huffman();
                    finnish.setupDictionaryFromFile("problem14Finnish.txt", false);
                    System.out.println("Finnish alphabet entropy: " + finnish.calculateTotalAlphabetEntropy(false) + "\n");

                    System.out.println("Computation of efficiency:\n");
                    System.out.println("Calculate total alphabet entropy by adding all character frequencies:");

                    System.out.println("Note: probability for current character is found by dividing current character frequency by total alphabet frequency");
                    System.out.println("Note: entropy for current character is found by (-probability)*(Math.log(probability) / Math.log(2))");

                    System.out.println("\nCalculate total alphabet entropy by adding up each individual character entropy:\n");
                    finnish.calculateTotalAlphabetEntropy(true); // pass boolean parameter as "true" so computation is printed
                    break;
                case "15":
                    System.out.println("NOTE: check \"problem15Explanations\" in \"explanations\" folder for explanations.\n");

                    huffmanDictionary = new Huffman();
                    huffmanDictionary.setupDictionaryFromFile("problem15EnglishAlphabet.txt", false);

                    double englishAlphabetFrequency = huffmanDictionary.calculateTotalAlphabetEntropy(false);

                    // Finds single best binomial from file
                    ArrayList<String> bestBinomial = huffmanDictionary.findBestBinomials("problem15EnglishAlphabet.txt", "problem15Binomials.txt", true, true);

                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                    System.out.println("Binomial I would add to dictionary: " + bestBinomial.get(0) + "\nThis binomial would reduce the total alphabet entropy from "
                            + String.format("%4f", englishAlphabetFrequency) + " to " +  String.format("%4f", huffmanDictionary.calculateTotalAlphabetEntropy(false)) +
                            ".\nThis reduction is the biggest of all binomials therefore proving this is the most efficient option.");
                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");

                    // Find all binomials that would lower entropy if they were put in an alphabet
                    ArrayList<String> bestBinomials = huffmanDictionary.findBestBinomials("problem15EnglishAlphabet.txt", "problem15Binomials.txt", false, false);
                    double entropyWithAllBeneficialBinomials = huffmanDictionary.calculateTotalAlphabetEntropy(false);

                    System.out.println("The following are all the possible binomials that can lower the dictionary entropy:\n" + bestBinomials);
                    System.out.println("\nFinal entropy with above binomials: " + entropyWithAllBeneficialBinomials);
                    System.out.println("This entropy compares to the original English by an entropy difference of: " + englishAlphabetFrequency + " - " + entropyWithAllBeneficialBinomials + " = " + (englishAlphabetFrequency - entropyWithAllBeneficialBinomials));
                    break;
                default:
                    System.out.println("Your input \"" + input + "\" is not an option. Here is the \"Menu\" to see all valid inputs:");
                    break;
            }
        }
    }
}


