import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CarRentalSystem extends JFrame implements ActionListener {

    private final JButton carManagementButton;
    private final JButton rentalManagementButton;
    private final JButton logoutButton;

    private final JLabel databaseStatus;

    private Connection connection;

    public CarRentalSystem() {

        setSize(720,720);
        setResizable(false);
        setTitle("CRMS V1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        databaseStatus = new JLabel();
        databaseStatus.setHorizontalAlignment(JLabel.CENTER);
        databaseStatus.setVerticalAlignment(JLabel.BOTTOM);
        add(databaseStatus);
        connectToDatabase();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);
        menuBar.setVisible(true);


        carManagementButton = new JButton("Car Management");
        carManagementButton.setBackground(Color.green);
        carManagementButton.addActionListener(this);
        menuBar.add(carManagementButton);

        rentalManagementButton = new JButton("Rental Management");
        rentalManagementButton.setBackground(Color.green);
        rentalManagementButton.addActionListener(this);
        menuBar.add(rentalManagementButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED.brighter());
        logoutButton.addActionListener(this);
        menuBar.add(logoutButton);

        setJMenuBar(menuBar);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }

    public void connectToDatabase() {

        String serverName = "localhost";
        String myDataBase = "carrentalsystem";
        String url = "jdbc:mysql://" + serverName + "/" + myDataBase;

        try {
            String username = "root";
            String password = "Zenit1925!";
            connection = DriverManager.getConnection(url, username, password);
            databaseStatus.setText("Connection successful");
            databaseStatus.setForeground(Color.green.darker());

        } catch (SQLException sqlException) {

            databaseStatus.setText("Connection to database failed!");
            databaseStatus.setForeground(Color.red);

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource() == carManagementButton) {

            CarManagement carManagement = new CarManagement(connection);
            carManagement.setVisible(true);

        } else if(actionEvent.getSource() == rentalManagementButton) {
            /*
               to be added in the future
             */

        } else if (actionEvent.getSource() == logoutButton) {
            dispose();
        }

    }
}
