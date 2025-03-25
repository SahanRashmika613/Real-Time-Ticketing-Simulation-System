package com.real_time.ticket.system.real_time.ticket.system.services;

// Import the ConfigurationEntity class to use configuration-related data
import com.real_time.ticket.system.real_time.ticket.system.entities.ConfigurationEntity;
// Import the ConfigurationRepo interface to interact with the database
import com.real_time.ticket.system.real_time.ticket.system.repo.ConfigurationRepo;
// Import Logger for printing messages during execution
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Import Service annotation to mark this class as a service for business logic
import org.springframework.stereotype.Service;

// This annotation marks this class as a Service in Spring
@Service
public class TicketServiceIMPL {

    // Logger to print logs or messages for debugging or tracking purposes
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceIMPL.class);

    // ConfigurationRepo is used to interact with the database table for configurations
    private final ConfigurationRepo configurationRepo;

    // Constructor to initialize ConfigurationRepo using dependency injection
    public TicketServiceIMPL(ConfigurationRepo configurationRepo) {
        this.configurationRepo = configurationRepo;  // Save the injected repository
    }

    // Method to get the ticket release rate from the database
    public int getTicketReleaseRate() {
        // Try to retrieve the configuration from the database using ID 1
        ConfigurationEntity config = configurationRepo.findById(1L).orElse(null);

        // If a configuration exists, return the ticket release rate; otherwise, return a default value
        return config != null ? config.getTicketReleaseRate() : 5; // Default rate if not found
    }

    // Method to update the ticket release rate in the database
    public void updateTicketReleaseRate(int newRate) {
        // Retrieve the configuration or create a new one if it doesn't exist
        ConfigurationEntity config = configurationRepo.findById(1L).orElse(new ConfigurationEntity());

        // Set the new ticket release rate
        config.setTicketReleaseRate(newRate);

        // Save the updated configuration back into the database
        configurationRepo.save(config);
    }

    // Method to simulate vendor action (example: releasing tickets)
    public void simulateVendorAction() {
        // Print a log message indicating that the vendor is releasing tickets
        logger.info("Vendor is releasing tickets...");

        // Placeholder for additional logic, such as releasing tickets or updating inventory
        // You can add more detailed actions for the vendor here
    }
}
