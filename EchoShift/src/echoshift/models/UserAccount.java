package echoshift.models;

/**
 * Represents a user account with credentials and role.
 *
 * @author Tudor Mihai Pristav
 */
public class UserAccount {
    private String id;
    private String username;
    private String password;
    private String role;

    /**
     * Default constructor for deserialization.
     */
    public UserAccount() {
    }

    /**
     * Creates a user account.
     *
     * @param id unique user ID
     * @param username account username
     * @param password account password
     * @param role account role
     */
    public UserAccount(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * @return user ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return user role
     */
    public String getRole() {
        return role;
    }
}