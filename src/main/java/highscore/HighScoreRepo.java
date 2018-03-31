package highscore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoreRepo {

    private static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:highscore.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

//    private static void createHighScoreDatabase(){
//        String url = "jdbc:sqlite:highscore.db";
//
//        try(Connection connection = DriverManager.getConnection(url))
//    }

    public static void createNewTable(){
        Connection conn = connect();
        String sql1 = "DROP TABLE IF EXISTS highscores";
        String sql2 = "CREATE TABLE highscores(name TEXT, score INTEGER)";

        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }

    public static void insertHighScoreValue(String name, int score){
        Connection conn = connect();
        String sql = "INSERT INTO highscores(name, score) VALUES(?, ?)";

        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }

    }

    public static List<Score> getHighScoreValue(){
        Connection conn = connect();
        List<Score> highScores = new ArrayList<>();
        String sql = "SELECT name, score FROM highscores";

        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){

            while(resultSet.next()){
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                Score highScore = new Score(name, score);
                highScores.add(highScore);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            try {
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }

        return highScores;
    }

    public static void deleteHighScoreValue(String name, int score){
        Connection conn = connect();
        String sql = "DELETE FROM highscores WHERE (name = ? and score = ?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            try{
                if (conn != null){
                    conn.close();
                }

            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }



}

