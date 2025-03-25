package com.real_time.ticket.system.real_time.ticket.system.controller;

// Import necessary classes and packages
import com.real_time.ticket.system.real_time.ticket.system.dto.SimulatingConfigDTO;
import com.real_time.ticket.system.real_time.ticket.system.services.ConsumerServiceIMPL;
import com.real_time.ticket.system.real_time.ticket.system.services.TicketServiceIMPL;
import com.real_time.ticket.system.real_time.ticket.system.threads.ConsumerThread;
import com.real_time.ticket.system.real_time.ticket.system.threads.VendorThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

// Indicate this is a REST controller
@RestController
@RequestMapping("/api/v1/simulating") // Base URL for this controller's endpoints
public class SimulatingController {

    // Logger for tracking system activities
    private static final Logger logger = LoggerFactory.getLogger(SimulatingController.class);

    // Automatically inject the required services
    @Autowired
    private final ConsumerServiceIMPL consumerService;
    @Autowired
    private final TicketServiceIMPL ticketService;

    // Thread objects for simulation
    private final ConsumerThread consumerThread;
    private final VendorThread vendorThread;

    // Variables to track system status and configuration
    private String systemStatus = "STOPPED"; // System is initially stopped
    private SimulatingConfigDTO simulatingConfig = new SimulatingConfigDTO(); // Holds simulation settings

    // Threads to run the simulation
    private Thread consumerThreadInstance;
    private Thread vendorThreadInstance;

    // Constructor to initialize services and threads
    public SimulatingController(ConsumerServiceIMPL consumerService, TicketServiceIMPL ticketService,
                                ConsumerThread consumerThread, VendorThread vendorThread) {
        this.consumerService = consumerService;
        this.ticketService = ticketService;
        this.consumerThread = consumerThread;
        this.vendorThread = vendorThread;
    }

    // Endpoint to start the simulation
    @PostMapping("/start")
    @CrossOrigin(origins = "http://localhost:5173") // Allow requests from the frontend URL
    public String startSimulation() {
        if ("RUNNING".equals(systemStatus)) { // Check if simulation is already running
            return "Simulation already running.";
        }

        systemStatus = "RUNNING"; // Update system status
        logger.info("Starting simulation...");

        // Create and start threads for the simulation
        consumerThreadInstance = new Thread(consumerThread);
        vendorThreadInstance = new Thread(vendorThread);

        consumerThreadInstance.start(); // Start consumer thread
        vendorThreadInstance.start(); // Start vendor thread

        return "Simulation started.";
    }

    // Endpoint to stop the simulation
    @PostMapping("/stop")
    @CrossOrigin(origins = "http://localhost:5173")
    public String stopSimulation() {
        if ("STOPPED".equals(systemStatus)) { // Check if simulation is already stopped
            return "Simulation already stopped.";
        }

        systemStatus = "STOPPED"; // Update system status
        logger.info("Stopping simulation...");

        // Stop both threads gracefully
        stopThread(consumerThreadInstance);
        stopThread(vendorThreadInstance);

        return "Simulation stopped.";
    }

    // Method to safely stop a thread
    private void stopThread(Thread thread) {
        if (thread != null && thread.isAlive()) { // Check if the thread is running
            thread.interrupt(); // Interrupt the thread
            try {
                thread.join(5000); // Wait for up to 5 seconds for the thread to finish
            } catch (InterruptedException e) {
                logger.error("Thread interrupted during stop", e); // Log any interruption
            }
        }
    }

    // Endpoint to update simulation configuration settings
    @PutMapping("/updateConfig")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> updateConfig(@Valid @RequestBody SimulatingConfigDTO config, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // Check for validation errors
            logger.error("Invalid configuration: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body("Invalid configuration: " + bindingResult.getAllErrors());
        }

        try {
            logger.info("Received configuration update: {}", config); // Log the configuration update

            // Update the simulation settings
            simulatingConfig = config;
            consumerService.updateConsumerRetrievalRate(config.getConsumerRetrievalRate());
            ticketService.updateTicketReleaseRate(config.getTicketReleaseRate());

            logger.info("Configuration updated successfully.");
            return ResponseEntity.ok("Configuration updated."); // Respond with success
        } catch (Exception e) {
            logger.error("Error updating configuration", e); // Log errors
            return ResponseEntity.status(500).body("Error updating configuration: " + e.getMessage());
        }
    }

    // Endpoint to get the current system status
    @GetMapping("/status")
    @CrossOrigin(origins = "http://localhost:5173")
    public SystemStatus getSystemStatus() {
        logger.info("Fetching system status...");
        // Return the current system status and available tickets
        return new SystemStatus(systemStatus, simulatingConfig.getTotalTickets());
    }

    // Inner class to hold system status information
    public static class SystemStatus {
        private String systemStatus; // Current status of the system
        private int ticketsAvailable; // Number of tickets available

        // Constructor to initialize status
        public SystemStatus(String systemStatus, int ticketsAvailable) {
            this.systemStatus = systemStatus;
            this.ticketsAvailable = ticketsAvailable;
        }

        // Getter for system status
        public String getSystemStatus() {
            return systemStatus;
        }

        // Getter for available tickets
        public int getTicketsAvailable() {
            return ticketsAvailable;
        }
    }
}
