package sample;

import java.util.ArrayList;
import static sample.Game.pitchTable;

public class AIPlayer extends Player {

    @Override
    // make a bid based on AI
    public void bid() {

        char[] suits = new char[]{'C', 'D', 'H', 'S'};
        int[] bids = new int[]{0, 0, 0, 0}; // possible bids for each suit
        int maxBid = 0; // bid of the most favorable suit

        // count points you can get for each suit
        for (int i=0; i<4; i++){
            if (haveHigh(suits[i])) bids[i]++;
            else break; // if no high card then don't bid
            if (haveJ(suits[i])) bids[i]++;
            if (haveLow(suits[i])) bids[i]++;
            if (haveGame(suits[i])) bids[i]++;
            if ((bids[i] == 4) && haveSuit(suits[i])) bids[i] = 5;
        }

        // count and bid based on the suit with most bids
        for (int i=0; i<4;i++)
            if (bids[i]>maxBid) maxBid = bids[i];
        pitchTable.makeBid(maxBid);
    }


    // True if finds a high card
    // Threshold depends on number of players
    boolean haveHigh(char suit){
        for (int i=0; i<6; i++)
            if ((hand.get(i).suit == suit) && (hand.get(i).rank > (6 + pitchTable.getNumPlayers())))
                return true;
        return false;
    }

    // true if finds J
    boolean haveJ(char suit){
        for (int i=0; i<6; i++)
            if ((hand.get(i).suit == suit) && (hand.get(i).rank == 11))
                return true;
        return false;
    }

    // True if finds low card
    // Threshold depends on number of players
    boolean haveLow(char suit){
        for (int i=0; i<6; i++)
            if ((hand.get(i).suit == suit) && (hand.get(i).rank < (10 - pitchTable.getNumPlayers())))
                return true;
        return false;
    }

    // true if finds at least 4 of the same suit
    boolean haveGame(char suit){
        int suitNum = 0; // number of cards that follow suit
        for (int i=0; i<6; i++)
            if (hand.get(i).suit == suit)
                suitNum++;
        return (suitNum > 3);
    }

    // true if all cards have the same suit
    boolean haveSuit(char suit){
        for (int i=0; i<6; i++)
            if (hand.get(i).suit != suit)
                return false;
        return true;
    }

    // picks a card to play

    @Override
    void pickCard() {

        boolean suitFound = false; // true if player has the suit
        Card cardToBeat; // strongest card on pile
        Card pickedCard = null; // card to throw
        ArrayList<Card> cardsToChooseFrom  = new ArrayList<Card>();
        char trump = pitchTable.getTrump();

        // find the strongest card on pile
        cardToBeat = pitchTable.getCardToBeat();

        // if pile is empty
        if (cardToBeat == null){
            // pick the strongest non trump card
            for (Card card : hand)
                if (card.suit != trump)
                    if ((pickedCard == null) || (pickedCard.rank < card.rank))
                        pickedCard = card;

            // if non trump card not found (means all cards are trump)
            // then pick the strongest card
            if (pickedCard == null)
                for (Card card : hand)
                    if ((pickedCard == null) || (pickedCard.rank < card.rank))
                        pickedCard = card;
        }
        else { // means leading suit is set already
            // check if player has suit
            for (Card card : hand) {
                if (card.suit == pitchTable.getLeadingSuit())
                    suitFound = true;
            }

            // if player does not have suit he can play any card
            if (!suitFound)
                cardsToChooseFrom = hand;
            else // if player has suit then cardsToChooseFrom = allowed cards
                for (Card card : hand)
                    if ((card.suit == pitchTable.getLeadingSuit()) ||
                            card.suit == pitchTable.getTrump())
                        cardsToChooseFrom.add(card);
        }

        // try to take the trick with the lowest winning card
        // if no winning card then throw the lowest you have

        // (trying to take the trick)
        // if card not picked yet
        // then find the lowest card that has the suit and is higher than cardToBeat
        if (pickedCard == null)
            for (Card card : cardsToChooseFrom)
                if ((cardToBeat.suit == card.suit) && (cardToBeat.rank < card.rank))
                    if ((pickedCard == null) || (pickedCard.rank > card.rank))
                        pickedCard = card;

        // (trying to take the trick)
        // if card not picked yet and cardToBeat is not trump
        // then find the lowest trump
        if ((pickedCard == null) && (cardToBeat.suit != trump))
            for (Card card : cardsToChooseFrom)
                if (card.suit == trump)
                    if ((pickedCard == null) || (pickedCard.rank > card.rank))
                        pickedCard = card;

        // (trying to throw the lowest)
        // if still no card picked (means player cannot take the trick)
        // then throw the lowest non trump
        if (pickedCard == null)
            for (Card card : cardsToChooseFrom)
                if (card.suit != trump)
                    if ((pickedCard == null) || (pickedCard.rank > card.rank))
                        pickedCard = card;

        // (trying to throw the lowest)
        // if still no card picked (means all allowed cards are trump)
        // then throw the lowest trump
        if (pickedCard == null)
            for (Card card : cardsToChooseFrom)
                if ((pickedCard == null) || (pickedCard.rank > card.rank))
                    pickedCard = card;

        if (pickedCard == null)
            System.out.println("Error: picked card still null");

        pitchTable.putOnPile(pickedCard);
        hand.remove(pickedCard);

    }
}
