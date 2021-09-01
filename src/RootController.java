import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    private ArrayList<Module> moduleObjects = new ArrayList<Module>();
    ObservableList<String> days = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle rb) {

        label_moduleEditorHeading.setText("Select a module above");
        vbox_newModule.setDisable(true);

        for(int i = 1; i <= 7; i++) {
            days.add(DayOfWeek.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        combo_problemsReleasedDay.setItems(days);
        combo_caEvaluationEndsDay.setItems(days);

        //Default "New module"
        moduleObjects.add(new Module(
                "New module",
                "",
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

        ObservableList<String> modules = FXCollections.observableArrayList();
        for(Module m : moduleObjects) {
            modules.add(m.getFullTitle());
        }
        list_savedModules.setItems(modules);
    }

    @FXML
    void setModuleEditor(MouseEvent event) {
        //Called when user selects a module from the list (only works for left-click atm)

        if(list_savedModules.getSelectionModel().getSelectedIndex() > -1) {

            vbox_newModule.setDisable(false);

            String selected = list_savedModules.getSelectionModel().getSelectedItem();
            Module module = getModule(selected);

            label_moduleEditorHeading.setText(module.getFullTitle());

            tf_moduleCode.setText(module.getCode());
            tf_moduleTitle.setText(module.getTitle());

            combo_problemsReleasedDay.setValue(module.getProblemsReleased().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            combo_caEvaluationEndsDay.setValue(module.getCaEvaluationEnds().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));

            date_problemsDisappear.setValue(module.getProblemsDisappear().toLocalDate());

            tf_problemsReleasedTime.setText(module.getProblemsReleased().getTime().toString());
            tf_caEvaluationEndsTime.setText(module.getCaEvaluationEnds().getTime().toString());
            tf_problemsDisappearTime.setText(module.getProblemsDisappear().toLocalTime().toString());
        }

    }

    @FXML
    void openGroupsEditor(ActionEvent event) {

    }

    @FXML
    void openWeeksEditor(ActionEvent event) {

    }

    @FXML
    void saveModule(ActionEvent event) {

    }

    public Module getModule(String fullTitle) {
        for(Module m : moduleObjects) {
            if(m.getFullTitle().equals(fullTitle)) {
                return m;
            }
        }
        return moduleObjects.get(0);
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

}
