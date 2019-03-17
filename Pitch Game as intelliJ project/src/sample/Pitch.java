package sample;

import java.util.ArrayList;
import static sample.Game.gameScreen;

public class Pitch implements DealerType{

    private final int winScore = 7; // score needed to win the game
    private int winner = 4; // winner's player number, 4 means no such player
    private PitchDealer theDealer;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Card> pile; // players throw cards on the pile
    private char trump;
    private char leadingSuit;
    private ArrayList<Integer> scores = new ArrayList<Integer>();
    private ArrayList<Integer> bids = new ArrayList<Integer>();
    private int winningBidder; // he has to make his bids
    private int openingPlayer; // throws first on the next play
    private int highBid; // winningBidder's bid
    private int numPlayers; // number of players in the game
    // players sorted in the order they move this turn
    private ArrayList<Player> orderedPlayers;

    Pitch(int numberOfPlayers) {

        numPlayers = numberOfPlayers;

        // Create the human player, AI players, dealer, and game screen
        players.add(new Player());
        for (int i=1; i<numPlayers; i++){
            players.add(new AIPlayer());
        }
        theDealer = createDealer();
        gameScreen = new GameScreen(numPlayers);

        // Fill score board with zeros
        players.forEach(i -> scores.add(0));

        // Everyone gets 6 cards
        dealHand();
    }

    void dealHand(){
        // Deal cards, shuffle if needed, show user's hand, ask for bids
        if (6 * numPlayers > theDealer.cardsLeft()) theDealer.shuffle();
        players.forEach(i -> i.setHand(theDealer.dealHand()));
        gameScreen.updateHand(players.get(0).getHand());
        pile = new ArrayList<Card>();
        highBid = 0; // no bids yet
        bids.clear();
        trump = 'N'; // no trump yet
        gameScreen.makeUserBid(); // get user's bid and call findWinningBidder()
    }

    // Gets AI to bid and picks the winning bidder
    // calls dealHand() if no winner
    void findWinningBidder() {

        // Get AI bids
        players.forEach(i -> i.bid());
        gameScreen.updateBids(bids);

        // Find winning bidder
        for (int i = 0; i < numPlayers; i++)
            if (bids.get(i) > highBid) {
                highBid = bids.get(i);
                winningBidder = i;
            }

        // If everyone passed then deal a hand again
        if (highBid == 0) {
            dealHand();
        }
        else {
            openingPlayer = winningBidder;
            makeOrderedList();
        }
    }

    // Make a list of players in correct order for this round
    // then make AIPlayers who go before user play their cards
    void makeOrderedList(){

        orderedPlayers = new ArrayList<Player>();

        for (int i=openingPlayer; i<numPlayers; i++)
            orderedPlayers.add(players.get(i));
        for (int i=0; i<openingPlayer; i++)
            orderedPlayers.add(players.get(i));

        leadingSuit = 'N'; // no leading suit yet

        // AI players whose turn is before user play their cards
        // then user plays
        for (int i=0; i<numPlayers; i++){
            orderedPlayers.get(i).pickCard();
            if (orderedPlayers.get(i) == players.get(0)) break;
        }
    }

    void userPlayedACard(){

        // find out who's next
        int next;
        for (next=0; next<numPlayers; next++)
            if (orderedPlayers.get(next) == players.get(0)) break;
        next++;

        // AI players whose turn is after the user play their cards now
        for (int i=next; i<numPlayers; i++)
            orderedPlayers.get(i).pickCard();

        // Find out who won the trick
        Player trickWinner = new Player();
        int highRank = 0;
        // find highest trump on pile
        for (int i=0; i<pile.size(); i++){
            if ((pile.get(i).suit == trump) && (pile.get(i).rank > highRank)) {
                trickWinner = orderedPlayers.get(i);
                highRank = pile.get(i).rank;
            }
        }
        // If no trump found then find highest leading suit
        if (highRank == 0)
            for (int i=0; i<pile.size(); i++){
                if ((pile.get(i).suit == leadingSuit) && (pile.get(i).rank > highRank)) {
                    trickWinner = orderedPlayers.get(i);
                    highRank = pile.get(i).rank;
                }
            }

        // trickWinner gets the pile as a trick
        for (int i=0; i<pile.size(); i++)
            trickWinner.tricks.add(pile.get(i));

        pile.clear();
        openingPlayer = players.indexOf(trickWinner);

        gameScreen.showPileOKButton();
    }

    void putOnPile(Card card){
        // If trump not set then this cards suit becomes trump
        if (trump == 'N') {
            trump = card.suit;
            gameScreen.updateTrump(card.suit);
        }
        if (leadingSuit == 'N')
            leadingSuit = card.suit;
        pile.add(card);
        gameScreen.updatePile(pile);
    }

