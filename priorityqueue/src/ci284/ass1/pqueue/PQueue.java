/** Assignment 1: Complexity of the insert statement
 * The complexity of the insert method is O(N). It is O(1) if the list is empty because this would mean only having to add one item and not having to carry out 
 * anything else. The method is O(N) because when the list is populated and the new item's position is in the middle or anywhere else, the method would need to 
 * iterate through the list to find the correct position then rearrange the pointers.
 * 
 * The insert method has two if statements because there are two possible conditions when inserting into the list. The two being the order of the list being 
 * ascending or descending, there is a minor difference between the two but they do the exact same thing. Although in the start of the insert method there is 
 * a segment which is made for if the list is empty so the best case scenario would be that the list is empty, when this is the case the only thing that would
 * need to happen would be to set the new item as the head and set the next item as null.
 * 
 * The best case scenario for the default which is also descending is that the numbers being inserted are increasing, this means that 
 * all that would happen is a number would be inserted and the only thing that would need to happen is the pointers will just need to be shuffled 
 * around and the head will just need to be reassigned to the newest item with the highest priority.
 * 
 * Average scenario would be the new item would either be next to the first item this would mean that the loop will not have to iterate through the rest of the 
 * list to find the position to then assign the new item it's place and reassign the pointers.
 * 
 * On the other hand the worst case scenario would be the new item is decreasing especially when there are numerous items on the list already, it would 
 * mean having to iterate through the list to find the correct place for the item. Although this would eliminate the process of having to reassign the head
 * there would need to be a loop to go through the list until it is found which would make the insert a O(N) notation.
 * 
 * Difference between space and time complexity:
 * Space and time complexity are different measures of the efficiency of program or algorithm. Space measuring "the amount of working storage an algorithm needs"
 * (https://www.cs.northwestern.edu/academics/courses/311/html/space-complexity.html). Taking the example of the insert method, again it would be O(N) due to the 
 * fact that it is an insert, this means that when a new item is added more memory is used. The best case scenario in terms of complexity for the insert would be very little
 * in the list or null or one. The average case would be two in the list and the worst case would be plenty of items in the list, using up a lot of memory.
 * 
 * Time measures "the amount of time taken by an algorithm to run as a function." (https://en.wikipedia.org/wiki/Time_complexity) In this case with the insert method, 
 * it is O(N), this is because when a new item is added the method would need to iterate. The best case scenario would be the list is empty, so all that would be
 * carried out is the item gets added and set as the list. The average case scenario is that the item that needs to be added is already next to the first element and worst
 * case scenario being the new item that needs to be added is in the middle of the list (the list having many items) because this means having to iterate through the list
 * to find the position then rearrange the pointers. 
 * 
 * Conclusion:
 * This project has taught numerous things about linked list and has helped me to understand the way in which a list functions. The hardest part of this assignment was the
 * insert, it took a lot of time, effort and trial and error. The key for me understanding it was to think about it physically this then allowed me to understand how the 
 * items in the list behaved.
 **/

package ci284.ass1.pqueue;

public class PQueue<T> {

	private PQueueItem<T> head;
	public static enum ORDER {
		ASC, DESC;
	}
	public static ORDER DEFAULT_ORDER;
	private ORDER order;
	private int itemTotal = 0;

	/**
	 * The default constructor for a PQueue, with the default order for priorities
	 */
	public PQueue() {
		/**
		 * head = null to make a new list
		 * set default order as desc
		 **/
		//when creating a new list the head is set as null to "erase" anything already on the list
		this.head = null;
		//setting the default order to desc
		this.order = ORDER.DESC;
	}

	/**
	 * The constructor to make a new PQueue with a given order
	 * @param order
	 */
	public PQueue(ORDER order) {
		/**
		 * set head as null to make a new list
		 * set the order as order
		 **/
		//when creating a new list the head is set as null to "erase" anything already on the list
		this.head = null;
		//setting the order to the order selected
		this.order = order;
	}

	/**
	 * Remove and return data from the item at the front of the queue
	 * @return
	 */
	public T pop() {
		/**
		 * if head null 
		 * return null
		 * else
		 * create previousHead T that is current head
		 * set the data after head as head
		 * return previousHead T
		 * itemTotal--
		 * */
		//begin with check if head is null 
		if (head == null) {
			return null;
		} 
		else {
			//creating an item with a name 'previoushead' to hold data of current head
			T previousHead = head.getData();
			//retrieve the item next to head and set it as new head
			head = head.getNext();
			//decrement the itemTotal because it is now one item less
			itemTotal--;
			//return the the previous head as an obj
			return previousHead;
		}
	}

