//KONSTANTINOS KASFIKIS
//2013030108
//LAB21124888

package semester4_project1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class file {
    private final int N; // number of elements 
    private final int PAGE_SIZE; // size of each page
    private final int INIT_PAGES; // how much pages the file contains once initialized
    private final String FILE_NAME = "project.bin"; // name of the file that is written in the disk
	private int PAGES=0; // number of overflow pages
	private int element; 
	private byte [] buffer; // buffer that saves the data of each page temporarilly to RAM
	private File file; 
	private RandomAccessFile myFile; // the file
	
	public file(int N,int INIT_PAGES,int PAGE_SIZE){
		this.N=N;
		this.INIT_PAGES=INIT_PAGES;
		this.PAGE_SIZE=PAGE_SIZE;
		buffer=new byte[PAGE_SIZE];
	}
	
	public void InitializeFile() throws IOException{  // creates a file of M pages that have no data only 0 (apart from the last element of each page that is -1)
		file=new File(FILE_NAME);
		if(file.exists()==true){file.delete();}
		
		myFile = new RandomAccessFile(file,"rw");
		
		for (int i=0;i<INIT_PAGES;i++){
			addPage();
		}
	}
	
	
	public void addPage() throws IOException{ // adds a page to the end of the file (if the file has no pages this method inserts the first page)
	    
		for (int j=0;j<PAGE_SIZE/4;j++){
			
			if(j!=(PAGE_SIZE/4)-1){
					myFile.writeInt(0);
			}
			else{
					myFile.writeInt(-1);
			}
		}
	}
	public void fillFile() throws IOException{ //this method fills our file with the elements 1-N and places these elements to the right page
	    long pointer;
		byte [] tmpVal;
		for (element=1;element<=N;element++){
			myFile.seek(hkey(element)*PAGE_SIZE);
			pointer=myFile.getFilePointer();
			myFile.read(buffer);
			myFile.seek(pointer);
		    tmpVal = Arrays.copyOfRange(buffer, PAGE_SIZE-4, PAGE_SIZE);
		    while( ByteToInteger(tmpVal) != -1){
		    	myFile.seek((ByteToInteger(tmpVal)-1)*PAGE_SIZE);
				pointer=myFile.getFilePointer();
				myFile.read(buffer);
				myFile.seek(pointer);
				tmpVal = Arrays.copyOfRange(buffer, PAGE_SIZE-4, PAGE_SIZE);
			}
		    tmpVal = Arrays.copyOfRange(buffer, PAGE_SIZE-8, PAGE_SIZE-4);
			if(ByteToInteger(tmpVal)>=126){
				PAGES++;
				transferBytes(PAGE_SIZE-4,PAGE_SIZE-1,buffer,IntegerToByte(INIT_PAGES+PAGES));
				pointer=myFile.getFilePointer();
				myFile.write(buffer);
				myFile.seek(myFile.length());
				addPage();
				myFile.seek((INIT_PAGES+PAGES-1)*PAGE_SIZE);
				pointer=myFile.getFilePointer();
				myFile.read(buffer);
				myFile.seek(pointer);
				transferBytes(0,3,buffer,IntegerToByte(element));
				transferBytes(PAGE_SIZE-8,PAGE_SIZE-5,buffer,IntegerToByte(1));
				myFile.write(buffer);
			}
			else{
				int x = ByteToInteger(tmpVal);				
				if(x!=0){
					transferBytes(x*4,(x*4)+3,buffer,IntegerToByte(element));
				}
				else{
					transferBytes(0,3,buffer,IntegerToByte(element));
				}
				x=x+1;
				transferBytes(PAGE_SIZE-8,PAGE_SIZE-5,buffer,IntegerToByte(x));
				myFile.write(buffer);
			}
		}
	}
	
	public void CloseFile() throws IOException{
		myFile.close();
	}
	
	public int ByteToInteger(byte [] bytes){
		return ByteBuffer.wrap(bytes).getInt();
	}

	public byte [] IntegerToByte(int element){
		return ByteBuffer.allocate(4).putInt(element).array();
	}
	
	public void transferBytes(int starting,int ending,byte [] buffer,byte [] result){ // secondary function that is used to transfer the bytes of an integer to the buffer ( the buffer will be written afterwards to the file)
		int j=0;
		for (int i=starting;i<=ending;i++){
			buffer[i]=result[j];
			j++;
		}
	}
    public int hkey(int key){ // this function shows us in which page should each element be placed
		return key % INIT_PAGES;
    }

	public RandomAccessFile getMyFile() { //returns the file 
		return myFile;
	}
    

}