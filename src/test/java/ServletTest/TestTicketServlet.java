package ServletTest;

import dao.DaoFactory;
import dao.TicketDao;
import entity.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import servlet.TicketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestTicketServlet {

    private static TicketDao ticketDao;

    @BeforeEach
    // since testing using h2 must recreate tables every time
    public void setUp() {
        ticketDao = DaoFactory.getTicketDao();
        ticketDao.initTables();
        ticketDao.fillTables();
    }

    @Test
    public void testGetAllTickets() throws IOException, ServletException {
        //mock up the request/response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // return writer to mimic response's writer
        when(response.getWriter()).thenReturn(printWriter);

        // create a new ticket servlet and do the get method
        new TicketServlet().doGet(request, response);

        // flush the writer to make sure all output is written
        printWriter.flush();

        //assert true that results contain all the proper tickets
        assertTrue(stringWriter.toString().contains("Ticket{id=1, customerid=1, ticketAmount=1.00, description='description 1', status='status 1', timestamp='timestamp'}"));
        assertTrue(stringWriter.toString().contains("Ticket{id=2, customerid=2, ticketAmount=2.00, description='description 2', status='status 2', timestamp='timestamp'}"));
        assertTrue(stringWriter.toString().contains("Ticket{id=3, customerid=3, ticketAmount=3.00, description='description 3', status='status 3', timestamp='timestamp'}"));


    }

    @Test
    public void testGetTicketById() throws IOException, ServletException {
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
        new TicketServlet().doGet(request, response);

        // verify parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush out printWriter to ensure all output is written
        printWriter.flush();

        //assert true that the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Ticket{id=1, customerid=1, ticketAmount=1.00, description='description 1', status='status 1', timestamp='timestamp'}"));

    }

    @Test
    public void TestPostTicket() throws IOException, ServletException {
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
        new TicketServlet().doPost(request, response);

        //flush the writer to ensure all output is printed
        printWriter.flush();

        //assert that the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Ticket{id=4, employeeId=4, ticketAmount=4.00, description='description 4', status='status 4', timestamp='timestamp'}"));

        //assert that ticket was inserted correctly
        Ticket ticket = ticketDao.getTicketById(4);
        assertEquals(ticket.getEmployeeId(), 4);
        assertEquals(ticket.getTicketAmount(), 4.00);
        assertEquals(ticket.getDescription(), "description 4");
        assertEquals(ticket.getStatus(), "status 4");
        assertEquals(ticket.getTimestamp(), "timestamp");


    }

    @Test
    public void TestPutTicket() throws IOException {
        //mock up request and response
        HttpServletRequest request =  mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since take in buffered reader to read body,
        // simulate it by putting mock data in local file called ticket_with_id.txt
        FileReader fileReader = new FileReader("  NEED FILE LOCATION");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //configure request to use buffered reader
        when(request.getReader()).thenReturn(bufferedReader);

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //create a new ticket servlet and do put method
        new TicketServlet().doPut(request, response);

        // flush the writer to ensure all output is printed
        printWriter.flush();

        //assert true the result contains the proper ticket
        assertTrue(stringWriter.toString().contains("Ticket{id=3, customerid=3, ticketAmount=3.00, description='NEW description 3', status='NEW status 3', timestamp='timestamp'}"));

        //assert that book was updated properly
        Ticket ticket = ticketDao.getTicketById(3);
        assertEquals(ticket.getEmployeeId(), 3);
        assertEquals(ticket.getTicketAmount(), 3.00);
        assertEquals(ticket.getDescription(), "NEW description 3");
        assertEquals(ticket.getStatus(), "NEW status 3");
        assertEquals(ticket.getTimestamp(), "timestamp");
    }

    @Test
    public void testDeleteTicket() throws IOException, ServletException {
        //mock request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        //configure id parameter
        when(request.getParameter("id")).thenReturn("3");

        //set up print writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        //create a new ticket servlet and do delete method
        new TicketServlet().doDelete(request, response);

        //verify the parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush the writer to ensure all output is written
        printWriter.flush();
        assertEquals("Deletion successful", stringWriter.toString());

        //make sure ticket deleted
        assertNull(ticketDao.getTicketById(3));


    }

    @Test
    public void TestDeleteTicketNotThere() throws IOException, ServletException {
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
        new TicketServlet().doDelete(request, response);

        //verify the parameter
        verify(request, atLeast(1)).getParameter("id");

        //flush the writer to ensure all output is printed
        printWriter.flush();

        assertEquals("Deletion failed!", stringWriter.toString());

    }
}
