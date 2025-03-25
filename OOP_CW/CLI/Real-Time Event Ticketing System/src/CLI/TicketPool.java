package CLI;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class manages a pool of tickets using a queue system,
 * which is used in a producer-consumer setup.
 */
public class TicketPool {
    private final BlockingQueue<Ticket> tickets; // A queue to hold tickets
    private final int maxCapacity;              // The maximum number of tickets the pool can hold
    private volatile boolean running = true;    // Indicates if the pool is active or not
    private int addedTickets = 0;               // Keeps track of the total number of tickets added

    /**
     * Constructor to create a TicketPool with a maximum capacity.
     *
     * @param maxCapacity The maximum number of tickets the pool can hold.
     */
    public TicketPool(int maxCapacity) {
        this.tickets = new LinkedBlockingQueue<>(maxCapacity); // Initialize the queue with max capacity
        this.maxCapacity = maxCapacity; // Store the maximum capacity
    }

    /**
     * Get the queue holding the tickets.
     *
     * @return The queue of tickets.
     */
    public BlockingQueue<Ticket> getTickets() {
        return tickets; // Return the ticket queue
    }

    /**
     * Check if the TicketPool is still running.
     *
     * @return True if the pool is running, false otherwise.
     */
    public boolean isRunning() {
        return running; // Return whether the pool is active
    }

    /**
     * Stop the TicketPool. This means no more tickets will be added.
     */
    public void stop() {
        running = false; // Set the pool to inactive
        System.out.println("TicketPool has stopped."); // Inform that the pool is stopped
    }

    /**
     * Get the total number of tickets added to the pool so far.
     *
     * @return The total count of tickets added.
     */
    public int getAddedTickets() {
        return addedTickets; // Return the count of tickets added
    }

    /**
     * Increase the count of added tickets by one.
     */
    public synchronized void incrementAddedTickets() {
        addedTickets++; // Increment the number of tickets added
    }

    /**
     * Take a ticket from the pool (remove it from the queue).
     *
     * @return The ticket taken from the pool.
     * @throws InterruptedException If the operation is interrupted.
     */
    public Ticket takeTicket() throws InterruptedException {
        Ticket ticket = tickets.take(); // Get and remove a ticket from the queue
        System.out.println("Ticket retrieved: " + ticket.getId()); // Print the ID of the ticket retrieved
        return ticket; // Return the retrieved ticket
    }

    /**
     * Add a ticket to the pool (put it into the queue).
     *
     * @param ticket The ticket to add.
     * @throws InterruptedException If the operation is interrupted.
     */
    public void addTicket(Ticket ticket) throws InterruptedException {
        tickets.put(ticket); // Add the ticket to the queue
        System.out.println("Ticket added: " + ticket.getId()); // Print the ID of the ticket added
    }

    /**
     * Get the current number of tickets in the pool.
     *
     * @return The number of tickets currently in the pool.
     */
    public int getCurrentTicketCount() {
        return tickets.size(); // Return the number of tickets in the queue
    }

    /**
     * Get the total number of tickets added to the pool so far.
     *
     * @return The total count of tickets added.
     */
    public int getTotalAddedTickets() {
        return addedTickets; // Return the total tickets added
    }

    /**
     * Get the total number of tickets retrieved from the pool so far.
     *
     * @return The total count of tickets retrieved.
     */
    public int getTotalRetrievedTickets() {
        return addedTickets - tickets.size(); // Calculate tickets retrieved (added - remaining in pool)
    }
}