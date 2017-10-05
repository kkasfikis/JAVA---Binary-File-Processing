//KONSTANTINOS KASFIKIS
//2013030108
//LAB21124888

package semester4_project1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class Search {
	RandomAccessFile myFile;
	int PAGE_SIZE;
	int element;
    int DiskAccesses=0;
	byte [] buffer;
	public Search(RandomAccessFile myFile,int element,int PAGE_SIZE) throws IOException{
		buffer=new byte[PAGE_SIZE];
		this.myFile=myFile;
		this.element=element;
		this.PAGE_SIZE=PAGE_SIZE;
	}
	public int getDiskAccesses(){
		return DiskAccesses;
	}
	public int ByteToInteger(byte [] bytes){
		return ByteBuffer.wrap(bytes).getInt();
	}
	public void read(RandomAccessFile myFile,byte [] buffer) throws IOException{
		myFile.read(buffer);
		DiskAccesses=DiskAccesses+1; // to count the sum of the disk accesses each method required !
	}
    public abstract void Search() throws IOException;
}
