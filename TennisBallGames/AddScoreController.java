package TennisBallGames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddScoreController implements Initializable
{

    // Some local variable declarations
    @FXML
    private Button saveBtn; // save button.
    @FXML
    private Button cancelBtn; // cancel button.
    @FXML
    private ComboBox matchBox; // The combo box that displays matches.
    @FXML
    private TextField homeScore; // get the score of the home team.
    @FXML
    private TextField visitorScore; // get the visitor score.


    // The data variable is used to populate the ComboBoxs


    final ObservableList<String> data = FXCollections.observableArrayList();
    // To reference the models inside the controller
    private MatchesAdapter matchesAdapter;
    private TeamsAdapter teamsAdapter;

    // Assigning class variables and initializes combo box data.
    public void setModel(MatchesAdapter match, TeamsAdapter team) {

        matchesAdapter = match;
        teamsAdapter = team;
        buildComboBoxData();
    }
    @FXML
    // Closes the pop up window when cancel is clicked.
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML

    // Saves the score when save is clicked.
    public void save() throws SQLException
    {
        String temp = matchBox.getValue().toString(); // get the match and convert it to a string.
        int num = Integer.parseInt(temp.substring(0,1)); // get the number of the match.
        int hScore = Integer.parseInt(homeScore.getText()); // convert the home score to an integer.
        int vScore = Integer.parseInt(visitorScore.getText()); // convert he visitor score to an integer.

        matchesAdapter.setTeamsScore(num,hScore,vScore); // call the set team score method to lock in the scores.
        String[] words = temp.split("-");
        teamsAdapter.setStatus(words[1], words[2], hScore, vScore); // update the status of the teams.

        // close the stage.
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();


    }


    // method retrieves data to load into the combo boxes.
    public void buildComboBoxData()
    {


        try
        {
            // load data with matches names list.
            data.addAll(matchesAdapter.getMatchesNamesList());

        } catch (SQLException ex) {

            displayAlert("ERROR: " + ex.getMessage());
        }
    }
    @Override
    // load combo box with data.
    public void initialize(URL url, ResourceBundle rb)
    {
        matchBox.setItems(data);

    }

    private void displayAlert(String msg)
    {

    }
}
