// Name: De Huo
// USC NetID: dehuo
// CSCI455 PA2
// Fall 2019

import java.util.ArrayList;
import java.util.Random;


/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total 
  number of cards is for the game by changing NUM_FINAL_PILES, below.  
  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.  (See comments 
  below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt 
   // description for details.
   
   
   /**
      Representation invariant:
   
      <put rep. invar. comment here>
      
      1. nums.length == CARD_TOTAL;
      2. 0 < size <= CARD_TOTAL;
      3. for i in range(0, size)
         nums[i] represents the number of cards in (i+1)-th pile, must greater than 0
         and all nums[i] add up to CARD_TOTAL;
   
   */
   
   // <add instance variables here>
   private int[] nums;
   private int size;
   private Random generater;
   
  
 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of 
      cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to 
      SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {
      nums = new int[CARD_TOTAL];
      size = piles.size();
      for (int i = 0; i < size; i++)
      {
         nums[i] = piles.get(i);
      }


      // sample assert statement (you will be adding more of these calls)
      // this statement stays at the end of the constructor.
      assert isValidSolitaireBoard();   
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() {
      generater = new Random();
      nums = new int[CARD_TOTAL];
      size = generater.ints(1, CARD_TOTAL + 1).findFirst().getAsInt();
      int numTotal = 0;
      
      for (int i = 0; i < size - 1; i++)
      {
         int numCur = generater.ints(1, CARD_TOTAL - (size - i - 1) - numTotal).findFirst().getAsInt();
         nums[i] = numCur;
         numTotal += numCur;
      }
      nums[size - 1] = CARD_TOTAL - numTotal;

      assert isValidSolitaireBoard();
   }
  
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration 
      according to the rules of Bulgarian solitaire: Takes one card from each
      pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
   */
   public void playRound() {
      int countZero = 0;

      for (int i = 0; i < size; i++)
      {
         nums[i]--;
      }
      for (int i = 0; i < size; i++)
      {
         nums[i - countZero] = nums[i];
         if (nums[i] == 0)
            countZero++;
      }
      for (int i = 0; i < countZero; i++)
      {
         nums[size - i - 1] = 0;
      }
      nums[size - countZero] = size;
      size = size - countZero + 1;

      assert isValidSolitaireBoard();
   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, 
      there are NUM_FINAL_PILES piles that are of sizes 
      1, 2, 3, . . . , NUM_FINAL_PILES, 
      in any order.
   */
   
   public boolean isDone() {
      int[] tmpNums = new int[CARD_TOTAL];

      for (int i = 0; i < NUM_FINAL_PILES; i++)
      {
         if (nums[i] == 0)
            return false;
         tmpNums[nums[i] - 1] = nums[i];
      }
      for (int i = 0; i < NUM_FINAL_PILES; i++)
      {
         if (tmpNums[i] != i + 1) 
            return false;
      }
      System.out.println("Done!");

      return true;  // dummy code to get stub to compile      
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {
      String numsString = "";

      for (int i = 0; i < size; i++)
         numsString += nums[i] + " ";

      return numsString;   // dummy code to get stub to compile
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
   */
   private boolean isValidSolitaireBoard() {
      boolean judgement = true;
      int sum = 0;
      
      if (nums.length != CARD_TOTAL)
         return false;
      if (size <= 0 || size > CARD_TOTAL)
         return false;
      for (int i = 0; i < size; i++)
      {
         if (nums[i] <= 0)
            return false;
         sum += nums[i];
      }
      if (sum != CARD_TOTAL)
         return false;

      return true;  // dummy code to get stub to compile

   }
   

   // <add any additional private methods here>


}
