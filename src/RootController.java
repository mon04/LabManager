import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

public class RootController implements Initializable {

    @FXML
    private VBox vbox_entireScene;
    @FXML
    private ListView<Module> list_savedModules;
    @FXML
    private VBox vbox_newModule;
    @FXML
    private Label label_moduleEditorHeading;
    @FXML
    private TextField tf_moduleCode;
    @FXML
    private TextField tf_moduleTitle;
    @FXML
    private ComboBox<String> combo_problemsReleasedDay;
    @FXML
    private TextField tf_problemsReleasedTime;
    @FXML
    private ComboBox<String> combo_caEvaluationEndsDay;
    @FXML
    private TextField tf_caEvaluationEndsTime;
    @FXML
    private DatePicker date_problemsDisappear;
    @FXML
    private TextField tf_problemsDisappearTime;
    @FXML
    private SplitPane splitPane_weeksTimedLabs;
    @FXML
    private TableView<LabSession> table_groups;
    @FXML
    private TableColumn<LabSession, String> tableColumn_groups_day;
    @FXML
    private TableColumn<LabSession, String> tableColumn_groups_time;
    @FXML
    private TableColumn<LabSession, String> tableColumn_groups_groupName;
    @FXML
    private Button btn_moduleWeeksEdit;
    @FXML
    private TableView<Week> table_weeks;
    @FXML
    private Button btn_moduleGroupsEdit;
    @FXML
    private Button btn_saveModule;
    @FXML
    private Button btn_deleteModule;

    public static ObservableList<String> daysFormatted = FXCollections.observableArrayList();

    private ObservableList<Module> modules = FXCollections.observableArrayList();


    // Initialize method

