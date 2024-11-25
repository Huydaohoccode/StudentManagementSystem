package chart;

import java.awt.Color;

public class ModelPieChart {
    private String name;
    private Color color;
    private double values;
    
    public ModelPieChart() {
        
    }
    
    public ModelPieChart(String name, double values, Color color) {
        this.name = name;
        this.color = color;
        this.values = values;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
