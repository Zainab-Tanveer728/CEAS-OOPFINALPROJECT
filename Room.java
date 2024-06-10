import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
public class Room  {
    // Attributes
    private String roomId;
    private String roomType;
    private double price;
    private boolean isAvailable;
    private int capacity;
    private ArrayList<String> amenities;

    // Parametrized Constructor
    public Room(String roomId, String roomType, double price, boolean isAvailable, int capacity,ArrayList<String>amenities) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
        this.capacity = capacity;
        this.amenities =amenities ;
    }
    // Getters
    public String getRoomId() {
        return roomId;
    }
    public String getRoomType() {
        return roomType;
    }
    public double getPrice() {
        return price;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public int getCapacity() {
        return capacity;
    }
    public ArrayList<String> getAmenities() {
        return amenities;
    }
    // Setters
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }
    // Methods to modify amenities
    public void addAmenity(String amenity) {
        if (!amenities.contains(amenity)) {
            amenities.add(amenity);
        }
    }
    public void removeAmenity(String amenity) {
        amenities.remove(amenity);
    }
    // Display room details
    public void displayRoomDetails() {
        System.out.println("Room ID: " + roomId);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Capacity: " + capacity);
        System.out.println("Amenities: " + String.join(", ", amenities));
    }

    public boolean isAvailable() {
        return true;
    }
}


