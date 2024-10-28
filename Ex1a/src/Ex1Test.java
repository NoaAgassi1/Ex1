
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Ex1Test {

    private BP_Server game;

    @BeforeEach
    void setUp() {
        // Initialize your BP_Server instance here
        game = new BP_Server(/* Provide necessary setup parameters */);
    }

    @Test
    void autoEx1Game() {
        int sum = 0;
        int[] averages = new int[5]; //create  array to the averages of which number of digits
        int a = 100;

        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < a; j++) {// checking the average of 100 games
                game.startGame(209280635, i);
                int guess = Ex1.autoEx1Game(game);
                sum += guess;
            }

            averages[i - 2] = sum ;
            sum = 0;
        }

        for (int j = 0; j < averages.length; j++) {
            System.out.println("-----the average for "+ +(j+2)+ " digit is " + (averages[j])/a);
        }

    }
    @Test
    void randomTest(){
        boolean[] op = new boolean[1000];  // Adjust the array size as needed
        int size = 3;  // Adjust the size as needed
        // Mark some numbers as true in the op array (for testing purposes)
        op[123] = true;
        op[456] = true;
        op[789] = true;
        op[001] = true;
        op[100] = false;
        int guess = Ex1.random(op,size);
        assertTrue(op[guess]);
        System.out.println("Generated Number: " + guess);
    }

    @Test
    void contains() {
        int temp = 334;
        int size = 2;
        int a = 3;
        assertTrue(Ex1.contains(temp,a,size));
        assertFalse(Ex1.contains(temp,0,3));
    }
}


