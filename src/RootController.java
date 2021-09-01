import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

public class RootController implements Initializable {

    private ArrayList<Module> moduleObjects = new ArrayList<Module>();
    ObservableList<String> moduleFullTitles = FXCollections.observableArrayList();
    ObservableList<String> days = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle rb) {

        label_moduleEditorHeading.setText("Select a module above");
        vbox_newModule.setDisable(true);

        //Set items for 'day of week' combo boxes
        for(DayOfWeek d : DayOfWeek.values()) {
            days.add(d.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        combo_problemsReleasedDay.setItems(days);
        combo_caEvaluationEndsDay.setItems(days);

        //Default "New" module
        moduleObjects.add(new Module(
                "",
                "New",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9,0)),
                new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(21,0)),
                LocalDateTime.of(2021,8,31,23,59)
        ));

        //Add some test Modules
        moduleObjects.add(new Module(
                "Introduction to Programming I",
                "CS161",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9,30)),
                new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(18,0)),
                LocalDateTime.of(2021,8,31,23,59)
        ));
        moduleObjects.add(new Module(
                "Introduction to Programming II",
                "CS162",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.TUESDAY, LocalTime.of(12,0)),
                new DayOfWeekAndTime(DayOfWeek.SUNDAY, LocalTime.of(15,0)),
                LocalDateTime.of(2022,7,29,22,29)
        ));

        //Set up items in 'Saved Modules' listView
        updateModulesList();
        updateModulesList();
        System.out.println("INITIALIZATION DONE");

        //Set placeholders for empty 'Weeks' and 'Groups' listViews
        list_moduleWeeks.setPlaceholder(new Label(String.format("Click '%s' to add weeks", btn_moduleWeeksEdit.getText())));
        list_moduleWeeks.getPlaceholder().setOpacity(0.5);
        list_moduleGroups.setPlaceholder(new Label(String.format("Click '%s' to add groups", btn_moduleGroupsEdit.getText())));
        list_moduleGroups.getPlaceholder().setOpacity(0.5);

    }

    @FXML
    void setModuleEditor(MouseEvent event) {
        //Called when user selects a module from the list (only works for left-clicks for now)

        if(list_savedModules.getSelectionModel().getSelectedIndex() > -1) {

            vbox_newModule.setDisable(false);

            int selected = list_savedModules.getSelectionModel().getSelectedIndex();
            Module selectedModule = moduleObjects.get(selected);
            //System.out.println("Selected: " + selectedModule.getCode());

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
    void openGroupsEditor(ActionEvent event) {

    }

    @FXML
    void openWeeksEditor(ActionEvent event) {

    }

    @FXML
    void deleteModule(ActionEvent e) {

        if(list_savedModules.getSelectionModel().getSelectedIndex() > 0) {

            int selected = list_savedModules.getSelectionModel().getSelectedIndex();
            moduleObjects.remove(selected);
            updateModulesList();//updateModulesList();
        }
    }

    @FXML
    void saveModule(ActionEvent event) {
        if(list_savedModules.getSelectionModel().getSelectedIndex() > -1) {

            int selected = list_savedModules.getSelectionModel().getSelectedIndex();
            Module module = moduleObjects.get(selected);

            if(moduleExists(tf_moduleCode.getText())) {
                setModuleData(getModule(tf_moduleCode.getText()));
            }
            else {
                moduleObjects.add(newModuleFromData());
            }
        }
        updateModulesList();
    }

    public Module getModule(String listString) {

        String code = listString;

        //Cases where listString has code AND title
        int index;
        if((index = listString.indexOf('-')) > -1) {
            code = listString.substring(0, index -1);
        }

        for(Module m : moduleObjects) {
            if(m.getCode().equals(code)) {
                return m;
            }
        }
        return moduleObjects.get(0);
    }

    public void updateModulesList() {

        moduleFullTitles = FXCollections.observableArrayList();

        sortModules();
        for(Module m : moduleObjects) {
            moduleFullTitles.add(m.getFullTitle());
        }

        list_savedModules.setItems(moduleFullTitles);
    }

    public void sortModules() {

        //System.out.println("Sort method called");

        for(int i = 0; i < moduleObjects.size(); i++) {
            //System.out.println(i+" --------");
            for (int j = 1; j < (moduleObjects.size() -i); j++) {

                Module m1 = moduleObjects.get(j-1);
                Module m2 = moduleObjects.get(j);
                //System.out.println("m1="+m1.getCode());
                //System.out.println("m2="+m2.getCode());

                if(!m1.getCode().equals("New") && !m2.getCode().equals("New")) {

                    if (m1.getCode().compareToIgnoreCase(m2.getCode()) > 0) {
                        Collections.swap(moduleObjects, j, j-1);
                        //System.out.println("!SWAPPED!");
                    }

                }
                //System.out.println("-");
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

        Module newModule = new Module(
                "",
                "CREATEME",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9,0)),
                new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(21,0)),
                LocalDateTime.of(2021,8,31,23,59)
        );

        setModuleData(newModule);
        newModule.setCode(tf_moduleCode.getText());

        return newModule;
    }

    public void setModuleData(Module targetModule) {
        targetModule.setTitle(tf_moduleTitle.getText());
        //targetModule.setCode(tf_moduleCode.getText());

        //DayOfWeekAndTime problemsReleased
        DayOfWeek problemsReleasedDay = DayOfWeek.of(combo_problemsReleasedDay.getSelectionModel().getSelectedIndex()+1);
        LocalTime problemsReleasedTime = LocalTime.parse(tf_problemsReleasedTime.getText());
        targetModule.setProblemsReleased(new DayOfWeekAndTime(problemsReleasedDay, problemsReleasedTime));

        //DayOfWeekAndTime caEvaluationEnds
        DayOfWeek caEvaluationEndsDay = DayOfWeek.of(combo_caEvaluationEndsDay.getSelectionModel().getSelectedIndex()+1);
        LocalTime caEvaluationEndsTime = LocalTime.parse(tf_caEvaluationEndsTime.getText());
        targetModule.setCaEvaluationEnds(new DayOfWeekAndTime(caEvaluationEndsDay, caEvaluationEndsTime));

        //LocalDateTime problemsDisappear
        LocalDate problemsDisappearDate = date_problemsDisappear.getValue();
        LocalTime problemsDisappearTime = LocalTime.parse(tf_problemsDisappearTime.getText());
        targetModule.setProblemsDisappear(LocalDateTime.of(problemsDisappearDate, problemsDisappearTime));
    }

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
    private ListView<String> list_moduleWeeks;

    @FXML
    private Button btn_moduleWeeksEdit;

    @FXML
    private ListView<String> list_moduleGroups;

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

}
