package echoshift.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the accounts.json structure.
 * Stores a list of user accounts.
 *
 * @author Tudor Mihai Pristav
 */
public class AccountsFile {
    private List<UserAccount> accounts;

    /**
     * Initializes an empty accounts list.
     */
    public AccountsFile() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Returns all user accounts.
     *
     * @return list of accounts
     */
    public List<UserAccount> getAccounts() {
        return accounts;
    }

    /**
     * Sets the list of user accounts.
     *
     * @param accounts list of accounts
     */
    public void setAccounts(List<UserAccount> accounts) {
        this.accounts = accounts;
    }
}