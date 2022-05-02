package dao;

public class DaoFactory {
    private static UserDao userDao;
    private static TicketDao ticketDao;


    private DaoFactory(){}

    public static UserDao getUserDao(){
        if(userDao == null){
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    public static TicketDao getTicketDao(){
        if(ticketDao == null){
            ticketDao = new TicketDaoImpl();
        }

        return ticketDao;
    }
}