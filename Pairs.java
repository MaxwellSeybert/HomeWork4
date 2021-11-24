
public class Pairs {
	private int start, end;
	public Pairs(int start, int end) {
		this.start = start;
		this.end = end;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public void printInfo() {
		System.out.println("start: " + start +" end: "+ end);
	}
	
}
