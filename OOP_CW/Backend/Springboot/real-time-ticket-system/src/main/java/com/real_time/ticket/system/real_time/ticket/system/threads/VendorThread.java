package com.real_time.ticket.system.real_time.ticket.system.threads;

// Import necessary service and logging classes
import com.real_time.ticket.system.real_time.ticket.system.services.TicketServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Mark this class as a Spring component
import org.springframework.stereotype.Component;

// This annotation tells Spring that this class is a component to be managed
@Component
public class VendorThread implements Runnable {

    // Create a logger to track and print messages about what's happening
    private static final Logger logger = LoggerFactory.getLogger(VendorThread.class);

    // Declare a reference to the TicketServiceIMPL, which handles ticket-related functionality
    private final TicketServiceIMPL ticketService;

    // A flag to control if the thread should keep running or stop
    private volatile boolean running = true;

    // Constructor to inject TicketServiceIMPL into this class
    public VendorThread(TicketServiceIMPL ticketService) {
        this.ticketService = ticketService; // Initialize the ticketService
    }

    // The method that will be executed when the thread starts
    @Override
    public void run() {
        try {
            // Loop that keeps running while 'running' is true and the thread isn't interrupted
            while (running && !Thread.currentThread().isInterrupted()) {
                // Get the ticket release rate (time interval) from the database
                int ticketReleaseRate = ticketService.getTicketReleaseRate();

                // Log that the vendor is releasing tickets
                logger.info("Vendor releasing tickets...");

                // Simulate the vendor's action of releasing tickets (this is a placeholder method)
                ticketService.simulateVendorAction();

                // Pause the thread based on the release rate (converted to milliseconds)
                Thread.sleep(ticketReleaseRate * 1000);
            }
        } catch (InterruptedException e) {
            // If the thread is interrupted, preserve the interrupt status and log a warning
            Thread.currentThread().interrupt();
            logger.warn("VendorThread interrupted.");
        } catch (Exception ex) {
            // If any other error occurs, log the error details
            logger.error("Error in VendorThread: {}", ex.getMessage(), ex);
        } finally {
            // Log that the thread has stopped
            logger.info("VendorThread stopped.");
        }
    }

    // Method to stop the thread safely
    public void stop() {
        // Set the 'running' flag to false to break out of the loop in the run method
        running = false;

        // Interrupt the thread to exit from any blocking calls (like sleep)
        Thread.currentThread().interrupt();
    }
}
