package com.real_time.ticket.system.real_time.ticket.system.threads;

// Import required service classes for consumer and ticket functionalities
import com.real_time.ticket.system.real_time.ticket.system.services.ConsumerServiceIMPL;
import com.real_time.ticket.system.real_time.ticket.system.services.TicketServiceIMPL;
// Import Logger to log important messages and errors
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Import Component annotation to mark this class as a Spring-managed component
import org.springframework.stereotype.Component;

// This annotation makes this class a Spring-managed bean
@Component
public class ConsumerThread implements Runnable {

    // Logger to track activity and print messages during execution
    private static final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

    // References to services that handle consumer and ticket-related logic
    private final ConsumerServiceIMPL consumerService;
    private final TicketServiceIMPL ticketService;

    // A flag to control whether the thread should keep running
    private volatile boolean running = true;

    // Constructor to inject ConsumerServiceIMPL and TicketServiceIMPL using dependency injection
    public ConsumerThread(ConsumerServiceIMPL consumerService, TicketServiceIMPL ticketService) {
        this.consumerService = consumerService; // Initialize consumerService
        this.ticketService = ticketService;     // Initialize ticketService
    }

    // This method runs when the thread starts
    @Override
    public void run() {
        try {
            // Loop that keeps running as long as the 'running' flag is true and the thread is not interrupted
            while (running && !Thread.currentThread().isInterrupted()) {
                // Get the consumer retrieval rate (in seconds) from the database
                int retrievalRate = consumerService.getConsumerRetrievalRate();

                // Log that the consumer is purchasing tickets
                logger.info("Consumer purchasing tickets...");

                // Simulate consumer action (e.g., buying a ticket)
                consumerService.simulateConsumerAction();

                // Pause the thread for a duration based on the retrieval rate (converted to milliseconds)
                Thread.sleep(retrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            // Handle thread interruption gracefully and set the thread's interrupt status
            Thread.currentThread().interrupt();
            logger.warn("ConsumerThread interrupted."); // Log the interruption
        } catch (Exception ex) {
            // Log any other unexpected errors that occur
            logger.error("Error in ConsumerThread: {}", ex.getMessage(), ex);
        } finally {
            // Log that the thread has stopped
            logger.info("ConsumerThread stopped.");
        }
    }

    // Method to stop the thread safely
    public void stop() {
        // Set the 'running' flag to false to exit the loop in the run method
        running = false;

        // Interrupt the thread to handle any blocking calls (like sleep)
        Thread.currentThread().interrupt();
    }
}
