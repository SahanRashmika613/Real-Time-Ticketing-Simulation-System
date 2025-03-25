package com.real_time.ticket.system.real_time.ticket.system.entities;

// Importing necessary annotations for database entity mapping
import jakarta.persistence.Entity; // Marks this class as a database entity
import jakarta.persistence.Id;    // Identifies the primary key of the entity

// This class represents the configuration settings stored in the database
@Entity
public class ConfigurationEntity {

    // Primary key for the configuration (Assumes a single configuration entry)
    @Id
    private Long id = 1L; // The ID is fixed at 1 since only one configuration is expected

    // Variable to store the rate at which consumers retrieve tickets
    private int consumerRetrievalRate;

    // Variable to store the rate at which tickets are released
    private int ticketReleaseRate;

    // Getter method to retrieve the consumer retrieval rate from the database
    public int getConsumerRetrievalRate() {
        return consumerRetrievalRate;
    }

    // Setter method to update the consumer retrieval rate in the database
    public void setConsumerRetrievalRate(int consumerRetrievalRate) {
        this.consumerRetrievalRate = consumerRetrievalRate;
    }

    // Getter method to retrieve the ticket release rate from the database
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    // Setter method to update the ticket release rate in the database
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
}
