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

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("=== Booking History ===\n");
        for (Reservation r : reservations) {
            r.display();
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            summary.put(type, summary.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n=== Booking Summary ===\n");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " -> Total Bookings: " + entry.getValue());
        }
    }
}

public class Main{
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("SR1", "Rohan", "Single Room"));
        history.addReservation(new Reservation("DR1", "Amit", "Double Room"));
        history.addReservation(new Reservation("SR2", "Sneha", "Single Room"));
        history.addReservation(new Reservation("SU1", "Kiran", "Suite Room"));

        reportService.displayAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nThank you for using Book My Stay App!");
    }
}