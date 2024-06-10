import java.util.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Booking {
    private String bookingId;
    private Room room;
    private User user;
    private ArrayList<Booking>bookings;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private boolean confirmed;

    public Booking(){

         bookings = new ArrayList<>();
    }
    // Constructor
    public Booking(String bookingId, Room room, User user, Date checkInDate, Date checkOutDate, double totalPrice, boolean confirmed) {
        this.bookingId = bookingId;
        this.user = user;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.confirmed = confirmed;
    }

    // Setters and Getters
    public String getBookingId() {
        return bookingId;
    }
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    // Display booking details
    // Method to iterate over bookings


    public ArrayList<Booking> returnBookings() {
        return bookings;
    }

    public String generateBookingId() {
        ArrayList<String> usedBookingIds = new ArrayList<>();
        String bookingId;
        do {
            // Generate a random 5-digit number
            Random random = new Random();
            int randomId = random.nextInt(90000) + 10000;
            bookingId = String.valueOf(randomId);
        } while (usedBookingIds.contains(bookingId)); // Check if the generated ID is already used

        // Add the generated ID to the list of used IDs
        usedBookingIds.add(bookingId);
        return bookingId;
    }
    public double calculateTotalPrice(Room room, Date checkInDate, Date checkOutDate) {
        // Implement logic to calculate total price based on room price, duration, etc.
        // (This is a placeholder, replace with your actual calculation)
        long durationInDays = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
        return room.getPrice() * durationInDays;
    }

    // Method to create and add a new booking
    public void createBooking(Student student, Inventory inventory) {
        // Display available rooms
        ArrayList<Room>roomsToBook = inventory.getAvailableRooms();
        System.out.println("Available Rooms:");
        inventory.displayAvailableRooms();
        Room bookedRoom = null;

        // Allow student to choose a room
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Room ID you want to book (or 'q' to quit): ");
        String roomId = input.nextLine();
        if(!roomId.equals("q")){
        for(int i = 0;i< roomsToBook.size();i++){
            if(roomId.equalsIgnoreCase(roomsToBook.get(i).getRoomId())){
                bookedRoom = roomsToBook.get(i);
            }
        }
        Date checkin = new Date();
        System.out.println("For how many days would you like to stay ??");
        int nod = input.nextInt();
        Date checkout  = new Date(checkin.getTime()+(nod*24*60*60*1000));
        // Confirm booking if room is available
        if (bookedRoom.getIsAvailable()) {
            double totalPrice = calculateTotalPrice(bookedRoom, checkin, checkout);
            String bookingId = generateBookingId();

            // Update room availability
            bookedRoom.setAvailable(false);
            inventory.updateRoom(bookedRoom);

            System.out.println("Booking confirmed! Your booking ID is: " + bookingId);
            Booking booked = new Booking(bookingId, bookedRoom, student,checkin,checkout, totalPrice, true);
            this.bookings.add(booked);
//            return new Booking(bookingId, chosenRoom, student, checkInDate, checkOutDate, totalPrice, true); // Use accommodation ID here
        } else {
            System.out.println("Room is not available. Please choose another room.");
            return ;
        }}
        else{
            return;
        }
    }
    public void displayBookingDetails() {
        for(int i = 0;i< bookings.size();i++){
            System.out.println("Booking ID: " + bookings.get(i).bookingId);
            System.out.println("Check-In Date: " + bookings.get(i).getCheckInDate());
            System.out.println("Check-Out Date: " + bookings.get(i).getCheckOutDate());
            System.out.println("Total Price: $" + bookings.get(i).totalPrice);
            System.out.println("Confirmed: " + bookings.get(i).confirmed);

        }
    }
}
