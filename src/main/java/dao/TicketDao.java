package dao;

import CustomLists.List;
import entity.Ticket;

import java.sql.ResultSet;

public interface TicketDao {
    public void insert(Ticket ticket);
    public Ticket update(Ticket ticket);
    public boolean delete(int id);
    public Ticket getTicket(ResultSet resultSet);
    public Ticket getTicketById(int id);
    public List<Ticket> getTicketsByEmployeeId(int id);
    public List<Ticket> getAllTickets();
}
