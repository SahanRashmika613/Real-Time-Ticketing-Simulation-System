package CLI;

// Consumer class represents a thread that processes tickets from the TicketPool
public class Consumer implements Runnable {
    private final TicketPool ticketPool; // The shared TicketPool used to retrieve tickets

    // Constructor to initialize the Consumer with a TicketPool
    public Consumer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // The run method is executed when the thread starts
    @Override
    public void run() {
        try {
            // Keep processing tickets while the TicketPool is active
            while (ticketPool.isRunning()) {
                // Take a ticket from the TicketPool (blocks if no tickets are available)
                Ticket ticket = ticketPool.takeTicket();

                // Display a message showing the current thread and ticket being processed
                System.out.println(Thread.currentThread().getName() + " processed Ticket ID: " + ticket.getId());

                // Pause the thread for 1 second to simulate ticket processing time
                Thread.sleep(1000L);
            }

            // When the TicketPool stops, show a message that this consumer has finished
            System.out.println(Thread.currentThread().getName() + " has completed processing tickets.");
        } catch (InterruptedException e) {
            // Handle interruptions gracefully, if the thread is interrupted
            Thread.currentThread().interrupt(); // Set the thread's interrupted status
            System.out.println(Thread.currentThread().getName() + " interrupted while processing tickets.");
        }
    }
}

