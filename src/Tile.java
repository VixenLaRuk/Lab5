/**
 * Represents a single Scrabble tile with a letter and
 * point value. Each tile contains a character, its corresponding
 * point value, and the quantity of the tile.
 * @author Vixen La Ruk
 * @author Peter Gian-Paolo Despues
 * @since 11/18/2025
 */
public class Tile {

    private char letter;
    private int value;
    private int howMany;

    /**
     * Constructs a new Tile with the specified letter, point value, and quantity.
     * @param letter the letter character on this tile
     * @param value the point value of this tile
     * @param howMany the quantity of this tile type in the game
     */
    public Tile(char letter, int value, int howMany) {
        this.letter = letter;
        this.value = value;
        this.howMany = howMany;
    }

    /**
     * Constructs a new Tile by copying the letter, value, and quantity
     * from another Tile object.
     * @param obj the Tile object to copy from
     */
    public Tile(Tile obj) {
        this.letter = obj.letter;
        this.value = obj.value;
        this.howMany = obj.howMany;
    }
 
    /**
     * Sets the letter character of this tile.
     * @param letter the new letter character for this tile
     */
    public void setLetter(char letter) {
        this.letter = letter;

    }

    /**
     * Sets the point value of this tile.
     * @param value the new point value for this tile
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the quantity of this tile type in the game.
     * @param howMany the new quantity value for this tile type
     */
    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    /**
     * Gets the letter character of this tile.
     * @return the letter character on this tile
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Gets the point value of this tile.
     * @return the point value of this tile
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the quantity of this tile type in the game.
     * @return the quantity of this tile type
     */
    public int getHowMany() {
        return this.howMany;
    }

    /**
     * Checks if this tile equals another tile based on letter and value.
     * @param other the Tile object to compare with
     * @return true if the tiles have the same letter and value, false otherwise
     */
    public boolean equals(Tile other) {
        return this.letter == other.letter && this.value == other.value;
    }

    /**
     * Returns a string representation of this tile showing the letter
     * and its point value in the format "L  (value = N)" where L is
     * the letter and N is the point value.
     * @return a formatted string representation of this tile
     */
    public String toString() {
        return String.format("%c  (value = %d)", letter, value);
    }
}