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

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("=== Room Details ===\n");

        System.out.println(single.getRoomType());
        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println(doubleRoom.getRoomType());
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println(suite.getRoomType());
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Thank you for using Book My Stay App!");
    }
}