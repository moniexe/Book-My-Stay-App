import java.util.*;

class Reservation {
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
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void decrement(String roomType) {
        if (inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, Reservation> confirmedBookings;
    private Map<String, String> reservationToRoomId;
    private Stack<String> rollbackStack;
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        confirmedBookings = new HashMap<>();
        reservationToRoomId = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public void confirmBooking(String guestName, String roomType) {
        if (inventory.getAvailability(roomType) > 0) {
            String reservationId = "R" + counter;
            String roomId = roomType.substring(0, 2).toUpperCase() + counter;
            counter++;

            inventory.decrement(roomType);

            Reservation reservation = new Reservation(reservationId, guestName, roomType);
            confirmedBookings.put(reservationId, reservation);
            reservationToRoomId.put(reservationId, roomId);

            System.out.println("Booking Confirmed: " + reservationId + " | Room ID: " + roomId);
        } else {
            System.out.println("Booking Failed for " + guestName + " (No availability)");
        }
    }

    public void cancelBooking(String reservationId) {
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID " + reservationId);
            return;
        }

        Reservation reservation = confirmedBookings.get(reservationId);
        String roomType = reservation.getRoomType();
        String roomId = reservationToRoomId.get(reservationId);

        rollbackStack.push(roomId);

        inventory.increment(roomType);

        confirmedBookings.remove(reservationId);
        reservationToRoomId.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId + " | Room Released: " + roomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Room IDs):");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.confirmBooking("Rohan", "Single Room");
        service.confirmBooking("Amit", "Double Room");
        service.confirmBooking("Sneha", "Suite Room");

        service.cancelBooking("R2");
        service.cancelBooking("R5");

        service.showRollbackStack();

        System.out.println("\nThank you for using Book My Stay App!");
    }
}