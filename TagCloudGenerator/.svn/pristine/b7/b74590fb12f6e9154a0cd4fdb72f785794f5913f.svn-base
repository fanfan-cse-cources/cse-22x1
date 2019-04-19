import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue2;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine5;

/**
 * Generates a tag cloud from a given input text in a given input file and
 * outputs an HTML document in alphabetical order.
 *
 * @author Yifan Yao, Yueyi Hua
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Compare {@code Map.Pair<String, Integer>}s by decreasing order by value.
     */
    private static class PairVLT
            implements Comparator<Map.Pair<String, Integer>>, Serializable {
        /**
         * Serial Version UID.
         */
        private static final long serialVersionUID = 357741384746983111L;

        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            Integer i1 = p1.value();
            Integer i2 = p2.value();
            return i2.compareTo(i1);
        }
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
     * Justify the String is digit or not.
     *
     * @param str
     *            the given {@code String}
     * @return is digit or not
     */
    public static boolean isDigit(String str) {
        assert str != null : "Violation of: str is not null";

        boolean result = true;

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                result = false;
            }
        }

        return result;
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
        String lText = text.toLowerCase();

        if (words.hasKey(lText)) {
            int value = words.value(lText);

            value++;
            words.replaceValue(lText, value);
        } else {
            words.add(lText, 1);
        }
    }

    /**
     * Generate html file from Map.
     *
     * @param words
     *            Map contain words
     * @param size
     *            number of words to be included in the generated tag cloud
     * @param inputFile
     *            path for input file
     * @param outputDir
     *            path for output file
     */
    public static void generateHTML(Map<String, Integer> words, int size,
            String inputFile, String outputDir) {
        SimpleWriter fileOut = new SimpleWriter1L(outputDir);
        final String css = "https://cse.aws.fanfanishere.org/cse-2231/tagcloud.css";
        final int maxFontSize = 48;
        final int minFontSize = 11;
        final int enlargeFactor = 5;

        Map<String, Integer> output = words.newInstance();
        int totalWords = 0;

        /*
         * Sort words
         */
        final Comparator<Pair<String, Integer>> pv = new PairVLT();
        SortingMachine<Pair<String, Integer>> sm;
        sm = new SortingMachine5<Pair<String, Integer>>(pv);

        /*
         * Put each pair into SortingMachine, and generate a new MAP with font
         * size
         */
        for (Pair<String, Integer> x : words) {
            sm.add(x);
            output.add(x.key(), x.value());
        }

        Queue<String> w = new Queue2<String>();

        sm.changeToExtractionMode();
        for (int i = 0; i < size; i++) {
            Pair<String, Integer> x = sm.removeFirst();
            totalWords += x.value();
            w.enqueue(x.key());
        }

        final int enlargeWeight = totalWords / size;

        for (Pair<String, Integer> x : output) {
            int classifiedValue = x.value() * enlargeFactor / enlargeWeight;

            classifiedValue += minFontSize;
            if (classifiedValue > maxFontSize) {
                classifiedValue = maxFontSize;
            } else if (classifiedValue < minFontSize) {
                classifiedValue = minFontSize;
            }
            output.replaceValue(x.key(), classifiedValue);
        }

        // Sort words by alphabet order
        final Comparator<String> cs = String.CASE_INSENSITIVE_ORDER;
        w.sort(cs);

        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>Top 100 words in " + inputFile + "</title>");
        fileOut.println("<link href=\"" + css
                + "\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2>Top 100 words in " + inputFile + "</h2>");
        fileOut.println("<hr />");
        fileOut.println("<div class=\"cdiv\">");
        fileOut.println("<p class=\"cbox\">");
        for (int i = 0; i < size; i++) {
            String outputWord = w.dequeue();

            fileOut.println("<span style=\"cursor:default\" class=\"f"
                    + output.value(outputWord) + "\" title=\"count:"
                    + words.value(outputWord) + "\">" + outputWord + "</span>");
        }
        fileOut.println("</p>");
        fileOut.println("</div>");
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
        final String separatorStr = " \t\n\r,-.!?[]';:/()";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        /*
         * Declare input/output file and number of words to be included in the
         * generated tag cloud
         */
        out.print("Input file: ");
        String inputFile = in.nextLine();
        while (inputFile.isEmpty()) {
            out.print("Input file: ");
            inputFile = in.nextLine();
        }

        SimpleReader fileIn = new SimpleReader1L(inputFile);
        out.print("Output file: ");
        String outputDir = in.nextLine();
        while (outputDir.isEmpty()) {
            out.print("Output file: ");
            outputDir = in.nextLine();
        }

        String sizeS = "";
        boolean validNumber = false;

        while (sizeS.isEmpty() || !validNumber) {
            out.print("Number of words: ");
            sizeS = in.nextLine();
            validNumber = isDigit(sizeS);
        }
        int size = Integer.parseInt(sizeS);

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
                    if (!isDigit(word)) {
                        addWordOrValue(word, words);
                    }
                }
                pos += word.length();
            }
        }
        generateHTML(words, size, inputFile, outputDir);
        out.println("Your tag cloud has been generated successfully.");

        /*
         * Close input and output streams
         */
        in.close();
        fileIn.close();
        out.close();
    }

}
