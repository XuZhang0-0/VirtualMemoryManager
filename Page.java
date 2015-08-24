
public class Page {
	private int frameNumber;
	private int access;
	private boolean valid;
	private int pageNumber;
	
	private long timeStamp;

	
	// constructor for TLB
	public Page(int _pageNumber, int _frameNumber){
		frameNumber = _frameNumber;
		pageNumber = _pageNumber;
		valid = false;
		access = 0;
		timeStamp = System.currentTimeMillis();
	}
	
	// constructor for page table
	public Page(){
		frameNumber = -1;
		access = 0;
		valid = false;
	}
	
	public void setFrameNumber(int _frameNumber){
		frameNumber = _frameNumber;
	}
	
	public int getFrameNumber(){
		return frameNumber;
	}

	public void setPageNumber(int _pageNumber){
		frameNumber = _pageNumber;
	}
	
	public int getPageNumber(){
		return pageNumber;
	}	
	
	public void incAccess(){
		access++;
	}
	
	public int getAccessTime(){
		return access;
	}
	
	public void setValid(boolean _valid){
		valid = _valid;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public void setTime(long newTime){
		timeStamp = newTime;
	}
	
	public long getTime(){
		return timeStamp;
	}
	
}
