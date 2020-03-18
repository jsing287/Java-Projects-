package TennisBallGames;

import java.sql.*;
import java.sql.PreparedStatement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




/**
 *
 * @author Abdelkader
 */
public class MatchesAdapter {

    Connection connection;

    public MatchesAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Matches");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the table of Matches
                stmt.execute("CREATE TABLE Matches ("
                        + "MatchNumber INT NOT NULL PRIMARY KEY, "
                        + "HomeTeam CHAR(15) NOT NULL REFERENCES Teams (TeamName), "
                        + "VisitorTeam CHAR(15) NOT NULL REFERENCES Teams (TeamName), "
                        + "HomeTeamScore INT, "
                        + "VisitorTeamScore INT "
                        + ")");
                populateSamples();
            }
        }
    }

    private void populateSamples() throws SQLException{
        // Create a listing of the matches to be played
        this.insertMatch(1, "Astros", "Brewers");
        this.insertMatch(2, "Brewers", "Cubs");
        this.insertMatch(3, "Cubs", "Astros");
    }


    // Returns the maximum match number.
    public int getMax() throws SQLException {

        int num = 0;

        // Add your work code here for Task #3
        Statement stmt = connection.createStatement();


        // retrieving max match number.
        ResultSet rs = stmt.executeQuery("SELECT MAX(MatchNumber) FROM Matches");

        // Shift the rs index from nowhere to somewhere.
        if (rs.next()) {

            // set num equal to the first rs.
            num = rs.getInt(1);
        }





        return num; // return this value.
    }

    public void insertMatch(int num, String home, String visitor) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Matches (MatchNumber, HomeTeam, VisitorTeam, HomeTeamScore, VisitorTeamScore) "
                + "VALUES (" + num + " , '" + home + "' , '" + visitor + "', 0, 0)");

    }

    // Get all Matches
    public ObservableList<Matches> getMatchesList() throws SQLException {
        ObservableList<Matches> matchesList = FXCollections.observableArrayList();
        // Add your code here for Task #2

        // Create a statement, excecute the SQL command and retrieve the information to make a matches list.
        Statement stmt = connection.createStatement();
        String query = "SELECT MatchNumber, HomeTeam, VisitorTeam, HomeTeamScore, VisitorTeamScore FROM Matches";
        ResultSet rs = stmt.executeQuery(query);

        // loop through every match.
        while(rs.next())
        {
            // Get all column and row information.
            int matchNum = rs.getInt("MatchNumber");
            String homeTeam = rs.getString("HomeTeam");
            String VisitorTeam = rs.getString("VisitorTeam");
            int homeScore = rs.getInt("HomeTeamScore");
            int visitorScore = rs.getInt("VisitorTeamScore");

            // create the new match as an instance of the Matches class.
            Matches temp = new Matches(matchNum, homeTeam, VisitorTeam, homeScore, visitorScore );
            matchesList.add(temp); // add the match to the list.
        }



        return matchesList; // return the list.
    }

    // Get a String list of matches to populate the ComboBox used in Task #4.
    public ObservableList<String> getMatchesNamesList() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();


        // Create a string with a SELECT statement
        String query = "SELECT MatchNumber, HomeTeam, VisitorTeam  FROM Matches";


        // Execute the statement and return the result
        rs = stmt.executeQuery(query);


        // Loop the entire rows of rs and set the string values of list
        while(rs.next())
        {
            int matchNum = rs.getInt("MatchNumber");
            String homeTeam = rs.getString("HomeTeam");
            String VisitorTeam = rs.getString("VisitorTeam");
            String option = matchNum + "-"+homeTeam + "-"+VisitorTeam;
            list.add(option);
        }


        return list;
    }


    // Update the team standings scores.
    public void setTeamsScore(int matchNumber, int hScore, int vScore) throws SQLException
    {

        // SQL command to update the data base.
        String query = "UPDATE Matches SET HomeTeamScore = ?,VisitorTeamScore = ? WHERE MatchNumber = ?";


        // Prepared statement to assign variables.
        PreparedStatement stmt = connection.prepareStatement(query);

        // variable assignments.
        stmt.setInt(1, hScore);
        stmt.setInt(2, vScore);
        stmt.setInt(3, matchNumber);

        // execute the update.
        stmt.executeUpdate();






    }
}