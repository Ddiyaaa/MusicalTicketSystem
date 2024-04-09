import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicalTicketSystemGUI extends JFrame {
    private JLabel titleLabel;
    private JButton musicalListButton;
    private JButton showScheduleButton;
    private JButton exitButton;
    private JLabel selectedMusicalLabel;
    private JComboBox<String> dateDropdown;
    private JComboBox<String> timeDropdown;
    private JComboBox<String> seatDropdown;
    private JComboBox<String> ticketTypeDropdown;
    private JButton bookTicketButton;

    private ArrayList<Musical> musicals; // Define musicals list

    public MusicalTicketSystemGUI() {
        setTitle("London Musical Tickets");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize components
        titleLabel = new JLabel("London Musical Tickets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        musicalListButton = new JButton("Musical List");
        showScheduleButton = new JButton("Show Schedule");
        exitButton = new JButton("Exit");
        selectedMusicalLabel = new JLabel("[Selected Musical Information]");
        dateDropdown = new JComboBox<>();
        timeDropdown = new JComboBox<>();
        seatDropdown = new JComboBox<>();
        ticketTypeDropdown = new JComboBox<>();
        bookTicketButton = new JButton("Book Ticket");

        // Layout setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        // Add components to buttonPanel
        buttonPanel.add(musicalListButton);
        buttonPanel.add(showScheduleButton);
        buttonPanel.add(exitButton);

        // Add components to formPanel
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateDropdown);
        formPanel.add(new JLabel("Time:"));
        formPanel.add(timeDropdown);
        formPanel.add(new JLabel("Seat:"));
        formPanel.add(seatDropdown);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(ticketTypeDropdown);
        formPanel.add(new JLabel()); // Placeholder for alignment
        formPanel.add(bookTicketButton);

        // Add components to mainPanel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(selectedMusicalLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add mainPanel and formPanel to the JFrame
        add(mainPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        musicalListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMusicalList();
            }
        });

        showScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayShowSchedule();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sample action for booking ticket
                String selectedDate = (String) dateDropdown.getSelectedItem();
                String selectedTime = (String) timeDropdown.getSelectedItem();
                String selectedSeat = (String) seatDropdown.getSelectedItem();
                String selectedTicketType = (String) ticketTypeDropdown.getSelectedItem();

                // Open payment dialog
                PaymentDialog paymentDialog = new PaymentDialog(selectedDate, selectedTime, selectedSeat, selectedTicketType, new PaymentStatusListener() {
                    @Override
                    public void onSuccess() {
                        // Handle successful payment
                        JOptionPane.showMessageDialog(null, "Payment successful. Ticket booked!");
                    }

                    @Override
                    public void onFailure() {
                        // Handle failed payment
                        JOptionPane.showMessageDialog(null, "Payment failed. Please try again.");
                    }
                });
                paymentDialog.setVisible(true);
            }
        });

        // Initialize musicals list
        musicals = new ArrayList<>();
        // Add sample musicals
        musicals.add(new Musical("Musical 1", "2024-04-01", "15:00", "A1, A2, B1"));
        musicals.add(new Musical("Musical 2", "2024-04-02", "18:00", "C1, C2, D1"));
        musicals.add(new Musical("Musical 3", "2024-04-03", "21:00", "E1, E2, F1"));

        // Populate dropdowns with sample values
        populateDropdowns();
    }

    private void populateDropdowns() {
        // Sample data for dropdowns
        String[] dates = {"2024-04-01", "2024-04-02", "2024-04-03"};
        String[] times = {"15:00", "18:00", "21:00"};
        String[] seats = {"A1", "A2", "B1", "B2", "C1", "C2"};
        String[] ticketTypes = {"Adult", "Senior", "Student"};

        // Populate date dropdown
        for (String date : dates) {
            dateDropdown.addItem(date);
        }

        // Populate time dropdown
        for (String time : times) {
            timeDropdown.addItem(time);
        }

        // Populate seat dropdown
        for (String seat : seats) {
            seatDropdown.addItem(seat);
        }

        // Populate ticket type dropdown
        for (String ticketType : ticketTypes) {
            ticketTypeDropdown.addItem(ticketType);
        }
    }

    private void displayMusicalList() {
        StringBuilder musicalsList = new StringBuilder("Musicals:\n");
        for (Musical musical : musicals) {
            musicalsList.append(musical.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, musicalsList.toString(), "Musical List", JOptionPane.PLAIN_MESSAGE);
    }

    private void displayShowSchedule() {
        String selectedMusicalName = (String) JOptionPane.showInputDialog(null, "Select a musical:", "Select Musical",
                JOptionPane.QUESTION_MESSAGE, null, musicals.toArray(), musicals.get(0).getName()).toString();

        Musical selectedMusical = null;
        for (Musical musical : musicals) {
            if (musical.getName().equals(selectedMusicalName)) {
                selectedMusical = musical;
                break;
            }
        }

        if (selectedMusical != null) {
            JOptionPane.showMessageDialog(null, "Schedule for " + selectedMusical.getName() + ":\n" +
                    "Date: " + selectedMusical.getDate() + "\n" +
                    "Time: " + selectedMusical.getTime() + "\n" +
                    "Available Seats: " + selectedMusical.getSeats(), "Show Schedule", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Schedule available.", "Show Schedule", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicalTicketSystemGUI gui = new MusicalTicketSystemGUI();
            gui.setVisible(true);
        });
    }
}

interface PaymentStatusListener {
    void onSuccess();
    void onFailure();
}

class Musical {
    private String name;
    private String date;
    private String time;
    private String seats;

    public Musical(String name, String date, String time, String seats) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSeats() {
        return seats;
    }
}

class PaymentDialog extends JDialog {
    private JTextField cardNumberField;
    private JTextField cvvField;
    private JTextField expiryDateField;
    private JButton payButton;

    private PaymentStatusListener paymentStatusListener;

    public PaymentDialog(String date, String time, String seat, String ticketType, PaymentStatusListener listener) {
        setTitle("Payment");
        setSize(300, 200);
        setModal(true);
        setLocationRelativeTo(null); // Center the dialog

        this.paymentStatusListener = listener;

        // Initialize components
        JPanel mainPanel = new JPanel(new GridLayout(4, 2));
        cardNumberField = new JTextField();
        cvvField = new JTextField();
        expiryDateField = new JTextField();
        payButton = new JButton("Pay");

        // Add components to mainPanel
        mainPanel.add(new JLabel("Card Number:"));
        mainPanel.add(cardNumberField);
        mainPanel.add(new JLabel("CVV:"));
        mainPanel.add(cvvField);
        mainPanel.add(new JLabel("Expiry Date:"));
        mainPanel.add(expiryDateField);
        mainPanel.add(new JLabel()); // Placeholder
        mainPanel.add(payButton);

        // Add mainPanel to the dialog
        add(mainPanel);

        // Add action listener to payButton
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sample action for payment
                String cardNumber = cardNumberField.getText();
                String cvv = cvvField.getText();
                String expiryDate = expiryDateField.getText();

                // Perform payment processing (dummy check for demonstration)
                boolean paymentSuccessful = true; // Change this with actual payment processing logic

                if (paymentSuccessful) {
                    paymentStatusListener.onSuccess(); // Notify success
                    dispose(); // Close the dialog
                } else {
                    paymentStatusListener.onFailure(); // Notify failure
                }
            }
        });
    }
}
