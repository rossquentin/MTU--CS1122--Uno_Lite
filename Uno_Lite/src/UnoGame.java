import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A class which implements the uno pile, where cards are played.
 * This class should not be modified.
 *
 * Below are tools for you to use, however.
 * Read the documentation carefully.
 */

public class UnoGame{
	private static final int HANDSIZE = 7;
	//Store all played cards in this arraylist
	private LinkedList<UnoCard> played;
	private LinkedList<UnoCard> deck;
	private ArrayList<ArrayList<UnoCard>> players;
	private int currentPlayer;


	/**
	 * A constructor for the UnoPile.
	 * The pile is initially going to have 0 cards in it.
	 * The constructor needs a List of cards to draw from!
	 * The provided deck's first element will be the top card
	 * of the deck, and the last element will be the bottom card.
	 *
	 * Note: This assumes the deck has enough cards to support the
	 * number of players. If it doesn't, some players will run out of
	 * cards immediately!
	 *
	 * @param deck		is the list of cards to draw from
	 * @param numPlayers	is the number of people playing the game
	 */
	public UnoGame(List<UnoCard> deck, int numPlayers) throws NullPointerException, IllegalArgumentException{
		if(deck == null){
			throw new NullPointerException("The list provided is null!");
		}
		if (numPlayers <= 1){
			throw new IllegalArgumentException("numPlayers needs to be > 1");
		}

		//initialize lists
		this.played = new LinkedList<UnoCard>();
		this.deck = new LinkedList<UnoCard>();
		this.players = new ArrayList<ArrayList<UnoCard>>();

		//Add all things in the provided deck to this.
		this.deck.addAll(deck);

		//initialize players and give them a hand
		for(int i = 0; i < numPlayers; i++){
			players.add(new ArrayList<UnoCard>());
			for(int j = 0; j < HANDSIZE; j++){
				draw(i);
			}
		}

		this.played.add(this.deck.removeFirst());
		currentPlayer = 0;
	}

	/**
	 * Get the current player's number.
	 *
	 * @return the index of the current player
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}

	/**
	 * Increments the game's current player to the next player.
	 * If I wanted to implement reverse cards, I'd need a way to
	 * decide whether to increment or decrement.
	 */
	public void nextPlayer(){
		if (++currentPlayer == players.size()){
			currentPlayer = 0;
		}
	}


	/**
	 * Draws and places a card from the deck into the player's hand
	 * This does nothing if there are no cards in the deck!
	 *
	 * @param hand	The hand to add cards to
	 */
	public void draw(int playerNum) throws NullPointerException{
		if (playerNum < 0 || playerNum >= players.size()){
			throw new NoSuchElementException("This is out of the player bounds!");
		} 
		if (deck.isEmpty()){
			return;
		} 
		//grab the player and add the top card of the deck to their hand
		players.get(playerNum).add(deck.removeFirst());
		
	}

	/**
	 * Returns whether the deck to draw from is empty.
	 *
	 * @return true if the deck is empty.
	 */
	public boolean isDeckEmpty(){
		return deck.isEmpty();
	}

	/**
	 * Prompts the user to set the color of the top card of the deck
	 *
	 * @param	A scanner pointing to standard input.
	 */
	public void setTopCardColor(Scanner stdin){
		System.out.println("You played a wild! What color should it be?");
		System.out.printf("0: Red%n1: Blue%n2: Yellow%n3: Green%n");
		int choice = -1;
		while(choice < 0 || choice > 4){
			try{
				choice = stdin.nextInt();
			} catch (NoSuchElementException e) {}
		}
		switch(choice){
			case 0: played.getFirst().setColor("Red"); break;
			case 1: played.getFirst().setColor("Blue"); break;
			case 2: played.getFirst().setColor("Yellow"); break;
			case 3: played.getFirst().setColor("Green"); break;
		}
		
	}

	/**
	 * Prints the deck state, pile state, and all hands for each player.
	 */
	public void printFullGameState(){
		System.out.printf("Deck State: %s%n", deck.toString());
		System.out.printf("Played Cards: %s%n", played.toString());

		printHands();
	}

	/**
	 * Print the hands of each player.
	 */
	public void printHands(){
		for(int i = 0; i < players.size(); i++){
			System.out.printf("Player %d: %s%n", i, players.get(i)); 
		}
	}

	/**
	 * This places a card on the top of the played pile.
	 * 
	 * Note: This only checks if the card is legally able to be played, and if it
	 * exists in the hand!
	 *
	 * @param card is the card to place on top of the uno's played pile.
	 */
	public void playCard(UnoCard card){
		if (card == null){
			throw new NullPointerException("Card provided is null!");
		}

		played.addFirst(card);
	}

	/**
	 * This returns the last played card in the card pile.
	 * If the card pile is empty, return null!
	 */
	public UnoCard getLastPlayed(){
		if (played.isEmpty()){
			return null;
		}
		return played.getFirst();
	}

	/**
	 * Returns a list of UnoCards held by the current player.
	 * If you remove a card from this list, then the player
	 * will also stop holding the card. Use this list to remove
	 * cards!
	 *
	 * @return		A reference to the list of cards they are holding.
	 */
	public List<UnoCard> getPlayerHand(){
		return players.get(currentPlayer);
	}

	/**
	 * Returns a number between 0 and the number of players - 1 if a player wins.
	 * Otherwise, returns -1.
	 *
	 * @return the index of the winning player if there is one, or -1 if there is none
	 */
	public int getWinner(){
		for(int i = 0; i < players.size(); i++){
			if (players.get(i).size() == 0){
				return i;
			}
		}
		return -1;
	}
}
