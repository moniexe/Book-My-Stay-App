import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int current = inventory.get(roomType);
        if (current <= 0) {
            throw new InvalidBookingException("Cannot decrement. No rooms available for: " + roomType);
        }
        inventory.put(roomType, current - 1);
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {
        try {
            inventory.validateRoomType(roomType);
            inventory.validateAvailability(roomType);

            inventory.decrement(roomType);

            System.out.println("Booking successful for " + guestName + " (" + roomType + ")");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed for " + guestName + ": " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        bookingService.bookRoom("Rohan", "Single Room");
        bookingService.bookRoom("Amit", "Double Room");
        bookingService.bookRoom("Sneha", "Suite Room");

        bookingService.bookRoom("Rahul", "Suite Room");
        bookingService.bookRoom("Kiran", "Luxury Room");

        System.out.println("\nSystem continues running safely.");
        System.out.println("Thank you for using Book My Stay App!");
    }
}