package com.kodillla.texasholdem;

import javafx.scene.image.Image;

import java.util.Stack;

public class CardPack extends Stack<Card>
{
    public static final int cardInPack = 52;

    public CardPack()
    {
        super();
        final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int cardCode = 1;

        for (String suit : suits)
        {
            for (int i = 1; i < 14; i++)
            {
                // read card images
                String cardName = "cards/" + (cardCode) + ".png";
                this.push(new Card(new Image(cardName), cardCode, suit, true));
                cardCode ++;
            }
        }
    }
}