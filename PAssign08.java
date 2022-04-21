package ch14;
/**
 * File: PAssign08 Class: CSCI 1302 Author: Andrew Lin Created on: Apr 20, 2022
 * Last Modified:Apr 21, 2022 Description: modified mileagecalculator with combobox instead of radio buttons and auto conversion
 * https://github.com/bingbing770/ExamExercise/blob/main/ExamPractice4.java
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PAssign08 extends Application {
	// default values/strings
		private double txtWidth = 125.0;
		private String defaultCalc = String.format("%.2f", 0.00);
		private String defaultEntry = String.format("%.2f", 0.00);
		private String defaultMileage = "Miles";
		private String defaultCapacity = "Gallons";
		private String defaultResult = "MPG";
		private String altMileage = "Kilometers";
		private String altCapacity = "Liters";
		private String altResult = "L/100KM";

		// create UI components split by type
		private Button btnCalc = new Button("Calculate");
		private Button btnReset = new Button("Reset");

		private Label lblDistance = new Label(defaultMileage);
		private Label lblCapacity = new Label(defaultCapacity);
		private Label lblResult = new Label(defaultResult);
		private Label lblEffType = new Label("Efficiency Type");

		private TextField tfDistance = new TextField(defaultEntry);
		private TextField tfCapacity = new TextField(defaultEntry);
		private TextField tfResult = new TextField(defaultCalc);

		// create combo box instead of radio buttons
		private ComboBox cbo = new ComboBox();

		// create gridpane layout
		private GridPane mainPane = new GridPane();

		public void start(Stage primaryStage) {
			// populate combo box and set it's value to the default
			cbo.getItems().addAll(defaultResult, altResult);
			cbo.setValue(defaultResult);

			// set preferences for UI components
			tfDistance.setMaxWidth(txtWidth);
			tfCapacity.setMaxWidth(txtWidth);
			tfResult.setMaxWidth(txtWidth);
			tfResult.setEditable(false);

			// create a main grid pane to hold items
			mainPane.setPadding(new Insets(10.0));
			mainPane.setHgap(txtWidth / 2.0);
			mainPane.setVgap(txtWidth / 12.0);

			// add items to mainPane
			mainPane.add(lblEffType, 0, 0);
			mainPane.add(cbo, 0, 1);
			mainPane.add(lblDistance, 0, 2);
			mainPane.add(tfDistance, 1, 2);
			mainPane.add(lblCapacity, 0, 3);
			mainPane.add(tfCapacity, 1, 3);
			mainPane.add(lblResult, 0, 4);
			mainPane.add(tfResult, 1, 4);
			mainPane.add(btnReset, 0, 5);
			mainPane.add(btnCalc, 1, 5);

			// register action handlers this time with combobox
			btnCalc.setOnAction(e -> calcMileage());
			tfDistance.setOnAction(e -> calcMileage());
			tfCapacity.setOnAction(e -> calcMileage());
			tfResult.setOnAction(e -> calcMileage());
			cbo.setOnAction((e) -> {
				changeLabels();
				convertCalculation();
			});
			btnReset.setOnAction(e -> resetForm());

			// create a scene and place it in the stage
			Scene scene = new Scene(mainPane);

			// set and show stage
			primaryStage.setTitle("Mileage Calculator");
			primaryStage.setScene(scene);
			primaryStage.show();

			// stick default focus in first field for usability
			tfDistance.requestFocus();
		}

		/**
		 * Convert existing figures and recalculate This needs to be separate to avoid
		 * converting when the conversion is not necessary
		 */
		private void changeLabels() {
			// distinguish between L/100KM and MPG
			if (cbo.getValue().equals("L/100KM") && lblCapacity.getText().equals(defaultCapacity)) {
				// update labels
				lblCapacity.setText(altCapacity);
				lblDistance.setText(altMileage);
				lblResult.setText(altResult);
			} else {
				// update labels
				lblCapacity.setText(defaultCapacity);
				lblDistance.setText(defaultMileage);
				lblResult.setText(defaultResult);
			}
		}

		/**
		 * Calculate expenses based on entered figures
		 */
		private void calcMileage() {
			// set default values
			double distance = 0.0, capacity = 0.0;

			// make sure to get numeric values only
			if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty() && tfDistance.getText() != null
					&& !tfDistance.getText().isEmpty()) {
				distance = Double.parseDouble(tfDistance.getText());
				capacity = Double.parseDouble(tfCapacity.getText());
			}

			// check for type of calculation
			double result = 0.0;
			if (cbo.getValue().equals("L/100KM")) {
				// liters / 100KM
				result = (distance != 0) ? capacity / (distance / 100.0) : 0;
			} else {
				// MPG
				result = (capacity != 0) ? distance / capacity : 0;
			}

			// update calculation fields with currency formatting
			tfResult.setText(String.format("%.2f", result));
		}

		/**
		 * Reset all values in the application
		 */
		private void resetForm() {
			// reset all form fields

			tfDistance.setText(defaultEntry);
			tfCapacity.setText(defaultEntry);
			tfResult.setText(defaultCalc);
			lblCapacity.setText(defaultCapacity);
			lblDistance.setText(defaultMileage);
			lblResult.setText(defaultResult);
		}

		private void convertCalculation() {

			double distance = 0.0, capacity = 0.0, result = 0.0;

			// make sure to get numeric values only
			if (tfCapacity.getText() != null && !tfCapacity.getText().isEmpty() && tfDistance.getText() != null
					&& !tfDistance.getText().isEmpty()) {
				distance = Double.parseDouble(tfDistance.getText());
				capacity = Double.parseDouble(tfCapacity.getText());
			}
			// check if in miles or km
			if (cbo.getValue().equals("L/100KM")) {
				distance *= 1.609;
				capacity *= 3.785;
				result = (distance != 0) ? capacity / (distance / 100.0) : 0;
			} else if (cbo.getValue().equals("MPG")) {
				distance /= 1.609;
				capacity /= 3.785;
				result = (capacity != 0) ? distance / capacity : 0;
			}
			// display new calculation
			tfDistance.setText(String.format("%.2f", distance));
			tfCapacity.setText(String.format("%.2f", capacity));
			tfResult.setText(String.format("%.2f", result));

		}

		public static void main(String[] args) {
			Application.launch(args);
		}
}
