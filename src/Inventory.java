import java.util.ArrayList;
public class Inventory {
    private String inventoryId;
    private String inventoryName;
    private static ArrayList<Room> availableRooms = new ArrayList<>();
    // Constructor
    public Inventory(){

    }
    public Inventory(String inventoryId, String inventoryName ) {
        this.inventoryId = inventoryId;
        this.inventoryName = inventoryName;
    }
    // Setters and Getters
    public String getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }
    public String getInventoryName() {
        return inventoryName;
    }
    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }


    // Display inventory details
    public void displayInventoryDetails() {
        System.out.println("Inventory ID: " + inventoryId);
        System.out.println("Inventory Name: " + inventoryName);

    }
    public static void addRoom(Room room) {
        availableRooms.add(room);
    }
    public ArrayList<Room> getAvailableRooms() {
        return availableRooms;
    }
    public static void removeRoom(Room room) {
        availableRooms.remove(room);
    }
    public static void updateRoom(Room updatedRoom) {
        for (int i = 0; i < availableRooms.size(); i++) {
            if (availableRooms.get(i).getRoomId().equals(updatedRoom.getRoomId())) {
                availableRooms.set(i, updatedRoom);
                break;
            }
        }
    }
    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms currently available.");
        } else {
            for (int i = 0;i< availableRooms.size();i++){
                System.out.println("Room ID: " + availableRooms.get(i).getRoomId());
                System.out.println("Room Type: " + availableRooms.get(i).getRoomType());
                System.out.println("Room Capacity: " + availableRooms.get(i).getCapacity());
                System.out.println("Room Price:   $"+availableRooms.get(i).getPrice());
                System.out.println("Room avaliability : " + availableRooms.get(i).getIsAvailable());
                // Add other room details as needed (price, capacity, amenities, etc.)
            }
        }
    }
}

