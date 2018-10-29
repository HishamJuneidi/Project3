/**
 * stores buffers for buffer pool
 * @author risha97, hishamj6
 * @version 10/29/2018
 */
public class MyQueue {
    
    private MyNode head;
    private MyNode tail;
    private MyNode current;
    private int numNodes;
    private int capacity;
    
    /**
     * constructor for MyQueue
     * @param numBuffers maxx number of buffers
     */
    public MyQueue(int numBuffers) {
        this.tail = new MyNode(null, null, null);
        this.current = this.tail;
        this.head = new MyNode(null, null, null);
        this.head.setNext(tail);
        this.tail.setPrevious(head);
        this.capacity = numBuffers;
        this.numNodes = 0;
    }
    
    /**
     * adds new block to head of queue
     * @param mb block being added
     */
    public void add(MyBlock mb) {
        this.current = new MyNode(mb, head.next(), head);
        this.head.setNext(this.current);
        this.current.next().setPrevious(this.current);
        numNodes++;
    }
    
    /**
     * removes block from tail
     * @return block that has been removed
     */
    public MyBlock remove() {
        if (this.numNodes == 0) {
            return null;
        }
        this.current = this.tail.previous();
        MyBlock output = this.current.block();
        this.current.previous().setNext(this.tail);
        this.tail.setPrevious(this.current.previous());
        this.current = this.head.next();
        this.numNodes--;
        return output;
    }
    
    /**
     * searches for a block at a specific index
     * @param index place in file being looked for
     * @return block that is at the index, or null
     * if that block is not in the queue
     */
    public MyBlock search(int index) {
        MyBlock output = null;
        MyNode temp = this.head.next();
        while (temp.block() != null) {
            if (temp.block().index() == index) {
                output = temp.block();
                break;
            }
            temp = temp.next();
        }
        return output;
    }
    
    /**
     * gets number of nodes in queue
     * @return current size of queue
     */
    public int size() {
        return this.numNodes;
    }
    
    /**
     * sets number of nodes
     * @param n new number of nodes
     */
    public void setSize(int n) {
        this.numNodes = n;
    }
    
    /**
     * gets capacity of queue
     * @return capacity of queue
     */
    public int capacity() {
        return this.capacity;
    }
    
    /**
     * sets max capacity
     * @param c new max capacity
     */
    public void setCapacity(int c) {
        this.capacity = c;
    }
}
