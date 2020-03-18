package TennisBallGames;
// import the required libraries
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


public class AddMatchController implements Initializable {
    // Some @FXML declarations

    // Some local variable declarations
    @FXML
    private Button saveBtn; // save button.
    @FXML
    private Button cancelBtn; // cancel button.
    @FXML
    private ComboBox homeTeamBox; // Combo box for the home team.
    @FXML
    private ComboBox visitorTeamBox; // Combo box for the visitor team.

    // The data variable is used to populate the ComboBoxs


    final ObservableList<String> data = FXCollections.observableArrayList();
    // To reference the models inside the controller
    private MatchesAdapter matchesAdapter;
    private TeamsAdapter teamsAdapter;
    public void setModel(MatchesAdapter match, TeamsAdapter team) {
        matchesAdapter = match;
        teamsAdapter = team;
        buildComboBoxData();
    }
    @FXML
    // Thus method closes thw pop up window for adding matches.
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    // This method retrieves information from the GUI and saves the new match.
    public void save()
    {
        // Do some work here

        try {

            // Creating a new match.
            matchesAdapter.insertMatch((matchesAdapter.getMax()+1),(String)homeTeamBox.getValue(),(String)visitorTeamBox.getValue());
        } catch (SQLException ex) {

            // Displays an error if there is an SQL exception.
            displayAlert("ERROR: " + ex.getMessage());
        }



        // Closes the stage on the click of the save button.
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    // This method loads a data variable with all of the data to put into thje combo boxes.
    public void buildComboBoxData() {
        try {
            // Retrieving team names.
            data.addAll(teamsAdapter.getTeamsNames());
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }
    }
    @Override
    // Initialize method is the first thing that will run/
    public void initialize(URL url, ResourceBundle rb)
    {
        // Loading combo boxes with data.
        homeTeamBox.setItems(data);
        visitorTeamBox.setItems(data);

    }

    private void displayAlert(String msg)
    {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMatch.fxml"));
            Parent ERROR = loader.load();
            AddMatchController controller = (AddMatchController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/TennisBallGames/WesternLogo.png"));
            // controller.setAlertText(msg);
            System.out.println(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }

    }
}