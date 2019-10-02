/*
BlackJack program
Created: 26/05/2019
Author: Borislav Kolev
*/

package BlackJack;

import java.util.Scanner;

public class BlackJack {
    public static void main(String[] args) throws Exception{

        System.out.println("Welcome to BlackJack");
        Thread.sleep(1000);

        // The deck from which we are drawing
        Deck playingDeck = new Deck();
        playingDeck.createDeck();
        playingDeck.shuffle();

        // The player and dealer hands
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        // Starting money
        int playerMoney = 500;

        /*

        This code removes all the cards who are valued below 10 ( Every card except ACE, JACK, KNIGHT, QUEEN )
        It is useful if you want to check how the code containing the insurance part is working
        Instead of waiting for the dealer to withdraw an ace as a first hand ( since you get an insurance only THEN )

        for(int i = 0; i < playingDeck.getDeckSize(); i++){
            if(playingDeck.getCard(i).numberValue() < 10){
                playingDeck.removeCard(i);
                i--;
            }
        }

        */
        Scanner usrInput = new Scanner(System.in);

        // #### GAME WHILE LOOP ####
        while(playerMoney > 0 && playerMoney < 1000) {

            // used later for insurance
            boolean insured = false;

            // User will be able to double only the first hand
            boolean doubled = false;

            // The initial insurance variable will always be 0
            int playerInsuranceBet = 0;

            System.out.println("You have $" + playerMoney + " how much would you like to bet?");

            // Make sure the user enters an integer
            while(!usrInput.hasNextInt()){
                System.out.println("\nPlease enter an integer:: ");
                usrInput.next();
            }

            int playerBet = usrInput.nextInt();

            // if the user bet more than you have
            if(playerBet > playerMoney){
                System.out.println("You cannot bet more than you have. Please try again\n");
                continue;
            }

            System.out.println("\nBetting.. \n");
            Thread.sleep(1000);

            // Start Dealing
            // Player draws two cards
            playerHand.drawCard(playingDeck);
            playerHand.drawCard(playingDeck);

            // Dealer draws two cards
            dealerHand.drawCard(playingDeck);
            dealerHand.drawCard(playingDeck);

            // #### PLAYER ACTION WHILE LOOP ####
            // Player decides whether to hit, stand, double or insure himself ( if the dealer has ace as a first card )
            while (true) {

                // Displaying player cards
                System.out.println("Your hand:: ");
                Thread.sleep(1000);
                System.out.print(playerHand.toString());
                Thread.sleep(1000);
                System.out.println("Your hand is valued at:: " + playerHand.cardValues() + "\n");
                Thread.sleep(2000);

                // Displaying dealer first card
                System.out.println("Dealer hand:: ");
                Thread.sleep(1000);
                System.out.println(dealerHand.getCard(0).toString() + "\n" + "[HIDDEN]");
                Thread.sleep(1000);

                // Getting the value of the first card of the dealer's hand
                Card f = dealerHand.getCard(0);
                System.out.println("Dealer hand is valued at:: " + f.numberValue() + "\n");
                Thread.sleep(1000);

                // if you have simultaneous 21
                if(playerHand.cardValues() == 21){
                    System.out.println("BlackJack!");

                    // it will break from this while loop but the dealer will still try to draw 21
                    break;
                }

                // CHECKING FOR FIRST HAND ACE AND ENTERING THE INSURANCE AVAILABILITY
                if(f.numberValue() == 11 && !insured){

                    // This if statement will not enter anymore
                    // since you have the right to insure only in the first hand
                    insured = true;

                    System.out.println("Dealer first hand is ace do you want an insurance ? Y/N ");
                    String playerAnswer = usrInput.next();

                    // Make sure the user enters Y/N
                    while(true) {

                        if (playerAnswer.toUpperCase().equals("N")) {
                            System.out.println("\nOkay you do not have an insurance");
                            break;
                        }

                        else if (playerAnswer.toUpperCase().equals("Y")) {

                            System.out.println("\nHow much:: ");

                            // Make sure user enters an integer
                            while (!usrInput.hasNextInt()) {
                                System.out.println("\nPlease enter an integer:: ");
                                usrInput.next();
                            }

                            // THE USER CURRENT MONEY BEFORE WINNING OR LOSING THE BET
                            // SINCE THE PLAYER MONEY ARE STILL ON THE TABLE
                            int userCurrentMoney = playerMoney - playerBet;

                            playerInsuranceBet = usrInput.nextInt();

                            // if the user tries to insure more than you have
                            if(playerInsuranceBet > userCurrentMoney){
                                System.out.println("You cannot insurance more than you have. Please try again\n");
                                continue;
                            }

                            System.out.println("\nYou now have $" + playerInsuranceBet + " insurance");

                            break;

                        }

                        else {
                            // The while loop will repeat if it enters the else statement
                            System.out.println("\nThe input " + playerAnswer + " is wrong");
                            Thread.sleep(1500);
                            System.out.println("Please enter Y/N ");
                            playerAnswer = usrInput.next();
                        }
                    }
                }

                if(!doubled){
                    System.out.println("What would you like to do (1) bet, (2) Stand or (3) double ? ");
                }

                else {
                    System.out.println("What would you like to do (1) bet, (2) Stand ?");
                }

                // Make sure user enters an integer
                while(!usrInput.hasNextInt()){
                    System.out.println();
                    System.out.println("Please enter an integer:: ");
                    usrInput.next();
                }

                int response = usrInput.nextInt();

                // Player hit
                if(response == 1){

                    // Now the user will not be able to double after the first hand
                    doubled = true;

                    System.out.println("\nDrawing..");
                    Thread.sleep(1000);
                    playerHand.drawCard(playingDeck);
                    System.out.println("You draw a:: " + playerHand.getCard(playerHand.getDeckSize() - 1) + "\n");

                    // If he has more than 21 round is over the code below the while true loop will not continue also
                    if(playerHand.cardValues() > 21){

                        Thread.sleep(1000);
                        System.out.println("Bust. Currently valued at:: "  + playerHand.cardValues() + "\n");
                        // We are taking the player money later after the while true loop
                        break;
                    }

                    continue;
                }

                // If player stand, it quits the while true loop
                // it will not continue the rest of the while true loop
                if(response == 2){
                    break;
                }

                // If the user wants to double ( he can do it ONLY the first time in the while true loop)
                if(!doubled && response == 3){


                    doubled = true;
                    playerBet *= 2;

                    System.out.println("\nYour bet is now:: $" + playerBet + "\n");
                    Thread.sleep(1000);

                    // When you double that means that you automatically withdraw one card
                    System.out.println("\nDrawing..");
                    Thread.sleep(1000);
                    playerHand.drawCard(playingDeck);
                    System.out.println("You draw a:: " + playerHand.getCard(playerHand.getDeckSize() - 1) + "\n");

                    // We have to check for more than 21 again
                    // If he has more than 21 round is over the code below the while true loop will not continue also
                    if(playerHand.cardValues() > 21){

                        Thread.sleep(1000);
                        System.out.println("Bust. Currently valued at:: "  + playerHand.cardValues() + "\n");
                        // We are taking the player money later after the while true loop
                        break;
                    }

                    continue;

                }

                // this message will be entered only::
                // IF the input in the first iteration of the while true loop is different from 1,2,3
                // IF the next inputs after the first iterations are different from 1,2
                System.out.println("\nWrong input:: " + response + "\n");

            }

            // #### HERE THE PLAYER HAS ALREADY MADE HIS CHOICES ####
            // #### THE WHILE TRUE LOOP IS OVER ####
            // #### BACK TO THE GAME LOOP ####

            // We get the value of the SECOND card in case the player has insured himself
            // And we have to check weather the insurance worth it or not
            Card secondCard = dealerHand.getCard(1);

            // IF THE PLAYER HAS ALREADY LOST WITHOUT THE DEALERS HELP I.E. (HAS MORE THAN 21)
            // IT WILL NOT EXECUTE THE CODE BELOW THIS IF STATEMENT
            if(playerHand.cardValues() > 21){

                if(secondCard.numberValue() == 10 && insured){
                    System.out.println("Dealer second card was:: " + secondCard.toString());
                    System.out.println("Insurance won \n");
                    playerInsuranceBet *= 2;
                    playerMoney += playerInsuranceBet;

                }

                if(secondCard.numberValue() != 10 && insured){
                    System.out.println("Dealer second card was:: " + secondCard.toString() );
                    System.out.println("Insurance lost \n");
                    playerMoney -= playerInsuranceBet;
                }

                playerMoney -= playerBet;

                // putting the hands of the dealer and the player back to the playingDeck
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            // IT IS THE DEALER TURN
            // Shows the dealer cards
            Thread.sleep(1000);
            System.out.println("\nDealer hand::" );
            Thread.sleep(1000);
            System.out.println(dealerHand.toString());
            Thread.sleep(1000);
            System.out.println("Dealer's hand is valued at:: " + dealerHand.cardValues());

            if(secondCard.numberValue() == 10 && insured){
                System.out.println("Dealer second card was:: " + secondCard.toString());
                System.out.println("Insurance won\n");
                playerMoney += playerInsuranceBet;
            }

            if(secondCard.numberValue() != 10 && insured){
                System.out.println("Dealer second card was:: " + secondCard.toString() );
                System.out.println("Insurance lost\n");
                playerMoney -= playerInsuranceBet;
            }

            // If dealer beats you without drawing cards
            if((dealerHand.cardValues() > playerHand.cardValues()) && dealerHand.cardValues() > 17){
                Thread.sleep(2000);
                System.out.println("Dealer beats you\n");
                playerMoney -= playerBet;
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            // Dealer draws while value is less than 17
            while(dealerHand.cardValues() < 17 ){
                dealerHand.drawCard(playingDeck);
                System.out.println("\nDealer drawing..");
                Thread.sleep(1000);
                System.out.println("Dealer draws:: " + dealerHand.getCard(dealerHand.getDeckSize()-1).toString());
                System.out.println("Dealer's hand is valued at:: " + dealerHand.cardValues());
            }

            // If dealer busted
            if((dealerHand.cardValues() > 21 ) ){

                Thread.sleep(1000);
                System.out.println("Dealer busts! You win\n");
                playerMoney += playerBet;
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            // If you and dealer have the same value
            if((playerHand.cardValues() == dealerHand.cardValues())){
                Thread.sleep(1000);
                System.out.println("Push\n");
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            // If you have more than the dealer
            if((playerHand.cardValues() >  dealerHand.cardValues())){
                Thread.sleep(1000);
                System.out.println("You win the hand\n");
                playerMoney += playerBet;
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            if((playerHand.cardValues() < dealerHand.cardValues())){
                Thread.sleep(1000);
                System.out.println("Dealer beats you\n");
                playerMoney -= playerBet;
                playerHand.movingCardBackToTheDeck(playingDeck);
                dealerHand.movingCardBackToTheDeck(playingDeck);
                continue;
            }

            // putting the hands of the dealer and the player back to the playingDeck
            playerHand.movingCardBackToTheDeck(playingDeck);
            dealerHand.movingCardBackToTheDeck(playingDeck);
        }

        if(playerMoney < 0) {
            System.out.println("House wins");
        }

        else{
            System.out.println("Congratulations");
            Thread.sleep(2500);
            System.out.println("You broke the casino please leave you are not welcomed here anymore");
        }
    }
}
