/** A class that holds the functions of the a 1D array of linked lists.
 * @author Insiah Kizilbash
 * Contains the functions for the the 1D array as well as the sequence such pushing and popping elements
 * from the array. It also utilizes the SequenceInterface<T>.
 * @version  JDK 17.0.8
 */
public class LinkedDS<T> implements SequenceInterface<T> {

    private Node[] array; //1-D array of linked lists
    private int size; //the number of items in the sequence
    private T[] alphabet; //the possible item values (e.g., the decimal digits)
    private T firstItem; //the first item
    private T lastItem; //the last item
  
    public LinkedDS(T[] alphabet){
		array = new Node[alphabet.length];
		size = 0;
		this.alphabet = alphabet;
		firstItem = lastItem = null;
    }

	// Add a new Object to the logical end of the SequenceInterface<T>. 
	//  * Checks to see whether there is anything in the sequence or not. 
	//  * If there is something in the seqeunce, then go to position in 2D
	//  * array based on the lastItem in the sequence and the item given
	//  * and then adds to a bag if it already exists or creates a new bag 
	//  * if there is nothing in that spot.

    /** Add a new Object to the logical start of the sequence in O(1) time. 
	 * Checks to see whether there is anything in the sequence or not. If not, 
	 * that item becomes the firstItem. Otherwise, it goes to the item index in 
	 * array and then adds the firstItem to that index to the top of the linked list. 
	 * @param item the item to be added.
	 */
	public void push(T item){
		if(size==0){
			firstItem = lastItem = item;
		}
		else {
			/** If there is nothing in the linked list, the firstItem is initialized to the
			 * array item index's head. There is a temporary node created which points to the current 
			 * head of the item index in the array and then afterwards the temp node is initialized 
			 * to the head of the list.*/ 

			int itemIndex = indexInAlphabet(item);
			int firstIndex = indexInAlphabet(firstItem);

			if(array[itemIndex]==null)
				array[itemIndex] = new Node(firstIndex);
			else{
				Node temp = new Node(firstIndex, array[itemIndex]);
				array[itemIndex] = temp;
			}
			firstItem = item;
		}
		size++; 
	}

    /** Delete the item at the logical start of the sequence in O(1) item. Checks to see whether the
	 * sequence is empty or not and throws and exception if it is. Otherwise, if there is only one item
	 * in the sequence then first and last item's become null. In all other cases, you go to the item's 
	 * linked list in the array and delete the head node.
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
	 */
	public T pop(){
		T original = firstItem;
		if(size==0){
			throw new EmptySequenceException("nothing");
		}
		else if(size==1){
			firstItem = lastItem = null;
		}
		/** The new first item is the next node after the head node in the array item index's linked list.
		 * So the head node becomes the original firstItem.next.
		*/
		else {
			int index = indexInAlphabet(original);
			firstItem = alphabet[array[index].item];
			array[index] = array[index].next;
		}
		size--;
		return original;
    }

	/** Return the index of a given item in the alphabet of the sequence. Loops through
	 * the alphabet array to check to see if the item exists within the possible given item values.
	 * @param item an item
	 * @return the index of item in the alphabet of the sequence
	 * or -1 if the item doesn't exist.
	 */
	public int indexInAlphabet(T item){
		int result = -1;
		for(int i=0; i<alphabet.length; i++){
			if(alphabet[i].equals(item))
				result = i;
		}
		return result;
    }

	/** Check if the sequence is empty in O(1) time
	 * @return true if the sequence is empty, and false otherwise
	 */
	public boolean isEmpty(){
		return size==0;
    }

	/** Return the number of items in the sequence in O(1) time
	 * @return the number of items currently in the sequence
	 */
	public int size(){
        return size;
    }

	/**
	 * @return the the logically first item in the sequence in O(1) time
	 */
	public T first(){
        return firstItem;
    }

	/**
	 * @return the the logically last item in the sequence in O(1) time
	 */
	public T last(){
        return lastItem;
    }

    /** Return the number of times in the sequence that an item appears.
     * The running time is O(frequency of item in sequence). A counter is 
	 * unsed to keep track of how many times the item occurs. If the lastItem 
	 * is also equal to the item, the counter is incremented too. Loops through 
	 * the linked list of the array item index. Counter is incremented for each 
	 * non-null node in the list.
	 * @param item an T item
	 * @return the number of occurences in the sequence of item
	 */
	public int getFrequencyOf(T item){
        int counter = 0;
		if(lastItem.equals(item))
			counter++;
		
		Node current = array[indexInAlphabet(item)];
		while(current!=null){
			counter++;
			current = current.next;
		}
		return counter;
    }


	/** Return the number of times in the sequence that an ordered pair of 
	 * items appear in the sequence. The running time is O(frequency of first 
	 * item). A counter is used to keep track of the frequency. Loops through 
	 * the first parameter's array index linked list. Checks to see whether 
	 * the current node's item is equivalent to the second item parameter and 
	 * if it is, then the counter is incremented.
	 * @param first the first item in the ordered pait
	 * @param second the second item in the ordered pair
	 * @return the number of occurences in the sequence of (first, second)
	 */
	public int getFrequencyOf(T first, T second){
        int counter = 0;
		
		Node current = array[indexInAlphabet(first)];
		while(current!=null){
			if(alphabet[current.item].equals(second))
				counter++;
			current = current.next;
		}
		return counter;
    }

	/**
	 * Returns an array of all unique successors of an item in the sequence.
	 * The running time is O(frequency of item in sequence * number of 
     * predecessors. Creates a temporary array and loops through the array item 
	 * index's linked list and add the items each node into the temporary array.
	 * Afterwards, creates another temporary array and all the non-null items in 
	 * the original temporary array created are added to the new array and that array 
	 * is returned.
	 * @param item an item
	 * @return an array of all unique predecessors of item or null if 
     * item doesn't exist in the sequence.
	 */
	public T[] successors(T item){
		if(getFrequencyOf(item)==0)
			return null;

		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[getFrequencyOf(item)];
		Node current = array[indexInAlphabet(item)];
		int counter = 0; // to keep track of the number of non-null indexes in the temporary array
		
		/** Loops through the array item index's linked list. */
		while(current!=null){
			int tempCount = 0;
			/** A counter used to check whether the current node's
			 * item has been added to the temporary array yet. This 
			 * is to keep track to avoid duplicates.
			 */
			for(int i=0; i<=counter; i++){
				if(temp[i]==alphabet[current.item])
					tempCount++;
			}
			/** If there are not other occurances of this current item in the temporary
			 * array, then the current node's item is added to the array. */ 
			if(tempCount==0){
				temp[counter] = alphabet[current.item];
				counter++;
			}
			current = current.next;
		}

		/** Loops through the new temporary array created and the original temporary array
		 * that was created. The non-null entries in the original temporary array are all
		 * copied into the new temporary array.
		 */
		@SuppressWarnings("unchecked")
		T[] temp2 = (T[]) new Object[counter]; // array for the final successors of item
		for(int i=0; i<temp2.length; i++){
			temp2[i] = temp[i];
		}
		return temp2;
    }

    private static class Node {
        private int item; //index in alphabet of item
        private Node next; // the reference to the next node
		//int size = 1 ---> if null otherwise prevHead.size+1
		// Node newHead = new Node(item, prevhead, prevHead.size+1)
		// otherwise for the first one Node newHead = new Node(item);
    
        private Node(int item){
            this.item = item;
            next = null;
        }

		private Node(int item, Node next){
			this.item = item;
			this.next = next;
		}
    }
}