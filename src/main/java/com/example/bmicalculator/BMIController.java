package com.example.bmicalculator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BMIController implements Initializable {

    @FXML
    private ComboBox<String> cmbHeight;
    @FXML
    private ComboBox<String> cmbWeight;
    @FXML
    private Label lblBMIIndex;
    @FXML
    private Label lblBMIResult;
    @FXML
    private Label lblError;
    @FXML
    private TextField tfHeight;
    @FXML
    private TextField tfWeight;

    private final List<String> heightUnits = Arrays.asList("Ft", "Cm");
    private final List<String> weightUnits = Arrays.asList("Lbs", "Kg");

    @FXML
    void calculateBMI() {
        double weight = calculateValidWeight();
        double height = calculateValidHeight();

        double bmiIndex = (weight / (height * height)) * 703;

        displayResult(bmiIndex);
        resetInputs();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add respective values to combo box
        cmbWeight.getItems().addAll(weightUnits);
        cmbHeight.getItems().addAll(heightUnits);

        // Set default selected value
        cmbWeight.getSelectionModel().select(0);
        cmbHeight.getSelectionModel().select(0);
    }

    private double calculateValidWeight() {
        double weight;
        int selectedWeightIndex = cmbWeight.getSelectionModel().getSelectedIndex();
        weight = tfWeight.getText().isEmpty() ? 0 : Double.parseDouble(tfWeight.getText());

        if (selectedWeightIndex != 0) {
            // Convert kilogram to lbs
            weight = convertKgToLbs(weight);
        }

        return weight;
    }

    private double calculateValidHeight() {
        double height;

        int selectedHeightIndex = cmbHeight.getSelectionModel().getSelectedIndex();

        if (selectedHeightIndex == 0) {
            height = convertFeetIntoInches(tfHeight.getText());
        } else {
            // cm to inches
            double cmValue = tfHeight.getText().isEmpty() ? 0 : Double.parseDouble(tfHeight.getText());
            height = convertCmToInches(cmValue);
        }
        return height;
    }

    private double convertKgToLbs(double kgValue) {
        return kgValue * 2.2;
    }

    private double convertCmToInches(double cmValue) {
        return cmValue / 2.54;
    }

    private double convertFeetIntoInches(String height) {
        if (height.isEmpty()) {
            return 0;
        }
        double feet;
        double inches = 0.0;

        String[] arr = height.split("\\.");
        feet = Double.parseDouble(arr[0]);

        if (arr.length > 1) {
            inches = Double.parseDouble(arr[1]);
        }
        return (feet * 12) + inches;
    }

    private void displayResult(double bmiIndex) {
        lblBMIIndex.setText(String.format("%.2f", bmiIndex));

        if (bmiIndex <= 18.5) {
            lblBMIResult.setText("Underweight");
        } else if (bmiIndex > 18.5 && bmiIndex <= 24.99) {
            lblBMIResult.setText("Normal");
        } else if (bmiIndex > 25 && bmiIndex <= 29.99) {
            lblBMIResult.setText("Overweight");
        } else {
            lblBMIResult.setText("Obese");
        }
    }

    private void resetInputs() {
        tfHeight.clear();
        tfWeight.clear();
        cmbWeight.getSelectionModel().select(0);
        cmbHeight.getSelectionModel().select(0);
    }

}
