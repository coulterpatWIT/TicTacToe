package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.shape.Ellipse;

public class TicTacToe extends Application {
  
  private char whoseTurn = 'X';

 
  private Cell[][] cell =  new Cell[3][3];

  
  private Label lblStatus = new Label("X's turn to play");

  @Override 
  public void start(Stage primaryStage) {
   
    GridPane pane = new GridPane(); 
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        pane.add(cell[i][j] = new Cell(), j, i);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
    borderPane.setBottom(lblStatus);
    
    lblStatus.setFont(new Font(24));
    
    lblStatus.setMaxWidth(Double.MAX_VALUE);
    AnchorPane.setLeftAnchor(lblStatus, 0.0);
    AnchorPane.setRightAnchor(lblStatus, 0.0);
    lblStatus.setAlignment(Pos.CENTER);
   
    Scene scene = new Scene(borderPane, 800, 800);
    primaryStage.setTitle("TicTacToe"); 
    primaryStage.setScene(scene); 
    primaryStage.show();    
  }
  
  public boolean isFull() {
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        if (cell[i][j].getToken() == ' ')
          return false;

    return true;
  }

  // Determine if the player with the specified token wins 
  public boolean isWon(char win) {
    for (int i = 0; i < 3; i++)
      if (cell[i][0].getToken() == win
          && cell[i][1].getToken() == win
          && cell[i][2].getToken() == win) {
        return true;
      }

    for (int j = 0; j < 3; j++)
      if (cell[0][j].getToken() ==  win
          && cell[1][j].getToken() == win
          && cell[2][j].getToken() == win) {
        return true;
      }

    if (cell[0][0].getToken() == win 
        && cell[1][1].getToken() == win        
        && cell[2][2].getToken() == win) {
      return true;
    }

    if (cell[0][2].getToken() == win
        && cell[1][1].getToken() == win
        && cell[2][0].getToken() == win) {
      return true;
    }

    return false;
  }

  
  public class Cell extends Pane {
    
    private char token = ' ';

    public Cell() {
      setStyle("-fx-border-color: black"); 
      this.setPrefSize(500, 500);
      this.setOnMouseClicked(e -> handleMouseClick());
    }

    
    public char getToken() {
      return token;
    }

   
    public void setToken(char c) {
      token = c;
      
      if (token == 'X') {
        Line line1 = new Line(10, 10, 
          this.getWidth() - 10, this.getHeight() - 10);
        line1.endXProperty().bind(this.widthProperty().subtract(10));
        line1.endYProperty().bind(this.heightProperty().subtract(10));
        Line line2 = new Line(10, this.getHeight() - 10, 
          this.getWidth() - 10, 10);
        line2.startYProperty().bind(
          this.heightProperty().subtract(10));
        line2.endXProperty().bind(this.widthProperty().subtract(10));

        this.getChildren().addAll(line1, line2); 
      }
      else if (token == 'O') {
        Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
          this.getHeight() / 2, this.getWidth() / 2 - 10, 
          this.getHeight() / 2 - 10);
        ellipse.centerXProperty().bind(
          this.widthProperty().divide(2));
        ellipse.centerYProperty().bind(
            this.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(
            this.widthProperty().divide(2).subtract(10));        
        ellipse.radiusYProperty().bind(
            this.heightProperty().divide(2).subtract(10));   
        ellipse.setStroke(Color.BLACK);
        Color lightgray = Color.web("#F4F4F4");
        ellipse.setFill(lightgray);
        
        getChildren().add(ellipse); 
      }
    }

    // Handle a mouse click event 
    private void handleMouseClick() {
      // If cell is empty and game is not over
      if (token == ' ' && whoseTurn != ' ') {
        setToken(whoseTurn); 

        // Check game status
        if (isWon(whoseTurn)) {
          lblStatus.setText(whoseTurn + " won! The game is over");
          whoseTurn = ' '; // Game is over
          
        }
        else if (isFull()) {
          lblStatus.setText("Draw! The game is over");
          whoseTurn = ' '; // Game is over
        }
        else {
          // Change the turn
          whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
          // Display whose turn
          lblStatus.setText(whoseTurn + "'s turn");
        }
      }
    }
  }
 
  public static void main(String[] args) {
    launch(args);
  }
}