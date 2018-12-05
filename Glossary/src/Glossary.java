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
 * A relatively easy-to-maintain glossary facility.
 *
 * @author Yifan Yao
 */
public final class Glossary {

    /**
     * Private no-argument constructor to prevent instantiation of this utility
     * class.
     */
    private Glossary() {
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
     * Adding words from user provided text file into the glossary.
     *
     * @param inputFile
     *            the name of the input file
     * @param terms
     *            the Queue contains all terms
     * @param glossary
     *            the Map contain all words with corresponding terms
     * @replace terms
     * @replace glossary
     * @requires #inputFile exist and #terms and glossary exist
     * @ensures #terms includes all term from input file
     * @ensures #glossary includes all term and definition from input file
     */
    public static void putWordsIntoGlossary(String inputFile,
            Queue<String> terms, Map<String, String> glossary) {
        assert inputFile != null : "Violation of: input is not null";
        assert terms != null : "Violation of: terms is not null";
        assert glossary != null : "Violation of: glossary is not null";

        SimpleReader input = new SimpleReader1L(inputFile);
        Queue<String> tempTerms = terms.newInstance();
        Map<String, String> tempGlossary = glossary.newInstance();

        while (!input.atEOS()) {
            // First line is the term
            String term = input.nextLine();
            tempTerms.enqueue(term);

            // Second line to the empty line is the definition of the term.
            String explain = input.nextLine();
            StringBuffer finalExplain = new StringBuffer();

            // Keep taking lines until it is empty (which is a new term)
            while (!explain.isEmpty()) {
                finalExplain.append(explain + " ");

                if (input.atEOS()) {
                    explain = "";
                } else {
                    explain = input.nextLine();
                }

            }

            // Remove the additional space
            finalExplain.deleteCharAt(finalExplain.length() - 1);

            // Add the word into glossary
            tempGlossary.add(term, finalExplain.toString());
        }

        terms.transferFrom(tempTerms);
        glossary.transferFrom(tempGlossary);

        input.close();
    }

    /**
     * Output index for each term form the glossary as lexicographic order.
     *
     * @param outputDir
     *            the path of output directory
     * @param terms
     *            the terms provided from user's input file
     * @requires #outputDir exist and #term exist
     * @ensures out.content = #out.content * #terms
     */
    public static void outputIndex(String outputDir, Queue<String> terms) {
        assert outputDir != null : "Violation of: outputDir is not null";
        assert terms != null : "Violation of: terms is not null";

        SimpleWriter output = new SimpleWriter1L(outputDir + "index.html");

        output.println("<html>");
        output.println("<head>");
        output.println("<title>Sample Glossary</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h2>Sample Glossary</h2>");
        output.println("<hr />");
        output.println("<h3>Index</h3>");
        output.println("<ul>");

        // Add hyperlink for each term
        for (String s : terms) {
            output.println("<li><a href=\"" + s + ".html\">" + s + "</a></li>");
        }

        output.println("</ul>");
        output.println("</body>");
        output.println("</html>");

        output.close();
    }

    /**
     * Output each term from Glossary. When the definition of a word present,
     * add a hyperlink to that term.
     *
     * @param outputDir
     *            the path of output directory
     * @param term
     *            the terms provided from user's input file
     * @param glossary
     *            the Map contain all words with corresponding terms
     * @requires #outputDir exist and #term exist
     * @requires #term is a key of #glossary
     * @ensures out.content = #out.content * #term * #glossery.value(#term)
     */
    public static void outputTerms(String outputDir, String term,
            Map<String, String> glossary) {
        assert outputDir != null : "Violation of: outputDir is not null";
        assert term != null : "Violation of: term is not null";
        assert glossary.hasKey(term) : "Violation of: glossary hasKey";

        /*
         * Define separator characters for test
         */
        final String separatorStr = " \t,";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        SimpleWriter output = new SimpleWriter1L(outputDir + term + ".html");

        output.println("<html>");
        output.println("<head>");
        output.println("<title>" + term + "</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>");

        String valueOfTerm = glossary.value(term);
        StringBuilder finalValueOfTerm = new StringBuilder();
        int position = 0;

        // Output terms
        while (position < valueOfTerm.length()) {
            boolean wrote = false;
            String token = nextWordOrSeparator(valueOfTerm, position,
                    separatorSet);
            for (Pair<String, String> s : glossary) {
                // Add hyperlink for each term which has a definition
                if (token.toLowerCase().equals(s.key().toLowerCase())) {
                    finalValueOfTerm.append("<a href=\"" + s.key() + ".html\">"
                            + token + "</a>");
                    wrote = true;
                }
            }

            // When the word does not have a definition
            if (!wrote) {
                finalValueOfTerm.append(token);
            }
            position += token.length();
        }

        output.println(
                "<blockquote>" + finalValueOfTerm.toString() + "</blockquote>");
        output.println("<hr />");
        output.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        output.println("</body>");
        output.println("</html>");

        output.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Your input file: ");
        String inputFile = in.nextLine();
        out.print("Output directory: ");
        String outputDir = in.nextLine();

        if (!outputDir.substring(outputDir.length()).equals("/")) {
            outputDir += "/";
        }

//        final String inputFile = "data/terms_copy.txt";
//        final String outputDir = "data/output/";

        Queue<String> terms = new Queue1L<String>();
        Map<String, String> glossary = new Map1L<String, String>();

        putWordsIntoGlossary(inputFile, terms, glossary);

        /*
         * Sort terms into non-decreasing lexicographic order and generate
         * index.html
         */
        final Comparator<String> cs = String.CASE_INSENSITIVE_ORDER;
        terms.sort(cs);
        outputIndex(outputDir, terms);

        // Generate <term>.html
        for (String s : terms) {
            outputTerms(outputDir, s, glossary);
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
