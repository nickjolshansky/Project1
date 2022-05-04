package entity;

public class User {
    private int id;
    private String username;
    private String pass;
    private boolean isManager;


    public User(){}
    public User(int id, String username, String pass, boolean isManager) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.isManager = isManager;
    }
    public User(String username, String pass, boolean isManager) {
        this.username = username;
        this.pass = pass;
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + pass + '\'' +
                ", isManager='" + isManager + '\'' +
                '}';
    }
}
