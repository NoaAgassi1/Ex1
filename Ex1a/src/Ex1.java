import java.util.*;
import java.util.Random;

/**
 * ID- 209280635
 * Introduction to Computer Science, Ariel University, Ex1 (manual Example + a Template for your solution)
 * See: https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * <p>
 *
 * Ex1 Bulls & Cows - Automatic solution.
 * **** Add a general readme text here ****
 * In this program we actually play bull & cows game.We have a manual algorithm and
 * automatic algorithm that we needed to write.
 * The department basically guesses the cod that the game gave,with a few guesses as possible.
 *
 * **** General Solution (algorithm) ****
 * Add your explanation here:
 The function I built starts from a Boolean array of the size of the maximum possible
 combinations depending on the number of digits.
 The function receives a random guess at a true value,
 turns it into an array and with the help of a filter helper function,
 the function turns all values that do not have the same result as ball and cows values into false.
 The function narrows down its possibilities and continues to guess until it reaches the victory of the game.
 * **** Results ****
 * Make sure to state the average required guesses
 * for 2,3,4,5,6 digit code:
 * <p>
 The average for 2 digit is 5
 The average for 3 digit is 5
 The average for 4 digit is 6
 The average for 5 digit is 6
 The average for 6 digit is 6
 * <p>
 * Process finished with exit code 0
 */
public class Ex1 {
    public static final String Title = "Ex1 demo: manual Bulls & Cows game";

    public static void main(String[] args) {
        BP_Server game = new BP_Server();   // Starting the "game-server"
        long myID = 209280635;             // Your ID should be written here
        int numOfDigits = 5;                // Number of digits [2,6]
        game.startGame(myID, numOfDigits);  // Starting a game
        System.out.println(Title + " with code of " + numOfDigits + " digits");
        // manualEx1Game(game);
        autoEx1Game(game); // you should implement this function and any additional required functions).
        // int count = 0;
    }

