package CAMs;

import java.io.File;
import java.util.Scanner;

public class OverallDensity {
	String filename;
	int maxLine;
	int allLines;
	double density;
	
	public OverallDensity(int maxLineLength, int NumOfLines, String name) throws Exception {
		filename=name;
		maxLine=maxLineLength;
		allLines=NumOfLines;
		
		double acovered=areaCovered();
		double aframe=areaFrame();
		density=acovered/aframe;
		
	}
	
	public double areaFrame() {
		double result=maxLine*allLines;//
		return result;
	}
	
	public double areaCovered() throws Exception {
		double result=0;
		String currentLine;
		char c;
		int tabs;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			tabs=0;
			int i=0;
			if(currentLine.length()>0) {
				c=currentLine.charAt(i);
				while(c==' ' && tabs<currentLine.length()-1) {
					tabs++;
					i++;
					c=currentLine.charAt(i);
				}
				result=result+currentLine.length()-tabs;
			}
		}
		reader.close();
		
		return result;
	}

	public double getDensity() {
		return density;
	}
	
}