    // counts score for the hand just played
    // checks for winner of each point, checks if bidder made the bid
    // credits points and checks if there is a winner
    void countScore(){
        ArrayList<Integer> newScore = new ArrayList<Integer>();

        // max and min get initialized to something any value beats
        int maxHigh = 0;
        int minLow = 15;
        int maxGame = 0;
        // player numbers for winners of high, low, jack, game, and smudge
        // initialized to 4, no such player
        int[] winners = new int[]{4, 4, 4, 4, 4};
        int biddersPoints = 0; // bidder needs to make his bid
        boolean bidderMadeIt = true; // true if bidder makes the bid

        // check each player for high, low, jack, and game
        // remember the rank and winning player number
        for (int i=0; i<numPlayers; i++) {

            int game = 0; // card values for this player

            // for each card in tricks check for high, low, and jack
            // and add up the card values
            for (Card card : players.get(i).tricks){
                game += getValue(card); // add the value of this card to players game point

                if (card.suit == trump){
                    // check for high and credit the player if so
                    if (card.rank > maxHigh) {
                        maxHigh = card.rank;
                        winners[0] = i;
                    }
                    // check for low and credit the player if so
                    if (card.rank < minLow){
                        minLow = card.rank;
                        winners[1] = i;
                    }
                    // check for jack and credit the player if so
                    if (card.rank == 11){
                        winners[2] = i;
                    }
                }
            }
            // if player has the highest trick value he gets the game point
            // if 2 or more players are tied no one gets the point
            if (game > maxGame){
                maxGame = game;
                winners[3] = i;
            }
            else if (game == maxGame){
                winners[3] = 4; // no such player
            }
        }

        // if the winning bidder bid smudge then check for smudge
        if (highBid == 5){
            // set smudge winner to be the bidder and see if he can defend this title
            winners[4] = winningBidder;
            // for high, low, jack, and game points
            // if different winner than the bidder then no smudge winner
            for (int i=0; i<4; i++) {
                if (winners[i] != winningBidder)
                    winners[4] = 4;
            }
            // if not all tricks collected then no smudge winner
            if (players.get(winningBidder).tricks.size() != 6 * numPlayers){
                winners[4] = 4;
            }
        }

        // check if bidder made the bid, set bidderMadeIt
        // and subtract points if he didn't
        for (int i=0; i<5; i++) { // out of 5 possible points how many are bidder's
            if (winners[i] == winningBidder)
                biddersPoints++;
        }
        if (biddersPoints < highBid){ // if less than bid then subtract points
            bidderMadeIt = false;
            scores.set(winningBidder, scores.get(winningBidder) - highBid);
        }

        // start adding players' scores, watch out for losing bidder,
        // stop counting as soon as there is a winner
        for (int i=0; i<5; i++) // for each possible point
            // if someone got this point and no one has won yet
            if ((winners[i] < numPlayers) && (winner == 4))
                    // if bidder made it or this is someone else's point
                    if (bidderMadeIt || (winners[i] != winningBidder)){
                        scores.set(winners[i], scores.get(winners[i]) + 1); // add the point
                        if (scores.get(winners[i]) >= winScore) // if that player just won
                        winner = winners[i]; // set winner to his player number
            }

        gameScreen.updateScore(scores);

        // if we have a winner
        if (winner != 4)
            gameScreen.displayWinner(winner);
    }

    // returns the strongest card on pile
    Card getCardToBeat() {
        // if pile empty return null
        if (pile.size() == 0)
            return null;

        // cardToBeat = first card on pile
        Card cardToBeat = pile.get(0);



        // Look for the highest trump
        // cardToBeat = the highest trump on pile
//        if (cardToBeat.suit == trump)
            for (int i = 0; i < pile.size(); i++)
                if ((pile.get(i).suit == trump) && (pile.get(i).rank > cardToBeat.rank))
                    cardToBeat = pile.get(i);

        // if cardToBeat is still not trump
        // then look for the highest with leading suit
        if (cardToBeat.suit != trump)
            for (int i=0; i<pile.size(); i++)
                if ((pile.get(i).suit == leadingSuit) && (pile.get(i).rank > cardToBeat.rank))
                    cardToBeat = pile.get(i);

        return cardToBeat;
    }

    // returns the value of the card used to count game point
    int getValue(Card card){
        if (card.rank > 10) return card.rank - 10;
        if (card.rank == 10) return 10;
        return 0;
    }

    public PitchDealer createDealer(){return new PitchDealer();}

    public void showScene(){gameScreen.showScene();}

    void makeBid(Integer bid){bids.add(bid);}

    int getNumPlayers(){return numPlayers;}

    char getTrump(){return trump;}

    char getLeadingSuit(){return leadingSuit;}
}

interface DealerType {
    public Dealer createDealer();
}