/*
Deck class
Created: 20/05/2019
Author: Borislav Kolev
*/

package BlackJack;
import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();

    // filling the deck with cards i.e. creating the deck
    void createDeck() {
        // get the values of each suit
        for (Card.Suit suit : Card.Suit.values()) {
            // get each value for each suit
            for (Card.Values value : Card.Values.values()) {
                // Create the card using the class card and add each to the cards ArrayList
                deck.add(new Card(value, suit));
            }
        }
    }

    void shuffle(){
        ArrayList<Card> tempDeck = new ArrayList<>();
        int deckSize = deck.size();
        for(int i = 0; i < deckSize; i++){
            int rand = (int) (Math.random()* deck.size());
            tempDeck.add(this.deck.get(rand));
            this.deck.remove(rand);
        }
        deck = tempDeck;
    }

    Card getCard(int i){
        return deck.get(i);
    }

    private void removeCard(int card){
        deck.remove(card);
    }

    void drawCard(Deck playingDeck){
        this.deck.add(playingDeck.getCard(0));
        playingDeck.removeCard(0);
    }

    int getDeckSize()
    {
        return this.deck.size();
    }

    int cardValues(){
        int totalValue = 0;
        int aces = 0;

        for(Card card: this.deck){
            switch(card.getValue()){
                case TWO: totalValue += 2; break;
                case THREE: totalValue += 3; break;
                case FOUR: totalValue += 4; break;
                case FIVE: totalValue += 5; break;
                case SIX: totalValue += 6; break;
                case SEVEN: totalValue += 7; break;
                case EIGHT: totalValue += 8; break;
                case NINE: totalValue += 9; break;
                case TEN:
                case JACK:
                case KING:
                case QUEEN:
                    totalValue += 10; break;
                case ACE: aces += 1; break;
            }
        }
        for(int i = 0; i < aces; i++){
            if(totalValue > 10){
                totalValue += 1;
            }
            else{
                totalValue += 11;
            }
        }
        return totalValue;
    }

    void movingCardBackToTheDeck(Deck d){
        while(!this.deck.isEmpty()){
            d.deck.add(this.getCard(0));
            this.removeCard(0);
        }
    }
    public String toString() {
        StringBuilder cardListOutput = new StringBuilder();
        for (Card aCard : this.deck) {
            cardListOutput.append(aCard).append("\n");
        }
        return cardListOutput.toString();
    }
}