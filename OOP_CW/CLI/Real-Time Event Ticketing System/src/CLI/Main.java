package CLI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Outer loop to restart the system after exiting the main menu
        while (true) {
            System.out.println("\nWelcome to the Real-Time Ticketing System");
            System.out.println("Please configure the system:");

            // Ask the user to input system configuration details
            Configuration configuration = promptForConfiguration(scanner);

            // Show the entered configuration details to the user
            configuration.displayConfiguration();

            // Create a TicketPool to manage ticket-related operations
            TicketPool ticketPool = new TicketPool(configuration.getMaximumTicketCapacity());

            boolean running = true;

            // Inner loop for the main menu operations
            while (running) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Start the Ticketing System");
                System.out.println("2. View System Status");
                System.out.println("3. Stop the Ticketing System");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                // Get a validated menu choice from the user
                int choice = getValidatedInput(scanner);

                // Perform actions based on the selected menu option
                switch (choice) {
                    case 1:
                        // Option 1: Start the ticketing system
                        startTicketingSystem(ticketPool, configuration);
                        break;

                    case 2:
                        // Option 2: Display the current system status
                        displaySystemStatus(ticketPool);
                        break;

                    case 3:
                        // Option 3: Stop the ticketing system
                        stopTicketingSystem(ticketPool);
                        System.out.println("Ticketing system stopped.");
                        break;

                    case 4:
                        // Option 4: Exit the menu and return to configuration
                        stopTicketingSystem(ticketPool);
                        System.out.println("Exiting the menu. Returning to the initial configuration...");
                        running = false; // Exit the inner menu loop
                        break;

                    default:
                        // Handle invalid menu options
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    /**
     * Prompts the user for system configuration details.
     */
    private static Configuration promptForConfiguration(Scanner scanner) {
        try {
            System.out.print("1. Enter the Total Number of Tickets: ");
            int totalTickets = getValidatedInput(scanner); // Get the total number of tickets

            System.out.print("2. Enter the Ticket Release Rate (in seconds): ");
            int ticketReleaseRate = getValidatedInput(scanner); // Get the ticket release rate

            System.out.print("3. Enter the Customer Retrieval Rate (in seconds): ");
            int customerRetrievalRate = getValidatedInput(scanner); // Get the customer retrieval rate

            System.out.print("4. Enter the Maximum Ticket Capacity: ");
            int maxTicketCapacity = getValidatedInput(scanner); // Get the maximum number of tickets the system can hold

            // Error handling to check if total tickets exceed the maximum capacity
            if (totalTickets > maxTicketCapacity) {
                System.out.println("Error: Total tickets cannot exceed maximum ticket capacity.");
                return promptForConfiguration(scanner); // Retry configuration
            }

            // Build and return the configuration object with the provided values
            return new Configuration.Builder()
                    .setTotalTickets(totalTickets)
                    .setTicketReleaseRate(ticketReleaseRate)
                    .setCustomerRetrievalRate(customerRetrievalRate)
                    .setMaximumTicketCapacity(maxTicketCapacity)
                    .build();
        } catch (Exception e) {
            // Handle invalid inputs and retry
            System.out.println("Invalid input. Please enter valid numeric values for all fields.");
            scanner.nextLine(); // Clear the invalid input
            return promptForConfiguration(scanner); // Retry configuration
        }
    }

    /**
     * Ensures the user enters a positive integer as input.
     */
    private static int getValidatedInput(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim(); // Read user input and remove extra spaces
                int number = Integer.parseInt(input);    // Convert the input to an integer
                if (number <= 0) {
                    throw new NumberFormatException("Input must be a positive integer."); // Ensure positive values
                }
                return number; // Return the valid number
            } catch (NumberFormatException e) {
                // If the input is not valid, ask the user to try again
                System.out.println("Invalid input. Please enter a positive integer.");
                System.out.print("Try again: ");
            }
        }
    }

    /**
     * Starts the ticketing system by initializing and running threads.
     */
    private static void startTicketingSystem(TicketPool ticketPool, Configuration configuration) {
        // Create and start a thread for ticket vending
        Thread vendorThread = new Thread(new Vendor(ticketPool, configuration), "Vendor");

        // Create and start a thread for ticket consuming
        Thread consumerThread = new Thread(new Consumer(ticketPool), "Consumer");

        vendorThread.start(); // Start the vendor thread
        consumerThread.start(); // Start the consumer thread

        System.out.println("Ticketing system started.");
    }

    /**
     * Displays the current status of the ticketing system.
     */
    private static void displaySystemStatus(TicketPool ticketPool) {
        System.out.println("\n--- System Status ---");
        System.out.println("Tickets Released: " + ticketPool.getTotalAddedTickets()); // Show the number of released tickets
        System.out.println("Tickets in Pool: " + ticketPool.getCurrentTicketCount()); // Show the current tickets in the system
        System.out.println("Tickets Retrieved: " + ticketPool.getTotalRetrievedTickets()); // Show the number of retrieved tickets
    }

    /**
     * Stops all ticketing system activities.
     */
    private static void stopTicketingSystem(TicketPool ticketPool) {
        ticketPool.stop(); // Call the stop method to halt operations
    }
}
