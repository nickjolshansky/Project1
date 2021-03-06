package dao;

import CustomLists.CustomArrayList;
import CustomLists.List;
import entity.User;

import java.sql.*;

public class UserDaoImpl implements UserDao{
    Connection connection;
    public UserDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }


    @Override
    public void insert(User user) {
        String sql = "insert into users (id, username, pass, isManager) values (default, ?, ?, ?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setBoolean(3, user.isManager());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("User created successfully.");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int id = resultSet.getInt(1);
                user.setId(id);
                System.out.println("generated id for this user is " + id);
            }
            else{
                System.out.println("Something went wrong adding the account");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User update(User user) {
        String sql = "update users set username = ?, pass = ?, isManager = ? where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setBoolean(3, user.isManager());
            preparedStatement.setInt(4, user.getId());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("Update Successful!");

                return user;
            }
            else{
                System.out.println("something went wrong with the update");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean delete(int idData) {
        String sql = "delete from users where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);

            int count = preparedStatement.executeUpdate();

            if(count == 1){
                System.out.println("Deletion successful");
                return true;
            }
            else{
                System.out.println("something went wrong");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(ResultSet resultSet){
        try{
            int idData = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("pass");
            boolean isManager = resultSet.getBoolean("isManager");

            return new User(idData, username, password, isManager);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserById(int idData) {
        String sql = "select * from users where id = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getUser(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new CustomArrayList<>();

        String sql = "select * from users;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = getUser(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean checkUsername(String username){
        boolean foundMatch = false;
        String sql = "SELECT * FROM users WHERE username like ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                foundMatch = true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return foundMatch;
    }

    @Override
    public User loginUser(String username, String password){
        String sql = "SELECT * FROM users WHERE username like '" + username + "' AND pass like '" + password + "';";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = getUser(resultSet);
                return user;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void initTables() {
        String sql = "CREATE TABLE users(id serial primary key, username VARCHAR(50), pass VARCHAR(50), isManager BOOLEAN);";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillTables() {
        String sql = "INSERT INTO users(id, username, pass, isManager) values (default, 'username 1', 'pass 1', true);\n";
        sql += "INSERT INTO users(id, username, pass, isManager) values (default, 'username 2', 'pass 2', true);\n";
        sql += "INSERT INTO users(id, username, pass, isManager) values (default, 'username 3', 'pass 3', true);";
        try{
            Statement statement =connection.createStatement();
            statement.execute(sql);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
