package CLI;

// This class represents a Ticket with a unique ID
import java.util.UUID;

public class Ticket {
    private final UUID uuid; // This is the unique ID of the ticket

    /**
     * Constructor to create a new Ticket object.
     *
     * @param addedTickets An extra value (not currently used, but kept for future features).
     */
    public Ticket(int addedTickets) {
        // Generate a unique ID for this ticket using the UUID class
        this.uuid = UUID.randomUUID();
    }

    /**
     * This method gives the unique ID of the ticket.
     *
     * @return The unique ID of the ticket as a UUID object.
     */
    public UUID getUuid() {
        return uuid; // Return the unique ID of the ticket
    }

    /**
     * This method gives the unique ID of the ticket as a text (string).
     *
     * @return The ticket's unique ID in text format.
     */
    public String getId() {
        return uuid.toString(); // Convert the unique ID to a string and return it
    }

    /**
     * This method is used to get a text description of the ticket.
     *
     * @return A message with the ticket's ID.
     */
    @Override
    public String toString() {
        return "Ticket ID: " + uuid; // Combine "Ticket ID: " with the unique ID
    }
}