import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Yifan Yao
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<html>");
        out.println("<head>");
        if (getChildElement(channel, "title") != -1
                && channel.child(getChildElement(channel, "title"))
                        .numberOfChildren() > 0) {
            /*
             * When <title> is available
             */
            out.println(
                    "<title>" + channel.child(getChildElement(channel, "title"))
                            .child(0).label() + "</title>");
        } else {
            /*
             * When <title> is unavailable
             */
            out.println("<title>Empty Title</title>");
        }
        out.println("</head>");
        out.println("<body>");

        /*
         * Print out title and link as header
         */
        out.println("<style>body {padding-bottom: 3em; }</style>");

        if (getChildElement(channel, "title") != -1
                && channel.child(getChildElement(channel, "title"))
                        .numberOfChildren() > 0) {
            out.println("<h1><a href=\""
                    + channel.child(getChildElement(channel, "link")).child(0)
                            .label()
                    + "\">" + channel.child(getChildElement(channel, "title"))
                            .child(0).label()
                    + "</a></h1>");
        } else {
            out.println(
                    "<h1><a href=\""
                            + channel.child(getChildElement(channel, "link"))
                                    .child(0).label()
                            + "\">" + "Empty Title</a></h1>");
        }

        if (getChildElement(channel, "description") != -1
                && channel.child(getChildElement(channel, "description"))
                        .numberOfChildren() > 0) {
            /*
             * When <description> is available
             */
            out.println("<p>"
                    + channel.child(getChildElement(channel, "description"))
                            .child(0).label()
                    + "</p>");
        } else {
            /*
             * When <description> is empty
             */
            out.println("<p>No description</p>");
        }

        out.println("<style>table, td, th { border: 1px solid black }</style>");
        out.println("<table>");
        out.println(
                "<tr>\n<th>Date</th>\n<th>Source</th>\n<th>News</th>\n</tr>");

    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        boolean loopBreaker = false;
        int returnCode = -1;

        int counter = 0;
        while (!loopBreaker && counter < xml.numberOfChildren()) {
            if (xml.isTag() && xml.child(counter).label().equals(tag)) {
                loopBreaker = true;
                returnCode = counter;
            }
            counter++;
        }
        return returnCode;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("<tr>");

        /*
         * <item>: <pubDate>
         */
        final int pubDateReturn = getChildElement(item, "pubDate");
        if (pubDateReturn != -1) {
            out.println("<td>" + item.child(pubDateReturn).child(0).label()
                    + "</td>");
        } else {
            out.println("<td>" + "No date available" + "</td>");
        }

        /*
         * <item>: <source>
         */
        final int sourceReturn = getChildElement(item, "source");
        if (sourceReturn != -1
                && item.child(sourceReturn).numberOfChildren() > 0) {
            out.println("<td>" + "<a href=\""
                    + item.child(sourceReturn).attributeValue("url") + "\">"
                    + item.child(sourceReturn).child(0).label() + "</a>"
                    + "</td>");
        } else {
            out.println("<td>" + "No source available" + "</td>");
        }

        /*
         * <item>: <title>, <description>, <link>
         */
        final int titleReturn = getChildElement(item, "title");
        final int descriptionReturn = getChildElement(item, "description");
        final int linkReturn = getChildElement(item, "link");
        String title = "No title available";

        if (titleReturn != -1
                && item.child(titleReturn).numberOfChildren() > 0) {
            title = item.child(titleReturn).child(0).label();
        } else if (descriptionReturn != -1
                && item.child(descriptionReturn).numberOfChildren() > 0) {
            title = item.child(descriptionReturn).child(0).label();
        }

        if (linkReturn != -1 && item.child(linkReturn).numberOfChildren() > 0) {
            out.println("<td>" + "<a href=\""
                    + item.child(linkReturn).child(0).label() + "\">" + title
                    + "</a>" + "</td>");
        } else {
            out.println("<td>" + title + "</td>");
        }

        out.println("</tr>");

    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        XMLTree xml = new XMLTree1(url);
        out.println(url);
        /*
         * Verify it is a valid RSS 2.0 feed or not
         */
        if (xml.label().equals("rss") && xml.hasAttribute("version")
                && xml.attributeValue("version").equals("2.0")) {
            /*
             * Create file writer
             */
            SimpleWriter fileOut = new SimpleWriter1L(file);

            /*
             * Analysis channel tag and output header
             */
            XMLTree channel = xml.child(0);
            outputHeader(channel, fileOut);

            /*
             * Process each <item> from channel tag
             */
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).isTag()
                        && channel.child(i).label().equals("item")) {
                    processItem(channel.child(i), fileOut);
                }
            }

            /*
             * Output confirmation and footer, then close file output stream
             */
            out.println(file + " has been generated");
            outputFooter(fileOut);
            fileOut.close();
        } else {
            out.println(url + " is not valid");
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        final String htmlPostfix = ".html";

        /*
         * Let user input RSS feeds and output file name
         */
        out.print("URL for your feeds: ");
        String seed = in.nextLine();

        out.print("Output File Name: ");
        String fileName = in.nextLine();

        /*
         * When user did not put anything for file name, use the default
         */
        if (fileName.length() == 0) {
            fileName = "index.html";
        }
        /*
         * Add html postfix when it did not present
         */
        if (fileName.length() < htmlPostfix.length()) {
            fileName += ".html";
        } else if (!fileName.substring(fileName.length() - htmlPostfix.length(),
                fileName.length()).equals(htmlPostfix)) {
            fileName += ".html";
        }

        SimpleWriter fileOut = new SimpleWriter1L(fileName);

        XMLTree xml = new XMLTree1(seed);

        if (xml.label().equals("feeds") && xml.hasAttribute("title")) {
            /*
             * html header
             */
            fileOut.println("<html>");
            fileOut.println("<head>");
            fileOut.println(
                    "<title>" + xml.attributeValue("title") + "</title>");
            fileOut.println("</head>\n");

            /*
             * html body
             */
            fileOut.println("<body>");
            fileOut.println("<h2>" + xml.attributeValue("title") + "</h2>");
            fileOut.println("<ul>");
            int counter = 0;
            while (counter < xml.numberOfChildren()) {
                if (xml.child(counter).isTag()
                        && xml.child(counter).label().equals("feed")
                        && xml.child(counter).hasAttribute("url")
                        && xml.child(counter).hasAttribute("name")
                        && xml.child(counter).hasAttribute("file")) {
                    fileOut.println("<li><a href=\""
                            + xml.child(counter).attributeValue("file") + "\">"
                            + xml.child(counter).attributeValue("name") + "</a>"
                            + "</li>");
                    processFeed(xml.child(counter).attributeValue("url"),
                            xml.child(counter).attributeValue("file"), out);
                }
                counter++;
            }
            fileOut.println("</ul>");
            fileOut.println("</body>");
            fileOut.println("</html>");
            out.println("\n" + fileName + " has been generated");
        } else {
            out.println("Please enter a valid feed");
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
        fileOut.close();
    }
}