    public void initialize(URL url, ResourceBundle rb) {

        list_savedModules.setItems(modules);
        setCellFactory(list_savedModules);

        tableColumn_groups_day.setCellValueFactory(new PropertyValueFactory<>("dayFormatted"));
        tableColumn_groups_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumn_groups_groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));

        //Set items for 'day of week' combo boxes
        for(DayOfWeek d : DayOfWeek.values()) {
            daysFormatted.add(d.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        combo_problemsReleasedDay.setItems(daysFormatted);
        combo_caEvaluationEndsDay.setItems(daysFormatted);

        //Default "New" module
        modules.add(new Module("New"));
        selectModule(0);

        //Add some test modules
        addTestModules(modules);

    }

    // FXML methods

    @FXML
    void moduleListClicked(MouseEvent e) {
        setModuleEditor();
    }

    @FXML
    void moduleListKeyPressed(KeyEvent event) {
        setModuleEditor();
    }

    @FXML
    void openGroupsEditor(ActionEvent event) throws IOException {

        vbox_entireScene.setDisable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Groups.fxml"));
        Parent root = loader.load();
        GroupsController groupsController = loader.getController();

        groupsController.setData(getSelectedModule(), this);

        String moduleCode = groupsController.getModule().getCode();

        Stage stage = new Stage();
        stage.setTitle(String.format("%s - Timed labs", moduleCode));
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/media/icon.png"));
        stage.setMinWidth(400);
        stage.setMinHeight(485);
        stage.showAndWait();

    }

    @FXML
    void openWeeksEditor(ActionEvent event) {

    }

    @FXML
    void comboProblemsReleasedDayKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            combo_problemsReleasedDay.show();
        }
    }

    @FXML
    void comboCaEvaluationEndsDayKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            combo_caEvaluationEndsDay.show();
        }
    }

    @FXML
    void deleteModule(ActionEvent event) {

        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > 0) {
            modules.remove(selectedIndex);
        }

    }

    @FXML
    void saveModule(ActionEvent event) {

        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {

            int existingIndex;
            if((existingIndex = getModuleIndex(tf_moduleCode.getText())) > -1) {
                modules.remove(existingIndex);
                modules.add(existingIndex, newModuleFromData());
            }
            else {
                modules.add(newModuleFromData());
            }

            selectModule(tf_moduleCode.getText());
        }

    }

    // Helper methods

    public void setModuleEditor() {

        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {

            vbox_newModule.setDisable(false);

            Module selectedModule = modules.get(selectedIndex);

            //Disable 'Delete module' btn for "New"
            if(selectedIndex == 0) btn_deleteModule.setDisable(true);
            else btn_deleteModule.setDisable(false);

            label_moduleEditorHeading.setText(selectedModule.getFullTitle());

            tf_moduleCode.setText(selectedModule.getCode());
            tf_moduleTitle.setText(selectedModule.getTitle());

            table_groups.setItems(selectedModule.getLabSessions());

            combo_problemsReleasedDay.setValue(selectedModule.getProblemsReleased().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tf_problemsReleasedTime.setText(selectedModule.getProblemsReleased().getTime().toString());

            combo_caEvaluationEndsDay.setValue(selectedModule.getCaEvaluationEnds().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tf_caEvaluationEndsTime.setText(selectedModule.getCaEvaluationEnds().getTime().toString());

            date_problemsDisappear.setValue(selectedModule.getProblemsDisappear().toLocalDate());
            tf_problemsDisappearTime.setText(selectedModule.getProblemsDisappear().toLocalTime().toString());
        }

    }

    public void setCellFactory(ListView<Module> listView) {

        listView.setCellFactory(param -> new ListCell<Module>() {
            @Override
            protected void updateItem(Module item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getFullTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getFullTitle());
                }
            }
        });

    }

    public void selectModule(int i) {

        list_savedModules.getSelectionModel().select(i);
        setModuleEditor();
    }

    public void selectModule(String code) {

        for(int i = 0; i < modules.size(); i++) {

            if(modules.get(i).getCode().equalsIgnoreCase(code)) {
                selectModule(i);
            }
        }

    }

    public int getModuleIndex(String code) {

        for(int i = 0; i < modules.size(); i++) {

            Module m = modules.get(i);

            if(m.getCode().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;

    }

    public Module getSelectedModule() {
        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {
            return modules.get(selectedIndex);
        }
        return null;
    }

    public void sortModules() {

        //Bubble sort
        for(int i = 0; i < modules.size(); i++) {
            for (int j = 1; j < (modules.size() -i); j++) {

                Module m1 = modules.get(j-1);
                Module m2 = modules.get(j);

                //Keep "New" at index 0
                if(!m1.getCode().equals("New") && !m2.getCode().equals("New")) {

                    if (m1.getCode().compareToIgnoreCase(m2.getCode()) > 0) {
                        Collections.swap(modules, j, j-1);
                    }
                }
            }
        }
    }

    public boolean moduleExists(String code) {

        for(Module m : modules) {
            if(m.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public Module newModuleFromData() {

        DayOfWeek problemsReleasedDay = DayOfWeek.of(combo_problemsReleasedDay.getSelectionModel().getSelectedIndex()+1);
        DayOfWeek caEvaluationEndsDay = DayOfWeek.of(combo_caEvaluationEndsDay.getSelectionModel().getSelectedIndex()+1);

        return new Module(
                tf_moduleTitle.getText(),
                tf_moduleCode.getText(),
                table_groups.getItems(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(problemsReleasedDay, LocalTime.parse(tf_problemsReleasedTime.getText())),
                new DayOfWeekAndTime(caEvaluationEndsDay, LocalTime.parse(tf_problemsReleasedTime.getText())),
                LocalDateTime.of(date_problemsDisappear.getValue(), LocalTime.parse(tf_problemsDisappearTime.getText()))
        );
    }

    public void setDisable(boolean disable) {

        vbox_entireScene.setDisable(disable);
    }

    // Testing

    public void addTestModules(ObservableList<Module> destination) {

        ObservableList<LabSession> sessions = FXCollections.observableArrayList();

        sessions.add(
                new LabSession("Red", 90, DayOfWeek.THURSDAY, LocalTime.of(9,0))
        );
        sessions.add(
                new LabSession("Blue", 90, DayOfWeek.THURSDAY, LocalTime.of(10,30))
        );
        sessions.add(
                new LabSession("Green", 90, DayOfWeek.FRIDAY, LocalTime.of(9,0))
        );
        sessions.add(
                new LabSession("Yellow", 90, DayOfWeek.FRIDAY, LocalTime.of(10,30))
        );


        destination.add(new Module(
                "Introduction to Programming 1",
                "CS161",
                sessions,
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9,30)),
                new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(18,0)),
                LocalDateTime.of(2022,8,31,23,59)
        ));

        destination.add(new Module(
                "Computer Systems 1",
                "CS171",
                FXCollections.observableArrayList(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.TUESDAY, LocalTime.of(12,0)),
                new DayOfWeekAndTime(DayOfWeek.SUNDAY, LocalTime.of(15,0)),
                LocalDateTime.of(2022,8,31,23,59)
        ));

    }

}
