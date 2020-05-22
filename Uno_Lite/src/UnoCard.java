import java.util.Scanner;

/**
 * A card abstract class.
 * This class will be used as the superclass for each
 * card you must implement.
 *
 * NOTE: This class is incomplete. It must have a compareTo method implemented!
 * I have provided the method header below. Do not change anything else!
 */

public abstract class UnoCard{
	protected int value;
	protected String color;

	/**
	 * This constructor sets the color and value of
	 * UnoCard to the provided arguments.
	 *
	 * If the card is a special card, then value should be set to -1!
	 */
	public UnoCard(String color, int value){
		this.color = color;
		this.value = value;
	}

	/**
	 * This should return true if the card is a
	 * legal card to play on top of this card. Otherwise, return false.
	 *
	 * A card is legal if:
	 * It is wild.
	 * Its number matches mine, and we are both a number card.
	 * It's color matches mine.	
	 *
	 * @param card		is the card to check against the current uno card.
	 * @return 		true, if the provided card can be played on this card.
	 */
	public boolean isLegal(UnoCard card) throws NullPointerException{

		// This card is illegal if it is null
		if (card == null)
			throw new NullPointerException("isLegal recieved argument null!");

		// If this card matches my color, or is wild...
		if (card instanceof UnoWildCard || card.getColor().equals(this.color)){
			return true;
		}
		
		//if we are both skip cards...
		if (card instanceof UnoSkipCard && this instanceof UnoSkipCard){
			return true;
		}

		// If this card matches my value and this card is a number card
		// return true. Otherwise, return false.
		return (card instanceof UnoNumberCard && this instanceof UnoNumberCard
				&& card.getValue() == this.value);
	}


	/**
	 * A toString method, so you aren't so sad when debugging.
	 *
	 * @return a pretty string explaining what type of card this is.
	 */
	public String toString(){
		if (this instanceof UnoWildCard){ 
			return "Wild";
		}
		else if (this instanceof UnoSkipCard){
			return getColor() + " Skip";
		}
		else{
			return getColor() + " " + getValue();
		}
	}


	/**
	 * This returns the value of the card, or -1 if the card is special.
	 * @return the value of the card
	 */
	public abstract int getValue();

	/**
	 * This returns the color of the card.
	 *@return the color of the card
	 */
	public abstract String getColor();

	/**
	 * When dealing with wild cards, we may need to set a color.
	 * Set a color if the card is a wild card. Otherwise, do not do anything.
	 *
	 * @param color		is the color you would like to set the card to.
	 */
	public abstract void setColor(String color);

	/**
	 * This plays the card on top of the UnoPile. 
	 * If the card is not a real card, throw an IllegalArgumentException.
	 * If the card cannot be legally played at the moment, have the player draw two for cheating.
	 * 
	 * If I play a wild card, I need the scanner to prompt for input with the
	 * game.setTopCardColor(Scanner stdin) method. Otherwise, don't touch the scanner!
	 *
	 * If you could play the card, return true. Otherwise, return false.
	 *
	 * @param game The pile on which to play the card. There should only be one of these per game.
	 */
	public abstract boolean play(UnoGame game, int player, Scanner stdin);
}
