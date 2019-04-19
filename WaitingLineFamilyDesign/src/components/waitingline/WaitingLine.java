package components.waitingline;

/**
 * {@code QueueKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    T nextCustomer();

    /**
     * Removes and returns {@code x} from {@code this}.
     *
     * @param x
     *            entry to remove
     * @return the removed entry
     * @aliases reference {@code x}
     * @updates this
     * @requires this /= <>
     * @requires #this.contains(x)
     * @ensures <pre>
     * <replaceFront> is prefix of #this  and
     * this = <x> * #this[1, |#this|)
     * </pre>
     * @ensures <pre>
     * this * <x> = #this
     * </pre>
     */
    T pull(T x);

    /**
     * Finds and returns the position of {@code x} in {@code this}.
     *
     * @param x
     *            the entry to find its position in {@code this}
     * @return the position
     * @requires x is in entries {@code this}
     * @ensures position is the position of x
     */
    int position(T x);

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
    void insert(int pos, T x);

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     *
     * @param q
     *            the {@code Queue} to be appended to the end of {@code this}
     * @updates this
     * @clears q
     * @ensures this = #this * #q
     */
    void append(WaitingLine<T> q);

}
