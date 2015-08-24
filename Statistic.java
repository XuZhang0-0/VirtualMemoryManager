
public class Statistic {
	private int total;
	private int pageFaultCount;
	private int TLBHitCount;
	
	public Statistic(){
		total = 0;
		pageFaultCount = 0;
		TLBHitCount = 0;
	}
	
	public void totalIncrease(){
		total++;
	}
	
	public void PageFault(){
		pageFaultCount++;
	}
	
	public void TLBhit(){
		TLBHitCount++;
	}
	
	public int getPF(){
		return pageFaultCount;
	}
	
	public int getTLB(){
		return TLBHitCount;
	}
	
	public int getTotal(){
		return total;
	}
}
