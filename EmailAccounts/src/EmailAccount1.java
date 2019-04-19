import components.map.Map;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Yifan Yao
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Client's first name.
     */
    private static String firstName;

    /**
     * Client's last name.
     */
    private static String lastName;

    /**
     * List of client's name and their account number.
     */
    private static Map<String, Integer> accountNumber;

    /**
     * Client's dot number.
     */
    private static int dotNumber;

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        EmailAccount1 account = new EmailAccount1(firstName, lastName);

    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return EmailAccount1.firstName + " " + EmailAccount1.lastName;
    }

    @Override
    public String emailAddress() {

        int number = 0;

        if (EmailAccount1.accountNumber
                .hasKey(EmailAccount1.lastName.toLowerCase())) {
            Map.Pair<String, Integer> p = EmailAccount1.accountNumber
                    .remove(EmailAccount1.lastName.toLowerCase());
            number = p.value() + 1;
            EmailAccount1.accountNumber
                    .add(EmailAccount1.lastName.toLowerCase(), number);
        } else {
            number = 1;
            EmailAccount1.accountNumber
                    .add(EmailAccount1.lastName.toLowerCase(), number);
        }

        String email = EmailAccount1.lastName.toLowerCase() + "." + number
                + "@osu.edu";

        return email;
    }

    @Override
    public String toString() {

        String name = "Name: " + EmailAccount1.firstName
                + EmailAccount1.lastName;
        String email = "Email: " + EmailAccount1.lastName.toLowerCase() + "."
                + EmailAccount1.dotNumber + "@osu.edu";

        return name + ",  " + email;
    }

}
