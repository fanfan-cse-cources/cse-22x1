import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Counts word occurrences in a given input file and outputs an HTML document
 * with a table of the words and counts listed in alphabetical order.
 *
 * @author Yifan Yao
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    public static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        Set<Character> newSet = strSet.newInstance();

        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (!newSet.contains(character)) {
                newSet.add(character);
            }
        }
        strSet.transferFrom(newSet);
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int counter = position;
        boolean contains = separators.contains(text.charAt(position));
        while (counter < text.length()
                && (contains == separators.contains(text.charAt(counter)))) {
            counter++;
        }

        return text.substring(position, counter);
    }

    /**
     * Add words into the Map(when word not exist) or increment the value.
     *
     * @param text
     *            word
     * @param words
     *            Map contain words
     * @update words
     * @ensure words = {Word}
     *
     */
    public static void addWordOrValue(String text, Map<String, Integer> words) {
        if (words.hasKey(text)) {
            int value = words.value(text);
            value++;
            words.replaceValue(text, value);
        } else {
            words.add(text, 1);
        }
    }

    /**
     * Generate html file from Map.
     *
     * @param words
     *            Map contain words
     * @param inputFile
     *            path for input file
     * @param outputDir
     *            path for output file
     */
    public static void generateHTML(Map<String, Integer> words,
            String inputFile, String outputDir) {
        SimpleWriter fileOut = new SimpleWriter1L(outputDir);

        /*
         * Sort words
         */
        Queue<String> wordS = new Queue1L<String>();
        for (Pair<String, Integer> s : words) {
            wordS.enqueue(s.key());
        }
        final Comparator<String> cs = String.CASE_INSENSITIVE_ORDER;
        wordS.sort(cs);

        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>Words Counted in " + inputFile + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2>Words Counted in " + inputFile + "</h2>");
        fileOut.println("<hr />");
        fileOut.println("<table border=\"1\">");

        fileOut.println("<tr>");
        fileOut.println("<th>Words</th>");
        fileOut.println("<th>Counts</th>");
        fileOut.println("</tr>");

        for (String s : wordS) {
            fileOut.println("<tr>");
            fileOut.println("<td>" + s + "</td>");
            fileOut.println("<td>" + words.value(s) + "</td>");
            fileOut.println("</tr>");
        }

        fileOut.println("</table>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        fileOut.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Define separator characters for test
         */
        final String separatorStr = " \t,.-";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        /*
         * Declare input File
         */
        out.print("Input File: ");
        String inputFile = in.nextLine();
        while (inputFile.isEmpty()) {
            out.print("Input File: ");
            inputFile = in.nextLine();
        }

        SimpleReader fileIn = new SimpleReader1L(inputFile);
        out.print("Output File: ");
        String outputDir = in.nextLine();
        while (outputDir.isEmpty()) {
            out.print("Output File: ");
            outputDir = in.nextLine();
        }

        /*
         * Put words into Map and generate html file
         */
        Map<String, Integer> words = new Map1L<String, Integer>();
        while (!fileIn.atEOS()) {
            int pos = 0;
            String token = fileIn.nextLine();

            while (pos < token.length()) {
                String word = nextWordOrSeparator(token, pos, separatorSet);
                if (!separatorSet.contains(word.charAt(0))) {
                    addWordOrValue(word, words);
                }
                pos += word.length();
            }
        }
        generateHTML(words, inputFile, outputDir);
        out.println("Your word counter file has been generated successfully.");

        /*
         * Close input and output streams
         */
        in.close();
        fileIn.close();
        out.close();
    }

}
