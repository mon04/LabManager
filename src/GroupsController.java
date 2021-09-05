import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    public TableView<LabSession> table_groups;
    public TextField tf_groupName;
    public TableColumn<LabSession, String> tableColumn_groups_day;
    public TableColumn<LabSession, String> tableColumn_groups_time;
    public TableColumn<LabSession, String> tableColumn_groups_groupName;
    public ComboBox<String> combo_day;
    public TextField tf_startTime;
    public Button btn_deleteGroup;
    public Button btn_addGroup;
    public TextField tf_labLengthHours;
    public TextField tf_labLengthMin;
    public Button btn_cancel;
    public Button btn_saveAll;


    ObservableList<LabSession> sessions;

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing GroupsController");
        combo_day.setItems(RootController.days);

        tableColumn_groups_day.setCellValueFactory(new PropertyValueFactory<>("dayFormatted"));
        tableColumn_groups_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumn_groups_groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        sessions = FXCollections.observableArrayList();
        sessions.setAll(getTestSessions());
        table_groups.setItems(sessions);

    }


    public void deleteGroup(ActionEvent actionEvent) {
    }

    public void addGroup(ActionEvent actionEvent) {
    }




    public void cancelGroupEdit(ActionEvent actionEvent) {
    }

    public void saveGroupEdit(ActionEvent actionEvent) {
    }

    @FXML
    void comboGroupsDayKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            combo_day.show();
        }
    }

    public void tableGroupsKeyPressed(KeyEvent keyEvent) {
        //switch(keyEvent.getCode()) {
            //If DELETE key, remove group
        //}
    }

    public ObservableList<LabSession> getTestSessions() {

        ObservableList<LabSession> testSessions = FXCollections.observableArrayList();
        testSessions.add(new LabSession(
                "red", 90, DayOfWeek.THURSDAY, LocalTime.of(9,30
        )));
        testSessions.add(new LabSession(
                "blue", 90, DayOfWeek.THURSDAY, LocalTime.of(11,10
        )));
        testSessions.add(new LabSession(
                "green", 90, DayOfWeek.FRIDAY, LocalTime.of(10,0
        )));
        testSessions.add(new LabSession(
                "yellow", 90, DayOfWeek.FRIDAY, LocalTime.of(11,40
        )));

        return testSessions;
    }
}
