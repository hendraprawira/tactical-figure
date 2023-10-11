package models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PointTactic {
    @JsonProperty("figure_type")
    private String figureType;
    @JsonProperty("coordinates")
    private double[] coordinates;

    @JsonProperty("color")
    private String color;

    @JsonProperty("amplifications")
    private String amplifications;

    @JsonProperty("opacity")
    private int opacity;

    @JsonProperty("altitude")
    private double altitude;

    // Getter and setter methods for the fields


    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAmplifications() {
        return amplifications;
    }

    public void setAmplifications(String amplifications) {
        this.amplifications = amplifications;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public static double[] parseCoordinates(String coordinates) {
        // Parse the coordinates string and convert it into a double array
        // You may need to implement this logic based on your data format.
        // For example, if the coordinates are stored as a JSON array, you can use a JSON parser.
        // Here's a simple example assuming the coordinates are in JSON format:
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(coordinates, double[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
