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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    private ArrayList<Module> moduleObjects = new ArrayList<Module>();

    public void initialize(URL url, ResourceBundle rb) {

        label_moduleEditorHeading.setText("New module");

        moduleObjects.add(new Module(
                "Introduction to Programming I",
                "CS161",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(1, LocalTime.of(9,30)),
                new DayOfWeekAndTime(4, LocalTime.of(18,0)),
                LocalDateTime.of(2021,8,31,23,59)
        ));

        moduleObjects.add(new Module(
                "Computer Systems I",
                "CS171",
                new ArrayList<LabSession>(),
                new ArrayList<Week>(),
                new DayOfWeekAndTime(2, LocalTime.of(16,0)),
                new DayOfWeekAndTime(4, LocalTime.of(18,0)),
                LocalDateTime.of(2023,7,29,22,1)
        ));

        ObservableList<String> modules = FXCollections.observableArrayList("New module");
        //modules.add("CS161 - Introduction to Programming I");
        //modules.add("CS162 - Introduction to Programming II");
        //modules.add("CS171 - Computer Systems I");
        //modules.add("CS172 - Computer Systems II");
        for(Module m : moduleObjects) {
            modules.add(m.getFullTitle());
        }

        list_savedModules.setItems(modules);
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
    private ListView<?> list_moduleWeeks;

    @FXML
    private Button btn_moduleWeeksEdit;

    @FXML
    private ListView<?> list_moduleGroups;

    @FXML
    private Button btn_moduleGroupsEdit;

    @FXML
    private ComboBox<?> combo_problemsReleasedDay;

    @FXML
    private TextField tf_problemsReleasedTime;

    @FXML
    private ComboBox<?> combo_caEvaluationEndsDay;

    @FXML
    private TextField tf_caEvaluationEndsTime;

    @FXML
    private DatePicker date_problemsDisappear;

    @FXML
    private TextField tf_problemsDisappearTime;

    @FXML
    private Button btn_saveModule;


    @FXML
    void openGroupsEditor(ActionEvent event) {

    }

    @FXML
    void openWeeksEditor(ActionEvent event) {

    }

    @FXML
    void saveModule(ActionEvent event) {

    }

    @FXML
    void setModuleEditor(MouseEvent event) {

        if(list_savedModules.getSelectionModel().getSelectedIndex() > -1) {
            vbox_newModule.setVisible(true);
            String selected = list_savedModules.getSelectionModel().getSelectedItem();
            label_moduleEditorHeading.setText(selected);
        }

    }

}
