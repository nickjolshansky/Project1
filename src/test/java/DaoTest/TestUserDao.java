package DaoTest;

import CustomLists.List;
import dao.DaoFactory;
import dao.UserDao;
import entity.Ticket;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUserDao {


    private UserDao userDao;

    @Before
    public void initTables() {
        userDao = DaoFactory.getUserDao();
        userDao.initTables();
    }

    @Test
    public void testInsertUser() {
        User user = new User("username", "password", true);
        userDao.insert(user);
        assertEquals(1,user.getId());

    }

  /* @Test
    public void testGetUser() {
        userDao.fillTables();
        User user = userDao.getUser(      ); //<-------"NOT SURE WHAT I SHOULD HAVE INSIDE PARENTHESIS
        assertEquals("1", user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPass());
        assertEquals(true, user.isManager());

    }*/

    @Test
    public void testGetUserById() {
        userDao.fillTables();
        User user = userDao.getUserById(1);
        assertEquals("user 1", user.getUsername());
    }


    @Test
    public void testUpdate() {
        userDao.fillTables();
        User user = new User(2, "Updated username", "Updated password", true);
        userDao.update(user);
        User userdb = userDao.getUserById(2);
        assertEquals("Updated username", userdb.getUsername());
        assertEquals("Updated password", userdb.getPass());
        assertEquals(true, userdb.isManager());
    }

    @Test
    public void testDeleteUser() {
        boolean remove = userDao.delete(1);
        User user = userDao.getUserById(1);
        assertTrue(remove);
        assertNull(user);
    }

    @Test
    public void testGetAllUsers() {

        userDao.fillTables();
        List<User> users = userDao.getAllUsers();
        //User users = (User) userDao.getAllUsers();
        assertEquals("Users{id=1, userName='user 1', pass='password 1', isManager= 'true'}", users.get(0).toString());
        assertEquals("Users{id=2, userName='user 2', pass='password 2', isManager= 'true'}", users.get(1).toString());
        assertEquals("Users{id=3, userName='user 3', pass='password 3', isManager= 'true'}", users.get(2).toString());


    }

    @Test
    public void testCheckUsername() {

    }

    @Test
    public void testLoginUser() {

    }
}