import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CEAP {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new FlowLayout());
            frame.getContentPane().setBackground(Color.LIGHT_GRAY);

            JLabel labels = new JLabel("Welcome to CEAP");
            JLabel label = new JLabel("Login as:");
            JButton userButton = new JButton("User");
            JButton adminButton = new JButton("Admin");

            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Are you a registered user?", "User Registration", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        showLoginScreen();
                    } else {
                        showRegistrationScreen();
                    }
                }
            });

            adminButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Code to handle admin login
                    JFrame adminFrame = new JFrame("Admin Login");
                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    adminFrame.setSize(400, 400);
                    adminFrame.setLayout(new GridLayout(3, 2));

                    JLabel adminIdLabel = new JLabel("Admin ID:");
                    JTextField adminIdField = new JTextField();

                    JLabel adminPwdLabel = new JLabel("Admin Password:");
                    JPasswordField adminPwdField = new JPasswordField();

                    JButton loginButton = new JButton("Login");

                    loginButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Check admin credentials
                            String adminId = adminIdField.getText();
                            String adminPwd = new String(adminPwdField.getPassword());

                            // Perform admin login validation here
                            if (adminId.equals("048") && adminPwd.equals("1234")) {
                                // Show partnership management window
                                PartnershipManagementWindow partnershipManagementWindow = new PartnershipManagementWindow(adminFrame);
                                partnershipManagementWindow.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid admin credentials!");
                            }
                        }
                    });

                    adminFrame.add(adminIdLabel);
                    adminFrame.add(adminIdField);
                    adminFrame.add(adminPwdLabel);
                    adminFrame.add(adminPwdField);
                    adminFrame.add(loginButton);

                    adminFrame.setVisible(true);
                }
            });

            frame.add(label);
            frame.add(userButton);
            frame.add(adminButton);

            frame.setVisible(true);
        });
    }

    static class PartnershipManagementWindow extends JFrame {
        private JFrame previousFrame;

        public PartnershipManagementWindow(JFrame previousFrame) {
            this.previousFrame = previousFrame;
            previousFrame.setVisible(false); // Hide the previous frame

            setTitle("Partnership Management");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 1));

            JButton managePartnershipButton = new JButton("Manage Partnership");

            managePartnershipButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to make a contract?", "Contract Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        LocalDate today = LocalDate.now();
                        JOptionPane.showMessageDialog(null, "Contract starts on: " + today);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String todayString = today.format(formatter);


                        String daysToEnd = JOptionPane.showInputDialog("Enter number of days for contract to end:");
                        LocalDate endingDate = today.plusDays(Integer.parseInt(daysToEnd));
                        String endingDateString = endingDate.format(formatter);

                        // Show contract details
                        JOptionPane.showMessageDialog(null, "Contract starts on: " + todayString + "\nContract ends on: " + endingDateString);

                        String company = JOptionPane.showInputDialog(null, "Enter the company name:");
                        int numberOfRooms = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of rooms:"));

                        // Open manage inventory window
                        ManageInventoryWindow manageInventoryWindow = new ManageInventoryWindow(previousFrame, numberOfRooms);
                        manageInventoryWindow.setVisible(true);
                        dispose(); // Close this window
                    } else {
                        // go back to the login screen
                        previousFrame.setVisible(true);
                        dispose(); // Close this window
                    }
                }
            });

            panel.add(new JLabel("Welcome, Admin!"));
            panel.add(new JLabel("What would you like to do?"));
            panel.add(managePartnershipButton);

            add(panel);
        }

        @Override
        public void dispose() {
            super.dispose();
            previousFrame.setVisible(true); // Show the previous frame when this frame is closed
        }
    }

    static class ManageInventoryWindow extends JFrame {
        private JFrame previousFrame;
        private int numberOfRoomsToAdd; // Track the number of rooms to add
        private int roomsAdded = 0;

        public ManageInventoryWindow(JFrame previousFrame, int numberOfRooms) {
            this.previousFrame = previousFrame;
            this.numberOfRoomsToAdd = numberOfRooms; // Set the number of rooms to add
            previousFrame.setVisible(false); // Hide the previous frame

            setTitle("Manage Inventory");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 2));

            JTextField roomIdField = new JTextField();
            JTextField roomTypeField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField capacityField = new JTextField();
            JCheckBox availableCheckBox = new JCheckBox();

            JButton addRoomButton = new JButton("Add Room");

            addRoomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get room details from input fields
                    String roomId = roomIdField.getText();
                    String roomType = roomTypeField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int capacity = Integer.parseInt(capacityField.getText());
                    boolean available = availableCheckBox.isSelected();

                    // Create a new room object with the entered details
                    Room room = new Room(roomId, roomType, price, available, capacity, new ArrayList<String>());

                    // Add the room to the inventory
                    Inventory inventory = new Inventory();
                    Inventory.addRoom(room);

                    // Show success message
                    JOptionPane.showMessageDialog(null, "Room added successfully!");

                    // Increment the number of rooms added
                    roomsAdded++;

                    // Clear input fields
                    roomIdField.setText("");
                    roomTypeField.setText("");
                    priceField.setText("");
                    capacityField.setText("");
                    availableCheckBox.setSelected(false);

                    // Check if all rooms have been added
                    if (roomsAdded >= numberOfRoomsToAdd) {
                        // Close the window
                        dispose();
                        // Show the previous frame
                        previousFrame.setVisible(true);
                    }
                }
            });

            panel.add(new JLabel("Room ID:"));
            panel.add(roomIdField);
            panel.add(new JLabel("Room Type:"));
            panel.add(roomTypeField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Capacity:"));
            panel.add(capacityField);
            panel.add(new JLabel("Available:"));
            panel.add(availableCheckBox);
            panel.add(new JLabel(""));
            panel.add(addRoomButton);

            add(panel);
        }

        @Override
        public void dispose() {
            super.dispose();
            previousFrame.setVisible(true); // Show the previous frame when this frame is closed
        }
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("User Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check user credentials
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Perform user login validation here
                if (validateUser(email, password)) {
                    // Show user options
                    showUserOptions();
                    loginFrame.dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });

        loginFrame.add(emailLabel);
        loginFrame.add(emailField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel(""));
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);
    }

    private static boolean validateUser(String email, String password) {
        return true;
    }


    private static void showRegistrationScreen() {
        // Implement registration screen for new users
        JFrame registrationFrame = new JFrame("User Registration");
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setSize(400, 300);
        registrationFrame.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel cnicLabel = new JLabel("CNIC:");
        JTextField cnicField = new JTextField();

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user registration details
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String cnic = cnicField.getText();

                // Perform user registration
                boolean registrationSuccess = registerUser(name, email, password, cnic);

                // Show registration success message
                if (registrationSuccess) {
                    JOptionPane.showMessageDialog(null, "Successfully registered!");
                    registrationFrame.dispose(); // Close the registration window
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
                }
            }
        });

        registrationFrame.add(nameLabel);
        registrationFrame.add(nameField);
        registrationFrame.add(emailLabel);
        registrationFrame.add(emailField);
        registrationFrame.add(passwordLabel);
        registrationFrame.add(passwordField);
        registrationFrame.add(cnicLabel);
        registrationFrame.add(cnicField);
        registrationFrame.add(new JLabel(""));
        registrationFrame.add(registerButton);

        registrationFrame.setVisible(true);
    }

    private static boolean registerUser(String name, String email, String password, String cnic) {
        return true;
    }

    private static void showUserOptions() {
        JFrame optionsFrame = new JFrame("User Options");
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setSize(300, 200);
        optionsFrame.setLayout(new FlowLayout());

        JLabel optionsLabel = new JLabel("Choose an option:");
        JButton viewRoomsButton = new JButton("View Available Rooms");
        JButton bookRoomButton = new JButton("Book a Room");

        viewRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display available rooms
                displayAvailableRooms();
            }
        });

        bookRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Book a room
                bookRoom();
            }
        });

        optionsFrame.add(optionsLabel);
        optionsFrame.add(viewRoomsButton);
        optionsFrame.add(bookRoomButton);

        optionsFrame.setVisible(true);
    }

    private static void displayAvailableRooms() {
        // Retrieve available rooms from the inventory
        Inventory inventory = new Inventory();
        ArrayList<Room> availableRooms = inventory.getAvailableRooms();

        if (availableRooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No rooms available at the moment.");
        } else {
            StringBuilder message = new StringBuilder("Available Rooms:\n");

            for (Room room : availableRooms) {
                message.append("Room ID: ").append(room.getRoomId()).append("\n");
                message.append("Room Type: ").append(room.getRoomType()).append("\n");
                message.append("Price: ").append(room.getPrice()).append("\n");
                message.append("Capacity: ").append(room.getCapacity()).append("\n");
                message.append("Available: ").append(room.isAvailable() ? "Yes" : "No").append("\n\n");
            }

            JOptionPane.showMessageDialog(null, message.toString());
        }
    }

    private static void bookRoom() {
        JFrame bookRoomFrame = new JFrame("Book a Room");
        bookRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookRoomFrame.setSize(300, 200);
                bookRoomFrame.setLayout(new GridLayout(3, 2));

        JLabel roomIdLabel = new JLabel("Enter Room ID:");
        JTextField roomIdField = new JTextField();

        JButton bookButton = new JButton("Book");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the room ID entered by the user
                String roomId = roomIdField.getText();
                JOptionPane.showMessageDialog(null, "Room booked successfully!");
                // Ask user if they want to view booking details
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to view booking details?", "View Booking Details", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Display booking details
                    displayBookingDetails();
                }
                // Display thank you message
                JOptionPane.showMessageDialog(null, "Thank you for visiting our site!");
                // Exit the program
                System.exit(0);
            }
        });

        bookRoomFrame.add(roomIdLabel);
        bookRoomFrame.add(roomIdField);
        bookRoomFrame.add(new JLabel(""));
        bookRoomFrame.add(bookButton);

        bookRoomFrame.setVisible(true);
    }

    private static void displayBookingDetails() {
        JFrame bookingDetailsFrame = new JFrame("Booking Details");
        bookingDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingDetailsFrame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        Booking book = new Booking();
        ArrayList<Booking> booking = book.returnBookings();

        // Append booking details to the text area
        for (int i = 0; i < booking.size(); i++) {
            textArea.append("Booking ID: " + booking.get(i).getBookingId() + "\n");
            textArea.append("Check-In Date: " + booking.get(i).getCheckInDate() + "\n");
            textArea.append("Check-Out Date: " + booking.get(i).getCheckOutDate() + "\n");
            textArea.append("Total Price: $" + booking.get(i).getTotalPrice() + "\n");
            textArea.append("Confirmed: " + booking.get(i).isConfirmed() + "\n\n");
        }

        panel.add(scrollPane, BorderLayout.CENTER);

        bookingDetailsFrame.add(panel);
        bookingDetailsFrame.setVisible(true);
    }
}


















