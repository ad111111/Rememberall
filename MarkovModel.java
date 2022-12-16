// Significant portion of this code was taken directly from our Markov Model
// coding assignment
public class MarkovModel {

    private int k; // order of Markov Model
    // number of times kgram appeared in input text
    private ST<String, Integer> appearenceNumber;
    // number of times each letter succeeded kgram in input text
    private ST<String, int[]> succeedingFrequency;

    // creates a Markov model of order k based on the specified text
    public MarkovModel(String text, int k) {
        // intialize instance variables
        this.k = k;
        appearenceNumber = new ST<String, Integer>();
        succeedingFrequency = new ST<String, int[]>();

        // create circular version of text
        text += text.substring(0, k);

        // populate appearance number symbol table
        for (int i = 0; i < text.length() - k; i++) {
            // define kgram
            String kGram = text.substring(i, i + k);

            // Update kgram frequencies
            if (appearenceNumber.contains(kGram)) {
                int oldFrequency = appearenceNumber.get(kGram);
                appearenceNumber.put(kGram, oldFrequency + 1);
                continue;
            }
            appearenceNumber.put(kGram, 1);
        }

        // populate succeeding frequency symbol table
        for (int j = 0; j < text.length() - k; j++) {
            // define kgram and next character
            String kGram = text.substring(j, j + k);
            char character = text.charAt(j + k);
            int[] characterFrequency;

            // Update succeeding character frequencies
            if (succeedingFrequency.contains(kGram)) {
                characterFrequency = succeedingFrequency.get(kGram);
                characterFrequency[character]++;
                succeedingFrequency.put(kGram, characterFrequency);
                continue;
            }
            characterFrequency = new int[128];
            characterFrequency[character] = 1;
            succeedingFrequency.put(kGram, characterFrequency);

        }
    }

    // returns the order of the model (also known as k)
    public int order() {
        return k;
    }

