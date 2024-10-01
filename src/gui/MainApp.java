package gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Yatzy;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Yatzy");
		GridPane pane = new GridPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	// -------------------------------------------------------------------------

	// Shows the face values of the 5 dice.
	private TextField[] txfValues = new TextField[5];
	// Shows the hold status of the 5 dice.
	private CheckBox[] chbHolds = new CheckBox[5];
	private String text;
	private int total = 0;
	private Yatzy dice = new Yatzy();
	// Shows the results previously selected .
	// For free results (results not set yet), the results
	// corresponding to the actual face values of the 5 dice are shown.
	private TextField[] txfResults = new TextField[15];
	// Shows points in sums, bonus and total.
	private TextField txfSumSame, txfBonus, txfSumOther, txfTotal;
	// Shows the number of times the dice has been rolled.
	private Label lblRolled = new Label("Rolled: " + this.dice.getThrowCount());
	private Label[] lblResults = new Label[6];
	private int rounds = 0;

	private Button btnRoll = new Button("Roll");
	private TextField txfTest = new TextField();

	private void initContent(GridPane pane) {
		pane.setGridLinesVisible(false);
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(10);

		// ---------------------------------------------------------------------

		GridPane dicePane = new GridPane();
		pane.add(dicePane, 0, 0);
		dicePane.setGridLinesVisible(false);
		dicePane.setPadding(new Insets(20));
		dicePane.setHgap(10);
		dicePane.setVgap(10);
		dicePane.setStyle("-fx-border-color: black");

		// initialize txfValues, chbHolds, btnRoll and lblRolled
		// TODO
		Font font1 = new Font("SansSerif", 30);
		// Text area for rolls
		for (int i = 0; i < txfValues.length; i++) {
			TextField text = new TextField();
			txfValues[i] = text;
			txfValues[i].setPrefWidth(60);
			txfValues[i].setPrefHeight(60);
			dicePane.add(txfValues[i], i, 0, 1, 1);
			txfValues[i].setEditable(false);
			txfValues[i].setFont(font1);
		}

		txfTest.setEditable(false);
		// Button roll

		dicePane.add(btnRoll, 3, 4);
		dicePane.setMargin(btnRoll, new Insets(1, 0, 1, 1));
		btnRoll.setOnAction(event -> rollAction());

		// Label Rolled
		dicePane.add(lblRolled, 4, 4);

		// Checkboxs
		for (int i = 0; i < txfValues.length; i++) {
			CheckBox check = new CheckBox();
			chbHolds[i] = check;
			dicePane.add(chbHolds[i], i, 2);
			check.setText("Hold");

		}

		// ---------------------------------------------------------------------

		GridPane scorePane = new GridPane();

		pane.add(scorePane, 0, 1);
		scorePane.setGridLinesVisible(false);
		scorePane.setPadding(new Insets(10));
		scorePane.setVgap(5);
		scorePane.setHgap(10);
		scorePane.setStyle("-fx-border-color: black");
		int w = 50; // width of the text fields

		// Initialize labels for results, txfResults,
		// labels and text fields for sums, bonus and total.

		for (int i = 0; i < txfResults.length; i++) {
			TextField text = new TextField();
			txfResults[i] = text;
			txfResults[i].setPrefWidth(50);
			txfResults[i].setPrefHeight(25);
			scorePane.add(txfResults[i], 2, i, 1, 1);
			txfResults[i].setEditable(false);

		}

		for (int i = 1; i <= lblResults.length; i++) {
			String label = Integer.toString(i);
			lblResults[i - 1] = new Label(label + "-s");
			scorePane.add(lblResults[i - 1], 1, i - 1);
		}

		Label lblSumSame = new Label("Sum:");
		Label lblSumOther = new Label("Sum:");
		Label lblBonus = new Label("Bonus:");
		Label lblTotal = new Label("Total:");

		Label lblOnePair = new Label("One pair");
		Label lblTwoPairs = new Label("Two pairs");
		Label lblThreeSame = new Label("Three Same");
		Label lblFourSame = new Label("Four Same");
		Label lblFullHouse = new Label("Full House");
		Label lblSmallStraight = new Label("Small Straight");
		Label lblLargeStraight = new Label("Large Straight");
		Label lblChance = new Label("Chance");
		Label lblYatzy = new Label("Yatzy");

		scorePane.add(lblSumSame, 3, 5);
		scorePane.add(lblSumOther, 3, 14);
		scorePane.add(lblBonus, 5, 5);
		scorePane.add(lblTotal, 5, 14);

		scorePane.add(lblOnePair, 1, 6);
		scorePane.add(lblTwoPairs, 1, 7);
		scorePane.add(lblThreeSame, 1, 8);
		scorePane.add(lblFourSame, 1, 9);
		scorePane.add(lblFullHouse, 1, 10);
		scorePane.add(lblSmallStraight, 1, 11);
		scorePane.add(lblLargeStraight, 1, 12);
		scorePane.add(lblChance, 1, 13);
		scorePane.add(lblYatzy, 1, 14);

		txfSumSame = new TextField();
		txfSumSame.setEditable(false);
		txfBonus = new TextField();
		txfBonus.setEditable(false);
		txfSumOther = new TextField();
		txfSumOther.setEditable(false);
		txfTotal = new TextField();
		txfTotal.setEditable(false);

		txfSumSame.setPrefWidth(50);
		txfSumSame.setPrefHeight(25);
		txfBonus.setPrefWidth(50);
		txfBonus.setPrefHeight(25);
		txfSumOther.setPrefWidth(50);
		txfSumOther.setPrefHeight(25);
		txfTotal.setPrefWidth(50);
		txfTotal.setPrefHeight(25);
		scorePane.add(txfSumSame, 4, 5);
		txfSumSame.setText("0");

		scorePane.add(txfBonus, 6, 5);
		scorePane.add(txfSumOther, 4, 14);
		txfSumOther.setText("0");
		scorePane.add(txfTotal, 6, 14);
		txfTotal.setText("0");
		txfBonus.setText("0");

	}

	// -------------------------------------------------------------------------

	// Create a method for btnRoll's action.
	// Hint: Create small helper methods to be used in the action method.

	public void rollAction() {

		boolean[] hold = new boolean[5];
		for (int i = 0; i < hold.length; i++) {
			hold[i] = chbHolds[i].isSelected();
		}

		dice.throwDice(hold);
		// set text
		for (int i = 0; i < txfValues.length; i++) {
			txfValues[i].setText(dice.getValues()[i] + "");

		}
		lblRolled.setText("Rolled: " + this.dice.getThrowCount());
		rollCounts();
		updateResults();
	}

	public void rollCounts() {

		if (this.dice.getThrowCount() == 3) {
			btnRoll.setDisable(true);
			for (int i = 0; i < txfResults.length; i++) {
				txfResults[i].setOnMouseClicked(event -> this.chooseFieldAction(event));
			}
		}
	}

	// -------------------------------------------------------------------------

	// Create a method for mouse click on one of the text fields in txfResults.
	// Hint: Create small helper methods to be used in the mouse click method.
	public void chooseFieldAction(Event event) {
		rounds++;
		TextField txt = (TextField) event.getSource();
		text = txt.getText();
		if (this.dice.getThrowCount() == 3) {
			checkTextField(txt);
			txt.setDisable(true);
			checkBonus();
			cleanSheet();
		}
		if (rounds == 15) {
			gameOver();
		}
	}

	public void checkTextField(TextField txt) {
		for (int i = 0; i < txfResults.length; i++) {
			if (txt.equals(txfResults[i]) && i < 6) {
				txfSumSame.setText(Integer
						.toString(Integer.parseInt(this.txfSumSame.getText()) + Integer.parseInt(txt.getText())));
			} else if (i >= 6 && txt.equals(txfResults[i])) {
				txfSumOther.setText(Integer
						.toString(Integer.parseInt(this.txfSumOther.getText()) + Integer.parseInt(txt.getText())));
			}

		}
		this.sumTotal(txt);
	}

	public void sumTotal(TextField txt) {
		txfTotal.setText(Integer.toString(Integer.parseInt(txfTotal.getText()) + Integer.parseInt(txt.getText())));
		cleanSheet();

	}

	public void checkBonus() {
		if (Integer.parseInt(txfSumSame.getText()) > 63 && Integer.parseInt(txfBonus.getText()) != 50) {
			txfBonus.setText("50");
			txfTotal.setText(Integer.toString(Integer.parseInt(txfTotal.getText()) + Integer.parseInt(txfBonus.getText())));
		}
	}

	public void updateResults() {
		for (int i = 0; i < txfResults.length; i++) {
			txfResults[i].setText(dice.getResults()[i] + "");

		}
	}

	public void cleanSheet() {
		for (int i = 0; i < txfValues.length; i++) {
			txfValues[i].setText("");
		}
		for (int i = 0; i < txfResults.length; i++) {
			txfResults[i].setText("");
		}
		for (int i = 0; i < txfValues.length; i++) {
			chbHolds[i].setSelected(false);
		}

		this.dice.resetThrowCount();
		lblRolled.setText("Rolled: " + this.dice.getThrowCount());
		btnRoll.setDisable(false);


	}
	
	
	public void gameOver() {
		 Alert alert = new Alert(AlertType.INFORMATION);
				 alert.setTitle("Yatzy");
				 alert.setHeaderText("Game over!");
				 alert.setContentText("Your final result is: " + txfTotal.getText());
				 alert.show();
		cleanSheet();
		txfBonus.setText("0");
		txfTotal.setText("0");
		txfSumOther.setText("0");
		txfSumSame.setText("0");
		for (int i = 0; i < txfResults.length; i++) {
			txfResults[i].setDisable(false);

		}
		
	} 
	
	
	
}
