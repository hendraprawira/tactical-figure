import TacticalPoint.TacticPoint;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import models.PointTactic;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.ServiceEnvironment;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.pub.DataWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PointHandler implements HttpHandler {
    private DataWriter<TacticPoint> writer;
    private DomainParticipant participant;
    private ServiceEnvironment env;
    private Connection conn;
    public PointHandler(DataWriter<TacticPoint> writer, DomainParticipant participant, ServiceEnvironment env,
                        Connection conn) {
        this.writer = writer;
        this.participant = participant;
        this.env = env;
        this.conn = conn;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get the HTTP request method (GET, POST, etc.)
        String requestMethod = exchange.getRequestMethod();

        // Get the output stream to write the response
        OutputStream os = exchange.getResponseBody();

        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseHeaders.set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Define the response message
        String response = "";

        // Check the request method
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            // This is a CORS preflight request, respond with 200 OK and no content
            exchange.sendResponseHeaders(200, -1);
        } else if ("POST".equals(requestMethod)) {

            InputStream requestBody = exchange.getRequestBody();
            // Create a byte array to read the data from the input stream
            byte[] buffer = new byte[10024];
            int bytesRead;
            StringBuilder requestBodyBuilder = new StringBuilder();

            // Read the data from the input stream in chunks
            while ((bytesRead = requestBody.read(buffer)) != -1) {
                requestBodyBuilder.append(new String(buffer, 0, bytesRead));
            }

            // Get the complete request body as a string
            String requestBodyString = requestBodyBuilder.toString();
            PointTactic pointTactic = null;

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                pointTactic = objectMapper.readValue(requestBodyString, PointTactic.class);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
            String SQL = "INSERT INTO tactical_figures\n" +
                    "(figure_type, coordinates, color, amplifications, opacity, altitude, updated_at, is_deleted) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            try {

                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            TacticPoint tacticMsg = new TacticPoint();
            tacticMsg.message = requestBodyString;
            tacticMsg.mockID = 2;
            tacticMsg.tacticType = "Point";
            try {
                writer.write(tacticMsg, InstanceHandle.nilHandle(env));
                System.out.println("| Publish Message : " + tacticMsg.message + "\n");
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // WS
            PointWS.sendMessageToAll(requestBodyString);
            // Handle POST request
            response = "This is a POST request!";
            // Send the response back to the client
            exchange.sendResponseHeaders(200, requestBodyString.length());
            os.write(requestBodyString.getBytes());
            os.close();
        } else if ("GET".equals(requestMethod)) {
            System.out.println("GET METHOD");
            String SQL = "SELECT figure_type, coordinates, color, amplifications, opacity, altitude \n" +
                    "FROM tactical_figures WHERE figure_type = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, "Point");
                ResultSet rs = pstmt.executeQuery();
                List<PointTactic> pointTactics = new ArrayList<>();
                System.out.println(rs);
                while (rs.next()) {
                    PointTactic data = new PointTactic();
                    data.setFigureType(rs.getString("figure_type"));
                    data.setCoordinates(PointTactic.parseCoordinates(rs.getString("coordinates")));
                    data.setColor(rs.getString("color"));
                    data.setAmplifications(rs.getString("amplifications"));
                    data.setOpacity(rs.getInt("opacity"));
                    data.setAltitude(rs.getDouble("altitude"));
                    pointTactics.add(data);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(pointTactics);
                System.out.println(json);
                exchange.sendResponseHeaders(200, json.length());
                os.write(json.getBytes());
                os.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            response = "This is a GET request!";
        } else {
            // Handle other request methods
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
        }
    }
}
