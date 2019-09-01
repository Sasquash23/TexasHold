package com.kodillla.texasholdem;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HandEvaluation {
    private List<Card> cards  = new ArrayList<Card>();
    private String handEvaluationType;
    private int cardsSocer = 0;
    private int handSocer = 0;
    private Map<Integer, Integer> mCardsCode;
    private Map<Integer, Integer> mCardsValue;
    private Map<Integer, Integer> mCardsValueD;
    private Map<String, Integer> mSuite;
    private final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};


    public HandEvaluation(List<Card> cards1, List<Card> cards2) {
        cards.addAll(cards1);
        cards.addAll(cards2);
        cards.sort(Comparator.comparing(Card::getCardValue).reversed());

        boolean isHandCategory = (
                isStraitFlush()    ||
                        isFourOfAKind()   ||
                        isFullHouse()     ||
                        isFlush()         ||
                        isStraight()      ||
                        isThreeOfAKind()  ||
                        isTwoPairs()      ||
                        isOnePair());
        if (!isHandCategory) {
            evaluteHighCard();
        }
    }

    private boolean isStraitFlush(){
        //System.out.println("Checking StraitFlush");
        mCardsCode = cards.stream()
                .collect(Collectors.toMap(
                        Card::getCardCode,
                        Card::getCardValue,
                        (cardValue1, cardValue2) -> {
                            //System.out.println("duplicate key found!");
                            return cardValue1;
                        }
                ));
        int cardNumber;

        for (Card card : cards) {
            cardNumber = card.getCardCode();

            if (card.getCardName().equals("Ace")){
                if ( mCardsCode.keySet().contains(12 + cardNumber) && mCardsCode.keySet().contains(11 + cardNumber) &&
                        mCardsCode.keySet().contains(10 + cardNumber) && mCardsCode.keySet().contains(9 + cardNumber)){
                    //System.out.println("Checking RoyalFlush");
                    handSocer = 514;
                    //System.out.println("Win RoyalFlush " + card.getCardValue() + " Socer: " + handSocer);
                    handEvaluationType = "RoyalFlush";
                    cardsSocer = 5;
                    return true;
                }

            }else {
                if (mCardsCode.keySet().contains(cardNumber - 1) && mCardsCode.keySet().contains(cardNumber - 2) &&
                        mCardsCode.keySet().contains(cardNumber - 3) && mCardsCode.keySet().contains(cardNumber - 4)) {

                    handSocer = 500 + card.getCardValue();
                    //System.out.println("StraightFlush " + card.getCardValue() + " Socer: " + handSocer);
                    handEvaluationType = "StraightFlush";
                    cardsSocer = 5;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFourOfAKind(){
        //System.out.println("Checking FourOfAKind");
        mCardsValue = new HashMap<Integer, Integer>();
        mCardsValueD = new HashMap<Integer, Integer>();

        for (Card card : cards) {
            int count = mCardsValue.containsKey(card.getCardValue()) ? mCardsValue.get(card.getCardValue()) : 0;
            if (mCardsValue.containsKey(card.getCardValue())) {
                mCardsValue.replace(card.getCardValue(), count + 1);
            } else {
                mCardsValue.put(card.getCardValue(), 1);
            }
        }

        mCardsValueD.putAll(mCardsValue);

        if (mCardsValue.containsValue(4)) {
            Stream<Integer> keyM = keys(mCardsValue, 4);
            int max = keyM.findFirst().get();

            mCardsValueD.remove(max);
            handSocer = 450 + max;
            handEvaluationType = "FourOfAKind";
            //System.out.println("FourOfAKind " + max + " Socer: " + handSocer );

            cardsSocer = 4;
            evaluteHighCard();
            return true;
        }

        return false;
    }

    private boolean isFullHouse(){
        //System.out.println("Checking FullHouse");

        if (mCardsValue.containsValue(3)) {
            Stream<Integer> keyM = keys(mCardsValue, 3);
            Set<Integer> cardValueList = keyM.collect(Collectors.toSet());
            int max = Collections.max(cardValueList);

            handSocer = 300 + max;
            mCardsValueD.remove(max);

            if (mCardsValueD.containsValue(3)) {
                keyM = keys(mCardsValueD, 3);
                cardValueList = keyM.collect(Collectors.toSet());
                max = Collections.max(cardValueList);

                handSocer = handSocer + 100 + max;
                handEvaluationType = "FullHouse";
                //System.out.println("FullHouse " + max + " Socer: " + handSocer);
                cardsSocer = 5;
                return true;
            }

            if (mCardsValueD.containsValue(2)) {
                keyM = keys(mCardsValueD, 2);
                cardValueList = keyM.collect(Collectors.toSet());
                max = Collections.max(cardValueList);

                handSocer = handSocer + 100 + max;
                handEvaluationType = "FullHouse";
                //System.out.println(handEvaluationType + " " + max + " Socer: " + handSocer);
                cardsSocer = 5;
                return true;
            }
        }

        return false;
    }

    public <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    private boolean isFlush() {
        //System.out.println("Checking Flush");
        mSuite = new HashMap<String, Integer>();
        Integer count = 1;

        for (Card card : cards) {
            count = mSuite.get(card.getSuite());

            if (count == null) {
                count = 0;
            }

            if (count != 0 ) {
                mSuite.replace(card.getSuite(), count + 1);
            } else {
                mSuite.put(card.getSuite(), 1);
            }
        }

        count = 1;
        Map<String, Integer> mSuiteDuplicate = new HashMap<String, Integer>();
        mSuiteDuplicate.putAll(mSuite);
        if (mSuite.containsValue(5) || mSuite.containsValue(6) || mSuite.containsValue(7) ) {
            for (String suiteL : suits) {
                if ( mSuite.containsKey(suiteL) && mSuiteDuplicate.get(suiteL) < 5) {
                    mSuiteDuplicate.remove(suiteL);
                }
            }

            for (Card card : cards) {
                if (card.getSuite() == mSuiteDuplicate.keySet().toArray()[0] && count <= 5){
                    handSocer = handSocer + card.getCardValue();
                    count++;
                }
            }

            handSocer = handSocer + 325;
            handEvaluationType = "Flush";
            //System.out.println(handEvaluationType + " Socer: " + handSocer);
            cardsSocer = 5;
            return true;
        }

        return false;
    }

    private boolean isStraight() {
        //System.out.println("Checking Straight");

        if(mSuite.size() >=2){
            for (Card card : cards) {
                if (mCardsValue.keySet().contains(card.getCardValue() - 1) && mCardsValue.keySet().contains(card.getCardValue() - 2) &&
                        mCardsValue.keySet().contains(card.getCardValue() - 3) && mCardsValue.keySet().contains(card.getCardValue() - 4)) {
                    handSocer = 320 + card.getCardValue();
                    //System.out.println("Straight " + card.getCardValue() + " Socer: " + handSocer);
                    handEvaluationType = "Straight";
                    cardsSocer = 5;
                    return true;
                }
            }
        }

        handEvaluationType = "NULL";
        return false;
    }

    private boolean isThreeOfAKind() {
        //System.out.println("Checking ThreeOfAKind");

        if (mCardsValue.containsValue(3)) {
            Stream<Integer> keyM = keys(mCardsValue, 3);
            Set<Integer> cardValueList = keyM.collect(Collectors.toSet());
            int max = Collections.max(cardValueList);
            mCardsValueD.remove(max);

            handSocer = 300 + max;
            handEvaluationType = "ThreeOfAKind";
            //System.out.println(handEvaluationType + " " + max + " Socer: " + handSocer);

            cardsSocer = 3;
            evaluteHighCard();
            return true;
        }
        return false;
    }

    private boolean isTwoPairs() {
        //System.out.println("Checking TwoPair");
        if (mCardsValue.containsValue(2)) {
            Stream<Integer> keyM = keys(mCardsValue, 2);
            Set<Integer> cardValueList = keyM.collect(Collectors.toSet());
            int max = Collections.max(cardValueList);
            mCardsValueD.remove(max);
            handSocer = 100 + max;

            if (mCardsValueD.containsValue(2)) {
                keyM = keys(mCardsValueD, 2);
                cardValueList = keyM.collect(Collectors.toSet());
                max = Collections.max(cardValueList);

                handSocer = handSocer + 100 + max;
                handEvaluationType = "TwoPairs";
                //System.out.println(handEvaluationType + " " + max + " Socer: " + handSocer);

                cardsSocer = 4;
                evaluteHighCard();
                return true;
            }

        }
        return false;
    }

    private boolean isOnePair() {
        //System.out.println("Checking OnePair");
        if (mCardsValue.containsValue(2)) {
            Stream<Integer> keyM = keys(mCardsValue, 2);
            Set<Integer> cardValueList = keyM.collect(Collectors.toSet());
            int max = Collections.max(cardValueList);
            mCardsValueD.remove(max);
            handSocer = 100 + max;

            handEvaluationType = "OnePair";
            //System.out.println(handEvaluationType + " " + max + " Socer: " + handSocer);

            cardsSocer = 2;
            evaluteHighCard();
            return true;
        }
        return false;
    }

    private void evaluteHighCard() {
        int count;

        if (cardsSocer > 0){
            count = cardsSocer;

            while (count < 5){
                Stream<Integer> keyM = keys(mCardsValueD, 1);
                Set<Integer> cardValueList = keyM.collect(Collectors.toSet());
                int max = Collections.max(cardValueList);

                handSocer = handSocer + max;
                mCardsValueD.remove(max);
                count++;
            }
        } else {
            count = 1;
            handEvaluationType = "HighCard";

            for (Card card : cards) {
                if (count <= 5){
                    handSocer = handSocer + card.getCardValue();
                    count++;
                }
            }
        }

        //System.out.println(handEvaluationType + " Socer: " + handSocer );
    }

    public boolean compareTo(Object o) {
        HandEvaluation that = (HandEvaluation) o;
        if ( this.handSocer > that.handSocer) return true;
        else return false;
    }

    public String getHandEvaluationType() {
        return handEvaluationType;
    }

    public int getHandScoer() {
        return handSocer;
    }

    public void setHandSocer(int handSocer) {
        this.handSocer = handSocer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandEvaluation that = (HandEvaluation) o;
        return handSocer == that.handSocer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handSocer);
    }

    private void findDuplicates() {
        // To DO - not needed for now
    }
}
