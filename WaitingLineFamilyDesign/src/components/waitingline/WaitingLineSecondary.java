package components.waitingline;

import java.util.Iterator;

/**
 * Layered implementations of secondary methods for {@code Queue}.
 *
 * <p>
 * Assuming execution-time performance of O(1) for method {@code iterator} and
 * its return value's method {@code next}, execution-time performance of
 * {@code front} as implemented in this class is O(1). Execution-time
 * performance of {@code replaceFront} and {@code flip} as implemented in this
 * class is O(|{@code this}|). Execution-time performance of {@code append} as
 * implemented in this class is O(|{@code q}|). Execution-time performance of
 * {@code sort} as implemented in this class is O(|{@code this}| log
 * |{@code this}|) expected, O(|{@code this}|^2) worst case. Execution-time
 * performance of {@code rotate} as implemented in this class is
 * O({@code distance} mod |{@code this}|).
 *
 * @param <T>
 *            type of {@code Queue} entries
 */
public abstract class WaitingLineSecondary<T> implements WaitingLine<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /*
     * Public members ---------------------------------------------------------
     */

    /**
     * Finds and returns the position of {@code x} in {@code this}.
     *
     * @param x
     *            the entry to find its position in {@code this}
     * @return the position
     * @requires x is in entries {@code this}
     * @ensures position is the position of x
     */
    @Override
    public int position(T x) {

        int pos = 0;
        int curr = 0;

        for (T s : this) {
            if (s.equals(x)) {
                pos = curr;
            } else {
                curr++;
            }
        }

        return pos;
    }

    /**
     * Inserts {@code x} into {@code this} at position {@code pos}, i.e., after
     * the {@code pos}-th entry of {@code this}; and clears {@code x}.
     *
     * @param pos
     *            the position at which to insert
     * @param x
     *            the {@code Sequence} to be inserted
     * @updates this
     * @clears x
     * @requires 0 <= pos and pos <= |this|
     * @ensures this = #this[0, pos) * #x * #this[pos, |#this|)
     */
    @Override
    public void insert(int pos, T x) {

        int curr = 0;
        WaitingLine<T> source = this.newInstance();

        while (curr < pos) {
            source.newCustomer(this.attendCustomer());
            curr++;
        }

        source.newCustomer(x);
        source.append(this);
        this.transferFrom(source);

    }

    /**
     * Concatenates ("appends") {@code source} to the end of {@code this}.
     *
     * @param source
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates this
     * @requires source is intersection of {@code this}
     * @clears source
     * @ensures this = #this *\ #source
     */
    @Override
    public void append(WaitingLine<T> source) {

        while (source.length() > 0) {
            this.newCustomer(source.attendCustomer());
        }

    }

    /*
     * Common methods (from Object) -------------------------------------------
     */

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WaitingLine<?>)) {
            return false;
        }
        WaitingLine<?> q = (WaitingLine<?>) obj;
        if (this.length() != q.length()) {
            return false;
        }
        Iterator<T> it1 = this.iterator();
        Iterator<?> it2 = q.iterator();
        while (it1.hasNext()) {
            T x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;
        /*
         * This code makes hashCode run in O(1) time. It works because of the
         * iterator order string specification, which guarantees that the (at
         * most) samples entries returned by the it.next() calls are the same
         * when the two Queues are equal.
         */
        int n = 0;
        Iterator<T> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            T x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

}
