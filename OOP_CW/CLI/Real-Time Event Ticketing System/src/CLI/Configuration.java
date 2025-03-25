package CLI;

import com.google.gson.Gson; // Library to handle JSON data.
import com.google.gson.GsonBuilder; // Library for creating well-formatted JSON.

import java.io.*; // Library for file operations (read/write).
import java.util.Scanner; // Library for user input.

/**
 * Configuration class to store and manage the system's settings.
 */
public class Configuration {

    // Store the total number of tickets to release.
    private final int totalTickets;
    // Store how often tickets are released (in seconds).
    private final int ticketReleaseRate;
    // Store how often customers can retrieve tickets (in seconds).
    private final int customerRetrievalRate;
    // Store the maximum number of tickets that can be in the system.
    private final int maximumTicketCapacity;

    // Constructor to create a Configuration object using values from the Builder.
    private Configuration(Builder builder) {
        this.totalTickets = builder.totalTickets;
        this.ticketReleaseRate = builder.ticketReleaseRate;
        this.customerRetrievalRate = builder.customerRetrievalRate;
        this.maximumTicketCapacity = builder.maximumTicketCapacity;
    }

    // Get the total number of tickets.
    public int getTotalTickets() {
        return totalTickets;
    }

    // Get the rate at which tickets are released (seconds).
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    // Get the rate at which customers retrieve tickets (seconds).
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    // Get the maximum number of tickets allowed in the system.
    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    // Print the configuration details to the console.
    public void displayConfiguration() {
        System.out.println("System Configuration:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate + " sec");
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate + " sec");
        System.out.println("Maximum Ticket Capacity: " + maximumTicketCapacity);
    }

    // Save the configuration as a JSON file for future use.
    public void saveToJson(String filePath) throws IOException {
        // Create a JSON object with pretty formatting for better readability.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer); // Write the configuration as JSON to the file.
        }
    }

    // Load the configuration from a saved JSON file.
    public static Configuration loadFromJson(String filePath) throws IOException {
        Gson gson = new Gson(); // Create a simple JSON object to read data.
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class); // Convert JSON data into a Configuration object.
        }
    }

    // Save the configuration to a plain text file (easy to read manually).
    public void saveToPlainText(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Total Tickets: " + totalTickets + "\n");
            writer.write("Ticket Release Rate: " + ticketReleaseRate + "\n");
            writer.write("Customer Retrieval Rate: " + customerRetrievalRate + "\n");
            writer.write("Maximum Ticket Capacity: " + maximumTicketCapacity + "\n");
        }
    }

    // Load the configuration from a plain text file.
    public static Configuration loadFromPlainText(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read each line and extract the value after ": ".
            int totalTickets = Integer.parseInt(reader.readLine().split(": ")[1]);
            int ticketReleaseRate = Integer.parseInt(reader.readLine().split(": ")[1]);
            int customerRetrievalRate = Integer.parseInt(reader.readLine().split(": ")[1]);
            int maximumTicketCapacity = Integer.parseInt(reader.readLine().split(": ")[1]);

            // Create and return a Configuration object using a Builder.
            return new Builder()
                    .setTotalTickets(totalTickets)
                    .setTicketReleaseRate(ticketReleaseRate)
                    .setCustomerRetrievalRate(customerRetrievalRate)
                    .setMaximumTicketCapacity(maximumTicketCapacity)
                    .build();
        }
    }

    // Inner Builder class to help create Configuration objects step by step.
    public static class Builder {
        private int totalTickets; // Total tickets to release.
        private int ticketReleaseRate; // Rate to release tickets (seconds).
        private int customerRetrievalRate; // Rate customers retrieve tickets (seconds).
        private int maximumTicketCapacity; // Maximum tickets allowed in the system.

        // Set the total number of tickets.
        public Builder setTotalTickets(int totalTickets) {
            if (totalTickets <= 0) { // Validate input.
                throw new IllegalArgumentException("Total tickets must be greater than zero.");
            }
            this.totalTickets = totalTickets;
            return this;
        }

        // Set the ticket release rate.
        public Builder setTicketReleaseRate(int ticketReleaseRate) {
            if (ticketReleaseRate <= 0) { // Validate input.
                throw new IllegalArgumentException("Ticket release rate must be greater than zero.");
            }
            this.ticketReleaseRate = ticketReleaseRate;
            return this;
        }

        // Set the customer retrieval rate.
        public Builder setCustomerRetrievalRate(int customerRetrievalRate) {
            if (customerRetrievalRate <= 0) { // Validate input.
                throw new IllegalArgumentException("Customer retrieval rate must be greater than zero.");
            }
            this.customerRetrievalRate = customerRetrievalRate;
            return this;
        }

        // Set the maximum ticket capacity.
        public Builder setMaximumTicketCapacity(int maximumTicketCapacity) {
            if (maximumTicketCapacity <= 0) { // Validate input.
                throw new IllegalArgumentException("Maximum ticket capacity must be greater than zero.");
            }
            this.maximumTicketCapacity = maximumTicketCapacity;
            return this;
        }

        // Create a Configuration object after validating all parameters.
        public Configuration build() {
            if (totalTickets == 0 || ticketReleaseRate == 0 || customerRetrievalRate == 0 || maximumTicketCapacity == 0) {
                throw new IllegalStateException("All configuration parameters must be set before building.");
            }
            return new Configuration(this);
        }
    }
}
