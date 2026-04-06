import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;
    private Set<String> allAllocatedRoomIds;
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        allAllocatedRoomIds = new HashSet<>();
    }

    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 2).toUpperCase() + roomCounter++;
        } while (allAllocatedRoomIds.contains(id));
        return id;
    }

    public void processRequest(Reservation reservation) {
        String type = reservation.getRoomType();

        if (inventory.getAvailability(type) > 0) {
            String roomId = generateRoomId(type);

            allAllocatedRoomIds.add(roomId);
            allocatedRooms.putIfAbsent(type, new HashSet<>());
            allocatedRooms.get(type).add(roomId);

            inventory.decrement(type);

            System.out.println("Booking Confirmed for " + reservation.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Allocated Room ID: " + roomId + "\n");
        } else {
            System.out.println("Booking Failed for " + reservation.getGuestName() + " (No availability)\n");
        }
    }
}

public class Main{
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        queue.addRequest(new Reservation("Rohan", "Single Room"));
        queue.addRequest(new Reservation("Amit", "Double Room"));
        queue.addRequest(new Reservation("Sneha", "Suite Room"));
        queue.addRequest(new Reservation("Kiran", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processRequest(r);
        }

        System.out.println("All requests processed.");
        System.out.println("Thank you for using Book My Stay App!");
    }
}