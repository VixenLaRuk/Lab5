import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This program is a simulation of the Scrabble game.
 * @author Vixen La Ruk
 * @author Peter Gian-Paolo Despues
 * @since 11/18/2025
 */
public class Scrabble {
    private static ArrayList<Tile> tiles = new ArrayList<Tile>();
    private static Random rand = new Random();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main method that starts and runs the Scrabble game. Players
     * draw tiles from the bag, form words, and accumulate scores until
     * no more tiles are available or the player chooses to quit.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        createAllTiles();
        ArrayList<Tile> hand = new ArrayList<Tile>();
        int totalScore = 0;

        System.out.println("Welcome to Vixen's Scrabble!");

        while (true) {
            refillHand(hand);

            if (hand.isEmpty()) {
                System.out.println ("No more tiles in the bag! Game over.");
                break;
            }

            System.out.println("Your tiles:");
            for (Tile tile : hand) {
                System.out.print(tile + " ");
            }
            System.out.println();

            System.out.print("Enter a word using your tiles (or 'quit' to exit): ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Thanks for playing! Total score: " + totalScore);
                break;
            }

            if (!canSpell(input, hand)) {
                System.out.println("Invalid word! You don't have the tiles for that. Try again.");
                continue;
            }

            int score = calculateScore(input, hand);
            totalScore += score;
            System.out.println("Word: " + input + " | Score: " + score + " | Total Score: " + totalScore);

            removeUsedTiles(input, hand);
        }

        scanner.close();
    }
        
    /**
     * Initializes the complete tile bag with standard Scrabble tiles.
     * Creates one tile for each letter with its corresponding point value
     * and quantity according to standard Scrabble rules. Populates
     * the static tiles ArrayList with all tiles in the game.
     */
    public static void createAllTiles() {

        char[] letters = {'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U', 
                          'D', 'G', 'B', 'C', 'M', 'P', 'F', 'H', 'V', 'W', 
                          'Y', 'K', 'J', 'X', 'Q', 'Z'};
        int[] values = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        2, 2, 3, 3, 3, 3, 4, 4, 4, 4,
                        4, 5, 8, 8, 10, 10};
        int[] counts = {12, 9, 9, 8, 6, 6, 6, 4, 4, 4,
                        4, 3, 2, 2, 2, 2, 2, 2, 2, 2,
                        2, 1, 1, 1, 1, 1};

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < counts[i]; j++) {
                tiles.add(new Tile(letters[i], values[i], counts[i]));
            }
        }
    }

    /**
     * Refills the player's hand with random tiles drawn from the bag.
     * Continues to draw tiles until the hand has 7 tiles or the bag is
     * empty. Removes drawn tiles from the static tiles bag.
     * @param hand the player's current hand of tiles to refill
     */
    public static void refillHand(ArrayList<Tile> hand) {
        while (hand.size() < 7 && !tiles.isEmpty()) {
            int index = rand.nextInt(tiles.size());
            hand.add(tiles.remove(index));
        }
    }

    /**
     * Counts how many times a specific letter appears in the hand.
     * @param letter the letter to count
     * @param hand the player's current hand of tiles
     * @return the count of the letter in the hand
     */
    private static int countLetterInHand(char letter, ArrayList<Tile> hand) {
        int count = 0;
        for (Tile tile : hand) {
            if (tile.getLetter() == letter) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts how many times a specific letter appears in a word.
     * @param letter the letter to count
     * @param word the word to search in
     * @return the count of the letter in the word
     */
    private static int countLetterInWord(char letter, String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines if a word can be spelled using the tiles in the player's
     * hand.
     * @param word the word to check if it can be spelled
     * @param hand the player's current hand of tiles
     * @return true if all letters in the word are available in the hand
     * with sufficient quantity, false otherwise
     */
    public static boolean canSpell(String word, ArrayList<Tile> hand) {
        // Check each unique letter in the word
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            // Only check each letter once (skip if we've seen it before in the word)
            boolean alreadyChecked = false;
            for (int j = 0; j < i; j++) {
                if (word.charAt(j) == letter) {
                    alreadyChecked = true;
                    break;
                }
            }
            
            if (!alreadyChecked) {
                int needed = countLetterInWord(letter, word);
                int available = countLetterInHand(letter, hand);
                if (available < needed) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds the point value of a letter by searching through the hand.
     * @param letter the letter to find the value for
     * @param hand the player's current hand of tiles
     * @return the point value of the letter, or 0 if not found
     */
    private static int getLetterValue(char letter, ArrayList<Tile> hand) {
        for (Tile tile : hand) {
            if (tile.getLetter() == letter) {
                return tile.getValue();
            }
        }
        return 0;
    }

    /**
     * Calculates the score for a word based on the point values of
     * its tiles. Sums the point value of each tile used in the word
     * by checking the corresponding tiles in the player's hand.
     * @param word the word to calculate the score for
     * @param hand the player's current hand of tiles
     * @return the total point value of the word
     */
    public static int calculateScore(String word, ArrayList<Tile> hand) {
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            score += getLetterValue(letter, hand);
        }
        return score;
    }

    /**
     * Removes the tiles used to spell a word from the player's hand.
     * @param word the tiles from the word that are removed
     * @param hand the tiles removed from the player's hand
     */
    public static void removeUsedTiles(String word, ArrayList<Tile> hand) {
        // For each letter in the word, find and remove one matching tile from hand
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            // Find the first matching tile in hand and remove it
            for (int j = 0; j < hand.size(); j++) {
                if (hand.get(j).getLetter() == letter) {
                    hand.remove(j);
                    break; // Only remove one tile per letter occurrence
                }
            }
        }
    }
}

