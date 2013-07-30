import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solution{
	
	int[][] data;
	int size;
	eachcoor[][] secondtable;
	int[] basin;
	int sinkidcounter = 1;
	
	class eachcoor{
		//the direction of the water flow
		int x;
		int y;
		int sinkid;
		
		public eachcoor(){
			x = -1;
			y = -1;
			sinkid = -1;
		}
	}
	//####################################################################
	public static void  main(String[] args) throws IOException{
		
		System.out.println("===============");
		
		Solution theinstance = new Solution();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//getting the size which is the first input
        String line = br.readLine();
        int tempsize = Integer.parseInt(line);
        theinstance.size = tempsize;
        theinstance.data = new int[tempsize][tempsize];
        theinstance.basin = new int[tempsize];
        theinstance.secondtable = new eachcoor[tempsize][tempsize];
        
        //getting the data and filling those data to our container which is int[][]data
        String[] temp;
        int y = 0;
        for(int x=0 ; x<tempsize ; x++){
        	line = br.readLine();
        	temp = line.split(" ");
        	y = 0;
        	for(String each:temp){
        		theinstance.data[x][y] = Integer.parseInt(each);
        		y++;
        	}
        }//end for
        
        
        theinstance.generateSecondTable();
        theinstance.identifyEachFinDestinationSink();
        theinstance.printdata();
       
	}//end main
	//####################################################################
	public void printdata(){
		
		System.out.println("===== THE DATA =====");
		for(int x=0 ; x<size ; x++){
			for(int y=0 ; y<size ; y++){
				System.out.print(data[x][y] + " ");
			}
			System.out.println("");
		}
		System.out.println("===== THE SECOND TABLE =====");
		for(int x=0 ; x<size ; x++){
			for(int y=0 ; y<size ; y++){
				System.out.print("(" + secondtable[x][y].x + "," + secondtable[x][y].y + ")" + "[" + secondtable[x][y].sinkid + "]");
			}
			System.out.println("");
		}
		
		java.util.Arrays.sort(basin);
		
		int temp;
		for(int x=size-1 ; x>=0 ; x--){
			temp = basin[x];
			if(temp!=0)
				System.out.print(temp + " ");
		}
		
	}//end printdata
	//####################################################################
	public void generateSecondTable(){
		System.out.println("test");
		for(int x=0 ; x<size ; x++){
			for(int y=0 ; y<size ; y++){
				secondtable[x][y] = new eachcoor();
				fillin(x,y);
			}//end for
		}//end for
	}
	//####################################################################
	public void fillin(int x, int y){
		int currentmin = data[x][y];
		int flowx = -1;
		int flowy = -1;
		int temp;
		
		//---- check top left, left, bottom left
		if( (x-1)>=0 && (y-1)>=0 ){
			temp = data[x-1][y-1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x-1;
				flowy = y-1;
			}
		}
		
		if( (x-1)>=0){
			temp = data[x-1][y];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x-1;
				flowy = y;
			}
		}
		
		if( (x-1)>=0 && (y+1)<size ){
			temp = data[x-1][y+1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x-1;
				flowy = y+1;
			}
		}
		//---- check top, bottom
		if( (y-1)>=0 ){
			temp = data[x][y-1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x;
				flowy = y-1;
			}
		}
		
		if(  (y+1)<size ){
			temp = data[x][y+1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x;
				flowy = y+1;
			}
		}
		//---- check top right, right, bottom right
		if( (x+1)<size && (y-1)>=0 ){
			temp = data[x+1][y-1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x+1;
				flowy = y-1;
			}
		}
		
		if( (x+1)<size ){
			temp = data[x+1][y];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x+1;
				flowy = y;
			}
		}
		
		if( (x+1)<size && (y+1)<size ){
			temp = data[x+1][y+1];
			if(temp < currentmin){
				currentmin = temp;
				flowx = x+1;
				flowy = y+1;
			}
		}
		//----
		secondtable[x][y].x = flowx;
		secondtable[x][y].y = flowy;
		if(flowx == -1 && flowy == -1){
			secondtable[x][y].sinkid = sinkidcounter;
			sinkidcounter++;
		}
	}
	//####################################################################
	public void identifyEachFinDestinationSink(){
		for(int x=0 ; x<size ; x++){
			for(int y=0 ; y<size ; y++){
				followwaterpath(x,y);
			}//end for
		}//end for
	}
	//####################################################################
	public void followwaterpath(int x, int y){
		
		if(secondtable[x][y].sinkid == -1){

			int sinkid = secondtable[x][y].sinkid;
			int runnerx = x;
			int runnery = y;
			
			int temp1;
			int temp2;
			while(sinkid == -1){//while we have not yet found the final destination
								//go to the next square by following the water path
				temp1 = secondtable[runnerx][runnery].x;
				temp2 = secondtable[runnerx][runnery].y;
				sinkid = secondtable[temp1][temp2].sinkid;
				runnerx = temp1;
				runnery = temp2;

			}
			secondtable[x][y].sinkid = sinkid;
			basin[(sinkid-1)]++;
		}//end if 
		else{
			basin[(secondtable[x][y].sinkid)-1]++;
		}
		
	}//followwaterpath
	//####################################################################
	
}//Solution



