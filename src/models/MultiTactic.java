package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class MultiTactic {
    @JsonProperty("figure_type")
    private String figureType;
    @JsonProperty("coordinates")
    private List<List<List<Double>>> coordinates;
    @JsonProperty("color")
    private String color;
    @JsonProperty("amplifications")
    private String amplifications;
    @JsonProperty("opacity")
    private int opacity;
    @JsonProperty("altitude")
    private double altitude;

    // Getters and Setters
    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
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

    @Override
    public String toString() {
        return "{" +
                "coordinates=" + coordinates +
                ", color='" + color + '\'' +
                ", amplifications='" + amplifications + '\'' +
                ", opacity=" + opacity +
                ", altitude=" + altitude +
                '}';
    }
    public static List<List<List<Double>>> parseCoordinates(String coordinates) {
        // Parse the coordinates string and convert it into a List<List<List<Double>>>
        // You may need to implement this logic based on your data format.
        // For example, if the coordinates are stored as a nested JSON array, you can use a JSON parser.
        // Here's a simple example assuming the coordinates are in JSON format:
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(coordinates, List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

