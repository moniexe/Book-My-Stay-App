import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Starting Book My Stay App...\n");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> history;

        if (state == null) {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            history = new ArrayList<>();

            history.add(new Reservation("R1", "Rohan", "Single Room"));
            history.add(new Reservation("R2", "Amit", "Double Room"));
        } else {
            inventory = state.inventory;
            history = state.bookingHistory;
        }

        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("\n=== Booking History ===");
        for (Reservation r : history) {
            r.display();
        }

        System.out.println("\nSimulating new booking...");

        inventory.put("Single Room", inventory.get("Single Room") - 1);
        history.add(new Reservation("R3", "Sneha", "Single Room"));

        SystemState newState = new SystemState(inventory, history);
        persistence.save(newState);

        System.out.println("\nShutdown complete. Restart to recover state.");
    }
}