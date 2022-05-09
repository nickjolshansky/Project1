package servlet;

import CustomLists.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
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

public class UserServlet extends HttpServlet {
    UserDao userDao = DaoFactory.getUserDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        int idToGet;

        try {
            idToGet = Integer.parseInt(req.getParameter("id"));
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            List<User> users = userDao.getAllUsers();
            out.println("All users:");
            for(int i = 0; i < users.getSize(); i++) {
                out.println(users.get(i));
            }
            return;
        }

        User user = userDao.getUserById(idToGet);
        out.println(user);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(req.getInputStream(), User.class);

            userDao.insert(user);

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
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(req.getReader(), User.class);
        User userResult = DaoFactory.getUserDao().loginUser(user.getUsername(), user.getPass());
        PrintWriter out = res.getWriter();
        out.write("You are now logged in as " + userResult.getUsername());

        Account.currentUser = userResult;
        System.out.println(userResult);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int idToDelete = Integer.parseInt(req.getParameter("id"));

        boolean success = userDao.delete(idToDelete);
        if(success) {
            resp.setStatus(200);
            resp.getWriter().print("Deletion successful");
        }
        else {
            resp.sendError(500, "Deletion failed!");
        }
    }
}
