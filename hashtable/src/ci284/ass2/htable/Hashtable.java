/**
 * Complexity:
 * The complexity of my functions are both O(n) (worst case) because they are not complete. If they were they would be O(1), but because the functions are not complete it has resulted
 * in my functions looking at the hash table's elements one by one which is explained. Later in this report.
 * 
 * Complexity of 'get()':
 * The complexity of both my the 'get()' and 'put()' methods are O(n). This is due to the fact that both are incrementing at a constant rate looking at the items in the hash table
 * one by one. The best case scenario for the 'get' function is if the provided key does not exist, this will mean that the loop does not need to happen, null will be returned.
 * Alternatively, the other best case scenario is if the value associated is found at the hashed value, this will mean that it will be found in the first step. On the other hand the
 * worst case scenario would be is if the associated value is found at the very end of the hash table meaning that loop would need to go on until the end.
 * 
 * Complexity of 'put()':
 * The best case scenario for the 'put' function would be if the hash table was empty, this would mean that there would be no need for a resize but only the creation of the new pair.
 * The average scenario would be if there were only one item in the table, and not the same value this would mean that again there would be no need to resize only the creation of 
 * the new pair, also no overwriting. The worst case scenario would be firstly the load factor exceeds the maxLoad meaning there would be a call for a resize, the key already exists 
 * and again the loop having to loop to the very last place to find an empty slot.
 * 
 * Load factor increase?:
 * When this variable increases, it means that the table is filled up to the max making it memory extensive. This will increase variables that show the efficiency of a hash table such 
 * as the lookup and time. By increasing the load factor this increases the quantity inside the table having a resulting effect of increasing loop up time as well as hash time.
 * 
 * Worst case scenario for maxLoad to be set as '1.0':
 * If the load factor is set to one this decreases the number of free buckets and increases the number of collisions increasing the need to rehash and also increasing the memory needed.
 * The worst case scenario would be too many elements hashed to the same keys, making this O(n) having to look through each key with numerous elements, furthermore if the hash table
 * is full and numerous elements were hashed with the same key this will mean having to look through each key which has numerous elements. This is because of the large collection to 
 * look through.
 */


package ci284.ass2.htable;

