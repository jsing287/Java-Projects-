/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TennisBallGames;

import java.sql.*;
import java.util.PropertyPermission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Abdelkader
 */
public class TeamsAdapter {

    Connection connection;

    public TeamsAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        if (reset) {
            Statement stmt = connection.createStatement();
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                // We drop Matches first because it refrences the table Teams
                stmt.execute("DROP TABLE Matches");
                stmt.execute("DROP TABLE Teams");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the table of teams
                stmt.execute("CREATE TABLE Teams ("
                        + "TeamName CHAR(15) NOT NULL PRIMARY KEY, "
                        + "Wins INT, " + "Losses INT, "
                        + "Ties INT" + ")");
                populateSampls();
            }
        }
    }

    private void populateSampls() throws SQLException {
        // Add some teams
        this.insertTeam("Astros");
        this.insertTeam("Marlins");
        this.insertTeam("Brewers");
        this.insertTeam("Cubs");
    }

    public void insertTeam(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Teams (TeamName, Wins, Losses, Ties) VALUES ('" + name + "', 0, 0, 0)");
    }

    // Get all teams Data
    public ObservableList<Teams> getTeamsList() throws SQLException {
        ObservableList<Teams> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM Teams";

        // Execute the statement and return the result
        rs = stmt.executeQuery(sqlStatement);

        while (rs.next()) {
            list.add(new Teams(rs.getString("TeamName"),
                    rs.getInt("Wins"),
                    rs.getInt("Losses"),
                    rs.getInt("Ties")));
        }
        return list;
    }

    // Get all teams names to populate the ComboBoxs used in Task #3.
    public ObservableList<String> getTeamsNames() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        ResultSet rs;

        // Create a Statement object
        Statement stmt = connection.createStatement();
        

        // Create a string with a SELECT statement
        String qeury = "SELECT TeamName FROM Teams";
        

        // Execute the statement and return the result
        rs = stmt.executeQuery(qeury);
        
        
        // loop for the all rs rows and update list
        while(rs.next())
        {
            list.add(rs.getString("TeamName"));
        }
        
        return list;
    }

    // Update teams status Wins, Losses, and Ties.
    public void setStatus(String hTeam, String vTeam, int hScore, int vScore) throws SQLException
    {

        String getData = "SELECT * FROM Teams"; // get the whole data base.
        // create a statement and set a result variable equal to the database.
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(getData);


        // variables to hold the wins, losses, and ties of home and visitor.
        int homeW= 0;
        int homeL = 0;
        int homeT = 0;
        int visW = 0;
        int visL = 0;
        int visT = 0;

        // Go through every team.
        while(rs.next())
        {
            // Set the variables equal to appropriate teams wins, losses, and ties.
            String[] temp = rs.getString("TeamName").split(" ");

            // checking if the database team if the one passed.
            if(rs.getString("TeamName").equals(hTeam))
            {
                homeW = rs.getInt("Wins"); // retrieving column data.
                homeL = rs.getInt("Losses");
                homeT = rs.getInt("Ties");
            }
            else if(rs.getString("TeamName").equals(vTeam))
            {
                visW = rs.getInt("Wins");
                visL = rs.getInt("Losses");
                visT = rs.getInt("Ties");
            }
        }

        // if the home team one.
        if(hScore>vScore)
        {
            // Update query creating a prepared staement and increasing the home teams wins.
            String updateW = "UPDATE TEAMS SET Wins = ? WHERE TeamName = ?";
            PreparedStatement pst = connection.prepareStatement(updateW);
            pst.setInt(1, ++homeW);
            pst.setString(2, hTeam);
            pst.executeUpdate();

            // Update query creating a prepared staement and increasing the visitor teams losses.
            String updateL = "UPDATE Teams SET Losses = ? WHERE TeamName = ?";
            PreparedStatement pst2 = connection.prepareStatement(updateL);
            pst2.setInt(1, ++visL);
            pst2.setString(2, vTeam);
            pst2.executeUpdate();


        }
        else if(vScore>hScore) // if the visitor team one.
        {
            // Update query creating a prepared staement and increasing the visitor teams wins.
            String updateW = "UPDATE TEAMS SET Wins = ? WHERE TeamName = ?";
            PreparedStatement pst = connection.prepareStatement(updateW);
            pst.setInt(1, ++visW);
            pst.setString(2, vTeam);
            pst.executeUpdate();

            // Update query creating a prepared staement and increasing the home teams losses.
            String updateL = "UPDATE Teams SET Losses = ? WHERE TeamName = ?";
            PreparedStatement pst2 = connection.prepareStatement(updateL);
            pst2.setInt(1, ++homeL);
            pst2.setString(2, hTeam);
            pst2.executeUpdate();

        }
        else // if the tied.
        {
            // Update query creating a prepared staement and increasing the home teams ties.
            String update = "UPDATE TEAMS SET Ties = ? WHERE TeamName = ?";
            PreparedStatement pst = connection.prepareStatement(update);
            pst.setInt(1, ++visT);
            pst.setString(2, vTeam);
            pst.executeUpdate();



            // Update query creating a prepared staement and increasing the visitor teams ties.
            String update1 = "UPDATE Teams SET Ties = ? WHERE TeamName = ?";
            PreparedStatement pst2 = connection.prepareStatement(update1);
            pst2.setInt(1, ++homeT);
            pst2.setString(2, hTeam);
            pst2.executeUpdate();

        }








        }
}
