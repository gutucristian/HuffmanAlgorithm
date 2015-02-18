/**
 * Stores Huffman code key and frequency. <Code>Code</Code> class is used as a value in <Code>dictionary</Code>
 * HashMap. Additionally <Code>Code</Code> is used as nodes when building a Huffman tree.
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

public class Code {
    public String key;
    public long frequency;
    public int leftPosition;
    public String code;
    public Code parentCode;

    /**
     * Code constructor which stores all needed information about a given key in a
     * data encoding algorithm.
     *
     * @param key           a symbol from the dictionary
     * @param frequency     how often this symbol is encountered
     * @param leftPosition  the index of this symbol in the given alphabet
     * @param parentCode    when creating a Huffman tree some two codes are combined.
     *                      When they are combined a new Code is created which is the parent
     *                      of the two
     */

    public Code(String key, long frequency, int leftPosition, Code parentCode){
        this.key = key;
        this.frequency = frequency;
        this.leftPosition = leftPosition;
        this.parentCode = parentCode;
    }

    /**
     * Builds <Code>String</Code> with key and frequency
     *
     * @return key and frequency in readable format
     */

    public String toString(){
        return "Key: " + key + " Frequency: " + frequency;
    }
}