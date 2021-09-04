import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private ListView<String> list_savedModules;
    @FXML
    private VBox vbox_newModule;
    @FXML
    private Label label_moduleEditorHeading;
    @FXML
    private TextField tf_moduleCode;
    @FXML
    private TextField tf_moduleTitle;
    @FXML
    private TableView table_groups;
    //private ListView<String> list_moduleWeeks;
    @FXML
    private Button btn_moduleWeeksEdit;
    @FXML
    private TableView table_weeks;
    //private ListView<String> list_moduleGroups;
    @FXML
    private Button btn_moduleGroupsEdit;
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
    private Button btn_saveModule;
    @FXML
    private Button btn_deleteModule;

    private ArrayList<Module> moduleObjects = new ArrayList<Module>();
    ObservableList<String> moduleFullTitles = FXCollections.observableArrayList();
    ObservableList<String> days = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {

        label_moduleEditorHeading.setText("Select a module to edit");
        vbox_newModule.setDisable(true);

        //Set items for 'day of week' combo boxes
        for(DayOfWeek d : DayOfWeek.values()) {
            days.add(d.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        combo_problemsReleasedDay.setItems(days);
        combo_caEvaluationEndsDay.setItems(days);

        //Default "New" module
        moduleObjects.add(new Module("New"));

        //Add some test Modules
        moduleObjects.add(new Module(
                "Introduction to Programming 1",
                "CS161",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9,30)),
                new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(18,0)),
                LocalDateTime.of(2022,8,31,23,59)
        ));
        moduleObjects.add(new Module(
                "Computer Systems 1",
                "CS171",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.TUESDAY, LocalTime.of(12,0)),
                new DayOfWeekAndTime(DayOfWeek.SUNDAY, LocalTime.of(15,0)),
                LocalDateTime.of(2022,8,31,23,59)
        ));
        updateModuleList();

        //Set placeholders for empty 'Weeks' and 'Groups' listViews
        //list_moduleWeeks.setPlaceholder(new Label(String.format("Click '%s' to add weeks", btn_moduleWeeksEdit.getText())));
        //list_moduleWeeks.getPlaceholder().setOpacity(0.5);
        //list_moduleGroups.setPlaceholder(new Label(String.format("Click '%s' to add groups", btn_moduleGroupsEdit.getText())));
        //list_moduleGroups.getPlaceholder().setOpacity(0.5);

    }

    @FXML
    void moduleListClicked(MouseEvent e) {
        setModuleEditor();
    }

    @FXML
    void moduleListKeyPressed(KeyEvent event) {
        setModuleEditor();
    }

    void setModuleEditor() {

        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {

            vbox_newModule.setDisable(false);

            Module selectedModule = moduleObjects.get(selectedIndex);

            //Disable 'Delete module' btn for "New"
            if(selectedIndex == 0) btn_deleteModule.setDisable(true);
            else btn_deleteModule.setDisable(false);

            label_moduleEditorHeading.setText(selectedModule.getFullTitle());

            tf_moduleCode.setText(selectedModule.getCode());
            tf_moduleTitle.setText(selectedModule.getTitle());

            combo_problemsReleasedDay.setValue(selectedModule.getProblemsReleased().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tf_problemsReleasedTime.setText(selectedModule.getProblemsReleased().getTime().toString());

            combo_caEvaluationEndsDay.setValue(selectedModule.getCaEvaluationEnds().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            tf_caEvaluationEndsTime.setText(selectedModule.getCaEvaluationEnds().getTime().toString());

            date_problemsDisappear.setValue(selectedModule.getProblemsDisappear().toLocalDate());
            tf_problemsDisappearTime.setText(selectedModule.getProblemsDisappear().toLocalTime().toString());
        }
    }

    @FXML
    void openGroupsEditor(ActionEvent event) throws IOException {
        System.out.println("Opening Groups window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Groups.fxml"));
        Parent root = loader.load();
        GroupsController groupsController = loader.getController();

        groupsController.combo_day.setItems(days);

        Stage stage = new Stage();
        stage.setTitle("LabManager - Groups");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/media/icon.png"));
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
    void deleteModule(ActionEvent e) {

        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > 0) {

            moduleObjects.remove(selectedIndex);
            updateModuleList();
        }
    }

    @FXML
    void saveModule(ActionEvent event) {
        int selectedIndex = list_savedModules.getSelectionModel().getSelectedIndex();
        if(selectedIndex > -1) {

            Module module = moduleObjects.get(selectedIndex);

            if(moduleExists(tf_moduleCode.getText())) {
                setModuleData(getModule(tf_moduleCode.getText()));
                label_moduleEditorHeading.setText(module.getFullTitle());
            }
            else {
                Module newModule = newModuleFromData();
                moduleObjects.add(newModule);
                label_moduleEditorHeading.setText(newModule.getFullTitle());
            }

        }
        updateModuleList();
    }

    public Module getModule(String code) {

        for(Module m : moduleObjects) {
            if(m.getCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return moduleObjects.get(0);
    }

    public void updateModuleList() {

        moduleFullTitles = FXCollections.observableArrayList();

        sortModules();
        for(Module m : moduleObjects) {
            moduleFullTitles.add(m.getFullTitle());
        }

        list_savedModules.setItems(moduleFullTitles);
    }

    public void sortModules() {

        //Bubble sort
        for(int i = 0; i < moduleObjects.size(); i++) {
            for (int j = 1; j < (moduleObjects.size() -i); j++) {

                Module m1 = moduleObjects.get(j-1);
                Module m2 = moduleObjects.get(j);

                //Keep "New" at index 0
                if(!m1.getCode().equals("New") && !m2.getCode().equals("New")) {

                    if (m1.getCode().compareToIgnoreCase(m2.getCode()) > 0) {
                        Collections.swap(moduleObjects, j, j-1);
                    }
                }
            }
        }
    }


    public boolean moduleExists(String code) {

        for(Module m : moduleObjects) {
            if(m.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public Module newModuleFromData() {

        Module newModule = new Module();
        setModuleData(newModule);
        newModule.setCode(tf_moduleCode.getText());
        return newModule;
    }

    public void setModuleData(Module target) {
        target.setTitle(tf_moduleTitle.getText());

        //DayOfWeekAndTime problemsReleased
        int selectedIndex = combo_problemsReleasedDay.getSelectionModel().getSelectedIndex();
        DayOfWeek problemsReleasedDay = DayOfWeek.of(selectedIndex+1);
        LocalTime problemsReleasedTime = LocalTime.parse(tf_problemsReleasedTime.getText());
        target.setProblemsReleased(new DayOfWeekAndTime(problemsReleasedDay, problemsReleasedTime));

        //DayOfWeekAndTime caEvaluationEnds
        selectedIndex = combo_caEvaluationEndsDay.getSelectionModel().getSelectedIndex();
        DayOfWeek caEvaluationEndsDay = DayOfWeek.of(selectedIndex+1);
        LocalTime caEvaluationEndsTime = LocalTime.parse(tf_caEvaluationEndsTime.getText());
        target.setCaEvaluationEnds(new DayOfWeekAndTime(caEvaluationEndsDay, caEvaluationEndsTime));

        //LocalDateTime problemsDisappear
        LocalDate problemsDisappearDate = date_problemsDisappear.getValue();
        LocalTime problemsDisappearTime = LocalTime.parse(tf_problemsDisappearTime.getText());
        target.setProblemsDisappear(LocalDateTime.of(problemsDisappearDate, problemsDisappearTime));
    }

}
