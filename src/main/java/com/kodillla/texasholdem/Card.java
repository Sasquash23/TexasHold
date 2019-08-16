package com.kodillla.texasholdem;

import javafx.scene.image.Image;

import java.util.Objects;

public class Card {
    private int cardValue;
    private String cardName;
    final private Image cardImage;
    final private Image cardImageBack = new Image("cards/red_back.png");
    final private int cardCode; // 1 - 52
    private String suite;       // spade, club, heart, or diamond
    private boolean face;
    private int cardToShow;

    //public Card(final Image cardImage, int tempValue,  final int cardCode, final String suite, boolean face) {
    public Card(final Image cardImage, final int cardCode, final String suite, boolean face) {
        this.cardImage = cardImage;
        findValue(cardCode);
        this.cardCode = cardCode;
        this.suite = suite;
        this.face = face;
    }

    public void setCardValue(final int cardValue) {
        this.cardValue = cardValue;
    }

    public void setCardName(final String cardName) {
        this.cardName = cardName;
    }

    public void faceDown() {
        this.face = false;
        showCard();
    }

    public void faceUp() {
        this.face = true;
        showCard();
    }

    public void findValue(int cardCode) {
        //int tempValue = cardCode % 13;

        switch (cardCode % 13)
        {
            case 1:
                setCardName("Ace");
                setCardValue(14);
                break;
            case 2:
                setCardName("Two");
                setCardValue(2);
                break;
            case 3:
                setCardName("Three");
                setCardValue(3);
                break;
            case 4:
                setCardName("Four");
                setCardValue(4);
                break;
            case 5:
                setCardName("Five");
                setCardValue(5);
                break;
            case 6:
                setCardName("Six");
                setCardValue(6);
                break;
            case 7:
                setCardName("Seven");
                setCardValue(7);
                break;
            case 8:
                setCardName("Eight");
                setCardValue(8);
                break;
            case 9:
                setCardName("Nine");
                setCardValue(9);
                break;
            case 10:
                setCardName("Ten");
                setCardValue(10);
                break;
            case 11:
                setCardName("Jack");
                setCardValue(11);
                break;
            case 12:
                setCardName("Queen");
                setCardValue(12);
                break;
            case 0:
                setCardName("King");
                setCardValue(13);
                break;
            default:
                break;
        }
        //System.out.println("tempValue haha: " + tempValue + " " + cardValue);
    }

    public Image showCard(){
        if ( this.face == true ){
            return this.cardImage;
        } else
            return this.cardImageBack;
    }

    public int getCardValue() {
        return cardValue;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardCode() {
        return cardCode;
    }

    public String getSuite() {
        return suite;
    }

    public int getCardToShow() {
        return cardToShow;
    }

    public Image getCardImage() {
        return cardImage;
    }

    public Image getCardImageBack() {
        return cardImageBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardCode == card.cardCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardCode);
    }

}
