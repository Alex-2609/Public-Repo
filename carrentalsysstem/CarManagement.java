import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * All mySQL Queries below are planned to be extracted to own classes in the future
 */

public class CarManagement extends JFrame implements ActionListener {

    private final JButton searchButton;
    private final JButton editButton;

    private final JButton addButton;

    private final JButton updateButton;

    private final JButton insertButton;


    private final JTextField licensePlateTextField;
    JLabel licensePlateLabel;

    JTextField manufacturerTextField;
    JLabel manufacturerLabel;


    JTextField carModelTextField;
    JLabel carModelLabel;

    JTextField fuelTypeTextField;
    JLabel fuelTypeLabel;

    JTextField gearBoxTypeTextField;
    JLabel gearBoxTypeLabel;

    JTextField carClassTextField;
    JLabel carClassLabel;

    JTextField carStatusTextField;
    JLabel carStatusLabel;

    private final Connection connection;

    public CarManagement(Connection connection) {

        this.connection = connection;

        setSize(400, 400);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);

        searchButton = new JButton("Search Car");
        searchButton.setBackground(Color.green);
        searchButton.addActionListener(this);
        menuBar.add(searchButton);

        editButton = new JButton("Edit Car");
        editButton.setBackground(Color.green);
        editButton.addActionListener(this);
        menuBar.add(editButton);

        addButton = new JButton("Add Car");
        addButton.setBackground(Color.green);
        addButton.addActionListener(this);
        menuBar.add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBackground(Color.green);
        updateButton.setVisible(false);
        updateButton.addActionListener(this);

        insertButton = new JButton("Insert");
        insertButton.setBackground(Color.green);
        insertButton.setVisible(false);
        insertButton.addActionListener(this);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED.darker());
        cancelButton.setVisible(false);
        cancelButton.addActionListener(this);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());

        licensePlateLabel = new JLabel("License Plate: ");
        manufacturerLabel = new JLabel("Manufacturer: ");
        carModelLabel = new JLabel("Car Model: ");
        fuelTypeLabel = new JLabel("Fuel Type: ");
        gearBoxTypeLabel = new JLabel("Gearbox: ");
        carClassLabel = new JLabel("Class: ");
        carStatusLabel = new JLabel("Status: ");

        licensePlateTextField = new JTextField(10);

        manufacturerTextField = new JTextField(10);
        manufacturerTextField.setEditable(false);

        carModelTextField = new JTextField(10);
        carModelTextField.setEditable(false);

        fuelTypeTextField = new JTextField(10);
        fuelTypeTextField.setEditable(false);

        gearBoxTypeTextField = new JTextField(10);
        gearBoxTypeTextField.setEditable(false);

        carClassTextField = new JTextField(10);
        carClassTextField.setEditable(false);

        carStatusTextField = new JTextField(10);
        carStatusTextField.setEditable(false);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7 ;
        panel.add(licensePlateLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(manufacturerLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carModelLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(fuelTypeLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(gearBoxTypeLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carClassLabel,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carStatusLabel,gridBagConstraints);


        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        panel.add(licensePlateTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(manufacturerTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carModelTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(fuelTypeTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(gearBoxTypeTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carClassTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(carStatusTextField,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(updateButton,gridBagConstraints);
        gridBagConstraints.gridy++;
        panel.add(insertButton,gridBagConstraints);

        add(panel);
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource() == searchButton) {
            deactivateTextEditFields();
            executeQuery();

        } else if(actionEvent.getSource() == editButton) {

            activateTextEditFields();
            clearAllTextFields();
            updateButton.setVisible(true);

        } else if(actionEvent.getSource() == updateButton) {
            deactivateTextEditFields();
            clearAllTextFields();
            updateDatabase();
            updateButton.setVisible(false);
        } else if(actionEvent.getSource() == addButton) {
            insertButton.setVisible(true);
            activateTextEditFields();
            clearAllTextFields();
        } else if(actionEvent.getSource() == insertButton) {
            insertIntoDatabase();
            clearAllTextFields();
        }

    }

    private void insertIntoDatabase() {

        String query = "INSERT INTO cars(license_plate_number,manufacturer, model, fuel_type, gearbox_type, car_class, car_status) "
        + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,licensePlateTextField.getText());
            preparedStatement.setString(2,manufacturerTextField.getText());
            preparedStatement.setString(3,carModelTextField.getText());
            preparedStatement.setString(4,fuelTypeTextField.getText());
            preparedStatement.setString(5,gearBoxTypeTextField.getText());
            preparedStatement.setString(6,carClassTextField.getText());
            preparedStatement.setString(7,carStatusTextField.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this,"You add successfully an new dataset to the database!","Update successfully",JOptionPane.INFORMATION_MESSAGE);
            deactivateTextEditFields();
            insertButton.setVisible(false);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(this,"An error occurred while adding an new dataset to the database","ERROR OCCURRED",JOptionPane.WARNING_MESSAGE);

        }

    }

    private void updateDatabase() {

        String query = "UPDATE cars SET license_plate_number = ?, manufacturer = ?, model = ?, fuel_type = ?, gearbox_type = ?, car_class = ?, car_status = ? WHERE license_plate_number = ?";
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,licensePlateTextField.getText());
            preparedStatement.setString(2,manufacturerTextField.getText());
            preparedStatement.setString(3,carModelTextField.getText());
            preparedStatement.setString(4,fuelTypeTextField.getText());
            preparedStatement.setString(5,gearBoxTypeTextField.getText());
            preparedStatement.setString(6,carClassTextField.getText());
            preparedStatement.setString(7,carStatusTextField.getText());
            preparedStatement.setString(8,licensePlateTextField.getText());
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this,"You update successful the dataset","Update successful",JOptionPane.WARNING_MESSAGE);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(this,"An Error occurred while updating the database","Update failed",JOptionPane.WARNING_MESSAGE);

        }

    }

    private void executeQuery() {

        try {
            String query = "SELECT * from cars WHERE license_plate_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,licensePlateTextField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()) {

                manufacturerTextField.setText(resultSet.getString("manufacturer"));
                carModelTextField.setText(resultSet.getString("model"));
                fuelTypeTextField.setText(resultSet.getString("fuel_type"));
                gearBoxTypeTextField.setText(resultSet.getString("gearbox_type"));
                carClassTextField.setText(resultSet.getString("car_class"));
                carStatusTextField.setText(resultSet.getString("car_status"));
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"No matching result in database","ERROR OCCURRED",JOptionPane.WARNING_MESSAGE);
        }

    }

    public void activateTextEditFields() {

        manufacturerTextField.setEditable(true);
        carModelTextField.setEditable(true);
        fuelTypeTextField.setEditable(true);
        gearBoxTypeTextField.setEditable(true);
        carClassTextField.setEditable(true);
        carStatusTextField.setEditable(true);
    }

    public void deactivateTextEditFields() {

        manufacturerTextField.setEditable(false);
        carModelTextField.setEditable(false);
        fuelTypeTextField.setEditable(false);
        gearBoxTypeTextField.setEditable(false);
        carClassTextField.setEditable(false);
        carStatusTextField.setEditable(false);

    }

    public void clearAllTextFields() {

        manufacturerTextField.setText(null);
        carModelTextField.setText(null);
        fuelTypeTextField.setText(null);
        gearBoxTypeTextField.setText(null);
        carClassTextField.setText(null);
        carStatusTextField.setText(null);

    }
}
