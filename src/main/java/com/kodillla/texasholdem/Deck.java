package com.kodillla.texasholdem;

import java.util.Collections;
import java.util.Stack;

public class Deck extends Stack<Card> {

    public Deck()
    {
        super();
        this.addAll(new CardPack());
        shuffle();
    }

    public void shuffle()
    {
        Collections.shuffle(this);
    }

    public Card getNewCard()
    {
        if (this.empty())
        {
            System.out.println("Run out of cards. New Deck.");
            this.addAll(new CardPack());
            shuffle();
        }
        return this.pop();
    }

    public Card getNewCardDealer()
    {
        if (this.empty())
        {
            System.out.println("Run out of cards. New Deck.");
            this.addAll(new CardPack());
            shuffle();
        }
        Card dealerCard = this.pop();
        dealerCard.faceDown();
        return dealerCard ;
    }

}