/**
 * A HashTable with no deletions allowed. Duplicates overwrite the existing value. Values are of
 * type V and keys are strings -- one extension is to adapt this class to use other types as keys.
 * 
 * The underlying data is stored in the array `arr', and the actual values stored are pairs of 
 * (key, value). This is so that we can detect collisions in the hash function and look for the next 
 * location when necessary.
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class Hashtable<V> {

	private Object[] arr; 												//an array of Pair objects, where each pair contains the key and value stored in the hashtable
	private int max; 													//the size of arr. This should be a prime number
	private int itemCount; 												//the number of items stored in arr
	private final double maxLoad = 0.6; 								//the maximum load factor

	public static enum PROBE_TYPE {
		LINEAR_PROBE, QUADRATIC_PROBE, DOUBLE_HASH;
	}

	PROBE_TYPE probeType; 												//the type of probe to use when dealing with collisions
	private final BigInteger DBL_HASH_K = BigInteger.valueOf(8);

	/**
	 * Create a new Hashtable with a given initial capacity and using a given probe type
	 * @param initialCapacity
	 * @param pt
	 */
    public Hashtable(int initialCapacity, PROBE_TYPE pt) 
    {
        if (isPrime(initialCapacity) == true)							//Checking if the input is prime
        {
        this.max = initialCapacity;										//If true assign the input as MAX
        }
        else
        {
            this.max = nextPrime(initialCapacity);						//otherwise look for next prime and set as MAX
        }
        arr = new Object[this.max];										//Declaration of new array with the input as size
        this.probeType = pt;											//Probe type as input probe
    }
   
    /**
     * Create a new Hashtable with a given initial capacity and using the default probe type
     * @param initialCapacity
     */
    public Hashtable(int initialCapacity) {
        if (isPrime(initialCapacity) == true)							//Checking if the input is prime
        {
        this.max = initialCapacity;										//If true assign the input as MAX
        }
        else
        {
            this.max = nextPrime(initialCapacity);						//otherwise look for next prime and set as MAX
        }
        arr = new Object[this.max];										//Declaration of new array with input as size
        this.probeType = PROBE_TYPE.LINEAR_PROBE;						//Probe type is default linear
    }

	/**
	 * Store the value against the given key. 
	 * If the loadFactor exceeds maxLoad, call the resize method to resize the array. 
	 * If key already exists then its value should be overwritten.
	 * Create a new Pair item containing the key and value, then use the findEmpty method to find an unoccupied 
	 * position in the array to store the pair. Call findEmmpty with the hashed value of the key as the starting
	 * position for the search, stepNum of zero and the original key.
	 * containing   
	 * @param key
	 * @param value
	 */
	public void put(String key, V value) {
        if (getLoadFactor() > maxLoad)									//checks if load factor is > maxLoad
        {
            resize();
        }
        if(hasKey(key) == true)											//if key exist
        {
        	new Pair(key, value);										//create new pair
        	findEmpty(hash(key), 0, key);								//find next empty with has val of key, stepNum 0, original key
        	itemCount++;												//increase item count because of new pair
        }
	}

	/**
	 * Get the value associated with key, or return null if key does not exists. Use the find method to search the
	 * array, starting at the hashed value of the key, stepNum of zero and the original key.
	 * @param key
	 * @return
	 */
	public V get(String key) {
        if(hasKey(key) == false)										//if key does not exist 
        {	
        	return null;												//return null
        }		
        return find(hash(key), key, 0);									//using find to search the array 							
	}

    /**
     * Return true if the Hashtable contains this key, false otherwise
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        if (find(hash(key), key, 0) == null)							//if table does not contain key
        {
            return false;												//return false
        }
        return true;													//else return false
       
    }
	
    /**
     * Return all the keys in this Hashtable as a collection
     * @return
     */
	public Collection<String> getKeys() {						
        Collection<String> col = new ArrayList<String>();				//declaration of string collection with name col
        for (int i = 0; i < this.max; i++)								//loop through whole array
        {
            if(arr[i] != null)											//if element does not equal null
            {		
                Hashtable<V>.Pair pair = (Hashtable<V>.Pair) arr[i];	//get the key of the element in the hTable and assign it to the array
                String str = pair.key;									//declaration of string str as the key
                col.add(str);											//add the key 'str' to the array list
            }
        }
        return col;														//return the array list with all the keys
       
       
    }
 
    /**
     * Return the load factor, which is the ratio of itemCount to max
     * @return
     */
    public double getLoadFactor() {
        int i = itemCount/ max;											//to get load factor divide no. of items by the max
        return i;														//return i
    }
 
    /**
     * return the maximum capacity of the Hashtable
     * @return
     */
    public int getCapacity() {
        return max;														//return max
    }
   
    /**
     * Find the value stored for this key, starting the search at position startPos in the array. If
     * the item at position startPos is null, the Hashtable does not contain the value, so return null.
     * If the key stored in the pair at position startPos matches the key we're looking for, return the associated
     * value. If the key stored in the pair at position startPos does not match the key we're looking for, this
     * is a hash collision so use the getNextLocation method with an incremented value of stepNum to find
     * the next location to search (the way that this is calculated will differ depending on the probe type
     * being used). Then use the value of the next location in a recursive call to find.
     * @param startPos
     * @param key
     * @param stepNum
     * @return
     */
    @SuppressWarnings("unchecked")													//eclipse auto generated
    private V find(int startPos, String key, int stepNum) {    
		Hashtable<V>.Pair pair = (Hashtable<V>.Pair) arr[startPos];					//matching the key with the obj array with position as 'startPos' 
        if (arr[startPos] == null)													//if first element is null
        {	
            return null;															//return null
        }
        else if(arr[startPos] == key)												//else if the obj matches the key input
        {
            return (V)pair.value;													//return the value
        }
        else {		
            return find(getNextLocation(startPos, stepNum , key), key, stepNum);	//else get next location
        }
           
    }
 
    /**
     * Find the first unoccupied location where a value associated with key can be stored, starting the
     * search at position startPos. If startPos is unoccupied, return startPos. Otherwise use the getNextLocation
     * method with an incremented value of stepNum to find the appropriate next position to check
     * (which will differ depending on the probe type being used) and use this in a recursive call to findEmpty.
     * @param startPos
     * @param stepNum
     * @param key
     * @return
     */
    private int findEmpty(int startPos, int stepNum, String key) {
        if (arr[startPos] == null)														//if first element is null
        {
            return startPos;															//return start pos
        }
        else
        {
            return findEmpty(getNextLocation(startPos, stepNum , key), stepNum, key);	//otherwise get next location and check if it is empty, if so return
        }
       
    }
 
    /**
     * Finds the next position in the Hashtable array starting at position startPos. If the linear
     * probe is being used, we just increment startPos. If the double hash probe type is being used,
     * add the double hashed value of the key to startPos. If the quadratic probe is being used, add
     * the square of the step number to startPos.
     * @param i
     * @param stepNum
     * @param key
     * @return
     */
    private int getNextLocation(int startPos, int stepNum, String key) {
        int step = startPos;
        switch (probeType) {
        case LINEAR_PROBE:
            step++;
            break;
        case DOUBLE_HASH:
            step += doubleHash(key);
            break;
        case QUADRATIC_PROBE:
            step += stepNum * stepNum;
            break;
        default:
            break;
        }
        return step % max;
    }
 
    /**
     * A secondary hash function which returns a small value (less than or equal to DBL_HASH_K)
     * to probe the next location if the double hash probe type is being used
     * @param key
     * @return
     */
    private int doubleHash(String key) {
        BigInteger hashVal = BigInteger.valueOf(key.charAt(0) - 96);		//declaring a bigint as 'hashVal' as the left most character of the key
        for (int i = 1; i < key.length(); i++) {							//looping through the key
            BigInteger c = BigInteger.valueOf(key.charAt(i) - 96);			//declaring a bigint as 'c' with the value of element i - 96
            hashVal = hashVal.multiply(BigInteger.valueOf(27)).add(c);		//hashVal * (valueOf(27) + c)
        }
        return DBL_HASH_K.subtract(hashVal.mod(DBL_HASH_K)).intValue();		//return DBL_HASH_K - (hashVal % DBL_HASH_K)
    }
 
    /**
     * Return an int value calculated by hashing the key. See the lecture slides for information
     * on creating hash functions. The return value should be less than max, the maximum capacity
     * of the array
     * @param key
     * @return
     */
    private int hash(String key) {
        int hashVal = key.charAt(0) - 31;									//declaring hashVal as the left most character of the key
        for (int i = 0; i<key.length(); i++)								//looping through the key
        {
            int c = key.charAt(i) - 31;										//declaring c as different character of key working right
            hashVal = (hashVal * 94 + c) % this.max;						//keeping the hashVal size down
        }
        return hashVal;														//return hashVal
    }
 
    /**
     * Return true if n is prime
     * @param n
     * @return
     */
    private boolean isPrime(int n) {
        if ( n <= 2 )														//if n <= 2
        {
            return true;													//return true it is prime
        }
        if( n%2 == 0)														//if n % 2 == 0
        {
            return false;													//return false																					
        }
        for (int j = 3; j*j < n; j += 2)									//starting at 3 because case less than three is handled above							
        {
            if(n % j == 0)													//n % j == 0
            {
                return false;												//false
            }
        }
        return true;														//otherwise return true
    }
 
    /**
     * Get the smallest prime number which is larger than n
     * @param n
     * @return
     */
    private int nextPrime(int n) {
        int i = n + 1;														//input + 1
 
        for (int j = 0; j <= i; j++)										//increment j
        {
        	if(isPrime(j) == false)											//if j is not prime
        	{
        		i++;														//increase i
        	}
        	else															
        		break;														//else break
        }
        return i;															//return i
    }
 
    /**
     * Resize the hashtable, to be used when the load factor exceeds maxLoad. The new size of
     * the underlying array should be the smallest prime number which is at least twice the size
     * of the old array.
     */
    private void resize() {
        int i = nextPrime(max * 2);											//i = nextPrime(max * 2)
        Object[] temp = arr;												//clone the arr as 'temp'
        arr = new Object[i];												//new obj i in arr
        max = i;															//max set as i
 
        for (int j = 0; j < temp.length; j++)								//loop through temp
        {
            if (temp[j] != null)											//if element != null
            {
                Hashtable<V>.Pair pair = (Hashtable<V>.Pair) temp[j];		//get key and assign is at element i
                String str = pair.key;										//str as key
                V value = (V) pair.value;									//value as value of key
                put(str, value);											//store value with key
            }
        }
    }
 
   
    /**
     * Instances of Pair are stored in the underlying array. We can't just store
     * the value because we need to check the original key in the case of collisions.
     * @author jb259
     *
     */
    private class Pair {
        private String key;
        private Object value;
 
        public Pair(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
 
}