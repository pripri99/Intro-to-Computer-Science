// STUDENT_NAME:Priscilia Momo
// STUDENT_ID:260722807

import java.util.*;
import java.io.*;



public class Scrabble{

    static HashSet<String> myDictionary; // this is where the words of the dictionary are stored

    // DO NOT CHANGE THIS METHOD
    // Reads dictionary from file
    public static void readDictionaryFromFile(String fileName) throws Exception {
        myDictionary=new HashSet<String>();

        BufferedReader myFileReader = new BufferedReader(new FileReader(fileName) );

        String word;
        while ((word=myFileReader.readLine())!=null) myDictionary.add(word);

 myFileReader.close();
    }



    /* Arguments: 
        char availableLetters[] : array of characters containing the letters that remain available
        String prefix : Word assembled to date
        Returns: String corresponding to the longest English word starting with prefix, completed with zero or more letters from availableLetters. 
          If no such word exists, it returns the String ""
     */
     public static String longestWord(char availableLetters[], String prefix) {
        
  /* WRITE YOUR CODE HERE */
  String longest = "";
  if (myDictionary.contains(prefix)){ //when the prefix is a word in dictionary the "longest word" become the prefix
   longest = prefix;
  }
  for (int i=0; i<availableLetters.length; i++){ // loop into the available letter at the beginning
   String newPrefix = prefix + availableLetters[i]; // at the beginning, a new prefix is created with the prefix and of the available letter 
   char[] newAvailableLetters= new char[availableLetters.length-1]; // a new set of available letter is created excluding the letter added to the prefic
   for (int j=0; j<i; j++) newAvailableLetters[j]=availableLetters[j];
   for (int k=i; k<availableLetters.length-1; k++) newAvailableLetters[k]=availableLetters[k+1];
   String word = longestWord(newAvailableLetters, newPrefix); // the temporary variable word will take as value the "longest word" returned by recursion
   if ((myDictionary.contains(word) && (word.length() > longest.length()))){ // the word returned will become the "true longest word" only if is in the dictionary and is longer than the string already in longest
     longest = word;
    }
   }
  return longest;
  }

    
    
    /* main method
        You should not need to change anything here.
     */
    public static void main (String args[]) throws Exception {
       
 // First, read the dictionary
 try {
     readDictionaryFromFile("englishDictionary.txt");
        }
        catch(Exception e) {
            System.out.println("Error reading the dictionary: "+e);
        }
        
        
        // Ask user to type in letters
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in) );
        char letters[]; 
        do {
            System.out.println("Enter your letters (no spaces or commas):");
            
            letters = keyboard.readLine().toCharArray();

     // now, enumerate the words that can be formed
            String longest = longestWord(letters, "compu");
     System.out.println("The longest word is "+longest);
        } while (letters.length!=0);

        keyboard.close();
        
    }
}