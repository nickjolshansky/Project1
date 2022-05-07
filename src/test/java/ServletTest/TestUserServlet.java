package ServletTest;

import dao.DaoFactory;
import dao.TicketDao;
import dao.UserDao;
import entity.Ticket;
import entity.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import servlet.TicketServlet;
import servlet.UserServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

public class TestUserServlet {


    private static UserDao userDao;

    @BeforeEach
    // since testing using h2 must recreate tables every time
    public void setUp() {
        userDao = DaoFactory.getUserDao();
        userDao.initTables();
        userDao.fillTables();
    }

    @Test
    public void testGetAllUser() throws IOException, ServletException {
        //mock up the request/response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // return writer to mimic response's writer
        when(response.getWriter()).thenReturn(printWriter);

        // create a new ticket servlet and do the get method
        new UserServlet().doGet(request, response);

        // flush the writer to make sure all output is written
        printWriter.flush();

        //assert true that results contain all the proper tickets
        assertTrue(stringWriter.toString().contains("Users{id=1, username='user 1', pass='password 1', ismanager='true'}"));
        assertTrue(stringWriter.toString().contains("Users{id=2, username='user 2', pass='password 2', ismanager='true'}"));
        assertTrue(stringWriter.toString().contains("Users{id=3, username='user 3', pass='password 3', ismanager='true'}"));


    }

    @Test
    public void testGetUserById() throws IOException, ServletException {
        //mock up request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //mocking up the id query parameter of id=1
        when(request.getParameter("id")).thenReturn("1");

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //create a new ticket servlet and do get method
        new UserServlet().doGet(request, response);

        // verify parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush out printWriter to ensure all output is written
        printWriter.flush();

        //assert true that the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Users{id=1, username='user 1', pass='password 1', ismanager='true'}"));

    }

    @Test
    public void TestPostUser() throws IOException, ServletException {
        //mock up request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since taking in a buffered reader to read body, must simulate it by putting mock data
        // in a local file called ticket.txt, this is simulating what we would put in body of request
        FileReader fileReader = new FileReader(" NEED FILE LOCATION");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //configure buffered reader
        when(request.getReader()).thenReturn(bufferedReader);

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //create a new book servlet and do post method
        new UserServlet().doPost(request, response);

        //flush the writer to ensure all output is printed
        printWriter.flush();

        //assert that the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Users{id=1, username='user 1', pass='password 1', ismanager='true'}"));

        //assert that ticket was inserted correctly
        User user = userDao.getUserById(1);
        assertEquals(user.getId(), 1);
        assertEquals(user.getUsername(), "user 1");
        assertEquals(user.getPass(), "password 1");
        assertEquals(user.isManager(), "true");


    }

    @Test
    public void TestPutUser() throws IOException {
        //mock up request and response
        HttpServletRequest request =  mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since take in buffered reader to read body, simulate it by putting mock data in local file called ticket_with_id.txt
        FileReader fileReader = new FileReader("  NEED FILE LOCATION");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //configure request to use buffered reader
        when(request.getReader()).thenReturn(bufferedReader);

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //create a new ticket servlet and do put method
        new UserServlet().doPut(request, response);

        // flush the writer to ensure all output is printed
        printWriter.flush();

        //assert true the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Users{id=3, username='NEW user 3', pass='NEW password 3', ismanager='true'}"));

        //assert that book was updated properly
        User user = userDao.getUserById(3);
        assertEquals(user.getId(), 3);
        assertEquals(user.getUsername(), "NEW user 3");
        assertEquals(user.getPass(), "NEW password 3");
        assertEquals(user.isManager(), "true");
    }

    @Test
    public void testDeleteUser() throws IOException, ServletException {
        //mock request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //configure id parameter
        when(request.getParameter("id")).thenReturn("3");

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        //create a new ticket servlet and do delete method
        new UserServlet().doDelete(request, response);

        //verify the parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush the writer to ensure all output is written
        printWriter.flush();
        assertEquals("Deletion successful", stringWriter.toString());

        //make sure ticket deleted
        assertNull(userDao.getUserById(3));


    }

    @Test
    public void TestDeleteUserNotThere() throws IOException, ServletException {
        //mock request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //configure the id parameter
        when(request.getParameter("id")).thenReturn("5");

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //create a new ticket servlet and do get method
        new UserServlet().doDelete(request, response);

        //verify the parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush the writer to ensure all output is printed
        printWriter.flush();

        assertEquals("Deletion failed!", stringWriter.toString());

    }
}
