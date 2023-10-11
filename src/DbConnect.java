import com.fasterxml.jackson.databind.ObjectMapper;
import models.MultiTactic;
import models.PointTactic;
import models.SingleTactic;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class DbConnect {

    private static final String url = "jdbc:postgresql://192.168.165.22:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "postgres";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void insertData(String figureType, String msg) {
        if (Objects.equals(figureType, "Point")) {
            PointTactic pointTactic = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                pointTactic = objectMapper.readValue(msg, PointTactic.class);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            String SQL = "INSERT INTO tactical_figures\n" +
                    "(figure_type, coordinates, color, amplifications, opacity, altitude, updated_at, is_deleted) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(SQL,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "Point");
                pstmt.setString(2, Arrays.toString(pointTactic.getCoordinates()));
                pstmt.setString(3, pointTactic.getColor());
                pstmt.setString(4, pointTactic.getAmplifications());
                pstmt.setInt(5, pointTactic.getOpacity());
                pstmt.setDouble(6, pointTactic.getAltitude());
                pstmt.setTimestamp(7, timestamp);
                pstmt.setBoolean(8, false);
                int affectedRows = pstmt.executeUpdate();
                System.out.println(affectedRows);
                // check the affected rows
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (Objects.equals(figureType, "Single")) {
            SingleTactic singleTactic = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                singleTactic = objectMapper.readValue(msg, SingleTactic.class);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            String SQL = "INSERT INTO tactical_figures\n" +
                    "(figure_type, coordinates, color, amplifications, opacity, altitude, updated_at, is_deleted) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(SQL,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "Single");
                pstmt.setString(2,singleTactic.getCoordinates().toString());
                pstmt.setString(3, singleTactic.getColor());
                pstmt.setString(4, singleTactic.getAmplifications());
                pstmt.setInt(5, singleTactic.getOpacity());
                pstmt.setDouble(6, singleTactic.getAltitude());
                pstmt.setTimestamp(7, timestamp);
                pstmt.setBoolean(8, false);
                int affectedRows = pstmt.executeUpdate();
                System.out.println(affectedRows);
                // check the affected rows
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } else if (Objects.equals(figureType, "Multi")) {
            MultiTactic multiTactic = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                multiTactic = objectMapper.readValue(msg, MultiTactic.class);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            String SQL = "INSERT INTO tactical_figures\n" +
                    "(figure_type, coordinates, color, amplifications, opacity, altitude, updated_at, is_deleted) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(SQL,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "Multi");
                pstmt.setString(2,multiTactic.getCoordinates().toString());
                pstmt.setString(3, multiTactic.getColor());
                pstmt.setString(4, multiTactic.getAmplifications());
                pstmt.setInt(5, multiTactic.getOpacity());
                pstmt.setDouble(6, multiTactic.getAltitude());
                pstmt.setTimestamp(7, timestamp);
                pstmt.setBoolean(8, false);
                int affectedRows = pstmt.executeUpdate();
                System.out.println(affectedRows);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
