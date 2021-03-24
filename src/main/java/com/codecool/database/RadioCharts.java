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
        String song = "";
        String SQL = "SELECT song, SUM(times_aired) as times_aired " +
                "FROM music_broadcast " +
                "GROUP BY artist " +
                "ORDER BY times_aired DESC " +
                "LIMIT 1";
        try (Connection conn = this.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                song = rs.getString("song");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }

    public String getMostActiveArtist() {
        String artist = "";
        String SQL = "SELECT artist, COUNT(DISTINCT song) AS song " +
                "FROM music_broadcast " +
                "GROUP BY artist " +
                "ORDER BY song " +
                "DESC LIMIT 1";
        try (Connection conn = this.getConnection()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                artist = rs.getString("artist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist;
    }
}
