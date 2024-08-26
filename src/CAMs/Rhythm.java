package CAMs;

import java.io.File;
import java.util.Scanner;

public class Rhythm {
	String filename;
	int maxLine;
	int allLines;
	double RHM;
	
	public Rhythm(int maxLineLength, int NumOfLines, String name) throws Exception {
		filename=name;
		maxLine=maxLineLength;
		allLines=NumOfLines;
		
		double x=RHMx();
		double y=RHMy();
		double area=RHMarea();
		RHM=1-(Math.abs(x)+Math.abs(y)+Math.abs(area))/3;
		
	}
	
	public double RHMx() throws Exception {
		double result=0;
		String currentLine;
		int lineNumber=0;
		int tabs;//spaces in front of the line
		int xc=maxLine/2;
		int yc=allLines/2;//(xc, yc) the center of the frame
		int xij;//the center of the line
		double xul=0;//for the type: Xul=Σ|xij-xc|
		double xur=0;
		double xll=0;
		double xlr=0;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			lineNumber++;
			tabs=0;
			if(currentLine.length()>0) {
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
					tabs++;
				}
				if(currentLine.length()-tabs>1) {
					xij=(currentLine.length()-tabs)/2+tabs;//center of the line
				}else {
					xij=currentLine.length();
				}
				if(lineNumber<=yc) {//upper half
					if(currentLine.length()<=xc) {//upper left
						xul=xul+xc-xij;
					}else {//upper left and upper right
						xul=xul+xc-((xc-tabs)/2+tabs);//we divide the line into two lines, one on the left and one on the right quadrant
						xur=xur+(currentLine.length()+xc)/2-xc;
					}
				}else {//lower half
					if(currentLine.length()<=xc) {//lower left quadrant
						xll=xll+xc-xij;
					}else {//lower left and lower right
						xll=xll+xc-((xc-tabs)/2+tabs);
						xlr=xlr+(currentLine.length()+xc)/2-xc;
					}
				}
			}
		}
		reader.close();

		double max=xul;//for the normalization of xul, xur, xll and xlr
		if(xur>max) {
			max=xur;
		}
		if(xll>max) {
			max=xll;
		}
		if(xlr>max) {
			max=xlr;
		}
		
		xul=xul/max;//normalization of xul, xur, xll and xlr
		xur=xur/max;
		xll=xll/max;
		xlr=xlr/max;

		result=Math.abs(xul-xur)+Math.abs(xul-xlr)+Math.abs(xul-xll)+Math.abs(xur-xlr)+Math.abs(xur-xll)+Math.abs(xlr-xll);
		result=result/6;
		return result;
	}
	
	
	public double RHMy() throws Exception {
		double result=0;
		int xc=maxLine/2;
		int yc=allLines/2;//(xc, yc) the center of the frame
		String currentLine;
		int lineNumber=0;
		double yul=0;
		double yur=0;
		double yll=0;
		double ylr=0;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			lineNumber++;
			if(currentLine.length()>0) {		
				if(lineNumber<=yc) {//upper half
					if(currentLine.length()<=xc) {//upper left
						yul=yul+yc-lineNumber;
					}else {//upper left and upper right
						yul=yul+yc-lineNumber;
						yur=yur+yc-lineNumber;
					}
				}else {//lower half
					if(currentLine.length()<=xc) {//lower left
						yll=yll+lineNumber-yc;
					}else {//lower left and lower right
						yll=yll+lineNumber-yc;
						ylr=ylr+lineNumber-yc;
					}
				}
			}
			
		}
		reader.close();
		
		double max=yul;//normalization
		if(yur>max) {
			max=yur;
		}
		if(yll>max) {
			max=yll;
		}
		if(ylr>max) {
			max=ylr;
		}
		
		yul=yul/max;
		yur=yur/max;
		yll=yll/max;
		ylr=ylr/max;
		
		result=Math.abs(yul-yur)+Math.abs(yul-ylr)+Math.abs(yul-yll)+Math.abs(yur-ylr)+Math.abs(yur-yll)+Math.abs(ylr-yll);
		result=result/6;
		
		return result;
	}
	
	public double RHMarea() throws Exception {
		double result=0;
		String currentLine;
		int lineNumber=0;
		int tabs;
		int xc=maxLine/2;//(xc,yc) the center of the frame
		int yc=allLines/2;
		double Aul=0;//area occupied by lines on the upper left quadrant
		double Aur=0;//area occupied by lines on the upper right quadrant
		double All=0;//area occupied by lines on the lower left quadrant
		double Alr=0;//area occupied by lines on the lower right quadrant
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			lineNumber++;
			tabs=0;
			if(currentLine.length()>0) {
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {//number of spaces in front of the line
					tabs++;
				}
				result=result+currentLine.length()-tabs;//area occupied by line
				if(lineNumber<=yc) {//upper half
					if(currentLine.length()<=xc) {//upper left quadrant
						Aul=Aul+currentLine.length()-tabs;
					}else {//upper left and upper right
						Aul=Aul+xc-tabs;
						Aur=Aur+currentLine.length()-xc;
					}
				}else {//lower half
					if(currentLine.length()<=xc) {//lower left
						All=All+currentLine.length()-tabs;
					}else {//lower left and lower right
						All=All+xc-tabs;
						Alr=Alr+currentLine.length()-xc;
					}
				}
			}
		}
		reader.close();
		
		double max=Aul;//normalization
		if(Aur>max) {
			max=Aur;
		}
		if(All>max) {
			max=All;
		}
		if(Alr>max) {
			max=Alr;
		}
		Aul=Aul/max;
		Aur=Aur/max;
		All=All/max;
		Alr=Alr/max;
		
		result=Math.abs(Aul-Aur)+Math.abs(Aul-Alr)+Math.abs(Aul-All)+Math.abs(Aur-Alr)+Math.abs(Aur-All)+Math.abs(Alr-All);
		result=result/6;
		return result;
	}

	public double getRHM() {
		return RHM;
	}
	
}