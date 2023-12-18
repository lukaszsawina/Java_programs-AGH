package com.example.pracownicy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PracownicyController {

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Text avgRateTextField;

    @FXML
    private Button addGroupButton;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<EmployeeClass, String> groupNameColumn;

    @FXML
    private Text groupNameTextField;

    @FXML
    private TableColumn<EmployeeClass, Integer> groupSizeColumn;

    @FXML
    private Text groupSizeTextField;

    @FXML
    private TableView<EmployeeClass> groupTableView;

    @FXML
    private Text surnameTextField;

    @FXML
    private Button modifyEmployeeButton;

    @FXML
    private Button modifyGroupButton;

    @FXML
    private TableColumn<Employee,String> nameColumn;

    @FXML
    private Text nameTextField;

    @FXML
    private Button removeEmployeeButton;

    @FXML
    private Button removeGroupButton;

    @FXML
    private Text salaryTextField;

    @FXML
    private Text statusTextField;

    @FXML
    private TableColumn<Employee,String> surnameColumn;
    @FXML
    private TableColumn<Employee,Integer> yearColumn;
    @FXML
    private Text yearOfBirthTextField;

    public void initialize(){
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeClass,String>("groupName"));
        groupSizeColumn.setCellValueFactory(new PropertyValueFactory<EmployeeClass,Integer>("size"));
        groupTableView.setItems(FXCollections.observableArrayList(DBManager.getInstance().getAllGroups()));

        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee,String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Employee,String>("surname"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("yearOfBirth"));

        groupTableView.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection != null){
                EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
                employeeTableView.setItems(FXCollections.observableArrayList(DBManager.getInstance().getEmployeeListByGroup(selectedGroup)));
            }
        });

    }

    public void addGroupButtonClicked(MouseEvent mouseEvent) {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Nowa grupa");
        dialogPane.setMinWidth(300);

        TextField groupNameField = new TextField();
        TextField numberOfEmployeeField = new TextField();

        dialogPane.setContent(new VBox(
                new Label("Nazwa:"),
                groupNameField,
                new Label("Rozmiar:"),
                numberOfEmployeeField
        ));

        Dialog<String> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (groupNameField.getText().isEmpty() || numberOfEmployeeField.getText().isEmpty()) {
                    showAlert("Wszystkie pola są wymagane!");
                    return null;
                }
                String groupName = groupNameField.getText();
                int maxEmployee;
                try {
                    maxEmployee = Integer.parseInt(numberOfEmployeeField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Źle wprowadzone dane!");
                    return null;
                }

                EmployeeClass newGroup = new EmployeeClass(groupName, maxEmployee);

                groupTableView.getItems().add(newGroup);
                DBManager.getInstance().saveGroup(newGroup);
            }
            return null;
        });

        dialog.showAndWait();
    }

    public void removeGroupButtonClickied(MouseEvent mouseEvent) {
        EmployeeClass selectedGroupName = groupTableView.getSelectionModel().getSelectedItem();
        if (selectedGroupName != null) {
            groupTableView.getItems().remove(selectedGroupName);
            DBManager.getInstance().deleteGroup(selectedGroupName);
            removeGroupButton.setDisable(true);
        }
    }

    public void addEmployeeButtonClicked(MouseEvent mouseEvent) {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Nowy pracownik");
        dialogPane.setMinWidth(300);

        TextField nameField = new TextField();
        TextField surnameField = new TextField();

        ChoiceBox<EmployeeStatus> employeeStatusChoiceBox = new ChoiceBox<>();
        employeeStatusChoiceBox.setItems(FXCollections.observableArrayList(Arrays.stream(EmployeeStatus.values()).toList()));
        employeeStatusChoiceBox.getSelectionModel().select(EmployeeStatus.OBECNY);

        TextField yearOfBirthField = new TextField();
        TextField salaryField = new TextField();

        VBox vBox =new VBox(
                new Label("Imie:"),
                nameField,
                new Label("Nazwisko:"),
                surnameField,
                new Label("Rok urodzenia:"),
                yearOfBirthField,
                new Label("Zarobki:"),
                salaryField,
                employeeStatusChoiceBox
        );
        vBox.setSpacing(5.0);
        dialogPane.setContent(vBox);

        Dialog<String> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || yearOfBirthField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                    showAlert("Wszystkie pola są wymgane!");
                    return null;
                }

                String name = nameField.getText();
                String surname = surnameField.getText();
                int yearOfBirth;
                double salary;

                try {
                    yearOfBirth = Integer.parseInt(yearOfBirthField.getText());
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Źle wprowadzone dane!");
                    return null;
                }

                EmployeeStatus status = employeeStatusChoiceBox.getSelectionModel().getSelectedItem();


                Employee newEmployee = new Employee(name, surname, salary , yearOfBirth, status);
                newEmployee.setEmployeeGroup(groupTableView.getSelectionModel().getSelectedItem());
                EmployeeClass employeeClass = groupTableView.getSelectionModel().getSelectedItem();
                DBManager.getInstance().saveEmployee(newEmployee);
                employeeTableView.getItems().add(newEmployee);
                updatePercentage();
                updateRating();
                if(employeeTableView.getItems().size() == employeeClass.getSize())
                    addEmployeeButton.setDisable(true);
            }
            return null;
        });
        dialog.showAndWait();
    }

    public void deleteEmployeeButtonClicked(MouseEvent mouseEvent) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeTableView.getItems().remove(selectedEmployee);
            DBManager.getInstance().deleteEmployee(selectedEmployee);
            removeGroupButton.setDisable(true);
        }
        updatePercentage();
        updateRating();
        addEmployeeButton.setDisable(false);
    }

    public void modifyEmployeeButtonClicked(MouseEvent mouseEvent) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            DialogPane dialogPane = new DialogPane();
            dialogPane.setHeaderText("Edycja pracownika");

            TextField nameField = new TextField(selectedEmployee.getName());
            TextField surnameField = new TextField(selectedEmployee.getSurname());
            ChoiceBox<EmployeeStatus> employeeStatusChoiceBox = new ChoiceBox<>();
            employeeStatusChoiceBox.setItems(FXCollections.observableArrayList(Arrays.stream(EmployeeStatus.values()).toList()));
            employeeStatusChoiceBox.getSelectionModel().select(selectedEmployee.getEmployeeStatus());
            TextField yearOfBirthField = new TextField(String.valueOf(selectedEmployee.getYearOfBirth()));
            TextField salaryField = new TextField(String.valueOf(selectedEmployee.getSalary()));

            VBox vBox =new VBox(
                    new Label("Imie:"),
                    nameField,
                    new Label("Nazwisko:"),
                    surnameField,
                    new Label("Rok urodzenia:"),
                    yearOfBirthField,
                    new Label("Zarobki:"),
                    salaryField,
                    employeeStatusChoiceBox
            );
            vBox.setSpacing(5.0);
            dialogPane.setContent(vBox);

            Dialog<String> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);

            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || yearOfBirthField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                        showAlert("Wszystkie pola są wymagane!");
                        return null;
                    }
                    selectedEmployee.setName(nameField.getText());
                    selectedEmployee.setSurname(surnameField.getText());

                    try {
                        selectedEmployee.setYearOfBirth(Integer.parseInt(yearOfBirthField.getText()));
                        selectedEmployee.setSalary(Double.parseDouble(salaryField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("Źle wprowadzone dane!");
                        return null;
                    }
                    selectedEmployee.setEmployeeStatus(employeeStatusChoiceBox.getSelectionModel().getSelectedItem());
                    DBManager.getInstance().modifyEmployee(selectedEmployee);
                    employeeTableView.refresh();
                    employteeTableViewClicked(null);
                }
                return null;
            });

            dialog.showAndWait();
        }
    }

    public void groupTableViewClicked(MouseEvent mouseEvent) {

        EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
        if(selectedGroup != null) {
            addEmployeeButton.setDisable(employeeTableView.getItems().size() == selectedGroup.getSize());

            groupNameTextField.setText(selectedGroup.getGroupName());
            updatePercentage();
            updateRating();
            if(employeeTableView.getSelectionModel().getSelectedItem() == null){
                nameTextField.setText("");
                surnameTextField.setText("");
                statusTextField.setText("");
                salaryTextField.setText("");
                yearOfBirthTextField.setText("");
            }

        }
    }

    private void updateRating() {
        EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
        if(selectedGroup == null || DBManager.getInstance().getRateListByGroup(selectedGroup).isEmpty())
            avgRateTextField.setText("");
        else {
            List<Rate> rates = DBManager.getInstance().getRateListByGroup(selectedGroup);

            double avg = rates.stream().mapToDouble(Rate::getStarNumber).sum() / rates.size();
            avgRateTextField.setText(new DecimalFormat("##.##").format(avg));
        }
    }

    public void employteeTableViewClicked(MouseEvent mouseEvent) {
        Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null){
            nameTextField.setText(selectedEmployee.getName());
            surnameTextField.setText(selectedEmployee.getSurname());
            statusTextField.setText(selectedEmployee.getEmployeeStatus().toString());
            salaryTextField.setText(String.valueOf(selectedEmployee.getSalary()));
            yearOfBirthTextField.setText(String.valueOf(selectedEmployee.getYearOfBirth()));
        }

    }

    public void keyInSearchPressed(KeyEvent keyEvent) {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        String partialText = "";
        EmployeeClass employeeClass = groupTableView.getSelectionModel().getSelectedItem();

        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            partialText = searchTextField.getText();
            List<Employee> employeeList = DBManager.getInstance().getEmployeeListByGroup(employeeClass);
            for (Employee e : employeeList) {
                if(e.getSurname().startsWith(partialText))
                    list.addAll(e);
            }
            employeeTableView.setItems(list);
        }
    }

    private void updatePercentage(){
        EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
        if(selectedGroup == null)
            groupSizeTextField.setText("");
        else
            groupSizeTextField.setText(new DecimalFormat("##.##").format((double) DBManager.getInstance().getEmployeeListByGroup(selectedGroup).size() * 100 / selectedGroup.getSize()) + " %");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void viewRatesButtonClicked(MouseEvent mouseEvent) {
        EmployeeClass selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
        TableView<Rate> tableView = new TableView<>();

        TableColumn<Rate, Integer> rateColumn = new TableColumn<>("Ocena");
        rateColumn.setCellValueFactory(new PropertyValueFactory<Rate,Integer>("starNumber"));
        rateColumn.setMinWidth(100);

        TableColumn<Rate, String> commentColumn = new TableColumn<>("Komentarz");
        commentColumn.setCellValueFactory(new PropertyValueFactory<Rate,String>("comment"));
        commentColumn.setMinWidth(300);

        TableColumn<Rate, Date> dateColumn = new TableColumn<>("Data");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Rate,Date>("reviewDate"));
        dateColumn.setMinWidth(100);

        tableView.getColumns().addAll(rateColumn, commentColumn, dateColumn);

        tableView.setItems(FXCollections.observableArrayList(DBManager.getInstance().getRateListByGroup(selectedGroup)));
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Lista ocen");
        dialogPane.setMinWidth(500);

        VBox vBox = new VBox(
                new Label("Lista ocen:"),
                tableView
        );
        vBox.setSpacing(5.0);
        dialogPane.setContent(vBox);

        Dialog<String> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        dialogPane.getButtonTypes().addAll(ButtonType.CLOSE);

        dialog.setOnCloseRequest(event -> dialog.close());

        dialog.showAndWait();
    }

    public void rateButtonClicked(MouseEvent mouseEvent) {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Dodaj ocene");

        TextField commentField = new TextField();

        ChoiceBox<Integer> rateChoiceBox = new ChoiceBox<>();
        rateChoiceBox.setItems(FXCollections.observableArrayList(List.of(1,2,3,4,5,6)));
        rateChoiceBox.getSelectionModel().select(0);

        VBox vBox =new VBox(
                new Label("Ocena grupy:"),
                rateChoiceBox,
                new Label("Komentarz: "),
                commentField
        );
        vBox.setSpacing(5.0);
        dialogPane.setContent(vBox);

        Dialog<String> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                int starNumber = rateChoiceBox.getSelectionModel().getSelectedItem();
                String comment = commentField.getText();
                if(comment.isEmpty() || comment == null)
                    comment = "Nie podano";

                Rate rate = new Rate(starNumber, new Date(), comment);
                EmployeeClass employeeClass = groupTableView.getSelectionModel().getSelectedItem();
                rate.setEmployeeGroup(employeeClass);
                DBManager.getInstance().saveRate(rate);
                updatePercentage();
                updateRating();
            }
            return null;
        });
        dialog.showAndWait();
    }

    public void exportToCSVButtonClicked(MouseEvent mouseEvent) {
        DBManager.getInstance().exportToCSV();
    }
}