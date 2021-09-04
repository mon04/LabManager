import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GroupsController {

    public TableView table_groups;
    public TextField tf_groupName;
    public ComboBox combo_day;
    public TextField tf_startTime;
    public Button btn_deleteGroup;
    public Button btn_addGroup;
    public TextField tf_labLengthHours;
    public TextField tf_labLengthMin;
    public Button btn_cancel;
    public Button btn_saveAll;


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
}
