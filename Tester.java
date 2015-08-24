import java.util.*;
import java.io.*;

public class Tester {
	
	public static void main(String[] args){
		
		// Create MMU virtual memory is the same size as physical memory
		MemoryManagementUnit mmu = new MemoryManagementUnit(256,256);
		
		// Read addresses from file and put into an array
		int[] a = readAddress("src/addresses.txt", 1000);
		
		// Translation
		int[] arr;
		
		for(int i = 0; i < 1000; i++){
			arr = mmu.translation(a[i]);
			System.out.println( "Virtual address: "+ a[i] + "	" + 
								"Physical address: " + arr[0] + "	" + 
								"Value: " + arr[1] );
		}

		// Display the report;
		mmu.printReport();

	}
	
	
	public static int[] readAddress(String fileName, int manyAddress){
		int[] ans = new int[manyAddress];
		File file = new File(fileName);
		
		try{			
			Scanner scanner = new Scanner(file);
			
			int j = 0;	
			while(scanner.hasNext()){
				ans[j] = scanner.nextInt();
				j++;
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		return ans;
	}

	
	
}
