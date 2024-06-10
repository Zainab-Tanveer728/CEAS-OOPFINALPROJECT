import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Partnership {
    private String providerName;
    private String partnerShipId;
    private Date startDate;
    private Date endDate;
    private double commissionRate;
    private Administrator administrator;
    // No argument Constructor
    public Partnership(){

    }
    // Paramterized Constructor
    public Partnership(String providerName,String partnerShipId){
        this.providerName = providerName;
        this.partnerShipId = partnerShipId;

    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    // Method to set the commission rate of the partnership
    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }
    // Method to get the ID of the partnership
    public String getPartnershipId() {
        return partnerShipId;
    }
    Scanner input = new Scanner(System.in);
    public boolean updateContract(Partnership partnership, Date newEndDate, double newCommissionRate) {
        boolean updateContract = false;
        partnership.setEndDate(newEndDate);
        partnership.setCommissionRate(newCommissionRate);
        System.out.println("Contract updated for partnership with ID: " + partnership.getPartnershipId());
        return updateContract = true;
    }
    public boolean isActive() {
        if (endDate == null) {
            return false; // Or throw an exception if an active end date is crucial
        }
        Date currentDate = new Date(); // Current date
        return endDate.after(currentDate);
    }
    public ArrayList<Room> allocateRoomstoAdmin(int numberOfRooms){
        ArrayList<Room> providedRooms = new ArrayList<>();
        System.out.println("\t In the Partner Class");
        System.out.println("\t The partner is now allocating rooms ");
        for(int i = 0;i<numberOfRooms;i++){
            System.out.println("Enter the room Id");
            String roomid = input.nextLine();
            input.nextLine();
            System.out.println("Enter the room type");
            String roomType = input.next();
            System.out.println("Enter the price ");
            double price = input.nextDouble();
            System.out.println("Enter the capacity of the room ");
            int capacity = input.nextInt();
            boolean isAvaliable = true;
            ArrayList<String> amenities = new ArrayList<>();
            amenities.add("Wi-Fi");
            amenities.add("TV");

            Room providingRoom = new Room(roomid,roomType,price,isAvaliable,capacity,amenities);
            providedRooms.add(i,providingRoom);

        }
        return providedRooms;
    }
}



