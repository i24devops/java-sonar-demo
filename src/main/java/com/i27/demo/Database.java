package com.i27.demo;

import java.sql.*;

public class Database {
    public String findUserByName(String jdbcUrl, String user, String pass, String name) throws Exception {
        // Noncompliant: potential SQL injection
        String query = "SELECT id, name FROM users WHERE name = '" + name + "'";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("id") + ":" + rs.getString("name");
            }
            return null;
        }
    }

    // Compliant version for class discussion:
    // try (PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM users WHERE name = ?")) {
    //     ps.setString(1, name);
    //     try (ResultSet rs = ps.executeQuery()) { ... }
    // }
}
