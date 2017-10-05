//KONSTANTINOS KASFIKIS
//2013030108
//LAB21124888

package semester4_project1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class SecondSearchMethod extends Search {
	public SecondSearchMethod(RandomAccessFile myFile,int element,int PAGE_SIZE) throws IOException{
		super(myFile,element,PAGE_SIZE);
		Search();
	}

	
	public void Search() throws IOException {
		myFile.seek(0);
		byte [] tmpVal;
		boolean check=false;
		long pointer=myFile.getFilePointer();
		while (pointer<myFile.length() && check==false){
			read(myFile,buffer);
			pointer=myFile.getFilePointer();
			for(int i=0;i<PAGE_SIZE/4;i++){
				tmpVal=Arrays.copyOfRange(buffer, (i*4), (i*4)+4);
				if(element==ByteToInteger(tmpVal)){
					check=true;
				}
			}
		}
	}
}
