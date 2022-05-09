package entity;

import java.sql.Timestamp;

public class Ticket {
    private int id;
    private int employeeId;
    private float ticketAmount;
    private String description;
    private String status;
    private Timestamp timestamp;

    public Ticket(){}
    public Ticket(int id, int employeeId, float ticketAmount, String description, String status, Timestamp timestamp) {
        this.id = id;
        this.employeeId = employeeId;
        this.ticketAmount = ticketAmount;
        this.description = description;
        this.status = status;
        this.timestamp = timestamp;
    }
    public Ticket(int employeeId, float ticketAmount, String description, String status, Timestamp timestamp) {
        this.employeeId = employeeId;
        this.ticketAmount = ticketAmount;
        this.description = description;
        this.status = status;
        this.timestamp = timestamp;
    }
    public Ticket(float ticketAmount, String description) {
        this.employeeId = Account.currentUser.getId();
        this.ticketAmount = ticketAmount;
        this.description = description;
        this.status = "pending";
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public float getTicketAmount() {
        return ticketAmount;
    }
    public void setTicketAmount(float ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", ticketAmount=" + ticketAmount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", date=" + timestamp +
                '}';
    }
}
