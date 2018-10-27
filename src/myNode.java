
public class myNode {
	private myBlock b;
	private myNode next;
	private myNode previous;
	
	public myNode(myBlock mb, myNode n, myNode p) {
		this.b = mb;
		this.next = n;
		this.previous = p;
	}
	
	public myNode next() {
		return this.next;
	}
	
	public void setNext(myNode n) {
		this.next = n;
	}
	
	public myNode previous() {
		return this.previous;
	}
	
	public void setPrevious(myNode p) {
		this.previous = p;
	}
	
	public myBlock block() {
		return this.b;
	}
	
	public void setBlock(myBlock mb) {
		this.b = mb;
	}
}
