import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeExploration() {
    }

    /**
     * Output information about the middle child of the given {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose middle child is to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires <pre>
     * [the label of the root of xt is a tag]  and
     * [xt has at least one child]  and  out.is_open
     * </pre>
     * @ensures <pre>
     * out.content = #out.content * [the label of the middle child
     *  of xt, whether the root of the middle child is a tag or text,
     *  and if it is a tag, the number of children of the middle child]
     * </pre>
     */
    private static void printMiddleNode(XMLTree xt, SimpleWriter out) {
        int middle = xt.numberOfChildren() / 2;
        if (xt.child(middle).isTag()) {
            if (middle % 2 == 0) {
                out.print(xt.child(middle - 1));
                out.print(xt.child(middle).numberOfChildren());
            } else {
                out.print(xt.child(middle));
                out.print(xt.child(middle).numberOfChildren());
            }

        }
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
        XMLTree xml = new XMLTree1(
                "http://web.cse.ohio-state.edu/software/2221/web-sw1/"
                        + "extras/instructions/xmltree-model/columbus-weather.xml");
        /*
         * Put your main program code here
         */

        //Constructing and Displaying an XMLTree

        out.println(xml);
        xml.display();

        out.println(xml.isTag());
        out.println(xml.label());

        XMLTree results = xml.child(0);
        //results.display();

        XMLTree channel = results.child(0);
        //channel.display();
        out.println(channel.numberOfChildren());

        XMLTree title = channel.child(1);
        //title.display();

        XMLTree titleText = title.child(0);
        out.println(titleText.label());

        out.println(xml.child(0).child(0).child(1).child(0).label());

        final int ten = 10;
        XMLTree astronomy = channel.child(ten);
        astronomy.hasAttribute("sunset");
        astronomy.hasAttribute("midday");
        out.println(astronomy.attributeValue("sunrise") + ", "
                + astronomy.attributeValue("sunset"));

        printMiddleNode(channel, out);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
