
public class Frame {
	private int data;
	private boolean isFree;
	private long timeStamp;
	
	public Frame(){
		data = 0;
		isFree = true;
		timeStamp = System.currentTimeMillis();
	}
	
	public void setData(int _data){
		data = _data;
	}
	
	public int getData(){
		return data;
	}
	
	public void setValid(boolean b){
		isFree = b;
	}
	
	public boolean getValid(){
		return isFree;
	}
	
	public void setTime(long newTime){
		timeStamp = newTime;
	}
	
	public long getTime(){
		return timeStamp;
	}
}
