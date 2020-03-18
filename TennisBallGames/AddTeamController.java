package TennisBallGames;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class AddTeamController implements Initializable {

    @FXML
    Button cancelBtn; // cancel button.

    @FXML
    Button saveBtn; // save button.
    
    @FXML
    TextField teamName; // text field to hold team name.

    private TeamsAdapter teamsAdapter;

    // assigning class fields.
    public void setModel(TeamsAdapter team) {
        teamsAdapter = team;
    }

    @FXML
    // close the pop up when cancel is clicked.
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    // Save the new team upon clicking save.
    public void save() {


        try {
            // Inserting a new team into the list .
            teamsAdapter.insertTeam(teamName.getText());
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }

        // close the stage.
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    // dsiaply alert handles error messages.
     private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/TennisBallGames/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
