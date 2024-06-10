import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        // Admin login
        Administrator admin = new Administrator("Admin", "admin@example.com", "adminCNIC");
        if (admin.login()) {
            // Admin manages partnership and inventory
            admin.managePartnership(new Partnership("Partner", "P001"));

            // Display available rooms
            Inventory inventory = new Inventory("INV001", "Main Inventory");
            inventory.displayAvailableRooms();

            // User registration and login
            System.out.println("Register as a user:");
            //Student student = new Student("", "", "", "", "", "");
              Student student = new Student();
            student.Register();
            if (student.login()) {
                // User books a room
                System.out.println("Book a room:");
                System.out.println("These are all the avaliabl                                                   e rooms ");
                inventory.displayAvailableRooms();
                Booking booking = new Booking();
                booking.createBooking(student, inventory);

                    System.out.println("Booking Details:");
                   booking.displayBookingDetails();
            }
        } else {
            System.out.println("Admin login failed.");
        }

    }
}
