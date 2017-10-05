//KONSTANTINOS KASFIKIS
//2013030108
//LAB21124888

package semester4_project1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

public class MainClass {

	public static void main(String [] args) throws IOException{
		int N=100000;
		int PAGE_SIZE=512;
		int INIT_PAGES=100;
		file FileProcess=CreateFile(N,PAGE_SIZE,INIT_PAGES);
		System.out.println("FirstMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,true,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		System.out.println("SecondMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,false,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		FileProcess.CloseFile();
		System.out.println("==============================================================================================================");
		INIT_PAGES=200;
		FileProcess=CreateFile(N,PAGE_SIZE,INIT_PAGES);
		System.out.println("FirstMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,true,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		System.out.println("SecondMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,false,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		FileProcess.CloseFile();
		System.out.println("==============================================================================================================");
		INIT_PAGES=500;
		FileProcess=CreateFile(N,PAGE_SIZE,INIT_PAGES);
		System.out.println("FirstMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,true,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		System.out.println("SecondMethodSearch:"+ CallFileSearch(N,PAGE_SIZE,INIT_PAGES,false,FileProcess)+" For N="+N+" and PAGE_SIZE="+PAGE_SIZE+" and INIT_PAGES(M)="+INIT_PAGES);
		FileProcess.CloseFile();
		System.out.println("==============================================================================================================");
	}

	public static file CreateFile(int N,int PAGE_SIZE,int INIT_PAGES) throws IOException{
		file FileProcess= new file(N,INIT_PAGES,PAGE_SIZE);
		FileProcess.InitializeFile();
		FileProcess.fillFile();
		return FileProcess;
	}
	
	public static int CallFileSearch(int N,int PAGE_SIZE,int INIT_PAGES,boolean method,file FileProcess) throws IOException{
		int rand;
		int sum=0;
		int MethodAverage;
		int sampling_tries=20;
		ArrayList<Integer> sampling_array;
		Search sm;
		sampling_array=new ArrayList<Integer>();
		for (int i=0;i<sampling_tries;i++){
			rand=GenerateRandomNumber(1,N);
			sm=null;
			if (method==true){
				sm = new FirstSearchMethod(FileProcess.getMyFile(),rand,PAGE_SIZE,FileProcess.hkey(rand));
				
			}
			else{
				sm = new SecondSearchMethod(FileProcess.getMyFile(),rand,PAGE_SIZE);
			}
			sampling_array.add(sm.getDiskAccesses());
		}
		
		for (int i=0;i<sampling_tries;i++){
			sum=sampling_array.get(i)+sum;
		}
		MethodAverage=sum/sampling_tries;
		return MethodAverage;
	}
	
	public static int GenerateRandomNumber(int min,int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
