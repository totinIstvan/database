package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;

    public RadioCharts(String DB_URL, String USERNAME, String PASSWORD) {
        this.DB_URL = DB_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    this.DB_URL, this.USERNAME, this.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public String getMostPlayedSong() {
        String requestedColumn = "song";
        String SQL = "SELECT song, SUM(times_aired) as times_aired " +
                "FROM music_broadcast " +
                "GROUP BY artist " +
                "ORDER BY times_aired DESC " +
                "LIMIT 1";
        return getResultOfQuery(SQL, requestedColumn);
    }

    public String getMostActiveArtist() {
        String requestedColumn = "artist";
        String SQL = "SELECT artist, COUNT(DISTINCT song) AS song " +
                "FROM music_broadcast " +
                "GROUP BY artist " +
                "ORDER BY song " +
                "DESC LIMIT 1";
        return getResultOfQuery(SQL, requestedColumn);
    }

    private String getResultOfQuery(String SQL, String column) {
        String res = "";
        try (Connection conn = this.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                res = rs.getString(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
