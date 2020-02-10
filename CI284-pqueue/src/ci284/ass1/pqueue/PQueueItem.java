package ci284.ass1.pqueue;

public class PQueueItem<T> {
	
	private int priority;
	private T data;
	private PQueueItem<T> next;
	
	public PQueueItem(T data, int priority) {
		this.data = data;
		this.priority = priority;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public PQueueItem<T> getNext() {
		return next;
	}
	public void setNext(PQueueItem<T> next) {
		this.next = next;
	}
	
	public String toString() {
		return String.format("[%s,%d]", data.toString(), priority);
	}

}

