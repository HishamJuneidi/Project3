
public class Record {
	private short key;
	private short value;
	
	public Record(short k, short v) {
		this.key = k;
		this.value = v;
	}
	
	public short key() {
		return this.key;
	}
	
	public short value() {
		return this.value;
	}
}
