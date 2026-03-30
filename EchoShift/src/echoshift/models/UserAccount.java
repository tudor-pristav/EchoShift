package echoshift.models;

public class UserAccount {
    private String id;
    private String username;
    private String password;
    private String role;
    private int coin;

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

    public int getCoinBalance() {return coin;}
}
