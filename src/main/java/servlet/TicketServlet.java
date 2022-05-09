package servlet;

import CustomLists.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.TicketDao;
import dao.UserDao;
import entity.Account;
import entity.Ticket;
import entity.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TicketServlet extends HttpServlet {
    private TicketDao ticketDao = DaoFactory.getTicketDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        int idToGet;
        int daoCase = Integer.parseInt(req.getParameter("case"));

        switch (daoCase){
            case 1:
                idToGet = Integer.parseInt(req.getParameter("id"));
                Ticket ticket = ticketDao.getTicketById(idToGet);
                System.out.println(Account.currentUser.toString());
                if(ticket.getEmployeeId() == Account.currentUser.getId() || Account.currentUser.isManager()){
                    out.println(ticket);
                }
                else{
                    out.write("Please choose a valid ticket id.");
                }

                break;

            case 2:
                if(Account.currentUser.isManager()){
                    List<Ticket> allTickets = ticketDao.getAllTickets();
                    out.println("All tickets:");
                    for(int i = 0; i < allTickets.getSize(); i++) {
                        out.println(allTickets.get(i));
                    }
                }
                else{
                    blockAccess(out);
                }

                break;
            case 3:
                if(Account.currentUser.isManager()){
                    List<Ticket> allPendingTickets = ticketDao.getAllPendingTickets();
                    out.println("All pending tickets:");
                    for(int i = 0; i < allPendingTickets.getSize(); i++) {
                        out.println(allPendingTickets.get(i));
                    }
                }
                else{
                    blockAccess(out);
                }

                break;

            case 4:
                idToGet = Integer.parseInt(req.getParameter("employeeId"));
                List<Ticket> allEmployeeTickets = ticketDao.getTicketsByEmployeeId(idToGet);
                out.println("All tickets of specified employee:");
                for(int i = 0; i < allEmployeeTickets.getSize(); i++) {
                    out.println(allEmployeeTickets.get(i));
                }
                break;

            case 5:
                idToGet = Integer.parseInt(req.getParameter("employeeId"));
                List<Ticket> pendingEmployeeTickets = ticketDao.getPendingTicketsByEmployeeId(idToGet);
                out.println("All pending tickets of specified employee:");
                for(int i = 0; i < pendingEmployeeTickets.getSize(); i++) {
                    out.println(pendingEmployeeTickets.get(i));
                }
                break;

            case 6:
                if(Account.currentUser.isManager()){
                    List<Ticket> allTicketsByDate = ticketDao.getAllTicketsByDate();
                    out.println("All tickets by date:");
                    for(int i = 0; i < allTicketsByDate.getSize(); i++) {
                        out.println(allTicketsByDate.get(i));
                    }
                }
                else{
                    List<Ticket> allTicketsByDate = ticketDao.getAllTicketsByDate(Account.currentUser.getId());
                    out.println("All tickets by date:");
                    for(int i = 0; i < allTicketsByDate.getSize(); i++) {
                        out.println(allTicketsByDate.get(i));
                    }
                }
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            ObjectMapper mapper = new ObjectMapper();
            Ticket ticket = mapper.readValue(req.getInputStream(), Ticket.class);

            ticket.setEmployeeId(Account.currentUser.getId());
            ticket.setStatus("pending");
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
        PrintWriter out = resp.getWriter();
        int idToGet = Integer.parseInt(req.getParameter("id"));
        String stringToGet = req.getParameter("status");

        if(Account.currentUser.isManager()){
            Ticket ticket = DaoFactory.getTicketDao().getTicketById(idToGet);
            ticket.setStatus(stringToGet);
            Ticket ticketResult = ticketDao.update(ticket);
            out.write(ticketResult.toString());
        }
        else{
            out.println("You do not have authorization for that action.");
        }

    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        if(Account.currentUser.isManager()){
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
        else{
            blockAccess(resp.getWriter());
        }

    }

    private void blockAccess(PrintWriter out){
        out.println("You do not have authorization to perform that action.");
    }

}
