
public class MyQueue {
	/**
	 * 
	 */
	public myNode head;
	/**
	 * 
	 */
	public myNode tail;
	/**
	 * 
	 */
	public myNode current;
	private int numNodes;
	private int capacity;
	
	public MyQueue(int numBuffers) {
		this.tail = new myNode(null, null, null);
		this.current = this.tail;
		this.head = new myNode(null, null, null);
		this.head.setNext(tail);
		this.tail.setPrevious(head);
		this.capacity = numBuffers;
		this.numNodes = 0;
	}
	
	public void add(myBlock mb) {
		this.current = new myNode(mb, head, head.next());
		this.head.setNext(current);
		this.current.next().setPrevious(current);
		numNodes++;
	}
	
	public myBlock remove() {
		if (this.numNodes == 0) {
			return null;
		}
		this.current = this.tail.previous();
		myBlock output = current.block();
		current.previous().setNext(tail);
		tail.setPrevious(current.previous());
		current = head.next();
		this.numNodes--;
		return output;
	}
	
	public myBlock search(int index) {
		myBlock output = null;
		myNode temp = this.head.next();
		while (temp.block() != null) {
			if (temp.block().index() == index) {
				output = temp.block();
				break;
			}
			temp = temp.next();
		}
		return output;
	}
	
	public int size() {
		return this.numNodes;
	}
	
	public void setSize(int n) {
		this.numNodes = n;
	}
	
	public int capacity() {
		return this.capacity;
	}
	
	public void setCapacity(int c) {
		this.capacity = c;
	}
}
