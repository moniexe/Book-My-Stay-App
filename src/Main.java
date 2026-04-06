import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("Services for Reservation ID: " + reservationId);

        for (Service s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getCost() + ")");
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId) + "\n");
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId1 = "SR1";
        String reservationId2 = "DR1";

        manager.addService(reservationId1, new Service("Breakfast", 500));
        manager.addService(reservationId1, new Service("Airport Pickup", 1200));

        manager.addService(reservationId2, new Service("Spa Access", 1500));

        manager.displayServices(reservationId1);
        manager.displayServices(reservationId2);

        System.out.println("Thank you for using Book My Stay App!");
    }
}