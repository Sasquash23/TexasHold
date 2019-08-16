package com.kodillla.texasholdem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;


public class TexasHoldem extends Application {

    //Gridpane variables
    private FlowPane dealerCardsF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane dealerCardsFF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane cardsF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane playerCardsF = new FlowPane(Orientation.HORIZONTAL);

    // Table variable
    private Image imageback = new Image("table.jpg");
    private ImageView dealerCardTable;

    //Hand variable
    final private int handNumberCards = 7;
    //private Card [] dealerCardsC = new Card [handNumberCards];
    private List<Card> dealerCardsC = new ArrayList<Card>(handNumberCards);
    private List<Card> tableCardC = new ArrayList<Card>(handNumberCards -2);
    private List<Card> playerCardsC = new ArrayList<Card>(handNumberCards);


    public static void main(String[] args) {
        launch(args);
    }

    public void getNewCard (List<Card> listCards, int n){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);

            //Card size 96 pixel, 72 pixel
            GridPane grid = new GridPane();
            for (int i = 0; i < 7; i++) {
                RowConstraints row = new RowConstraints(96);
                grid.getRowConstraints().add(row);
            }
            for (int i = 0; i < 10;i++) {
                ColumnConstraints column = new ColumnConstraints(72);
                grid.getColumnConstraints().add(column);
                // set max.width
            }

            grid.setAlignment(Pos.CENTER);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(4);
            grid.setBackground(background);

            Deck firstPack = new Deck();

            for (int i = 0; i < handNumberCards -2 ; i++) {
                tableCardC.add(firstPack.getNewCard());
                //ImageView cardTable = new ImageView(tableCardC.get(i).showCard());
                cardsF.getChildren().add(new ImageView(tableCardC.get(i).showCard()));
            }

            for (int i = 0; i < 2; i++) {
                dealerCardsC.add(firstPack.getNewCard());
                //dealerCardTable = new ImageView(dealerCardsC.get(i).showCard());
                dealerCardsF.getChildren().add(new ImageView(dealerCardsC.get(i).showCard()));
            }

            for (int i = 0; i < 2; i++) {
                playerCardsC.add(firstPack.getNewCard());
                //ImageView playerCardTable = new ImageView(playerCardsC.get(i).showCard());
                playerCardsF.getChildren().add( new ImageView(playerCardsC.get(i).showCard()));
            }

            dealerCardsFF.getChildren().add(new ImageView("cards/red_back.png"));
            dealerCardsFF.getChildren().add(new ImageView("cards/red_back.png"));
            grid.add(dealerCardsFF, 5, 2, 2, 1);
            grid.add(cardsF, 3, 3, 5, 1);
            grid.add(playerCardsF, 5, 4, 5, 1);

            Button btnCheck = new Button("Show cards");

            Text playerSocer = new Text();
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (btnCheck.getText() == "New Game") {
                        grid.getChildren().remove(dealerCardsF);
                        grid.getChildren().remove(cardsF);
                        grid.getChildren().remove(playerCardsF);

                        Deck second = new Deck();
                        tableCardC.clear();
                        cardsF.getChildren().clear();
                        dealerCardsC.clear();
                        dealerCardsF.getChildren().clear();
                        playerCardsC.clear();
                        playerCardsF.getChildren().clear();
                        for (int i = 0; i <5 ; i++) {
                            tableCardC.add(firstPack.getNewCard());
                            cardsF.getChildren().add(new ImageView(tableCardC.get(i).showCard()));
                        }

                        for (int i = 0; i < 2; i++) {
                            dealerCardsC.add(firstPack.getNewCard());
                            dealerCardsF.getChildren().add(new ImageView(dealerCardsC.get(i).showCard()));
                        }

                        for (int i = 0; i < 2; i++) {
                            playerCardsC.add(firstPack.getNewCard());
                            playerCardsF.getChildren().add( new ImageView(playerCardsC.get(i).showCard()));
                        }

                        grid.add(dealerCardsFF, 5, 2, 2, 1);
                        grid.add(cardsF, 3, 3, 5, 1);
                        grid.add(playerCardsF, 5, 4, 5, 1);

                        playerSocer.setText("");
                        btnCheck.setText("Show cards");
                    } else if (btnCheck.getText() == "Call") {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        HandEvaluation handEvaluation = new HandEvaluation(playerCardsC, tableCardC);
                        HandEvaluation handEvaluation2 = new HandEvaluation(dealerCardsC, tableCardC);

                        if (handEvaluation.equals(handEvaluation2)) {
                            System.out.println("Remis");
                            alert.setTitle("Winner is ... ");
                            alert.setHeaderText(null);
                            alert.setGraphic(new ImageView("tie.png"));
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image("tie.png"));
                            alert.setContentText("It is a tie." + "\nYour score: " + handEvaluation.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                                    "\nDelare score: " + handEvaluation2.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());
                        } else if (handEvaluation.compareTo(handEvaluation2)) {
                            alert.setTitle("Winner is ... ");
                            alert.setHeaderText(null);
                            alert.setGraphic(new ImageView("winner.png"));
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image("winner.png"));
                            alert.setContentText("Congratulation you won!!!!" + "\nYour score: " + handEvaluation.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                                    "\nDelare score: " + handEvaluation2.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());
                        } else {
                            alert.setTitle("Winner is ... ");
                            alert.setHeaderText(null);
                            alert.setGraphic(new ImageView("loser2.png"));
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image("loser2.png"));
                            alert.setContentText("Oh no!!!! You have lost :(" + "\nYour score: " + handEvaluation.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                                    "\nDelare score: " + handEvaluation2.getHandScoer() +
                                    "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());
                        }

                        alert.showAndWait();
                        //Optional<Pair<String, String>> result = dialog.showAndWait();
                        playerSocer.setText("Player score: " + handEvaluation.getHandScoer() +
                                "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                                "\nDelare score: " + handEvaluation2.getHandScoer() +
                                "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());

                        btnCheck.setText("New Game");
                    } else if (btnCheck.getText() == "Show cards") {
                        grid.getChildren().remove(dealerCardsFF);
                        grid.add(dealerCardsF, 5, 2, 2, 1);
                        btnCheck.setText("Call");
                    }

                }
            };

            btnCheck.setOnAction(event);
            grid.add(btnCheck, 10,4, 1, 1);

            playerSocer.setFill(Color.WHITE);
            grid.add(playerSocer, 10,5, 1, 1);

            Scene scene = new Scene(grid, 1400, 800, Color.BLACK);

            primaryStage.setTitle("TexasHold'em");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}