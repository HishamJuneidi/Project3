/**
 * node that is stored in myQueue
 * @author risha97, hishamj6
 * @version 10/29/2018
 */
public class MyNode {
    private MyBlock b;
    private MyNode next;
    private MyNode previous;
    
    /**
     * constructor for myNode
     * @param mb value being stored
     * @param n next node
     * @param p previous node
     */
    public MyNode(MyBlock mb, MyNode n, MyNode p) {
        this.b = mb;
        this.next = n;
        this.previous = p;
    }
    
    /**
     * returns next node
     * @return next node
     */
    public MyNode next() {
        return this.next;
    }
    
    /**
     * sets next node
     * @param n new value of next node
     */
    public void setNext(MyNode n) {
        this.next = n;
    }
    
    /**
     * returns previous node
     * @return previous node
     */
    public MyNode previous() {
        return this.previous;
    }
    
    /**
     * sets previous node
     * @param p new value of previous node
     */
    public void setPrevious(MyNode p) {
        this.previous = p;
    }
    
    /**
     * returns data being stored
     * @return block being stored
     */
    public MyBlock block() {
        return this.b;
    }
    
}
