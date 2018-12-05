import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods {@code add} and {@code remove}
 * for {@code Set}.
 *
 * @param <T>
 *            type of {@code Set} elements
 */
public final class SetSecondary1L<T> extends Set1L<T> {

    /**
     * No-argument constructor.
     */
    public SetSecondary1L() {
        super();
    }

    /**
     * Removes and returns an arbitrary element from {@code this}.
     *
     * @return the element removed from {@code this}
     * @updates this
     * @requires |this| > 0
     * @ensures <pre>
     * removeAny is in #this and
     * this = #this \ {removeAny}
     * </pre>
     */
    @Override
    public Set<T> remove(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> removedSet = s.newInstance();
        Set<T> thisCopy = this.newInstance();

        // Transfer this to thisCopy
        // #this = empty
        thisCopy.transferFrom(this);

        // When thisCopy.size() > 0
        while (thisCopy.size() > 0) {
            // Remove one thing from thisCopy to x
            T x = thisCopy.removeAny();
            // If thisCopy contains x
            if (s.contains(x)) {
                removedSet.add(x);
            } else {
                // If thisCopy does not contains x
                this.add(x);
            }
        }

        return removedSet;
    }

    /**
     * Adds to {@code this} all elements of {@code s} that are not already in
     * {@code this}, also removing just those elements from {@code s}.
     *
     * @param s
     *            the {@code Set} whose elements are to be added to {@code this}
     * @updates this, s
     * @ensures <pre>
     * this = #this union #s  and
     * s = #this intersection #s
     * </pre>
     */
    @Override
    public void add(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> sCopy = this.newInstance();
        sCopy.transferFrom(s);

        while (sCopy.size() > 0) {
            T x = sCopy.removeAny();
            if (!this.contains(x)) {
                this.add(x);
            } else {
                s.add(x);
            }
        }
    }

}
