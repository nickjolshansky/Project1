package dao;

import CustomLists.List;
import entity.User;

import java.sql.ResultSet;

public interface UserDao {
    public void insert(User user);
    public User update(User user);
    public boolean delete(int id);
    public User getUser(ResultSet resultSet);
    public User getUserById(int id);
    public List<User> getAllUsers();
    public boolean checkUsername(String username);
    public User loginUser(String username, String password);
    public void initTables();
    public void fillTables();
}
