package ru.rrenat358.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.config.ConfigConst;

import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class AuthService {

  private static Connection connection;
  private static Statement statement;

  @Data
  @AllArgsConstructor
  private class Entry {

    private String login;
    private String password;
    private int capacity;
  }

  private final HashMap<String, Entry> entries = new HashMap<>();

  public void insertUser(String login, String password, int capacity) throws SQLException {
    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users (login, password, capacity) VALUES (?, ?, ?)")) {
      ps.setString(1, login);
      ps.setString(2, password);
      ps.setInt(3, capacity);
      ps.executeUpdate();

      entries.put(login, new Entry(login, password, capacity));
      log.info("User " + login + " added to users.db");
      createFolder(Paths.get(ConfigConst.SERVER_REPO, login).toString());
    }
  }

  public boolean authUser(String login, String password) {
    if (entries.get(login) == null) {
      return false;
    }
    return entries.get(login).getPassword().equals(password);
  }

  public void start() {
    log.info("AuthService started");

    try {
      connect();
      createTable();
      readUsers();
      createFolders();
    } catch (SQLException e) {
      log.error(e);
      log.debug(e.toString(), e);
    }
  }

  private void connect() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite:db/users.db");
    statement = connection.createStatement();
  }

  private void createTable() throws SQLException{
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
        "login TEXT NOT NULL UNIQUE,\n" +
        "password TEXT NOT NULL,\n" +
        "capacity INT NOT NULL\n" +
        ");"
    );
  }

  private void readUsers() throws SQLException{
    try (ResultSet rs = statement.executeQuery("SELECT * FROM users")) {
      while (rs.next()) {
        entries.put(
            rs.getString("login"),
            new Entry(rs.getString("login"),
                rs.getString("password"),
                rs.getInt("capacity")));
      }
    }
  }

  private void createFolders() {
    createFolder(ConfigConst.SERVER_REPO);
    for (Map.Entry<String, Entry> entry : entries.entrySet()) {
      String login = entry.getKey();
      createFolder(Paths.get(ConfigConst.SERVER_REPO, login).toString());
    }
  }

  private void createFolder(String pathname) {
    File folder = new File(pathname);
    if (!folder.exists()) {
      folder.mkdir();
      log.info("Folder " + folder.getPath() + " created");
    }
  }

  public void stop() {
    log.info("AuthService stopped");
  }
}
