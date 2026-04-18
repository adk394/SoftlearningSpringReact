package com.example.core.entities.shared.physicals;

import com.example.shared.exceptions.BuildException;

public class PhysicalData {

    protected double weight, height, width, depth;

    protected PhysicalData() {
    }

    public static PhysicalData getInstance(double weight, double height, double width, double depth) throws BuildException {
        PhysicalData pd = new PhysicalData();
        String errorMessage = PhysicalDataValidation(weight, height, width, depth);
        if (!errorMessage.isEmpty()) {
            throw new BuildException(errorMessage);
        }

        pd.setWeight(weight);
        pd.setHeight(height);
        pd.setWidth(width);
        pd.setDepth(depth);
        return pd;
    }

    private static String PhysicalDataValidation(double weight, double height, double width, double depth) {
        String errorMessage = "";
        if (weight <= 0) {
            errorMessage += "Peso incorrecto. ";
        }
        if (height <= 0) {
            errorMessage += "Altura incorrecta. ";
        }
        if (width <= 0) {
            errorMessage += "Anchura incorrecta. ";
        }
        if (depth <= 0) {
            errorMessage += "Profundidad incorrecta. ";
        }
        return errorMessage;
    }

    public double getWeight() {
        return weight;
    }

    public int setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
            return 0;
        }
        return -1;
    }

    public double getHeight() {
        return height;
    }

    public int setHeight(double height) {
        if (height > 0) {
            this.height = height;
            return 0;
        }
        return -1;
    }

    public double getWidth() {
        return width;
    }

    public int setWidth(double width) {
        if (width > 0) {
            this.width = width;
            return 0;
        }
        return -1;
    }

    public double getDepth() {
        return depth;
    }

    public int setDepth(double depth) {
        if (depth > 0) {
            this.depth = depth;
            return 0;
        }
        return -1;
    }

    public double getVolume() {
        return height * width * depth;
    }

    public double getArea() {
        return width * height;
    }

    public String getSize() {
        return "height: " + height + "; width: " + width + "; depth: " + depth;
    }

}
