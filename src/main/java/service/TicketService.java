package service;

import app.App;
import dao.DaoFactory;
import dao.TicketDao;
import entity.Ticket;

import java.sql.Timestamp;
import java.util.Scanner;

public class TicketService {
    public static void submitTicket(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the ticket amount:");
        float ticketAmount = scanner.nextFloat();
        System.out.println("Please enter a description for the ticket:");
        String description = scanner.nextLine();

        Ticket ticket = new Ticket(App.currentUser.getId(), ticketAmount, description, "pending", new Timestamp(System.currentTimeMillis()));
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.insert(ticket);

        System.out.println("You have now submitted the ticket for approval!");
    }
}