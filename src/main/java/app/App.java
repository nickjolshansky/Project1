package app;

import entity.User;
import service.UserService;

import java.util.Scanner;

public class App {
    public static boolean loggedIn;
    public static User currentUser;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean breakFlag = true;
        while(breakFlag){
            if (!loggedIn) {
                System.out.println("Please select an option:");
                System.out.println("1. Create new user");
                System.out.println("2. Login");
                System.out.println("3. Quit application");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        UserService.createNewUser();
                        break;

                    case 2:
                        UserService.loginUser();
                        break;

                    case 3:
                        breakFlag = false;
                        break;

                    default:
                        System.out.println("Please enter a valid option.");
                }
            }
            else{
                if(currentUser.isManager()){
                    System.out.println("Please select an option:");
                    System.out.println("1. Lookup an ticket by id");
                    System.out.println("2. Lookup an employee's tickets");
                    System.out.println("3. Lookup all tickets");
                    System.out.println("4. Lookup all pending tickets");
                    System.out.println("5. Logout");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            //TicketService.getTicketById();
                            break;

                        case 2:
                            //TicketService.getTicketsByUserId();
                            break;

                        case 3:
                            //TicketService.getAllTickets();
                            break;

                        case 4:
                            //TicketService.getAllPendingTickets()
                            break;

                        case 5:
                            loggedIn = false;
                            currentUser = null;
                            break;

                        default:
                            System.out.println("please enter a valid option.");
                            break;
                    }
                }
                else{
                    System.out.println("Please select an option:");
                    System.out.println("1. Submit a new ticket");
                    System.out.println("2. Lookup all tickets");
                    System.out.println("3. Lookup pending tickets");
                    System.out.println("4. Lookup all tickets by date");
                    System.out.println("5. Logout");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            //TicketService.submitTicket();
                            break;

                        case 2:
                            //TicketService.getTicketsByUserId();
                            break;

                        case 3:
                            //TicketService.getAllPendingTicketsByUserId();
                            break;

                        case 4:
                            //TicketService.getAllTicketsByUserIdByDate();
                            break;

                        case 5:
                            loggedIn = false;
                            currentUser = null;
                            break;

                        default:
                            System.out.println("please enter a valid option.");
                            break;
                    }
                }
            }
            System.out.println();
        }
    }
}

/*
TO DO:
tests
 */