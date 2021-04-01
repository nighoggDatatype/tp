package seedu.address.logic.filters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.customer.Customer;


public class NameFilter extends AbstractFilter {
    public static final String MESSAGE_CONSTRAINTS = "Name Filter must contain at least one valid name to test by";
    /*
     * The first character of the search string must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private static final int MISTAKE_THRESHOLD = 3;

    private final String[] nameList;

    /**
     * Creates a filter that filters by name, with tolerances for mis-spelling.
     *
     * @param nameListSingleString String with names to search, separated by spaces.
     */
    public NameFilter(String nameListSingleString) {
        super(nameListSingleString);
        requireNonNull(nameListSingleString);
        checkArgument(isValidFilter(nameListSingleString), MESSAGE_CONSTRAINTS);
        this.nameList = nameListSingleString.split("\\s+");
    }

    /**
     * Returns true if a given string is a valid filter.
     */
    public static boolean isValidFilter(String filterString) {
        return filterString.matches(VALIDATION_REGEX);
    }

    //@@author nighoggDatatype-reused
    //Reused from https://stackoverflow.com/a/13564498/11358676
    private boolean levenshteinDistance(String s1, String s2) {
        return isSubsequence(s1.toCharArray(), s2.toCharArray());
    }

    private int dist(char[] s1, char[] s2) {
        // memoize only previous line of distance matrix
        int[] prev = new int[s2.length + 1];

        for (int j = 0; j < s2.length + 1; j++) {
            prev[j] = j;
        }

        for (int i = 1; i < s1.length + 1; i++) {
            // calculate current line of distance matrix
            int[] curr = new int[s2.length + 1];
            curr[0] = i;

            for (int j = 1; j < s2.length + 1; j++) {
                int d1 = prev[j] + 1;
                int d2 = curr[j - 1] + 1;
                int d3 = prev[j - 1];
                if (s1[i - 1] != s2[j - 1]) {
                    d3 += 1;
                }
                curr[j] = Math.min(Math.min(d1, d2), d3);
            }
            // define current line of distance matrix as previous
            prev = curr;
        }
        return prev[s2.length];
    }

    private boolean isSubsequence(char[] s1, char[] s2) {
        int ptr1 = 0;
        int ptr2 = 0;
        while (ptr1 < s1.length && ptr2 < s2.length) {
            if (s1[ptr1] == s2[ptr2]) {
                ptr2++;
            }

            ptr1++;
        }

        return ptr2 == s2.length;
    }

    @Override
    public boolean test(Customer customer) {
        requireNonNull(customer);
        String[] customerNameTokens = customer.getName().fullName.split("\\s+");
        for (String token : customerNameTokens) {
            for (String possibleName : nameList) {
                if (levenshteinDistance(token, possibleName)) {
                    return true;
                }
            }
        }
        return false;
    }
    //@@author
}