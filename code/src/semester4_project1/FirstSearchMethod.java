//KONSTANTINOS KASFIKIS
//2013030108
//LAB21124888

package semester4_project1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FirstSearchMethod extends Search{
	private int PageToSearch;
	public FirstSearchMethod(RandomAccessFile myFile,int element,int PAGE_SIZE,int PageToSearch) throws IOException{
		super(myFile,element,PAGE_SIZE);
		this.PageToSearch=PageToSearch;
		Search();
		
	}
	public void Search() throws IOException{
		boolean check=false; // becomes true when the element we want to find is found
		int check1=0; // a flag that saves the last element of each page( pointer to overflow pages) and if it is -1 it stops the while
		byte [] tmpVal;
		byte [] tmpVal1;
		myFile.seek((PageToSearch)*PAGE_SIZE);
		read(myFile,buffer);
		tmpVal = Arrays.copyOfRange(buffer, PAGE_SIZE-4, PAGE_SIZE);
		while(check1!=-1 && check==false){
			for (int i=0;i<PAGE_SIZE/4;i++){
				tmpVal1=Arrays.copyOfRange(buffer,i,i+4);
				if (element==ByteToInteger(tmpVal1)){
					check=true;
				}
			}
			if(check==false && ByteToInteger(tmpVal)!=-1){
				myFile.seek((ByteToInteger(tmpVal)-1)*PAGE_SIZE);
				read(myFile,buffer);
				tmpVal = Arrays.copyOfRange(buffer, PAGE_SIZE-4, PAGE_SIZE);
				check1=ByteToInteger(tmpVal);
			}
			else{
				check1=-1;
			}
		}
	}
}
