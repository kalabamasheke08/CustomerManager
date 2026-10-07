package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    // 2. Create an ObservableList<Customer>
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    // UI Components
    private TextField nameField;
    private ComboBox<String> provinceBox;
    private TableView<Customer> tableView;

    @Override
    public void start(Stage primaryStage) {
        // 1. Build a form with a name field and province list
        nameField = new TextField();
        nameField.setPromptText("Enter Name");

        provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll("Gauteng", "Western Cape", "KwaZulu-Natal", "Eastern Cape", "Free State");
        provinceBox.setPromptText("Select Province");

        Button addButton = new Button("Add Customer");
        Button deleteButton = new Button("Delete Selected");

        // Form Layout
        HBox formLayout = new HBox(10, new Label("Name:"), nameField, new Label("Province:"), provinceBox, addButton);
        formLayout.setPadding(new Insets(10));

        // 3. Add a TableView with name and province columns
        tableView = new TableView<>();
        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> provCol = new TableColumn<>("Province");
        provCol.setCellValueFactory(new PropertyValueFactory<>("province"));

        tableView.getColumns().addAll(nameCol, provCol);
        tableView.setItems(customerList); // Connect table to the list

        // Overall Layout
        VBox root = new VBox(10, formLayout, tableView, deleteButton);
        root.setPadding(new Insets(10));

        // 4. Validate input, then add the customer
        addButton.setOnAction(e -> addCustomer());

        // 5. Confirm deletion of a selected customer
        deleteButton.setOnAction(e -> deleteCustomer());

        // Final Scene Setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Customer Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Logic for Adding a Customer
    private void addCustomer() {
        String name = nameField.getText().trim();
        String province = provinceBox.getValue();

        // Validation
        if (name.isEmpty() || province == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a name and select a province.");
            return;
        }

        customerList.add(new Customer(name, province));
        nameField.clear();
        provinceBox.getSelectionModel().clearSelection();
    }

    // Logic for Deleting a Customer
    private void deleteCustomer() {
        Customer selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to delete.");
            return;
        }

        // Confirmation Dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete Customer?");
        confirm.setContentText("Are you sure you want to delete " + selected.getName() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            customerList.remove(selected);
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
