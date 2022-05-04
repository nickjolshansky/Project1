package servlet;

import CustomLists.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.TicketDao;
import dao.UserDao;
import entity.Ticket;
import entity.User;
import service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TicketServlet extends HttpServlet {
    private TicketDao ticketDao = DaoFactory.getTicketDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        int idToGet;

        try {
            idToGet = Integer.parseInt(req.getParameter("id"));
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            List<Ticket> tickets = ticketDao.getAllTickets();
            out.println("All tickets:");
            for(int i = 0; i < tickets.getSize(); i++) {
                out.println(tickets.get(i));
            }
            return;
        }

        Ticket ticket = ticketDao.getTicketById(idToGet);
        out.println(ticket);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            ObjectMapper mapper = new ObjectMapper();
            Ticket ticket = mapper.readValue(req.getInputStream(), Ticket.class);

            ticketDao.insert(ticket);

            resp.setStatus(203);
            resp.getWriter().print("Success!");
        }
        catch(IOException e){
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);
        Ticket ticketResult = ticketDao.update(ticket);
        PrintWriter out = resp.getWriter();
        out.write(ticketResult.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int idToDelete = Integer.parseInt(req.getParameter("id"));

        boolean success = ticketDao.delete(idToDelete);
        if(success) {
            resp.setStatus(200);
            resp.getWriter().print("Deletion successful");
        }
        else {
            resp.sendError(500, "Deletion failed!");
        }
    }
}
