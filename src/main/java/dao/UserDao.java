package dao;

import CustomLists.List;
import entity.User;

import java.sql.ResultSet;

public interface UserDao {
    public void insert(User user);
    public void update(User user);
    public void delete(int id);
    public User getUser(ResultSet resultSet);
    public User getUserById(int id);
    public List<User> getAllUsers();
    public boolean checkUsername(String username);
    public User loginUser(String username, String password);
}
