package DaoTest;

import dao.DaoFactory;
import dao.TicketDao;
import dao.TicketDaoImpl;
import entity.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestTicketDao {

    private TicketDao ticketDao;

    @Before
    public void initTables() {
        ticketDao = DaoFactory.getTicketDao();
        ticketDao.initTables();
    }

    @Test
    public void testInsertTicket() {
       Timestamp testTime = Timestamp.valueOf(LocalDateTime.now());
        Ticket ticket = new Ticket(1, 10, "description", "status", testTime);
        ticketDao.insert(ticket);
        assertEquals(1,ticket.getId());

    }

    @Test
    public void testDeleteTicket() {
        boolean remove = ticketDao.delete(1);
        Ticket ticket = ticketDao.getTicketById(1);
        assertTrue(remove);
        assertNull(ticket);
    }

   /* @Test
    public void testGetTicket() {
        ticketDao.fillTables();
        Ticket ticket = ticketDao.getTicket(     ); //<-------"NOT SURE WHAT I SHOULD HAVE INSIDE PARENTHESIS

        //not fixed below here
        assertEquals(1, ticket.getId());
        assertEquals(10, ticket.getTicketAmount());
        assertEquals("description", ticket.getDescription());
        assertEquals("status", ticket.getStatus());
        assertEquals("Timestamp", ticket.getTimestamp());

    }*/

    @Test
    public void testGetTicketById() {
        ticketDao.fillTables();
        Ticket ticket = ticketDao.getTicketById(1);
        assertEquals("description", ticket.getDescription());
    }


    @Test
    public void testGetTicketsByEmployeeId() {
        ticketDao.fillTables();
        Ticket ticket = (Ticket) ticketDao.getTicketsByEmployeeId(1);
        assertEquals("description", ticket.getDescription());
    }

    @Test
    public void testGetAllTickets() {
        ticketDao.fillTables();
        Ticket ticket = (Ticket) ticketDao;
        assertEquals("Ticket{id=1, ticketAmount=10, description='UPDATED description', status= 'UPDATED status', timestamp= 'updatedTime'}");
    }


    @Test
    public void testUpdate() {
        ticketDao.fillTables();
        Timestamp updatedTime = Timestamp.valueOf(LocalDateTime.now());
        Ticket ticket = new Ticket(1, 10, "UPDATED description", "UPDATED status", updatedTime);
        ticketDao.update(ticket);
        Ticket tickDB = ticketDao.getTicketById(1);
        assertEquals(1, ticket.getId());
        assertEquals(10, ticket.getTicketAmount());
        assertEquals("UPDATED description", ticket.getDescription());
        assertEquals("UPDATED status", ticket.getStatus());
        assertEquals("Timestamp", updatedTime);
    }

   /*@Test
    public void testNotUpdateTicket() {
        Ticket ticket = ticketDao.getTicketById(1);
        TicketDaoImpl ticketTest =mock(TicketDaoImpl.class);
        Timestamp updatedTime = Timestamp.valueOf(LocalDateTime.now());
        Ticket updatedTicket = new Ticket(5, 10, "description", "status", updatedTime);

        try {
            when(ticketTest.update(updatedTicket)).thenThrow(SQLException.class);
            ticketTest.update(updatedTicket);
            Assertions.fail();
        }
        catch(SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }*/




}



