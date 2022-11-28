package bullscows;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static int[] turn (String guess, String code, int range) {
        int bulls = 0;
        int cows = 0;
        ArrayList<Boolean> isDigitFound = new ArrayList<>();
        ArrayList<Boolean> isLetterFound = new ArrayList<>();
        char border = Character.forDigit(range - 1,36);
        for (int i = 0; i < range; i++) {
            isDigitFound.add(Boolean.FALSE);
        }
        for (int i = 0; i < range - 10; i++) {
            isLetterFound.add(Boolean.FALSE);
        }
        for (int i = 0; i < guess.length(); i++) {
            int charNum = guess.charAt(i);
            boolean isDigit;
            if (charNum > 47 && charNum < 58 && charNum - 48 < range) {
                //subtrahend = 48;
                isDigit = true;
            } else if (charNum > 96 && charNum < 123 && charNum - 97 < range - 10) {
                //subtrahend = 97;
                isDigit = false;
            } else {
                System.out.println("One of your characters is out of range");
                if (range > 10) {
                    System.out.format("P.S. Your range is: (0-9, a-%c).%n",
                            border);
                } else {
                    System.out.format("P.S. Your range is: (0-%c).%n",
                            border);
                }
                System.out.println("Please, try again:");
                return new int[]{-1, -1};
            }
            if (isDigit) {
                if (isDigitFound.get(charNum - 48) == Boolean.TRUE) {
                    System.out.println("One of your characters is written twice");
                    System.out.println("Please, try again:");
                    return new int[]{-1, -1};
                }
            } else {
                if (isLetterFound.get(charNum - 97) == Boolean.TRUE) {
                    System.out.println("One of your characters is written twice");
                    System.out.println("Please, try again:");
                    return new int[]{-1, -1};
                }
            }
            if (guess.charAt(i) == code.charAt(i)) {
                bulls++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
            if (isDigit) {
                isDigitFound.set(charNum - 48, Boolean.TRUE);
            } else {
                isLetterFound.set(charNum - 97, Boolean.TRUE);
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
        System.out.println("""
                Welcome to the 'Bull and Cows' game!
                The rules are simple: computer creates a random 'secret code' from a specific range of characters,
                where each character can be written only once or never.
                Each turn you need to guess this code. If in your guess there is a character that is also in the
                secret code, but on the other position, it's called 'cow', but if positions are similar, it's called
                'bull'.
                If you want to exit, write "#exit", but remember that after this the secret code will be deleted and
                you will never be able to guess it.
                Good luck!""");
        System.out.println("Please, enter the secret code's length:");
        int codeLength;
        String length;
        while (true) {
            length = scan.nextLine();
            if (length.equals("#exit")) {
                System.out.println("Bye! I hope you enjoyed this game!");
                System.exit(1);
            }
            try {
                codeLength = Integer.parseInt(length);
            } catch (NumberFormatException e) {
                System.out.format("\"%s\" isn't a valid number.%n", length);
                System.out.println("Please, try again:");
                continue;
            }
            if (codeLength > 36 || codeLength < 1) {
                System.out.format("Can't generate a secret number with a " +
                        "length of %d", codeLength);
                System.out.println("Please, try again:");
                continue;
            }
            break;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int range;
        while (true) {
            length = scan.nextLine();
            if (length.equals("#exit")) {
                System.out.println("Bye! I hope you enjoyed this game!");
                System.exit(1);
            }
            try {
                range = Integer.parseInt(length);
            } catch (NumberFormatException e) {
                System.out.format("Error: \"%s\" isn't a valid number.", length);
                System.out.println("Please, try again:");
                continue;
            }
            if (range > 36) {
                System.out.println("Error: maximum number of possible symbols in the " +
                        "code is 36 (0-9, a-z).");
                System.out.println("Please, try again:");
                continue;
            }
            if (range < codeLength) {
                System.out.format("Error: it's not possible to generate a code with " +
                        "a length of %d with %d unique symbols.", codeLength, range);
                System.out.println("Please, try again:");
                continue;
            }
            break;
        }
        char border = Character.forDigit(range - 1,36);
        String asterisk = "*";
        String asterisks = asterisk.repeat(codeLength);
        String code = randomNumber(codeLength, range);
        if (range > 10) {
            System.out.format("The secret is prepared: %s (0-9, a-%c).%n",
                    asterisks, border);
        } else {
            System.out.format("The secret is prepared: %s (0-%c).%n",
                    asterisks, border);
        }
        String guess;
        System.out.println("Okay, let's start a game!");
        int counter = 0;
        int[] grade;
        while (bulls != codeLength) {
            counter++;
            System.out.format("Turn %d: ", counter);
            while (true) {
                guess = scan.nextLine();
                if (guess.equals("#exit")) {
                    System.out.println("Bye! I hope you enjoyed this game!");
                    System.exit(1);
                }
                if (guess.length() != codeLength) {
                    System.out.println("Your guess's length is not the same as the length of a secret code");
                    System.out.println("Please, try again:");
                    continue;
                }
                grade = turn(guess, code, range);
                if (grade[0] == -1 || grade[1] == -1) {
                    continue;
                }
                break;
            }
            bulls = grade[0];
            output(grade[0], grade[1]);
        }
        System.out.println("Congratulations! You guessed the secret code.");

    }

}
