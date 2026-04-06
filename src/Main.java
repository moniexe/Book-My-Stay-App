import java.util.*;

abstract class Room {
    protected int numberOfBeds;
    protected double size;
    protected double price;

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }

    public abstract String getRoomType();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 120.0, 2000.0);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 200.0, 3500.0);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 350.0, 7000.0);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class RoomSearchService {
    private List<Room> rooms;
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
        rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());
    }

    public void searchAvailableRooms() {
        System.out.println("=== Available Rooms ===\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {
                System.out.println(room.getRoomType());
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();

        System.out.println("Thank you for using Book My Stay App!");
    }
}