
public class TLB {
	private Page[] tlb;
	
	public TLB(int entries){
		tlb = new Page[entries];
		for(int i = 0; i < entries; i++){
			tlb[i] = new Page(-1,-1);
		}
	}
	
	public void update(int pageNum, int frameNum){
		// TLB has free entry
		if(this.hasFreeEntry()){
			int index = this.getFreeEntry();
			this.setInfo(index, pageNum, frameNum);
		}
		// update the full TLB 
		else{
			int victim = this.LRU();
			this.setInfo(victim, pageNum, frameNum);
		}
		
	}
	
	public void setInfo(int index, int pageNum, int frameNum){
		tlb[index].setPageNumber(pageNum);
		tlb[index].setFrameNumber(frameNum);
		tlb[index].setTime(System.currentTimeMillis());
	}
	
	public boolean hasFreeEntry(){
		for(int i = 0; i < tlb.length; i++){
			if(tlb[i].getFrameNumber()<0){
				return true;
			}
		}
		return false;
	}
	
	
	public int getFreeEntry(){
		int index = -1;
		for(int i = 0; i < tlb.length; i++){
			if(tlb[i].getFrameNumber()<0){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int[] find(int pageNum){
		int[] ans = new int[3];
		for(int i = 0; i < ans.length; i++){
			if(tlb[i].getPageNumber() == pageNum){
				ans[0] = 1;
				ans[1] = tlb[i].getFrameNumber();
				ans[2] = pageNum;
			}
			else
				ans[0] = 0;
		}
		return ans;
 	}
	
	
	public int LRU(){
		int ans = 0;
		long tmp = System.currentTimeMillis();
		for(int i = 0; i < tlb.length; i++){
			if(tlb[i].getTime() < tmp){
				tmp = tlb[i].getTime();
				ans = i;
			}

		}
		return ans;
	}

}
