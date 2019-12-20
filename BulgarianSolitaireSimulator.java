import java.util.ArrayList;
import java.util.Scanner;

// Name: De Huo
// USC NetID: dehuo
// CSCI455 PA2
// Fall 2019


/**
   This program simulates Bulgarian Solitaire game.
   Running program with -u arg will switch to user input mode, otherwise the initial configuration generated automatically.
   Running program with -s arg will switch to single step mode, otherwise the simulator will keep running till the end.
*/

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;
      boolean readingFaild = true;
      ArrayList<Integer> nums = new ArrayList<Integer>();
      Scanner in = new Scanner(System.in);
      SolitaireBoard board;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }

      /**
       * SETUP SIMULATOR
       */

      // Applying user input mode
      if (userConfig == true)
      {
         System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
         System.out
               .println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
         System.out.println("Please enter a space-separated list of positive integers followed by newline:");
         while (readingFaild) {
            nums.clear();
            readingFaild = !readInput(nums, in);
         }
         board = new SolitaireBoard(nums);
      }
      // NOT applying user input mode
      else 
      {
         board = new SolitaireBoard();
      }


      System.out.println("Initial configuration: " + board.configString());


      /**
       * RUNNING SIMULATOR
       */

      // Applying single step mode
      if (singleStep == true)
      {
         boolean tap = true;
         int count = 1;

         while (tap) {
            if (board.isDone())
               break;
            board.playRound();
            System.out.println("[" + count + "] Current configuration: " + board.configString());
            System.out.print("<Type return to continue>");
            count++;
            tap = false;
            if (in.nextLine().length() == 0)
               tap = true;
         }
      }      
      // NOT applying single step mode
      else
      {
         int count = 1;

         while (true)
         {
            if (board.isDone())
            break;
            board.playRound();
            System.out.println("[" + count + "] Current configuration: " + board.configString());
            count++;
         }
      }
      
      
   }
   
   /**
    * Helper method for reading user input.
    */
   private static boolean readInput(ArrayList<Integer> nums, Scanner in)
   {
      int sum = 0;
      Scanner scan = new Scanner(in.nextLine());

      while (scan.hasNextInt())
      {
         int numCur = scan.nextInt();
         //Checking input numbers greater than 0 or not.
         if (numCur <= 0) {
            System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
            System.out.println("Please enter a space-separated list of positive integers followed by newline:");
            return false;
         } else {
            nums.add(numCur);
            sum += numCur;
         }
      }

      //Checking input containing nonnumeric strings or not.
      if (scan.hasNext())
      {
         System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
         System.out.println("Please enter a space-separated list of positive integers followed by newline:");
         return false;
      }
      
      //Checking input numbers adding up to CARD_TOTAL or not.
      if (sum != SolitaireBoard.CARD_TOTAL)
      {
         System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
         System.out.println("Please enter a space-separated list of positive integers followed by newline:");
         return false;
      } 
      
      return true;

      
   }

   
}
