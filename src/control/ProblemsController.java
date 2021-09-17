package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Problem;
import model.ProblemDescription;
import model.ProblemSet;
import model.Week;

public class ProblemsController {

    @FXML
    private VBox vBox_entireScene;
    @FXML
    private Label label_mainHeading;
    @FXML
    private TableView<Problem> table_problems;
    @FXML
    private TableColumn<Problem, String> tableColumn_title;
    @FXML
    private TableColumn<Problem, String> tableColumn_type;
    @FXML
    private TableColumn<Problem, String> tableColumn_hidden;
    @FXML
    private TextField tf_title;
    @FXML
    private ComboBox<String> combo_type;
    @FXML
    private ComboBox<String> combo_language;
    @FXML
    private CheckBox checkBox_hidden;

    private WeeksController weeksController;

    private Week week;

    private ObservableList<Problem> problems = FXCollections.observableArrayList();


    @FXML
    public void openDescriptionEditor(ActionEvent actionEvent) {
    }

    @FXML
    public void openEvaluationEditor(ActionEvent actionEvent) {
    }

    @FXML
    public void tableProblemsKeyPressed(KeyEvent event) {

        if(event.getCode() == KeyCode.DELETE) {
            deleteSelectedProblem();
        }
        if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            setProblemsEditor();
        }
    }

    @FXML
    public void tableProblemsMouseClicked(MouseEvent mouseEvent) {
        setProblemsEditor();
    }

    @FXML
    public void deleteButtonPressed(ActionEvent actionEvent) {
        deleteSelectedProblem();
    }

    @FXML
    public void addButtonPressed(ActionEvent actionEvent) {
        addProblem();
    }

    @FXML
    public void saveProblemsEdit(ActionEvent actionEvent) {

        week.setProblemSet(new ProblemSet(FXCollections.observableArrayList(problems)));

        closeStage();
        weeksController.setWeeksEditor();
    }

    @FXML
    public void cancelProblemsEdit(ActionEvent actionEvent) {

        closeStage();
    }

    public void setWeeksController(WeeksController weeksController) {
        this.weeksController = weeksController;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public void setData(Week w, WeeksController wc) {

        setWeeksController(wc);
        setWeek(w);

        label_mainHeading.setText(weeksController.getModule().getFullTitle());

        problems.addAll(week.getProblemSet().getProblems());

        tableColumn_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumn_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumn_hidden.setCellValueFactory(new PropertyValueFactory<>("hidden"));
        table_problems.setItems(problems);
        table_problems.getSortOrder().add(tableColumn_type);
        table_problems.getSortOrder().add(tableColumn_hidden);
        table_problems.getSortOrder().add(tableColumn_title);

        combo_type.setItems(week.getProblemSet().getAllTypes());

        ObservableList<String> langs = FXCollections.observableArrayList();
        langs.add("Java");
        combo_language.setItems(langs);
        combo_language.setValue(langs.get(0));
        //combo_language.setDisable(true);

    }

    public void setProblemsEditor() {

        int selectedIndex = table_problems.getSelectionModel().getSelectedIndex();

        if(selectedIndex > -1) {

            Problem p = problems.get(selectedIndex);

            tf_title.setText(p.getTitle());
            combo_language.getSelectionModel().select(p.getLanguage());
            combo_type.getSelectionModel().select(p.getType());
            checkBox_hidden.setSelected(p.isHidden());
        }
    }

    public void addProblem() {

        int existingIndex;
        if((existingIndex = getProblemIndex(tf_title.getText())) != -1) {
            problems.remove(existingIndex);
            problems.add(existingIndex, newProblemFromData());
        }
        else {
            problems.add(newProblemFromData());
        }
        //If new type added, update comboBox items
        combo_type.setItems(getAllTypes());
    }

    public void deleteSelectedProblem() {

        problems.remove(table_problems.getSelectionModel().getSelectedIndex());
    }

    public Problem getSelectedProblem() {

        return table_problems.getSelectionModel().getSelectedItem();
    }

    public int getProblemIndex(String title) {

        for(int i = 0; i < problems.size(); i++) {
            Problem p = problems.get(i);
            if(p.getTitle().equals(title)) {
                return i;
            }
        }
        return -1;

    }

    public Problem newProblemFromData() {

        return new Problem(
                tf_title.getText(),
                combo_language.getValue(),
                combo_type.getValue(),
                checkBox_hidden.isSelected(),
                FXCollections.observableArrayList(),
                new ProblemDescription()
        );

    }

    public ObservableList<String> getAllTypes() {

        ObservableList<String> typesFound = FXCollections.observableArrayList();

        for(Problem p: problems) {

            String type = p.getType();

            if(!(typesFound.contains(type))) {
                typesFound.add(type);
            }
        }

        return typesFound;

    }

    public void closeStage() {

        weeksController.setDisable(false);
        Stage stage = (Stage) label_mainHeading.getScene().getWindow();
        stage.close();
    }
}
