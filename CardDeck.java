import java.util.*;

class CardDeck {
    LinkedList<Integer> deck;

    // constructor, creates a deck with n cards, placed in increasing order
    CardDeck(int n) {
	deck = new LinkedList<Integer> ();
	for (int i=1;i<=n;i++) deck.addLast(new Integer(i));
    }

    // executes the card trick
    public void runTrick() {

	while (!deck.isEmpty()) {
	    // remove the first card and remove it
		System.out.println(deck);
	    Integer topCard = deck.removeFirst();
	    System.out.println("Showing card "+topCard);

	    // if there's nothing left, we are done
	    if (deck.isEmpty()) break;
	    
	    // otherwise, remove the top card and place it at the back.
	    Integer secondCard = deck.removeFirst();
	    deck.addLast(secondCard);

	    System.out.println("Remaining deck: "+deck);

	}
    }



    public void setupDeck(int n) {
	/* WRITE YOUR CODE HERE */
    	LinkedList<Integer> tempDeck = new LinkedList<Integer> ();
    	tempDeck.addFirst(new Integer(1));
    	for(int i=n; i>1; i++){
    		Integer lastCard = tempDeck.removeLast();
    		tempDeck.addFirst(lastCard);
    		tempDeck.addFirst(new Integer(i));
    	}
    	//deck = tempDeck;
    	//System.out.println(deck);
    	
    }
 
    


    public static void main(String args[]) {
	// this is just creating a deck with cards in increasing order, and running the trick. 
	/*CardDeck d = new CardDeck(10);
	d.runTrick();*/

	// this is calling the method you are supposed to write, and executing the trick.
	CardDeck e = new CardDeck(0);
	e.setupDeck(10);
	e.runTrick();
    }
}

    