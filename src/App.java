import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * This program is a simulation of the Scrabble game.
 * @author Vixen La Ruk
 * @author Peter Gian-Paolo Despues
 * @since 11/18/2025
 */
public class App {
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
        Map<Character, int[]> tileData = Map.ofEntries(
            Map.entry('E', new int[]{1, 12}),
            Map.entry('A', new int[]{1, 9}),
            Map.entry('I', new int[]{1, 9}),
            Map.entry('O', new int[]{1, 8}),
            Map.entry('N', new int[]{1, 6}),
            Map.entry('R', new int[]{1, 6}),
            Map.entry('T', new int[]{1, 6}),
            Map.entry('L', new int[]{1, 4}),
            Map.entry('S', new int[]{1, 4}),
            Map.entry('U', new int[]{1, 4}),
            Map.entry('D', new int[]{2, 4}),
            Map.entry('G', new int[]{2, 3}),
            Map.entry('B', new int[]{3, 2}),
            Map.entry('C', new int[]{3, 2}),
            Map.entry('M', new int[]{3, 2}),
            Map.entry('P', new int[]{3, 2}),
            Map.entry('F', new int[]{4, 2}),
            Map.entry('H', new int[]{4, 2}),
            Map.entry('V', new int[]{4, 2}),
            Map.entry('W', new int[]{4, 2}),
            Map.entry('Y', new int[]{4, 2}),
            Map.entry('K', new int[]{5, 1}),
            Map.entry('J', new int[]{8, 1}),
            Map.entry('X', new int[]{8, 1}),
            Map.entry('Q', new int[]{10, 1}),
            Map.entry('Z', new int[]{10, 1})
        );

        for (Map.Entry<Character, int[]> entry : tileData.entrySet()) {
            char letter = entry.getKey();
            int value = entry.getValue()[0];
            int count = entry.getValue()[1];
            for (int i = 0; i < count; i++) {
                tiles.add(new Tile(letter, value, count));
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
     * Determines if a word can be spelled using the tiles in the player's
     * hand.
     * @param word the word to check if it can be spelled
     * @param hand the player's current hand of tiles
     * @return true if all letters in the word are available in the hand
     * with sufficient quantity, false otherwise
     */
    public static boolean canSpell(String word, ArrayList<Tile> hand) {
        Map<Character, Integer> handCount = new HashMap<>();
        for (Tile tile : hand) {
            handCount.put(tile.getLetter(), handCount.getOrDefault(tile.getLetter(), 0) + 1);
        }

        Map<Character, Integer> wordCount = new HashMap<>();
        for (char c : word.toCharArray()) {
            wordCount.put(c, wordCount.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : wordCount.entrySet()) {
            if (handCount.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
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
        Map<Character, Integer> handMap = new HashMap<>();
        for (Tile tile : hand) {
            handMap.put(tile.getLetter(), tile.getValue());
        }
        for (char c : word.toCharArray()) {
            if (handMap.containsKey(c)) {
                score += handMap.get(c);
            }
        }
        return score;
    }

    /**
     * Removes the tiles used to spell a word from the player's hand. Creates
     * a frequency map of letters to remove
     * @param word the tiles from the word that are removed
     * @param hand the tiles removed from the player's hand
     */
    public static void removeUsedTiles(String word, ArrayList<Tile> hand) {
        Map<Character, Integer> toRemove = new HashMap<>();
        for (char c : word.toCharArray()) {
            toRemove.put(c, toRemove.getOrDefault(c, 0) + 1);
        }

        hand.removeIf(tile -> {
            char letter = tile.getLetter();
            if (toRemove.getOrDefault(letter, 0) > 0) {
                toRemove.put(letter, toRemove.get(letter) - 1);
                return true;
            }
            return false;
        });
    }
}