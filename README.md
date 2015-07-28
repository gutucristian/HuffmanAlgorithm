# HuffmanAlgorithm

**Note:** all input should be in the form of a text file with a `.txt` extension. All program output will be done using Java's `System.out.println()`.

##Overview of Compression, Huffman, EqualLengthCompression classes

###Reading input:

Both the `Huffman` class and the `EqualLengthCompression` class read input with the same inherited method.

`huffman.getInputFromFile("example.txt");`

`equalLengthCompression.getInputFromFile("example.txt");`

###Building Huffman codes and EqualLengthCompression codes from a file:

**Setup dictionary from file:**

`setupDictionaryFromFile(String fileName, boolean isAlphabetRawText)`

 * `fileName` - the file name and a ".txt" extension added to it

* `isAlphabetRawText` - indicates if text is raw (a play or a book is considered "raw text") or if its an alphabet such as
   `a=23,b=34,c=98`

`huffman.setupDictionaryFromFile("test.txt", false);`

**Build the codes:**

`huffman.setupHuffmanCodes();`

`equalLengthCompression.setupDictionaryFromFile("test.txt");`

`setupDictionaryFromFile(String fileName) // will build the codes as well as setup the dictionary`

**Note you can also print the codes and their letter:**

`huffman.printCodes();`

`equalLengthCompression.printCodes();`

##Encoding text with Huffman object or EqualLengthCompression object:

**You can either encode text by passing a** `String`**:**

`huffman.encodeInput("Hello"); // encoding input passed as String`

**Or you can encode text from a file:**

**Note:** that input from file is not filtered and you might have to filter it.

`result = huffman.encodeInput(huffman.getInputFromFile("test.txt"));`

`result = equalLengthCompression.encodeInput(equalLengthCompression.getInputFromFile("test.txt"));`

**Filtering input:**

Use `removeNonLetterSymbols(String input, String additionalCharactersToKeep)` for `Huffman` objects and `EqualLengthCompression` objects to filter input.

`input`: is the text to be filtered of non-letter characters

`additionalCharactersToKeep`: are symbols to be kept

##Decoding text with Huffman object or EqualLengthCompression object:

**Note:** in order to successfully decode Huffman codes or EqualLengthCompression codes
their dictionaries must be setup and the codes must be built. Also know that if file has
unwanted characters it should be filtered.

**Decoding input from** `String`**:**

`huffman.decodeInput("10011101101");`

**Decoding input from file:**

`huffman.decodeInput(huffman.getInputFromFile("test.txt"));`

`equalLengthCompression.decodeInput(equalLengthCompression.getInputFromFile("test.txt"));`
