package com.kodillla.texasholdem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TexasHoldem extends Application {

    //Gridpane variables
    private FlowPane dealerCardsF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane dealerCardsFF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane cardsF = new FlowPane(Orientation.HORIZONTAL);
    private FlowPane playerCardsF = new FlowPane(Orientation.HORIZONTAL);

    // Table variable
    private Image imageback = new Image("table.jpg");
    //private ImageView dealerCardTable;

    //Hand variable
    final private int handNumberCards = 7;
    //private Card [] dealerCardsC = new Card [handNumberCards];
    private List<Card> dealerCardsC = new ArrayList<Card>(handNumberCards);
    private List<Card> tableCardC = new ArrayList<Card>(handNumberCards -2);
    private List<Card> playerCardsC = new ArrayList<Card>(handNumberCards);

    private int saldoPlayer = 4000;
    private int playerBet = 0;
    private int saldoDealer = 4000;
    private int dealerBet = 0;
    private int totalBet = 0;
    private String check = "0";

    private NumberTextField firstValue;
    private Button btnBetting;
    private Button btnRise;


    public static void main(String[] args) {
        launch(args);
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

            Text saldoDealerText = new Text();
            saldoDealerText.setText("Current dealer saldo: " + saldoDealer + " PLN");
            saldoDealerText.setFill(Color.WHITE);

            Text saldoPlayerText = new Text();
            saldoPlayerText.setText("Current player saldo: " + saldoPlayer + " PLN");
            saldoPlayerText.setFill(Color.WHITE);

            grid.add(saldoDealerText, 1, 2, 1,1);
            grid.add(saldoPlayerText, 1, 5, 1,1);

            Text playerInfoText = new Text();
            playerInfoText.setText("Start with big blind bet, dealer will do small blind bet");
            playerInfoText.setFill(Color.WHITE);

            grid.add(playerInfoText, 3, 6, 1,1);

            Text outputTextArea = new Text();
            //Text outputTextArea2 = new Text();


            btnBetting = new Button("Bet");
            btnBetting.setVisible(false);
            btnRise = new Button("Rise");
            btnRise.setVisible(false);
            Button btnCall = new Button("Call");
            btnCall.setVisible(false);
            Button btnNew = new Button("New");
            btnNew.setVisible(false);

            EventHandler<ActionEvent> eventStartBetting = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                        dealerBet = 1 + (int) (Math.random() * (((40*saldoDealer/saldoDealer)- 1) + 1));
                        totalBet = playerBet + dealerBet;

                        outputTextArea.setText("Current bet: " + totalBet);
                        saldoPlayer = saldoPlayer - playerBet;
                        saldoDealer = saldoDealer - dealerBet;

                        saldoDealerText.setText("Current player saldo: " + saldoDealer + " PLN");
                        saldoPlayerText.setText("Current player saldo: " + saldoPlayer + " PLN");
                        playerInfoText.setText("You can rise bet or call");

                        btnBetting.setVisible(false);
                        btnRise.setVisible(true);
                        btnCall.setVisible(true);
                }
            };

            EventHandler<ActionEvent> eventRise = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    dealerBet = 1 + (int) (Math.random() * ((saldoDealer - 1) + 1));
                    totalBet = totalBet + playerBet + dealerBet;

                    saldoPlayer = saldoPlayer - playerBet;
                    saldoDealer = saldoDealer - dealerBet;

                    if (saldoDealer < 0 || saldoPlayer < 0) {
                        btnRise.setVisible(false);
                        btnNew.setVisible(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        playerInfoText.setText("You cannot rise anymore, call the game or lower bet.");

                    } else {
                        outputTextArea.setText("Current bet: " + totalBet);


                        saldoDealerText.setText("Current player saldo: " + saldoDealer + " PLN");
                        saldoPlayerText.setText("Current player saldo: " + saldoPlayer + " PLN");

                        if (saldoPlayer <= 0) {
                            playerInfoText.setText("You cannot rise anymore, call the game.");
                            btnRise.setVisible(false);
                        } else {
                            playerInfoText.setText("You can rise bet or call.");
                        }
                    }
                }
            };

            Text playerSocer = new Text();
            playerSocer.setText("Player score:     " +
                    "\nHand-ranking:               " +
                    "\nDelare score:     " +
                    "\nHand-ranking:               ");
            playerSocer.setVisible(false);
            EventHandler<ActionEvent> eventCall = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {

                    grid.getChildren().remove(dealerCardsFF);
                    grid.add(dealerCardsF, 5, 2, 2, 1);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    HandEvaluation handEvaluation = new HandEvaluation(playerCardsC, tableCardC);
                    HandEvaluation handEvaluation2 = new HandEvaluation(dealerCardsC, tableCardC);

                    if (handEvaluation.equals(handEvaluation2)) {
                        alert.setTitle("Winner is ... ");
                        alert.setHeaderText(null);
                        alert.setGraphic(new ImageView("tie.png"));
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("tie.png"));
                        alert.setContentText("It is a tie." + "\nYour score: " + handEvaluation.getHandScoer() +
                                "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                                "\nDelare score: " + handEvaluation2.getHandScoer() +
                                "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());
                        saldoPlayer = saldoPlayer + totalBet/2;
                        saldoDealer = saldoDealer + totalBet/2;
                        totalBet = 0;
                        saldoPlayerText.setText("Current player saldo: " + saldoPlayer + " PLN");
                        saldoDealerText.setText("Current player saldo: " + saldoDealer + " PLN");
                        outputTextArea.setText("Current bet: " + totalBet);
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
                        saldoPlayer = saldoPlayer + totalBet;
                        totalBet = 0;
                        saldoPlayerText.setText("Current player saldo: " + saldoPlayer + " PLN");
                        outputTextArea.setText("Current bet: " + totalBet);
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
                        saldoDealer = saldoDealer + totalBet;
                        totalBet = 0;
                        saldoDealerText.setText("Current player saldo: " + saldoDealer + " PLN");
                        outputTextArea.setText("Current bet: " + totalBet);
                    }

                    alert.showAndWait();
                    //Optional<Pair<String, String>> result = dialog.showAndWait();
                    playerSocer.setText("Player score: " + handEvaluation.getHandScoer() +
                            "\nHand-ranking: " + handEvaluation.getHandEvaluationType() +
                            "\nDelare score: " + handEvaluation2.getHandScoer() +
                            "\nHand-ranking: " + handEvaluation2.getHandEvaluationType());
                    playerSocer.setVisible(true);

                    btnRise.setVisible(false);

                    if (saldoDealer == 0 || saldoPlayer == 0) {
                        btnNew.setDisable(false);
                        btnCall.setVisible(false);
                    }
                    else {
                        btnCall.setVisible(false);
                        btnNew.setVisible(true);
                    }
                }
            };

            EventHandler<ActionEvent> eventNew = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    if (saldoDealer <= 0 || saldoPlayer <= 0) {
                        btnRise.setVisible(false);
                        btnNew.setVisible(false);
                        btnCall.setVisible(false);
                        btnBetting.setVisible(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        if (saldoPlayer > saldoDealer) {
                            alert.setTitle("Winner is ... ");
                            alert.setHeaderText(null);
                            alert.setGraphic(new ImageView("winner.png"));
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image("winner.png"));
                            alert.setContentText("Congratulation you won whole game!!!!" + "\nYour saldo: " + saldoPlayer);
                            saldoPlayer = saldoPlayer + totalBet;
                        } else {
                            alert.setTitle("Winner is ... ");
                            alert.setHeaderText(null);
                            alert.setGraphic(new ImageView("loser2.png"));
                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image("loser2.png"));
                            alert.setContentText("Oh no!!!! You have lost whole game :(" + "\nYour score: " + saldoPlayer);
                        }

                    } else {
                        grid.getChildren().remove(dealerCardsF);
                        grid.getChildren().remove(cardsF);
                        grid.getChildren().remove(playerCardsF);
                        outputTextArea.setText("");

                        Deck second = new Deck();
                        tableCardC.clear();
                        cardsF.getChildren().clear();
                        dealerCardsC.clear();
                        dealerCardsF.getChildren().clear();
                        playerCardsC.clear();
                        playerCardsF.getChildren().clear();
                        for (int i = 0; i < 5; i++) {
                            tableCardC.add(firstPack.getNewCard());
                            cardsF.getChildren().add(new ImageView(tableCardC.get(i).showCard()));
                        }

                        for (int i = 0; i < 2; i++) {
                            dealerCardsC.add(firstPack.getNewCard());
                            dealerCardsF.getChildren().add(new ImageView(dealerCardsC.get(i).showCard()));
                        }

                        for (int i = 0; i < 2; i++) {
                            playerCardsC.add(firstPack.getNewCard());
                            playerCardsF.getChildren().add(new ImageView(playerCardsC.get(i).showCard()));
                        }

                        grid.add(dealerCardsFF, 5, 2, 2, 1);
                        grid.add(cardsF, 3, 3, 5, 1);
                        grid.add(playerCardsF, 5, 4, 5, 1);

                        playerSocer.setVisible(false);
                        btnNew.setVisible(false);
                        btnRise.setVisible(false);
                        btnBetting.setVisible(true);
                    }
                }
            };

            btnBetting.setOnAction(eventStartBetting);
            btnRise.setOnAction(eventRise);
            btnCall.setOnAction(eventCall);
            btnNew.setOnAction(eventNew);

            grid.add(btnBetting, 10,3, 1, 1);
            grid.add(btnRise, 10,3, 1, 1);
            grid.add(btnCall, 10,4, 1, 1);
            grid.add(btnNew, 10,4, 1, 1);

            // Handle TextField text changes.
            firstValue = new NumberTextField();
            firstValue.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {

                    handleInput(newValue);
                    playerBet = 0;
                    check = "1";
                    if (!newValue.matches("")) {
                        int value = Integer.valueOf(newValue);
                        if ( value > 0 && value < saldoPlayer) {
                            check = "0";
                            playerBet = value;
                            //outputTextArea2.setText(" " + playerBet + " " + check);
                            if (totalBet > 0) {
                                btnRise.setVisible(true);
                            } else {
                                btnBetting.setVisible(true);
                            }
                        }
                        else {
                            check = "1";
                            //outputTextArea2.setText("You want to bet more than you have on the account!!!");
                            playerBet = 0;
                            btnBetting.setVisible(false);
                        }
                    }
                    else {
                        handleInput(newValue);
                    }
                }
            });


            outputTextArea.setFill(Color.WHITE);
            //outputTextArea2.setFill(Color.WHITE);
            grid.add(firstValue, 7, 6, 1,1);
            grid.add(outputTextArea, 6, 7, 1,1);
            //grid.add(outputTextArea2, 5, 7, 1,1);

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

    public class NumberTextField extends TextField
    {

        @Override
        public void replaceText(int start, int end, String text)
        {
            if (validate(text))
            {
                super.replaceText(start, end, text);
            }
        }

        @Override
        public void replaceSelection(String text)
        {
            if (validate(text))
            {
                super.replaceSelection(text);
            }
        }

        private boolean validate(String text)
        {
            return text.matches("[0-9]*");
        }
    }

    private void handleInput(String s){
        Matcher matcher = Pattern
                .compile("1")
                .matcher(check);

        if (matcher.find()) {
            Platform.runLater(() -> {
                firstValue.clear();
                check = "0";
                playerBet = 0;
                btnBetting.setVisible(false);
                btnRise.setVisible(false);
            });
        }
    }
}