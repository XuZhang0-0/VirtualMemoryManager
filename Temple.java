import java.io.IOException;
import java.io.RandomAccessFile;


public class Temple {
	public static void main(String[] args){
		int ans = 0;
		try {
			String binFile = "src/BACKING_STORE.bin";
			
			RandomAccessFile randomFile = new RandomAccessFile(binFile, "r");
	           
	        int beginIndex = 228*256 + 216;

	        randomFile.seek(beginIndex);
	            
	        ans = (int)randomFile.readByte();
	            
	        randomFile.close();

  
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 	
		System.out.print(ans);
	        		
	}
		
}
