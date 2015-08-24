/**
 * Memory Management Unit
 * Initialization of Page Table, TLB, Main memory, Statistic
 * 
 * @author Xu Zhang 
 * @version 2014/12/04
 */

import java.util.Stack;
import java.io.*;

public class MemoryManagementUnit {
	// instance variables
	private TLB tlb;
	private PageTable pageTable;
	private PhysicalMemory mainMemory;
	private Statistic report;
	
	/**
     * Constructor for objects of class MemoryManagementUnit
     */
	public MemoryManagementUnit(int pageNum, int frameNum){
		tlb = new TLB(16);
		pageTable = new PageTable(pageNum);
		mainMemory = new PhysicalMemory(frameNum);
		report = new Statistic();
	}
	
	/**
     * Convert decimal integer into binary string
     * 
     * @param	logicalAddress	a integer logical address
     * @return	String of binary code
     */
	public static String convert(int logicalAddress) {
        int copyOfInput = logicalAddress;
        Stack s = new Stack();
        StringBuilder sb = new StringBuilder();
 
        while (copyOfInput > 0) {
            s.push(Integer.toString(copyOfInput % 2));         
            copyOfInput = copyOfInput / 2;
        }
        while (!s.empty()){
        	sb.append(s.pop());
        }
        
        return sb.toString();
        
    }
	
	/**
     * Extract binary logical address
     * 
     * @param	s	a String of binary code
     * @return	String array, first item is offset, second is page number
     */
	public static String[] addressExtract(String s) {
        String[] ans = new String[2];
        if(s.length()>8){
        	ans[0] = s.substring(s.length()-8, s.length());
            ans[1] = s.substring(0, s.length()-8);
        }
        else{
        	ans[0] = s;
        	ans[1] = "0";
        }
        
        return ans;    
    }
	
	/**
     * Translate into physical Address 
     * no page replacement algorithm
     * 
     * @param	logicalAddress	integer logical address
     * @return	Integer array, first item is physical address, 
     * 			second is the signed byte value stored at the translated address
     */
	public int[] translation(int logicalAddress){
		report.totalIncrease();
		
		int[] a = new int[2];

		int frameNum, pageNum, data;
		String[] tmp = addressExtract(convert(logicalAddress));
		pageNum = Integer.parseInt(tmp[1], 2);
		String offset = tmp[0];
		
		// search TLB first
		int[] tlbResult = tlb.find(pageNum);
		
		// TLB hit, get the frame number & data stored at the frame
		if(tlbResult[0] == 1){
			frameNum = tlbResult[1];
			data = mainMemory.getData(frameNum);
			report.TLBhit();
		}
		// TLB miss
		else{
			// search the page table and meet a page fault
			if(!pageTable.isValid(pageNum)){
				
				report.PageFault();
			
				// read data from Backing_store.bin
				data = readByte(pageNum,Integer.parseInt(tmp[0], 2));
				
				// memory has free frame
				if(mainMemory.hasFree()){
					// get the frame number from main memory
					frameNum = mainMemory.getFreeFrame();
					
					// update the page table
					pageTable.setPage(pageNum, frameNum);
				}
				// memory is full use LRU algorithm
				else{
					// get the victim frame by using LRU algorithm
					frameNum = mainMemory.LRU();
					int oldPnum = pageTable.findPnum(frameNum);
					// update the page table
					pageTable.setPage(pageNum, frameNum);
					pageTable.setPage(oldPnum, false);
				}
				
			}
			// search the page table and get the frame number
			else{
				frameNum = pageTable.find(pageNum);
				data = mainMemory.getData(frameNum);
			}
			
			// update TLB table		
			/*// update the TLB if TLB has free entry
			if(tlb.hasFreeEntry()){
				int index = tlb.getFreeEntry();
				tlb.setInfo(index, pageNum, frameNum);
			}
			// update the full TLB 
			else{
				int victim = tlb.LRU();
				tlb.setInfo(victim, pageNum, frameNum);
			}*/
			
		}
		String p = convert(frameNum)+offset;
		a[0] = Integer.parseInt(p, 2);
		a[1] = data;
		
		return a;
		
	}

	/**
     * Translate into physical Address 
     * using LRU algorithm
     * 
     * @param	logicalAddress	integer logical address
     * @return	Integer array, first item is physical address, 
     * 			second is the signed byte value stored at the translated address
     */
	public int[] translationLRU(int logicalAddress){
		int[] a = new int[2];

		int frameNum, data;
		String phyAdd;
		String[] tmp = addressExtract(convert(logicalAddress));
		int pageNum = Integer.parseInt(tmp[1], 2);
		int offSet = Integer.parseInt(tmp[0], 2);
		String offset = tmp[0];
		
		// search TLB first
		int[] tlbResult = tlb.find(pageNum);
		
		// TLB hit, get the frame number & data stored at the frame
		if(tlbResult[0] == 1){
			frameNum = tlbResult[1];
			data = mainMemory.getData(frameNum);
			report.TLBhit();
		}
		// TLB miss
		else{
			// search the page table and meet a page fault
			if(pageTable.isValid(pageNum)){
				
				report.PageFault();
			
				// read data from Backing_store.bin
				data = readByte(pageNum,offSet);
				
				// memory has free frame
				if(mainMemory.hasFree()){
					// get the frame number from main memory
					frameNum = mainMemory.getFreeFrame();	
				}
				// memory is full use LRU algorithm
				else{
					// get the victim frame by using LRU algorithm
					frameNum = mainMemory.LRU();
				}
				
				// update the page table
				pageTable.setPage(pageNum, frameNum);
				
				
			}
			// search the page table and get the frame number
			else{
				frameNum = pageTable.find(pageNum);
				data = mainMemory.getData(frameNum);
			}
			
			// update TLB table		
			// update the TLB if TLB has free entry
			if(tlb.hasFreeEntry()){
				int index = tlb.getFreeEntry();
				tlb.setInfo(index, pageNum, frameNum);
			}
			// update the full TLB 
			else{
				int victim = tlb.LRU();
				tlb.setInfo(victim, pageNum, frameNum);
			}
			
		}
		
		report.totalIncrease();
		
		phyAdd = convert(frameNum)+offset;
		a[0] = Integer.parseInt(phyAdd, 2);
		a[1] = data;
		
		return a;
		
	}
	

	// read data from backing_store.bin
	public static int readByte(int pageNum, int offset){
		int ans = 0;
		try {
			String binFile = "src/BACKING_STORE.bin";
			
			RandomAccessFile randomFile = new RandomAccessFile(binFile, "r");
	           
	        int beginIndex = pageNum*256 + offset;

	        randomFile.seek(beginIndex);
	            
	        ans = (int)randomFile.readByte();
	            
	        randomFile.close();

	        return ans;  
  
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 		
	        		
		return ans;
	}
	
	// print the result of report
	public void printReport(){
		double pf = report.getPF();
		double total = report.getTotal();
		double hit = report.getTLB();
		
		double pr = pf/total*100.00;
		double tr = hit/total*100.00;
		
		System.out.println("Page Fault Rate: " + pr +"%");
		System.out.println("TLB hit Rate: " + tr +"%");
	}
	
	

	
}
