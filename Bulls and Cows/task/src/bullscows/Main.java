package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {

    public static int[] turn (String guess, String code, int range) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            if ((int)guess.charAt(i) > range + 47) {
                System.out.println("Error: character is out of range");
            }
            if (guess.charAt(i) == code.charAt(i)) {
                bulls++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        return new int[]{bulls, cows};
    }

    public static void output(int bulls, int cows) {
        if (bulls != 0 && cows != 0) {
            String temp1;
            String temp2;
            if(bulls == 1) {
                temp1 = "bull";
            } else {
                temp1 = "bulls";
            }
            if(cows == 1) {
                temp2 = "cow";
            } else {
                temp2 = "cows";
            }
            System.out.format("Grade: %d %s and %d %s%n", bulls, temp1, cows, temp2);
        } else if (bulls == 0 && cows != 0) {
            String temp2;
            if(cows == 1) {
                temp2 = "cow";
            } else {
                temp2 = "cows";
            }
            System.out.format("Grade: %d %s%n", cows, temp2);
        } else if (bulls != 0) {
            String temp1;
            if(bulls == 1) {
                temp1 = "bull";
            } else {
                temp1 = "bulls";
            }
            System.out.format("Grade: %d %s%n", bulls, temp1);
        } else {
            System.out.println("None");
        }
    }

    public static String randomNumber(int length, int range) {
        StringBuilder code = new StringBuilder();
        Random rand = new Random(10_000_000);
        int random;
        String temp;
        while (code.length() != length) {
            random = rand.nextInt(range);
            temp = String.valueOf(Character.forDigit(random, range));
            if (!code.toString().contains(temp)) {
                code.append(temp);
            }
        }
        return code.toString();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int bulls = 0;
        System.out.println("Please, enter the secret code's length:");
        //do {
        String temp = scan.nextLine();
        int length = 0;
        try {
            length = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            System.out.format("Error: \"%s\" isn't a valid number.", temp);
            System.exit(1);
        }
        //scan.nextLine();
        if (length > 36 || length < 1) {
            System.out.format("Error: can't generate a secret number with a " +
                    "length of %d", length);
            System.exit(1);
        }
        //} while (length > 36);
        System.out.println("Input the number of possible symbols in the code:");
        int range = 0;
        temp = scan.nextLine();
        try {
            range = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            System.out.format("Error: \"%s\" isn't a valid number.", temp);
            System.exit(1);
        }
        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the " +
                    "code is 36 (0-9, a-z).");
            System.exit(1);
        }
        if (range < length) {
            System.out.format("Error: it's not possible to generate a code with " +
                    "a length of %d with %d unique symbols.", length, range);
            System.exit(1);
        }
        char border = Character.forDigit(range - 1,36);
        String asterisk = "*";
        String asterisks = asterisk.repeat(length);
        String code = randomNumber(length, range);
        if (range > 10) {
            System.out.format("The secret is prepared: %s (0-9, a-%c).%n",
                    asterisks, border);
        } else {
            System.out.format("The secret is prepared: %s (0-%c).%n",
                    asterisks, border);
        }
        //scan.nextLine();
        String guess;
        System.out.println("Okay, let's start a game!");
        int counter = 0;
        int[] grade;
        while (bulls != length) {
            counter++;
            System.out.format("Turn %d: ", counter);
            guess = scan.nextLine();
            grade = turn(guess, code, range);
            bulls = grade[0];
            output(grade[0], grade[1]);
        }
        System.out.println("Congratulations! You guessed the secret code.");

    }

}
