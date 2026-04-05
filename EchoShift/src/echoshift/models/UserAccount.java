package echoshift.models;

public class UserAccount {
    private String id;
    private String username;
    private String password;
    private String role;

    public UserAccount() {
    }

    public UserAccount(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}