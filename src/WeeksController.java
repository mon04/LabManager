import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class WeeksController {

    @FXML
    private TableView<Week> table_weeks;
    @FXML
    private TableColumn<Week, LocalDate> tableColumn_begins;
    @FXML
    private TableColumn<Week, ObservableList<Problem>> tableColumn_problemSet;
    @FXML
    private DatePicker date_weekBegins;
    @FXML
    private Label label_problemSetInfo;
    @FXML
    private Button btn_editProblemSet;
    @FXML
    private Button btn_cancelWeeksEdit;
    @FXML
    private Button btn_saveWeeksEdit;

    private Module module;
    private RootController rootController;

    private ObservableList<Week> weeks = FXCollections.observableArrayList();


    public void setData(Module m, RootController r) {

        setModule(m);
        setRootController(r);

        weeks.addAll(module.getWeeks());

        tableColumn_begins.setCellValueFactory(new PropertyValueFactory<>("weekBegins"));
        tableColumn_problemSet.setCellValueFactory(new PropertyValueFactory<>("problemSet"));
        table_weeks.setItems(weeks);

    }

    private void setRootController(RootController rootController) {

        this.rootController = rootController;
    }

    private void setModule(Module module) {

        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public RootController getRootController() {
        return rootController;
    }
}
