package com.kodillla.texasholdem;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import org.junit.Assert;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HandEvalutionTestSuite {
    final private int handNumberCards = 7;
    private List<Card> testCard = new ArrayList<Card>(handNumberCards-2);
    private List<Card> testCard1 = new ArrayList<Card>(2);
    private JFXPanel jfxPanel;

    @Before
    public void before(){
        System.out.println("Test Case: begin");
    }
    @After
    public void after(){
        System.out.println("Test Case: end");
    }

    @Test
    public void testIsRoyalFlush (){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/13.png"), 13, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard.add(new Card(new Image("cards/11.png"), 11, "Hearts", true));
        testCard1.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("RoyalFlush", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsStraitFlush(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(null, 9, "Hearts", true));
        testCard.add(new Card(null, 13, "Hearts", true));
        testCard.add(new Card(null, 12, "Hearts", true));
        testCard.add(new Card(null, 11, "Hearts", true));
        testCard.add(new Card(null, 10, "Hearts", true));
        testCard1.add(new Card(null, 14, "Diamonds", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("StraightFlush", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsFourOfAKind(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/14.png"), 14, "Diamonds", true));
        testCard.add(new Card(new Image("cards/27.png"), 27, "Clubs", true));
        testCard.add(new Card(new Image("cards/40.png"), 40, "Spades", true));
        testCard1.add(new Card(new Image("cards/13.png"), 13, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("FourOfAKind", handEvaluation.getHandEvaluationType());
    }


    @Test
    public void testIsFullHouse(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/14.png"), 14, "Diamonds", true));
        testCard.add(new Card(new Image("cards/27.png"), 27, "Clubs", true));
        testCard.add(new Card(new Image("cards/52.png"), 52, "Spades", true));
        testCard1.add(new Card(new Image("cards/13.png"), 13, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("FullHouse", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsFullHouse2x3Cards(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 2, "Hearts", true));
        testCard.add(new Card(new Image("cards/14.png"), 15, "Diamonds", true));
        testCard.add(new Card(new Image("cards/27.png"), 28, "Clubs", true));
        testCard.add(new Card(new Image("cards/52.png"), 52, "Spades", true));
        testCard.add(new Card(new Image("cards/13.png"), 13, "Hearts", true));
        testCard1.add(new Card(new Image("cards/26.png"), 26, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("FullHouse", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsFlush(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/5.png"), 5, "Hearts", true));
        testCard.add(new Card(new Image("cards/52.png"), 52, "Spades", true));
        testCard.add(new Card(new Image("cards/2.png"), 2, "Hearts", true));
        testCard.add(new Card(new Image("cards/25.png"), 26, "Clubs", true));
        testCard1.add(new Card(new Image("cards/4.png"), 4, "Hearts", true));
        testCard1.add(new Card(new Image("cards/9.png"), 9, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("Flush", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsFlushGetSocer(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/5.png"), 5, "Hearts", true));
        testCard.add(new Card(new Image("cards/3.png"), 3, "Hearts", true));
        testCard.add(new Card(new Image("cards/2.png"), 2, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard1.add(new Card(new Image("cards/13.png"), 13, "Hearts", true));
        testCard1.add(new Card(new Image("cards/9.png"), 9, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("Flush", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsFlushFail(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/5.png"), 5, "Hearts", true));
        testCard.add(new Card(new Image("cards/41.png"), 41, "Spades", true));
        testCard.add(new Card(new Image("cards/2.png"), 2, "Hearts", true));
        testCard1.add(new Card(new Image("cards/25.png"), 26, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertNotEquals("Flush", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsStraight(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard.add(new Card(new Image("cards/24.png"), 24, "Spades", true));
        testCard.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));
        testCard1.add(new Card(new Image("cards/26.png"), 26, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("Straight", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsThreeOfAKind(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard.add(new Card(new Image("cards/25.png"), 25, "Spades", true));
        testCard.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));
        testCard1.add(new Card(new Image("cards/38.png"), 38, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("ThreeOfAKind", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsTwoPairs(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard.add(new Card(new Image("cards/25.png"), 25, "Spades", true));
        testCard.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));
        testCard1.add(new Card(new Image("cards/36.png"), 36, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("TwoPairs", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testIsOnePairs(){

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/12.png"), 12, "Hearts", true));
        testCard.add(new Card(new Image("cards/25.png"), 25, "Spades", true));
        testCard.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));
        testCard.add(new Card(new Image("cards/5.png"), 5, "Hearts", true));
        testCard1.add(new Card(new Image("cards/33.png"), 33, "Clubs", true));
        testCard1.add(new Card(new Image("cards/34.png"), 34, "Clubs", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertEquals("OnePair", handEvaluation.getHandEvaluationType());
    }

    @Test
    public void testEvaluteHighCard() {

        //Given
        jfxPanel = new JFXPanel();
        testCard.add(new Card(new Image("cards/1.png"), 1, "Hearts", true));
        testCard.add(new Card(new Image("cards/33.png"), 33, "Clubs", true));
        testCard.add(new Card(new Image("cards/24.png"), 24, "Spades", true));
        testCard.add(new Card(new Image("cards/10.png"), 10, "Hearts", true));
        testCard.add(new Card(new Image("cards/5.png"), 5, "Hearts", true));
        testCard1.add(new Card(new Image("cards/22.png"), 22, "Spades", true));
        testCard1.add(new Card(new Image("cards/25.png"), 25, "Hearts", true));

        //When
        HandEvaluation handEvaluation = new HandEvaluation(testCard, testCard1);
        //Then
        Assert.assertNotEquals("OnePair", handEvaluation.getHandEvaluationType());
    }
}
