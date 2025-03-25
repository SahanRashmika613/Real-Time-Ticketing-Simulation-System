package com.real_time.ticket.system.real_time.ticket.system.services;

// Import the ConfigurationEntity to work with the configuration data
import com.real_time.ticket.system.real_time.ticket.system.entities.ConfigurationEntity;
// Import the ConfigurationRepo to interact with the database
import com.real_time.ticket.system.real_time.ticket.system.repo.ConfigurationRepo;
// Import logging functionality to record messages during the execution of methods
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Import Spring's Service annotation to mark this class as a service layer
import org.springframework.stereotype.Service;

// This annotation marks the class as a Service, meaning it contains business logic
@Service
public class ConsumerServiceIMPL {

    // Logger to record messages, warnings, and errors for debugging purposes
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceIMPL.class);

    // A reference to ConfigurationRepo, which is used to interact with configuration data in the database
    private final ConfigurationRepo configurationRepo;

    // Constructor for injecting the ConfigurationRepo dependency
    public ConsumerServiceIMPL(ConfigurationRepo configurationRepo) {
        this.configurationRepo = configurationRepo;  // Assigns the passed repository to the class variable
    }

    // Method to get the consumer retrieval rate from the database
    public int getConsumerRetrievalRate() {
        // Try to retrieve the configuration from the database using the ID 1
        ConfigurationEntity config = configurationRepo.findById(1L).orElse(null);

        // If the configuration is found, return the consumer retrieval rate
        if (config != null) {
            return config.getConsumerRetrievalRate();
        } else {
            // If no configuration is found, log a warning and return a default value
            logger.warn("No configuration found, returning default consumer retrieval rate.");
            return 5;  // Default rate if no data is found
        }
    }

    // Method to update the consumer retrieval rate in the database
    public void updateConsumerRetrievalRate(int newRate) {
        // Try to retrieve the configuration by ID or create a new one if not found
        ConfigurationEntity config = configurationRepo.findById(1L).orElse(new ConfigurationEntity());

        // Update the consumer retrieval rate with the new value
        config.setConsumerRetrievalRate(newRate);

        // Save the updated configuration back to the database
        configurationRepo.save(config);

        // Log a message indicating the update was successful
        logger.info("Updated consumer retrieval rate to {}", newRate);
    }

    // Example placeholder method to simulate a consumer action
    public void simulateConsumerAction() {
        // Log a message indicating that a consumer is performing an action
        logger.info("Consumer is performing an action (e.g., purchasing a ticket)");
        // You can add more code here to define the actions performed by the consumer
    }
}
