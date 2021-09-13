package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Module;
import model.ProblemSet;
import model.Week;

import java.io.IOException;
import java.time.LocalDate;

public class WeeksController {

    @FXML
    private VBox vbox_entireScene;
    @FXML
    private Label label_mainHeading;
    @FXML
    private TableView<Week> table_weeks;
    @FXML
    private TableColumn<Week, LocalDate> tableColumn_begins;
    @FXML
    private TableColumn<Week, ProblemSet> tableColumn_problemSet;
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

        label_mainHeading.setText(m.getFullTitle());

        weeks.addAll(module.getWeeks());

        tableColumn_begins.setCellValueFactory(new PropertyValueFactory<>("weekBegins"));
        tableColumn_problemSet.setCellValueFactory(new PropertyValueFactory<>("problemSet"));
        table_weeks.setItems(weeks);

    }

    @FXML
    public void openProblemSetEditor(ActionEvent event) throws IOException {

        vbox_entireScene.setDisable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Problems.fxml"));
        Parent root = loader.load();
        ProblemsController problemsController = loader.getController();

        problemsController.setData(getSelectedWeek(), this);

        String moduleCode = module.getCode();

        Stage stage = new Stage();
        stage.setTitle(String.format("%s - Problems (%s)", moduleCode, getSelectedWeek().getWeekBegins()));
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/media/icon.png"));
        stage.setMinWidth(400);
        stage.setMinHeight(350);
        stage.showAndWait();

    }

    @FXML
    public void tableWeeksMouseClicked(MouseEvent event) {
        setWeeksEditor();
    }

    @FXML
    public void tableWeeksKeyPressed(KeyEvent event) {

        if(event.getCode() == KeyCode.DELETE) {
            deleteSelectedWeek();
        }
        if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            setWeeksEditor();
        }
    }

    @FXML
    public void addWeek(ActionEvent event) {

        int existingIndex;
        if((existingIndex = getWeekIndex(date_weekBegins.getValue())) != -1) {
            System.out.println("Overwriting: "+date_weekBegins.getValue());
            weeks.remove(existingIndex);
            weeks.add(existingIndex, newWeekFromData());
        }
        else {
            weeks.add(newWeekFromData());
        }

    }

    @FXML
    public void plusOneWeek(ActionEvent event) {

        LocalDate oldValue = date_weekBegins.getValue();
        date_weekBegins.setValue(oldValue.plusWeeks(1));
    }

    @FXML
    public void minusOneWeek(ActionEvent event) {

        LocalDate oldValue = date_weekBegins.getValue();
        date_weekBegins.setValue(oldValue.minusWeeks(1));
    }


    @FXML
    public void deleteButtonAction(ActionEvent event) {
        deleteSelectedWeek();
    }

    @FXML
    public void saveWeeksEdit(ActionEvent event) {

        module.setWeeks(FXCollections.observableArrayList(weeks));

        closeStage();
        rootController.setModuleEditor();
    }

    @FXML
    public void cancelWeeksEdit(ActionEvent event) {

        closeStage();
        rootController.setDisable(false);
    }

    public int getWeekIndex(LocalDate weekBegins) {

        for(int i = 0; i < weeks.size(); i++) {
            Week w = weeks.get(i);
            if(w.getWeekBegins().equals(weekBegins)) {
                return i;
            }
        }
        return -1;

    }

    public Week newWeekFromData() {

        LocalDate weekBegins = date_weekBegins.getValue();
        ProblemSet problemSet = new ProblemSet(); //Change this when model.ProblemSet editor is being done

        return new Week(weekBegins, problemSet);
    }

    public void setWeeksEditor() {

        int selectedIndex = table_weeks.getSelectionModel().getSelectedIndex();

        if(selectedIndex > -1) {

            Week w = weeks.get(selectedIndex);

            date_weekBegins.setValue(w.getWeekBegins());
            label_problemSetInfo.setText(w.getProblemSet().getInfo());
        }
    }

    public void setRootController(RootController rootController) {

        this.rootController = rootController;
    }

    public void setModule(Module module) {

        this.module = module;
    }

    public void deleteSelectedWeek() {
        weeks.remove(table_weeks.getSelectionModel().getSelectedIndex());
    }

    public void closeStage() {

        rootController.setDisable(false);
        Stage stage = (Stage) btn_saveWeeksEdit.getScene().getWindow();
        stage.close();
    }

    public void setDisable(boolean disable) {

        vbox_entireScene.setDisable(disable);
    }


    public Module getModule() {
        return module;
    }

    public Week getSelectedWeek() {

        return table_weeks.getSelectionModel().getSelectedItem();
    }

    public RootController getRootController() {
        return rootController;
    }
}
