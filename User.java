import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
public class User implements Serializable {
    private static final long serialVersionUID = 123456789L;
    // Attributes
    // Protected attributes because there will be child classes of this class
    protected  String name;
    protected  String email;
    protected  String password;
    protected  String CNIC;
    protected int otp;
    protected boolean verified;
    private static final String fileName  = "user1"; // File to store user data

    public User(){

    }

    // parametrized Constructor
    public User(String name, String email, String password, String CNIC){
        this.name = name;
        this.email = email;
        this.password = password;
        this.CNIC = CNIC;
        this.otp = 0; // Initialize otp to avoid potential null pointer exceptions
        this.verified = false;

    }
    static Scanner input = new Scanner(System.in);
    // Methods for a user
    public  void Register() {
        // you can solve the problem of writing different objects by two ways 1. by static but that will not make us able to register different users 2.
        try {
            boolean append = new File(fileName).exists();
            // this will check if the file exists and wil not the give the error of file not found

            try (FileOutputStream fos = new FileOutputStream(fileName, true);
                 // true means that new data will be written at the end of the file meaning that previous data will remain there
                 ObjectOutputStream oos = append ? new ObjectOutputStream(fos) {
                     // this ternary operator will check that if the header is already written it does not overwrite it again causing error in the system
                     @Override
                     protected void writeStreamHeader() throws IOException {
                         reset();
                     }// this is an overriden method of the objectOutputstream class
                 } : new ObjectOutputStream(fos)) {

                System.out.println("Enter your name:");
                name = input.nextLine();
                input.nextLine();

                System.out.println("Enter your email:");
                email = input.nextLine();

                System.out.println("Enter your password:");
                password = input.nextLine();

                System.out.println("Enter your CNIC:");
                CNIC = input.nextLine();

                // Write user data to the file
                User user = new User(name, email, password, CNIC);
                oos.writeObject(user);
                System.out.println("User registered successfully!");

            }

        } catch (IOException e) {
            System.out.println("An error has occurred while registering the user: " + e.getMessage());
        }
    }
    public boolean login()  {
        try (FileInputStream fis = new FileInputStream("user1");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            System.out.println("Enter your email");
            String inputEmail = input.next();
            input.nextLine();
            System.out.println("Enter your password");
            String inputPassword = input.next();

            boolean userFound = false;
            while (true) {
                User readUser = (User) ois.readObject();
                if (readUser.email.equals(inputEmail) && readUser.password.equals(inputPassword)) {
                    System.out.println("Login successful!");
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                System.out.println("Invalid email or password! Please register.");
                return false;
            }

            return true;

        } catch (EOFException ex) { //This exception will be caught when EOF isreached
            // System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public void sendOTP(String userEmail) {
        try {
            Random random = new Random();
            otp = 1000 + random.nextInt(9000); // Generate a random 4-digit OTP
            System.out.println("OTP sent to " + userEmail + ": " + otp);

        } catch (Exception e) {
            System.out.println("Error occurred while sending OTP: " + e.getMessage());
        }
    }
    public boolean verifyOTP() {
        try {
            System.out.println("Enter your OTP");
            int inputOTP = input.nextInt();
            if (inputOTP == this.otp) {
                this.verified = true;
                System.out.println("You have been verified successfully!");
                return true;
            } else {
                System.out.println("Invalid OTP!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error occurred while verifying OTP: " + e.getMessage());
            return false;
        }
    }
}
class Student extends User{
    // Attributes
    private String studentID;
    private String testDate;

    public Student(){

    }
    // parametrized Constructor
    public Student(String name, String email, String password, String CNIC,String studentID, String testDate) {
        super(name, email, password, CNIC);
        this.studentID = studentID;
        this.testDate = testDate;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    // Getters
    public String getStudentID() {
        return studentID;
    }

    public String getTestDate() {
        return testDate;
    }

    // Methods

    public void viewBookings() {
        Booking bookings = new Booking();
        bookings.displayBookingDetails();
    }

    // Method to submit feedback for a booking
}
class Administrator extends User{
    // Attributes
    private String administratorId;
    private String adminPassword;

    // parametrized Constructor
    public Administrator(String name, String email, String CNIC){
        super(name, email,"", CNIC);
        administratorId = "048";
        adminPassword = "1234";
    }
    // Methods

    public boolean login(){
        System.out.println("Enter your admin Id ");
        String adminId = input.next();
        System.out.println("Enter your admin password");
        String password = input.next();

        if(adminId.equalsIgnoreCase(administratorId) && password.equalsIgnoreCase(adminPassword)){
            System.out.println("You have been sent an otp enter it to verify ");
            sendOTP(email);
            if(verifyOTP())
                return true;

        }
        return false;
    }
    public void managePartnership(Partnership partnership) {
        System.out.println("Do you want to update your contract with you partner ? Enter 1 for YES and 2 for No");
        int choice = input.nextInt();
        if(choice == 1){
            System.out.println("The starting date for the contract is today's date ");
            Date startDate = new Date();
            System.out.println("Your starting date of contract is " + startDate);
            System.out.println("Enter the amount of days after which the contract will be ended ");
            int noOfDays = input.nextInt();
            int endingDate = noOfDays* 24 * 60 * 60 * 1000;
            Date endDate = new Date(startDate.getTime() + endingDate);
            System.out.println("Your ending date of contract is " + endDate);
            System.out.println("Enter the commission Rate ");
            double commisionRate = input.nextDouble();
            System.out.println("Enter provider name : ");
            String providerName  = input.nextLine();
            input.nextLine();
            System.out.println("Enter partnership id ");
            String partnerId = input.next();
            Partnership newPartner = new Partnership(providerName,partnerId);
            newPartner.updateContract(newPartner,endDate,commisionRate);
            System.out.println("A new contract have been made with the partners.");
            manageInventory();
        }
        if(choice == 2){
            return;
        }
    }

    public void manageInventory() {
        System.out.println("Enter the number of rooms you want ?");
        int noR = input.nextInt();
        Partnership partners = new Partnership();
        ArrayList<Room> newRooms = partners.allocateRoomstoAdmin(noR);
                for(int i =0;i< newRooms.size();i++){
                    Inventory.addRoom(newRooms.get(i));
                    System.out.println("Room added to inventory: " + newRooms.get(i).getRoomId());
                }
    }

}


