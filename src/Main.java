import Texas_Hold_Em.TexasController;

public class Main {
    public static void main(String[] args) {
        String[] names = {"Human", "Tom", "Dick", "Harry"};

        System.out.println("\nWelcome to the Automated Texas Hold'Em Machine ...\n\n");

        System.out.print("\nWhat is your name?  ");

        byte[] input = new byte[100];

        try {
            int numBytesRead = System.in.read(input);
            String userInput = new String(input, 0, numBytesRead).trim();
            if (!userInput.isEmpty()) {
                names[0] = userInput;
            }
        }
        catch (Exception e){};

        int startingBank = 100;

        System.out.println("\nLet's play BLACKJACK ...\n\n");
        TexasController game = new TexasController();
        game.setUp(names, startingBank);
        game.play();
    }
}