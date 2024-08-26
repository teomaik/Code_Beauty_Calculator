package CAMs;
import java.io.File;
import java.util.Scanner;

public class Equilibrium {
	int maxLine;
	int allLines;
	String filename;
	double EM;
	
	public Equilibrium(int maxLineLength, int NumOfLines, String name) throws Exception {
		maxLine=maxLineLength;
		allLines=NumOfLines;
		filename=name;
		
		double x=EMx();
		double y=EMy();
		
		EM=100-(Math.abs(x)+Math.abs(y))/2;
		EM=EM/100;
	}
	
	public double EMx() throws Exception {
		double result=0;
		String currentLine;
		int tabs;
		int a;//area occupied by each line
		int xc=maxLine/2;//(xc,yc) the coordinates at the center of the frame
		int xi;//center of the line
		int num=0;//numerator of the fraction
		int denom=0;//denominator of the fraction
		int bframe=maxLine;//width of the frame
		
		File file=new File(filename);
		Scanner reader= new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			tabs=0;
			if(currentLine.length()>0) {
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
					tabs++;
				}
				a=currentLine.length()-tabs;
				xi=a/2+tabs;
				num=num+a*(xi-xc);
				denom=denom+a;
			}
		}
		reader.close();
		num=num*2;
		denom=denom*bframe;
		result=num*100/denom;
		
		return result;
	}
	
	public double EMy() throws Exception {
		double result=0;
		int hframe=allLines;
		int yc=allLines/2;//(xc,yc) the coordinates at the center of the frame
		int a;//area occupied by the line
		int num=0;
		int denom=0;
		int yi=0;//center of the line
		String currentLine;
		int tabs;
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			yi++;
			tabs=0;
			if(currentLine.length()>0) {
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
					tabs++;
				}
				a=currentLine.length()-tabs;
				num=num+a*(yi-yc);
				denom=denom+a;
			}
		}
		reader.close();
		
		num=num*2;
		denom=denom*hframe;
		result=num*100/denom;
		
		return result;
	}

	public double getEM() {
		return EM;
	}
	
}