	/**
	 * Return the data from the item at the front of the queue, without removing the item itself
	 * @return
	 */
	public T peek() {
		/**
		 *if head null return null
		 *else get head data
		 **/
		//check if head is empty
		if (head == null) {
			//return null
			return null;
		}
		//if head is not empty = list is not empty
		else{
			//retrieve the current head's data
			return head.getData();
		}

	}

	/**
	 * Remove and return the item at the front of the queue
	 * @return
	 */
	public PQueueItem<T> popItem() {
		/**
		 *if head is null
		 *return null
		 *else
		 *create an object and set it as poppedHead
		 *set the next item from the head as the new head
		 *return poppedHead
		 **/
		//check if the head is null because this means the list is empty
		if (head == null) {			
			//returns null because list is empty
			return null;
		}
		//if not empty
		else {
			//create an object named poppedHead and give it the data of the current head
			PQueueItem<T> poppedHead = head;
			//retrieve the next item on the list and set it as the head
			head = head.getNext();
			//decrement totalItems because one item has been taken off the list
			itemTotal--;
			//return the poppedHead object
			return (poppedHead);
		}
	}

	/**
	 * Return the item at the front of the queue without removing it
	 * @return
	 */
	public PQueueItem<T> peekItem() {
		/**
		 *if head is null
		 *return null
		 *else return head
		 **/
		//check if head is null because this would mean that the list is null
		if (head == null) {
			//return null if list is null
			return null;
		}
		//if list is not null
		else {
			//return the current head's data of the list
			return head;
		}
	}

	/**
	 * Insert a new item into the queue, which should be put in the right place according to its priority.
	 * That is, is order == ASC, then the new item should appear in the queue before all items with a
	 * HIGHER priority. If order == DESC, then the new item should appear in the queue before all items
	 * with a LOWER priority.
	 * @param data
	 * @param priority
	 */
	public void insert(T data, int priority) {
		//declaring anyting inserted as an item named newItem: it uses the variables input when this method is used
		PQueueItem<T> newItem = new PQueueItem<T>(data, priority);
		//firstly checks if current head is null, because this would mean that the list is empty
		if (head == null) {
			//new item is set as the head because the list is empty
			head = newItem;
			//next is set as null because first link is always null
			newItem.setNext(null);
			//itemTotal var is incremented because a new item is being added
			itemTotal++;
		} 
		else {
			//two further items are declared and used as pointers with name of previous and next for clarity 
			PQueueItem<T> next = head;
			PQueueItem<T> previous = head;
			//if order declared is desc
			if (order == ORDER.DESC) {	
				// while loop is to check if the next item is null
				while (next != null){
					//if newItem has a greater priority than next
					if (newItem.getPriority() > next.getPriority()) {
						//break if new item has greater priority than the next and go to while
						break;
					}
					//if new item has less priority than the next
					else {
						//if the newItem has less priority than the next set previous as next and set next as the data next to next
						previous = next;
						//retrieve the element next to next and set it as next
						next = next.getNext();
					}
				}
				//once the loop has found an item that is null set it as the last element
				newItem.setNext(next);
				//if the new item has got greater priority than the current head
				if (newItem.getPriority() > head.getPriority()) {
					//make the head the new item
					head = newItem;
				}
				//if the new item has less priority than the current head set new item as the next item to head
				else previous.setNext(newItem);
			}
			//if the order is asc
			else if (order == ORDER.ASC) {
				//while next item is not null iterate
				while (next != null) {
					//if the new item has less priority than next
					if (newItem.getPriority() < next.getPriority()) {
						//break if new item has greater priority than the next
						break;
					}
					else {
						//if the newItem has less priority than the next set previous as next and set next as the data next to next
						//continue to iterate until the next item is null
						previous = next;
						next = next.getNext();
					}
				}
				//when next is null then this is the last item so set it as the next item to newItem
				newItem.setNext(next);
				//if new item has less priority than the current head
				if (newItem.getPriority() < head.getPriority()) {
					//set the new item as the head because it's priority is less therefor the lowest must be at the beggning
					head = newItem;
				}
				//if the new item has a greater priority than the current head then set previous next item as the newItem
				//example of this would be:
				//previous is 98 and newItem is 99 (if you think about the number is priority)
				//so 98 will come first in ASC order so newItem is next
				else previous.setNext(newItem);
			}
			//this is an insert method therefor there will be an incremenet in the item total value
			itemTotal++;
		}
	}

	/**
	 * Return the length of the queue
	 * @return
	 */
	public int length() {
		//this will just call the item variable and return an int
		return itemTotal;
		//throw new UnsupportedOperationException("Method not implemented");
	}

	public String toString() {
		int i = length();
		PQueueItem<T> current = head;
		StringBuffer sb = new StringBuffer();
		while (i > 0) {
			sb.append(current.toString());
			if (i > 1)
				sb.append(": ");
			current = current.getNext();
			i--;
		}
		return sb.toString();
	}

}

