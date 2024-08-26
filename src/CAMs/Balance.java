package CAMs;
import java.io.File;
import java.util.Scanner;

public class Balance {
	int allLines, maxLength;
	String filename;
	int wt, wb, wl, wr;
	double BM;
	
	public Balance(int maxLineLength, int lines, String name) throws Exception {
		allLines=lines;
		maxLength=maxLineLength;
		filename=name;
		
		
		String BMhorizontal=horizontalBalance();//stores the values of wt and wb, separated by a comma (,)
		String temp="";
		
		int i=0;
		while(BMhorizontal.charAt(i)!=',') {
			temp=temp+BMhorizontal.charAt(i);
			i++;
		}
		wt=Integer.valueOf(temp);
		temp="";
		i++;
		for(int j=i;j<BMhorizontal.length();j++) {
			temp=temp+BMhorizontal.charAt(j);
		}
		wb=Integer.valueOf(temp);
		
		String BMvertical=verticalBalance();//stores the values of wt and wb, separated by a comma
		i=0;
		temp="";
		while(BMvertical.charAt(i)!=',') {
			temp=temp+BMvertical.charAt(i);
			i++;
		}
		wl=Integer.valueOf(temp);
		temp="";
		i++;
		for(int j=i;j<BMvertical.length();j++) {
			temp=temp+BMvertical.charAt(j);
		}
		wr=Integer.valueOf(temp);
		
		double BMh;//for the horizontal Balance ((wt-wb)/max(wt,wb))
		if(Math.max(wt, wb)>0) {
			BMh=(wt-wb)*100/Math.max(wt, wb);
			BMh=Math.abs(BMh);
		}else {
			BMh=0;
		}
		double BMv;//for the vertical Balance ((wl-wr)/max(wl,wr))
		if(Math.max(wl, wr)>0) {
			BMv=(wl-wr)*100/Math.max(wl, wr);
			BMv=Math.abs(BMv);
		}else {
			BMv=0;
		}
		
		BM=100-(BMv+BMh)/2;
		BM=BM/100;
		
	}
	
	public String horizontalBalance() throws Exception {
		String currentLine;
		int yc=allLines/2;//center of the frame on y-axis
		char c;
		int a;//area occupied by the line
		int y;//center of the line on y-axis
		int d;//distance of the center of the line from the center of the frame (yc)
		int tabs;
		int wt=0;
		int wb=0;
		int lineNumber=0;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			lineNumber++;
			tabs=0;
			if(currentLine.length()>0) {
				c=currentLine.charAt(tabs);
				while(c==' ' && tabs<currentLine.length()-1) {
					tabs++;
					c=currentLine.charAt(tabs);
				}
				a=currentLine.length()-tabs;
				y=lineNumber;
				
				if(y<=yc) {//the line is placed in the upper half of the frame
					d=yc-y;
					wt=wt+a*d;
				}else {//the line is placed in the lower half of the frame
					d=y-yc;
					wb=wb+a*d;
				}
			}
		}
		reader.close();
		
		String wtb=wt+","+wb;
		
		return wtb;
	}
	
	public String verticalBalance() throws Exception {
		String currentLine;
		int xc=maxLength/2;//center of the frame on x-axis
		char c;
		int tabs;
		int a;//area occupied by each line
		int x;//center of the line
		int d;//distance of the center of the line from the center of the frame (xc)
		int wl=0;
		int wr=0;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			tabs=0;
			if(currentLine.length()>0) {
				c=currentLine.charAt(tabs);
				while(c==' ' && tabs<currentLine.length()-1) {
					tabs++;
					c=currentLine.charAt(tabs);
				}
				
				a=currentLine.length()-tabs;
				x=a/2+tabs;
				
				if(x!=xc) {
					if(currentLine.length()<=xc) {//the line is placed in the left of the middle of the frame (left of xc)
						d=xc-x;
						wl=wl+a*d;
					}else {//the line extends to both the left and right of the middle of the frame
						a=xc-tabs;
						x=xc/2+tabs;
						d=xc-x;
						wl=wl+a*d;
						
						a=currentLine.length()-xc;
						x=(currentLine.length()+xc)/2;
						d=x-xc;
						wr=wr+a*d;
					}
				}
			}
		}
		reader.close();
		
		String temp=wl+","+wr;
		return temp;

	}

	public double getBM() {
		return BM;
	}
	
}
