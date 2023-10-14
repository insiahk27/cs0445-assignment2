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

    /** Add a new Object to the logical start of the sequence in O(1) time
	 * @param item the item to be added.
	 * go through the back of the sequence --> badcab --> 
	 * --> first item is going to be in the array{item} spot and then first item is changed at the end --> size++
	 * if there is a node in the array index --> then you add to head 
	 * can use node(first, next) for the push
	 */
	public void push(T item){
		if(size==0){
			firstItem = lastItem = item;
		}
		else {
			if(array[indexInAlphabet(item)]==null)
				array[indexInAlphabet(item)] = new Node(indexInAlphabet(firstItem));
			else{
				Node temp = new Node(indexInAlphabet(firstItem), array[indexInAlphabet(item)]);
				array[indexInAlphabet(item)] = temp;
			}
			firstItem = item;
		}
		size++; 
	}

    /** Delete the item at the logical start of the sequence in O(1) item
	 * @return the deleted item
	 * @throws EmptySequenceException if the sequence is empty
	 */

	/**
	 * go to the index of the first item in the array 	
	 * then you go to the last node of the linked list in that index
	 * make that last item the first item 
	 * (find the last item through making sure the current.next.next!=null) --> when it is then you make that current.next the first item 
	 * and then you put current.next = null
	 * check to make sure the size is not equal to 1 --> if it is then current = null;
	 * when you get to that index in the array --> the head becomes the new firstItem and then the array{index} points to head.next = array[].next
	 * in the node you are storing the alphabet index value
	 */
	public T pop(){
		T original = firstItem;
		if(size==0){
			throw new EmptySequenceException("nothing");
		}
		else if(size==1){
			firstItem = lastItem = null;
		}
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

	// make index in alphabet
	// can make getFrequenceOf(single) if you modify node class
	// add methods/variables to the node class if you want
	// added another constructor for it

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
     * The running time is O(frequency of item in sequence).
	 * @param item an T item
	 * @return the number of occurences in the sequence of item
	 * freq=0 or 1 if last.equaals(item);
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
	 * item).
	 * @param first the first item in the ordered pait
	 * @param second the second item in the ordered pair
	 * @return the number of occurences in the sequence of (first, second)
	 * successors(first) --> go through linked list through loop and then use a counter 
	 */
	public int getFrequencyOf(T first, T second){
        int counter = 0;
		
		Node current = array[indexInAlphabet(first)];
		while(current!=null){
			if(alphabet[current.item]==second)
				counter++;
			current = current.next;
		}
		return counter;
    }

	/**
	 * Returns an array of all unique successors of an item in the sequence.
	 * The running time is O(frequency of item in sequence * number of 
     * predecessors)
	 * @param item an item
	 * @return an array of all unique predecessors of item or null if 
     * item doesn't exist in the sequence.
	 * add(3) & add(1)
	 */
	public T[] successors(T item){
		if(getFrequencyOf(item)==0)
			return null;

		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[getFrequencyOf(item)];
		Node current = array[indexInAlphabet(item)];
		int counter = 0;
		
		while(current!=null){
			int tempCount = 0;
			for(int i=0; i<=counter; i++){
				if(temp[i]==alphabet[current.item])
					tempCount++;
			}
			if(tempCount==0){
				temp[counter] = alphabet[current.item];
				counter++;
			}
			current = current.next;
		}

		@SuppressWarnings("unchecked")
		T[] temp2 = (T[]) new Object[counter];
		for(int i=0; i<temp2.length; i++){
			if(temp[i]!=null)
				temp2[i] = temp[i];
		}
		return temp2;
    }

    private static class Node {
        private int item; //index in alphabet of item
        private Node next;
    
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