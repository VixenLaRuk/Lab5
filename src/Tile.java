public class Tile {

    private char letter;
    private int value;
    private int howMany;

    public Tile(char letter, int value, int howMany) {
        this.letter = letter;
        this.value = value;
        this.howMany = howMany;
    }

    public Tile(Tile obj) {
        this.letter = obj.letter;
        this.value = obj.value;
        this.howMany = obj.howMany;
    }
 
    public void setLetter(char letter) {
        this.letter = letter;

    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getValue() {
        return this.value;
    }

    public int getHowMany() {
        return this.howMany;
    }

    public boolean equals(Tile other) {
        return this.letter == other.letter && this.value == other.value;
    }

    public String toString() {
        return String.format("%c  (value = %d)", letter, value);
    }
}