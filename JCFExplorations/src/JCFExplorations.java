import components.map.Map;
import components.naturalnumber.NaturalNumber;

/**
 * Simple class to experiment with the Java Collections Framework and how it
 * compares with the OSU CSE collection components.
 *
 * @author Yifan Yao
 *
 */
public final class JCFExplorations {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private JCFExplorations() {
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires [the salaries in map are positive] and raisePercent > 0
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(components.map.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        Map<String, Integer> m = map.newInstance();

        int rasiePercentD = 1 + raisePercent / 100;

        for (int i = 0; i < map.size(); i++) {
            components.map.Map.Pair<String, Integer> x = map.removeAny();

            if (x.key().charAt(0) == initial) {
                m.add(x.key(), x.value() * rasiePercentD);
            } else {
                m.add(x.key(), x.value());
            }
        }
        map.transferFrom(m);

    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires <pre>
     * [the salaries in map are positive] and raisePercent > 0 and
     * [the dynamic types of map and of all objects reachable from map
     *  (including any objects returned by operations (such as
     *  entrySet() and, from there, iterator()), and so on,
     *  recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(java.util.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        for (java.util.Map.Entry<String, Integer> x : map.entrySet()) {
            if (x.getKey().charAt(0) == initial) {
                x.setValue(x.getValue() * (initial));
            }
        }

    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(components.set.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        components.set.Set<NaturalNumber> s = set.newInstance();

        while (set.size() > 0) {
            NaturalNumber n = set.removeAny();

            n.increment();
            s.add(n);
        }

        set.transferFrom(s);

    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @requires <pre>
     * [the dynamic types of set and of all objects reachable from set
     *  (including any objects returned by operations (such as iterator()), and
     *  so on, recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(java.util.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        NaturalNumber[] n = new NaturalNumber[set.size()];
        set.toArray(n);
        set.clear();

        for (int i = 0; i < n.length; i++) {
            n[i].increment();
            set.add(n[i]);
        }

    }

}
