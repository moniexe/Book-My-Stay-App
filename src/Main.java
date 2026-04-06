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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    public void viewAllRequests() {
        System.out.println("\n=== Booking Request Queue ===\n");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Rohan", "Single Room"));
        bookingQueue.addRequest(new Reservation("Amit", "Double Room"));
        bookingQueue.addRequest(new Reservation("Sneha", "Suite Room"));

        bookingQueue.viewAllRequests();

        System.out.println("\nAll requests are queued and waiting for processing.");
        System.out.println("Thank you for using Book My Stay App!");
    }
}