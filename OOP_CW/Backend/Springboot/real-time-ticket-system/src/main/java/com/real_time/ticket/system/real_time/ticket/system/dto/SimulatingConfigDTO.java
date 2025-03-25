package com.real_time.ticket.system.real_time.ticket.system.dto;

// Importing validation classes to check input values
import jakarta.validation.constraints.Min;      // Ensures minimum value for variables
import jakarta.validation.constraints.NotNull; // Ensures variables cannot be null

// This class is used to hold the configuration settings for the simulation
public class SimulatingConfigDTO {

    // Number of total tickets available (cannot be null and must be at least 1)
    @NotNull(message = "Total tickets cannot be null")
    @Min(value = 1, message = "Total tickets must be greater than or equal to 1")
    private int totalTickets;

    // Rate at which tickets are released (cannot be null and must be at least 1)
    @NotNull(message = "Ticket release rate cannot be null")
    @Min(value = 1, message = "Ticket release rate must be greater than or equal to 1")
    private int ticketReleaseRate;

    // Rate at which consumers retrieve tickets (cannot be null and must be at least 1)
    @NotNull(message = "Consumer retrieval rate cannot be null")
    @Min(value = 1, message = "Consumer retrieval rate must be greater than or equal to 1")
    private int consumerRetrievalRate;

    // Maximum capacity of tickets in the system (cannot be null and must be at least 1)
    @NotNull(message = "Max ticket capacity cannot be null")
    @Min(value = 1, message = "Max ticket capacity must be greater than or equal to 1")
    private int maxTicketCapacity;

    // Default constructor (used when no values are provided)
    public SimulatingConfigDTO() {}

    // Constructor with parameters to initialize all settings at once
    public SimulatingConfigDTO(int totalTickets, int ticketReleaseRate, int consumerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;                 // Set the total number of tickets
        this.ticketReleaseRate = ticketReleaseRate;       // Set the ticket release rate
        this.consumerRetrievalRate = consumerRetrievalRate; // Set the consumer retrieval rate
        this.maxTicketCapacity = maxTicketCapacity;       // Set the maximum ticket capacity
    }

    // Getter method to retrieve the total number of tickets
    public int getTotalTickets() {
        return totalTickets;
    }

    // Setter method to update the total number of tickets
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    // Getter method to retrieve the ticket release rate
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    // Setter method to update the ticket release rate
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    // Getter method to retrieve the consumer retrieval rate
    public int getConsumerRetrievalRate() {
        return consumerRetrievalRate;
    }

    // Setter method to update the consumer retrieval rate
    public void setConsumerRetrievalRate(int consumerRetrievalRate) {
        this.consumerRetrievalRate = consumerRetrievalRate;
    }

    // Getter method to retrieve the maximum ticket capacity
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    // Setter method to update the maximum ticket capacity
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Method to update ticket rates from database values (if the system syncs with a database)
    public void syncWithDatabaseValues(int dbTicketReleaseRate, int dbConsumerRetrievalRate) {
        this.ticketReleaseRate = dbTicketReleaseRate;         // Update ticket release rate with database value
        this.consumerRetrievalRate = dbConsumerRetrievalRate; // Update consumer retrieval rate with database value
    }

    // Converts the configuration settings into a readable string format
    @Override
    public String toString() {
        return "SimulatingConfigDTO{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", consumerRetrievalRate=" + consumerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}
