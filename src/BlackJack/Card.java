/*
Card class
Created: 18/05/2019
Author: Borislav Kolev
*/

package BlackJack;

// Class which is using Values and Suit enum classes to create a single card
public class Card {

    public enum Values{
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }
    public enum Suit{
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    // Using Values and Suit enum classes
    private Values value;
    private Suit suit;

    // Constructing a card
    Card(Values value, Suit suit){
        this.value = value;
        this.suit = suit;
    }

    // Getting the value of the card
    Values getValue(){
        return value;
    }

    // Getting the actual value of the card
    // This method is only used to output the value of the first card of the dealer
    int numberValue(){
        switch(Card.this.getValue()){
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
            case JACK:
            case KING:
            case QUEEN:
                return 10;
            case ACE:
                return 11;
        }
        return 0;
    }

    public String toString(){
        return value + " of " + suit;
    }

}
