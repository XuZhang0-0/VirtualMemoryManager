
public class PhysicalMemory {
	private Frame[] mainMemory;
	
	public PhysicalMemory(int frameNum){
		mainMemory = new Frame[frameNum];
		for(int i = 0; i < frameNum; i++){
			mainMemory[i] = new Frame();
		}
	}
	
	public boolean hasFree(){
		for(int i = 0; i < mainMemory.length; i++){
			if(mainMemory[i].getValid())
				return true;
		}
		return false;
	}
	
	public int getFreeFrame(){
		int frameNum = -1;
		for(int i = 0; i < mainMemory.length; i++){
			if(mainMemory[i].getValid() == true){
				frameNum = i;
				break;
			}
			
		}
		return frameNum;
	}
	

	
	public void setTime(int frameNum){
		mainMemory[frameNum].setTime(System.currentTimeMillis());
	}
	
	public int getData(int frameNum){
		return mainMemory[frameNum].getData();
	}
	
	public int LRU(){
		int frameNum = 0;
		long tmp = System.currentTimeMillis();
		for(int i = 0; i < mainMemory.length; i++){
			if(mainMemory[i].getTime()<tmp){
				tmp = mainMemory[i].getTime();
				frameNum = i;
			}

		}
		
		return frameNum;
	}
}