    public static void manualEx1Game(BP_Server game) {
        Scanner sc = new Scanner(System.in);
        int ind = 1;      // Index of the guess
        int numOfDigits = game.getNumOfDigits();
        double max = Math.pow(10, numOfDigits); // עשר בחזקת מספר הספרות בקוד שצריך לנחש
        while (game.isRunning()) {           // While the game is running (the code has not been found yet
            System.out.println(ind + ") enter a guess: ");
            int g = sc.nextInt();
            if (g >= 0 && g < max) { // אם הספרה שבחרנו נמצאת בכמות הספרות שיש בקוד המקורי
                int[] guess = toArray(g, numOfDigits); // int to digit array
                int[] res = game.play(guess); // Playing a round and getting the B,C //מחזיר כמה בול וכמה פגיעה
                if (game.isRunning()) {     // While the game is running
                    System.out.println(ind + ") B: " + res[0] + ",  C: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                    ind += 1;               // Increasing the index
                }
            } else {
                System.out.println("ERR: wrong input, try again");
            }
        }
        System.out.println(game.getStatus());
    }


    /**
     * Simple parsing function that gets an int and returns an array of digits
     * @param a    - a natural number (as a guess)
     * @param size - number of digits (to handle the 00 case).
     * @return an array of digits
     */
    private static int[] toArray(int a, int size) {
        int[] c = new int[size];
        for (int j = 0; j < c.length; j += 1) {
            c[j] = a % 10;
            a = a / 10;
        }
        return c;
    }
    /**
     * This function solves the Bulls & Cows game automatically.
     * The function I built starts from a Boolean array of the size of the maximum possible
     * combinations depending on the number of digits.
     * The function receives a random guess at a true value,
     * turns it into an array and with the help of a filter helper function,
     * the function turns all values that do not have the same result as ball and cows values into false.
     * the function narrows down its possibilities and continues to guess until it reaches the victory of the game.
     */
    public static int autoEx1Game(BP_Server game) {
        int ind = 0;      // Index of the guess
        //int count = 0;
        int numOfDigits = game.getNumOfDigits();
        int max = (int) Math.pow(10, numOfDigits);
        boolean[] op = new boolean[max]; //new array with the size of all the options
        Arrays.fill(op, true); // fill the array in true value
        int count = random(op,numOfDigits);// getting a random guess

        while (game.isRunning()) { // While the game is running (the code has not been found yet
            if (ind < max) {
                int [] guess = toArray(count, numOfDigits); // turns the guess into an array
                int[] res = game.play(guess); // Playing a round and getting the B,C

                if (game.isRunning()) {     // While the game is running
                    if (res[0] == 0 && res[1] == 0 && numOfDigits<6) {//if there is no balls and cows
                        for (int i = 0; i < guess.length; i++) {
                            for (int j = 0; j < op.length; j++) {
                                if (!op[j]) {
                                    continue;
                                }
                                if (contains(j, guess[i], numOfDigits)) { //turn all the numbers with this digits to false
                                    op[j] = false;
                                }
                            }
                        }
                    }

                    for (int i = 0; i < op.length; i++) {
                        if (op[i]) {
                            int[] ans = filter(toArray(i, numOfDigits), guess);
                            //remove all the function that aren't the with the same B and C
                            if (ans[0] != res[0] || ans[1] != res[1]) {
                                op[i] = false;
                            }
                        }
                    }
                    // System.out.println(ind + ") B: " + res[0] + ",  C: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                }
                ind++;
                boolean f = false;
                while (f != true) {
                    if (op[count] == true) {
                        f = true;
                    } else {
                        count = random(op,numOfDigits);// getting a new guess
                        //  System.out.println(count);
                    }
                }
            }
        }
        System.out.println(game.getStatus());
        return ind;
    }

    /**
     * This function generates a random number,
     * if the number that we got is a "false" number in the op array in the game,
     * the function will generate a new number.
     * @param op
     * @param size
     * @return
     */
    public static int random(boolean [] op,int size){
//        double max = Math.pow(10,size);
//        Random random = new Random();
//        int random1 =  random.nextInt((int) max);
//        for (int i = 0; i < op.length; i++) {
//           // System.out.println(random1);
//            if (op[random1]){
//                return random1;
//            }
//        }
//        return random1;
        Random random = new Random();
        while (true) {
            // Generate a random number within the specified range.
            int random1 = random.nextInt((int) Math.pow(10, size));
            // Check if the generated number is marked as "true" in the op array.
            if (op[random1]) {
                // If the number is marked as "true," return it.
                return random1;
            }
        }
    }

    /**
     * this function get a guess and a digit and finding if tha guess contain the digit that we got.
     * if ther is the digit that we got in the guess,the function return true.
     * @param ind - the guess that we got.
     * @param a - the digit that we want to check if contain in the guess.
     * @param size - the number of the digits of the game.
     * @return true or false.
     */
    public static boolean contains(int ind, int a, int size) {
            while (ind != 0) {
                int temp = ind % 10;
                if (a == temp) {
                    return true;
                }
                ind /= 10;
            }
            return false;
    }

    /**
     * Calculates the number of bulls and cows pegs between a secret code and a guess.
     * @param opCode The secret code array.
     * @param guess  The guess array to be compared with the secret code.
     * @return An array containing the count of bulls pegs
     * at index 0, and white pegs (correct number in wrong position) at index 1.
     */
    public static int[] filter(int[] opCode, int[] guess) {
        int[] result = new int[2]; // result[0] for B , result[1] for C 

        for (int i = 0; i < opCode.length; i++) {// First count the bulls
            if (opCode[i] == guess[i]) {
                result[0]++;
            }
        }
        // Create copies of the arrays to track remaining elements
        int[] secretCodeCopy = Arrays.copyOf(opCode, opCode.length);
        int[] guessCopy = Arrays.copyOf(guess, guess.length);


        for (int i = 0; i < secretCodeCopy.length; i++) {//count the cows
            for (int j = 0; j < guessCopy.length; j++) {
                if (secretCodeCopy[i] == guessCopy[j]) {
                    result[1]++;
                    secretCodeCopy[i] = -1; // Mark matched elements as -1 to avoid double counting
                    guessCopy[j] = -1;
                    break;
                }
            }
        }
        // Subtract bulls from cows to get the correct count of cows pegs
        result[1] -= result[0];
        return result;
    }

    public static int[] firstGuess(int numOfDigits) {
        int[] guess = new int[numOfDigits];
        for (int i = 0; i < numOfDigits; i++) {
            guess[i] = i;
        }
        return guess;
    }
    public static ArrayList<int[]> allTrueNum(int size) {
        int end = (int) Math.pow(10, size);
        ArrayList<int[]> arr = new ArrayList<int[]>();
        ;
        for (int i = 0; i < end; i++) {
            arr.add(toArray(i, size));
        }
        return arr;
    }

}


