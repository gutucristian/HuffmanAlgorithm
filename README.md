# HuffmanAlgorithm
___________________________________

Input to program: all input should be in the form of a text file with a ".txt" extension. 

Output from program: all program output will be done using System.out.println() to the IDE console.
____________________________________

General overview of using Compression class, Huffman class, EqualLengthCompression class:

~ ~ ~ ~ ~ ~ ~
Reading input:
~ ~ ~ ~ ~ ~ ~

Both the Huffman class and the EqualLengthCompression class read input with the same inherited method.

Example file name: "test.txt"

Having an object of Huffman class or EqualLengthCompression class:
__________________________________________________________________

	Huffman huffman = new Huffman(); // instantiate Huffman class
	EqualLengthCompression equalLengthCompression = new EqualLengthCompression(); // instantiate equalLengthCompression class

Make a result String variable:
______________________________

	String result = "";

Read from a file:
_________________

	result = huffman.getInputFromFile("test.txt"); // reading with the huffman object
	result = equalLengthCompression.getInputFromFile("test.txt") // reading with the equalLengthCompression object

REMEMBER: the file must be in the MITPrimes folder and must be passed with a ".txt" added to whatever its name already is

Building Huffman codes and EqualLengthCompression codes from a file:

Example file name: "test.txt"

Instantiate Huffman object:
___________________________

	Huffman huffman = new Huffman();
	EqualLengthCompression equalLengthCompression = new EqualLengthCompression();

Setup dictionary from file:
___________________________

Use setupDictionaryFromFile(String fileName, boolean isAlphabetRawText) to setup dictionary.

fileName - the file name and a ".txt" extension added to it
isAlphabetRawText - indicates if text is raw (a play or a book is considered "raw text") or if its an alphabet such as a=23,b=34,c=98

	huffman.setupDictionaryFromFile("test.txt", false);

Note: for EqualLengthCompression class setupDictionaryFromFile(String fileName) builds the codes and
      sets up the dictionary so there is no need to do it twice. Also for EqualLengthCompression class input file	
      contents are never raw text so 'boolean isAlphabetRawText' parameter is not there when calling this method 
      with an EqualLengthCompression object.


Build the codes:
________________

	huffman.setupHuffmanCodes(); // for Huffman class
	equalLengthCompression.setupDictionaryFromFile("test.txt"); // for EqualLengthCompression class setupDictionaryFromFile(String fileName) will build the codes as well as setup the dictionary

It is worth noting that you can also print the codes and their letter:
______________________________________________________________________

	huffman.printCodes();
	equalLengthCompression.printCodes();

Encoding text with Huffman object or EqualLengthCompression object:

You can either encode text by passing a String or you can encode text from a file.

Having a Huffman object or a EqualLengthCompression object:
___________________________________________________________

	Huffman huffman = new Huffman();
	EqualLengthCompression equalLengthCompression = new EqualLengthCompression();

Simply call encodeInput(String input) and pass in the input:
____________________________________________________________

	String result = "";

	result = huffman.encodeInput("Hello"); // encoding input passed as String
	
	// Encoding input from file. Note that input from file is not filtered and you might have to filter it
	result = huffman.encodeInput(huffman.getInputFromFile("test.txt")); 	

	result = equalLengthCompression.encodeInput(equalLengthCompression.getInputFromFile("test.txt"));

Filtering input:
________________

Use removeNonLetterSymbols(String input, String additionalCharactersToKeep) for Huffman objects and EqualLengthCompression objects
to filter input.

input - is the text to be filtered of non-letter characters
additionalCharactersToKeep - are symbols to be kept

returns -> 'String' with filtered input

Decoding text with Huffman object or EqualLengthCompression object:

Remember that in order to successfully decode Huffman codes or EqualLengthCompression codes
their dictionaries must be setup and the codes must be built. Also know that if file has
unwanted characters it should be filtered.

Having a Huffman object or a EqualLengthCompression object:
___________________________________________________________

	Huffman huffman = new Huffman();
	EqualLengthCompression equalLengthCompression = new EqualLengthCompression();

Simply call decodeInput(String input) and pass in the input:
____________________________________________________________

	String result = "";

	result = huffman.decodeInput("10011101101"); // decoding input passed as String
	
	// decoding input from file
	result = huffman.decodeInput(huffman.getInputFromFile("test.txt")); 	

	result = equalLengthCompression.decodeInput(equalLengthCompression.getInputFromFile("test.txt"));

NOTE: for full documentation see MITPrimes -> documentation -> index.html
