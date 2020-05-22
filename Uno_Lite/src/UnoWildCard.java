import java.util.Scanner;

/**
 * A class implementing the wild cards for Uno.
 *
 * @author Quentin Ross
 */

public class UnoWildCard extends UnoCard{

    /**
     * This constructor sets the color and value of
     * UnoCard to the provided arguments.
     * <p>
     * If the card is a special card, then value should be set to -1!
     */
    public UnoWildCard(String color, int value) {
        super(color, value);
    }

    public int getValue() {
        return -1;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean play(UnoGame game, int player, Scanner stdin) throws IllegalArgumentException {
        if (this == null) {
            throw new IllegalArgumentException("Card is not real.");
        }
        else if (this.isLegal(game.getLastPlayed()) || this.color.equals(game.getLastPlayed().color)) {
            game.playCard(this);
            game.setTopCardColor(stdin);
            return true;
        }
        else {
            game.playCard(this);
            game.setTopCardColor(stdin);
            game.draw(player);
            game.draw(player);
            return false;
        }
    }
}
