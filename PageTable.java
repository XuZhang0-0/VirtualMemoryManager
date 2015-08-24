
public class PageTable {
	private Page[] table;


	public PageTable(int manyPage){
		table = new Page[manyPage];
		for(int i = 0; i < manyPage; i++){
			table[i] = new Page();
		}
	}
	
	public boolean isValid(int pageNum){
		return table[pageNum].getValid();
		//return table[pageNum].getFrameNumber()==-1;
	}
	
	public void setPage(int pageNum, int frameNum){
		table[pageNum].setFrameNumber(frameNum);
		table[pageNum].setValid(true);
		table[pageNum].incAccess();
	}
	
	public void setPage(int pageNum, boolean b){
		table[pageNum].setFrameNumber(-1);
		table[pageNum].setValid(b);
		table[pageNum].incAccess();
	}
	
	public int find(int pageNumber){
		 return table[pageNumber].getFrameNumber();	 
	 }
	
	public int findPnum(int frameNumber){
		int ans = 0;
		for(int i = 0; i < table.length; i++){
			if(table[i].getFrameNumber() == frameNumber)
				return i;
		}
		return ans;
		
	}

}