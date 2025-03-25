package CLI;

// The Vendor class represents a thread responsible for adding tickets to the TicketPool
public class Vendor implements Runnable {
    private final TicketPool ticketPool;          // The shared TicketPool where tickets are added
    private final Configuration configuration;    // Holds the settings like total tickets and release rate

    // Constructor to initialize the Vendor with a TicketPool and Configuration
    public Vendor(TicketPool ticketPool, Configuration configuration) {
        this.ticketPool = ticketPool;             // Assign the TicketPool to this Vendor
        this.configuration = configuration;       // Assign the Configuration to this Vendor
    }

    // The run method is executed when the thread starts
    @Override
    public void run() {
        try {
            // Keep adding tickets as long as the TicketPool is running
            // and the total number of tickets added is less than the configured limit
            while (ticketPool.isRunning() && ticketPool.getAddedTickets() < configuration.getTotalTickets()) {
                // Create a new Ticket with the current ticket count as its ID
                Ticket ticket = new Ticket(ticketPool.getAddedTickets());

                // Add the new ticket to the TicketPool queue
                ticketPool.getTickets().put(ticket);

                // Increase the count of total tickets added
                ticketPool.incrementAddedTickets();

                // Display a message showing the added ticket's ID
                System.out.println(Thread.currentThread().getName() + " added Ticket ID: " + ticket.getId());

                // Wait for the time specified in the configuration before adding the next ticket
                Thread.sleep(configuration.getTicketReleaseRate() * 1000L);
            }

            // If all tickets are added, show a message that this Vendor is done
            System.out.println(Thread.currentThread().getName() + " has completed adding tickets.");
        } catch (InterruptedException e) {
            // If the thread is interrupted, stop gracefully
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " interrupted while adding tickets.");
        }
    }
}