    // returns a String representation of the model
    public String toString() {
        // Create a new string builder object and build string
        StringBuilder result = new StringBuilder();
        for (String key : succeedingFrequency) {
            result.append(key + ": ");

            // get the character frequency array
            int[] frequency = succeedingFrequency.get(key);

            // for each non-zero entry, append the character and the frequency
            // trailing space is allowed
            for (int i = 0; i < frequency.length; i++) {
                if (frequency[i] != 0) {
                    result.append((char) i);
                    result.append(" " + frequency[i] + " ");
                }
            }

            // append a newline character
            result.append("\n");
        }
        // return string representation
        return result.toString();
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {
        // throw illegalargumentexception for kgrams not of correct length
        if (kgram.length() != k)
            throw new IllegalArgumentException("Kgram not of length k.");

        if (appearenceNumber.contains(kgram)) return appearenceNumber.get(kgram);
        return 0;
    }

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {
        // throw illegalargumentexception for kgrams not of correct length
        if (kgram.length() != k)
            throw new IllegalArgumentException("Kgram not of length k.");

        else if (freq(kgram) == 0) return 0;
        int[] frequencyList = succeedingFrequency.get(kgram);
        return frequencyList[c];
    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {
        // throw illegalargumentexception for kgrams not of correct length
        if (kgram.length() != k)
            throw new IllegalArgumentException("Kgram not of length k.");

            // throw illegalargumentexception for kgrams not in input text
        else if (!succeedingFrequency.contains(kgram))
            throw new IllegalArgumentException("Kgram not found in text.");

        int[] frequencyList = succeedingFrequency.get(kgram);
        char randomChar = (char) StdRandom.discrete(frequencyList);
        return randomChar;
    }

    // returns an edited array of the text specific to the quiz game,
    // with the text split over multiple lines, each line being represented
    // by an element within the array
    public static String[] textEditor(String uneditedText) {
        // maximum number of characters in each line
        int LINE_CHARS = 50;

        // create new array of the edited text
        String[] editedText = new String[(int) (uneditedText.length() /
                (double) LINE_CHARS) + 1];

        // populate array
        for (int i = 0; i < editedText.length; i++) {
            editedText[i] = "";
        }

        if (uneditedText.length() > LINE_CHARS) {
            // iterate through the text, splitting it into different lines
            // of 50 characters
            for (int i = 0; i < uneditedText.length() / (double) LINE_CHARS; i++) {
                // append the remainder of the text for last line
                if ((i * LINE_CHARS + LINE_CHARS) > uneditedText.length() - 1) {
                    editedText[i] = uneditedText.substring(i * LINE_CHARS,
                                                           (uneditedText.length()
                                                                   - 1));
                    break;
                }
                // append line
                editedText[i] = uneditedText.substring(i * LINE_CHARS,
                                                       i * LINE_CHARS +
                                                               LINE_CHARS) + "–";
            }
        }
        else editedText[0] = uneditedText;
        return editedText;
    }

    // edit fortune cookie text by returning a string, taken from the
    // generated text starting with the character after the quotation mark
    // and ending with a period
    public static String fortuneTextEdit(String uneditedText) {
        int editedTextStart = 0;
        int editedTextEnd = 0;

        // find the first quotation mark in the text
        // and set it to text start
        for (int i = 0; i < uneditedText.length(); i++) {
            if (uneditedText.charAt(i) == ('\"')) {
                if (i > editedTextStart) {
                    editedTextStart = i;
                    break;
                }
            }
        }

        // find the first period after the quotation mark in the text
        // and set it to text end
        for (int i = editedTextStart + 1; i < uneditedText.length(); i++) {
            if (uneditedText.charAt(i) == ('.')) {
                if (i > editedTextEnd) {
                    editedTextEnd = i;
                    break;
                }
            }
        }

        // return the edited text
        String editedText = uneditedText.substring(editedTextStart + 2,
                                                   editedTextEnd + 1);
        return editedText;
    }

    // return the text to train the Markov Model based on the number of points
    // and given the happy and sad texts
    public static String categoryText(double points, String happy, String sad) {
        StringBuilder sb = new StringBuilder();
        if (points < 1000) {
            sb.append(sad);
        }
        else sb.append(happy);
        return sb.toString();
    }

    // Return the Markov text generated based on the training data
    public static String markovTextReturn(String text, int k, int t) {
        StringBuilder sb = new StringBuilder();
        // Create a Markov Model of order k using given text
        MarkovModel newText = new MarkovModel(text, k);

        // Define first kgram
        String kgram = text.substring(0, k);

        // Generate a text of t random characters and print it to StdOut
        for (int i = 0; i < t - k; i++) {
            char randomCharacter = newText.random(kgram);
            sb.append(randomCharacter);
            kgram += randomCharacter;
            kgram = kgram.substring(1, k + 1);
        }
        return sb.toString();
    }

    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        // test Markov Model on model text
        String text1 = "banana";
        MarkovModel model1 = new MarkovModel(text1, 2);
        StdOut.println("freq(\"an\", 'a')    = " + model1.freq("an", 'a'));
        StdOut.println("freq(\"na\", 'b')    = " + model1.freq("na", 'b'));
        StdOut.println("freq(\"na\", 'a')    = " + model1.freq("na", 'a'));
        StdOut.println("freq(\"na\")         = " + model1.freq("na"));
        StdOut.println();

        // test random function for model 1
        int TESTING_VALUE = 100;
        double bCounter = 0;
        for (int i = 0; i < TESTING_VALUE; i++) {
            char randomCharacter = model1.random("na");
            if (randomCharacter == 'b') bCounter++;
        }
        StdOut.println("B was counted " + 100 * (bCounter / TESTING_VALUE) +
                               " percent of the time.");

        // test Markov Model on second model text
        String text3 = "one fish two fish red fish blue fish";
        MarkovModel model3 = new MarkovModel(text3, 4);
        StdOut.println("freq(\"ish \", 'r') = " + model3.freq("ish ", 'r'));
        StdOut.println("freq(\"ish \", 'x') = " + model3.freq("ish ", 'x'));
        StdOut.println("freq(\"ish \")      = " + model3.freq("ish "));
        StdOut.println("freq(\"tuna\")      = " + model3.freq("tuna"));

        // test random function for model 3
        double oCounter = 0;
        for (int i = 0; i < TESTING_VALUE; i++) {
            char randomCharacter = model3.random("fish");
            if (randomCharacter == 'o') oCounter++;
        }
        StdOut.println("O was counted " + 100 * (oCounter / TESTING_VALUE) +
                               " percent of the time.");

        // test order function
        int order1 = model1.order();
        int order3 = model3.order();
        StdOut.println("Order of model 1: " + order1);
        StdOut.println("Order of model 3: " + order3);

        String sampleHappyText = "You will reach your goals.";
        String sampleSadText = "Challenges await.";
        double userPoints = 1200;
        String usedText = categoryText(1200, sampleHappyText, sampleSadText);
        StdOut.println("Text that will be used if the user has " + userPoints
                               + " points: \n" + usedText);

        String sampleUneditedText = "the world.\"\"You will have a great day.\""
                + " You will receive flowers today. \"Grass is green.\"\"A Dragon is"
                + " a symbol of good luck and prosperity.\"\"You will have an"
                + "amazing day.\"";
        String sampleFortuneEdit = fortuneTextEdit(sampleUneditedText);
        StdOut.println("Edited fortune text: " + sampleFortuneEdit);

        int k = 7;
        int t = 253;
        String sampleMarkovText = markovTextReturn(sampleUneditedText, k, t);
        StdOut.println("Random generated Markov text: " + sampleMarkovText);

        StdOut.println("Sample output text line by line: ");
        String[] sampleOutputText = textEditor(sampleUneditedText);
        for (int i = 0; i < sampleOutputText.length; i++) {
            StdOut.println(sampleOutputText[i]);
        }
    }
}
