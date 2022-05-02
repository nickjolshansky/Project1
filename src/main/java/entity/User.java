package entity;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean isManager;

    public User(int id, String username, String password, boolean isEmployee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isManager = isEmployee;
    }

    public User(String username, String password, boolean isEmployee) {
        this.username = username;
        this.password = password;
        this.isManager = isEmployee;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsEmployee(boolean employee) {
        isManager = employee;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEmployee='" + isManager + '\'' +
                '}';
    }
}
