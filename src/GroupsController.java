import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupsController {

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
    public Label label_groupsEditorHeading;

    private Module module;
    private ObservableList<LabSession> sessions = FXCollections.observableArrayList();


    @FXML
    public void deleteButtonAction(ActionEvent actionEvent) {
        deleteSelectedGroup();
    }

    @FXML
    public void addGroup(ActionEvent actionEvent) {

        int existingIndex;
        if((existingIndex = getSessionIndex(tf_groupName.getText())) != -1) {
            System.out.println("Overwriting: "+tf_groupName.getText());
            sessions.remove(existingIndex);
            sessions.add(existingIndex, newSessionFromData());
        }
        else {
            sessions.add(newSessionFromData());
        }

    }

    @FXML
    public void cancelGroupEdit(ActionEvent actionEvent) {
    }

    @FXML
    public void saveGroupEdit(ActionEvent actionEvent) {
    }

    @FXML
    void comboGroupsDayKeyPressed(KeyEvent event) {

        if(event.getCode() == KeyCode.ENTER) {
            combo_day.show();
        }
    }

    @FXML
    public void tableGroupsMouseClicked(MouseEvent mouseEvent) {
        setGroupEditor();
    }

    @FXML
    public void tableGroupsKeyPressed(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.DELETE) {
            deleteSelectedGroup();
        }
        if(keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) {
            setGroupEditor();
        }
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public void set(Module m) {

        setModule(m);
        label_groupsEditorHeading.setText(m.getFullTitle());
        sessions.addAll(module.getLabSessions());

        tableColumn_groups_day.setCellValueFactory(new PropertyValueFactory<>("dayFormatted"));
        tableColumn_groups_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumn_groups_groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        table_groups.setItems(sessions);

        combo_day.setItems(RootController.daysFormatted);
    }

    public void setGroupEditor() {

        int selectedIndex = table_groups.getSelectionModel().getSelectedIndex();

        if(selectedIndex > -1) {
            LabSession s = sessions.get(selectedIndex);

            tf_groupName.setText(s.getGroupName());
            combo_day.setValue(s.getDayFormatted());
            tf_startTime.setText(s.getStartTime().toString());
        }

    }

    public int getSessionIndex(String groupName) {

        for(int i = 0; i < sessions.size(); i++) {
            LabSession s = sessions.get(i);
            if(s.getGroupName().equalsIgnoreCase(groupName)) {
                return i;
            }
        }
        return -1;

    }

    public LabSession newSessionFromData() {

        //Length will be set to 0. Real value must be assigned when user clicks 'Save all'
        return new LabSession(
                tf_groupName.getText(),
                0,
                DayOfWeek.of(combo_day.getSelectionModel().getSelectedIndex()+1),
                LocalTime.parse(tf_startTime.getText())
        );
    }

    public void deleteSelectedGroup() {
        sessions.remove(table_groups.getSelectionModel().getSelectedIndex());
    }


    // Testing
    public ObservableList<LabSession> getTestSessions() {

        //Get an Ob. List of placeholder sessions to fill table with
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
