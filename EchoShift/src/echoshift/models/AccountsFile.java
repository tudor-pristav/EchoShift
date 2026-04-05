package echoshift.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class matching the structure of accounts.json.
 */
public class AccountsFile {
    private List<UserAccount> accounts;

    public AccountsFile() {
        this.accounts = new ArrayList<>();
    }

    public List<UserAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<UserAccount> accounts) {
        this.accounts = accounts;
    }
}