package dao;

import CustomLists.CustomArrayList;
import CustomLists.List;
import entity.Ticket;

import java.sql.*;

public class TicketDaoImpl implements TicketDao{
    Connection connection;
    public TicketDaoImpl(){
        connection = ConnectionFactory.getConnection();
    }


    @Override
    public void insert(Ticket ticket) {
        String sql = "insert into tickets (id, employeeId, ticketAmount, description, status, submissionTime) values (default, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticket.getEmployeeId());
            preparedStatement.setFloat(2, ticket.getTicketAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());
            preparedStatement.setTimestamp(5, ticket.getTimestamp());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("User created successfully.");

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();

                int id = resultSet.getInt(1);
                ticket.setId(id);
                System.out.println("generated id for this ticket is " + id);
            }
            else{
                System.out.println("Something went wrong adding the ticket");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Ticket update(Ticket ticket) {
        String sql = "update tickets set employeeId = ?, ticketAmount = ?, description = ?, status = ?, submissionTime = ? where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticket.getEmployeeId());
            preparedStatement.setFloat(2, ticket.getTicketAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());
            preparedStatement.setTimestamp(5, ticket.getTimestamp());
            preparedStatement.setInt(6, ticket.getId());

            int count = preparedStatement.executeUpdate();
            if(count == 1){
                System.out.println("Update Successful!");
                return ticket;
            }
            else{
                System.out.println("something went wrong with the update");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int idData) {
        String sql = "delete from tickets where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);

            int count = preparedStatement.executeUpdate();

            if(count == 1){
                System.out.println("Deletion successful");
                return true;
            }
            else{
                System.out.println("something went wrong");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Ticket getTicket(ResultSet resultSet) {
        try{
            int idData = resultSet.getInt("id");
            int employeeId = resultSet.getInt("employeeId");
            float ticketAmount = resultSet.getFloat("ticketAmount");
            String description = resultSet.getString("description");
            String status = resultSet.getString("status");
            Timestamp timestamp = resultSet.getTimestamp("submissionTime");

            return new Ticket(idData, employeeId, ticketAmount, description, status, timestamp);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Ticket getTicketById(int idData) {
        String sql = "select * from tickets where id = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getTicket(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Ticket> getTicketsByEmployeeId(int idData) {
        List<Ticket> tickets = new CustomArrayList<>();
        String sql = "select * from tickets where employeeId = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idData);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Ticket ticket = getTicket(resultSet);
                tickets.add(ticket);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new CustomArrayList<>();

        String sql = "select * from tickets;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Ticket ticket = getTicket(resultSet);
                tickets.add(ticket);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public void initTables() {

    }

    @Override
    public void fillTables() {

    }
}
