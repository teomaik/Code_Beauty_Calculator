package CAMs;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Regularity {
	int allLines;
	String filename;
	double RM;
	
	public Regularity(int NumOfLines, String name) {
		allLines=NumOfLines;
		filename=name;
		try {
			
			ArrayList<Integer> horizontalAP=new ArrayList<>();//horizontal alignment points (unique tabs)
			ArrayList<Integer> verticalAP=new ArrayList<>();//vertical alignment points (non-blank lines)
			String currentLine;
			int tabs=0;
			int lineNumber=0;
			
			if(allLines>1) {
				File file=new File(filename);
				Scanner reader=new Scanner(file);
				while(reader.hasNext()) {
					currentLine=reader.nextLine();
					lineNumber++;
					tabs=0;
					if(currentLine.length()>0) {//non-blank line
						verticalAP.add(lineNumber);
						while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
							tabs++;
						}
						if(!horizontalAP.contains(tabs)) {
							horizontalAP.add(tabs);//stores the unique tabs
						}
					}
				}
				reader.close();
				
				double nhap=horizontalAP.size();//number of unique tabs
				double nvap=verticalAP.size();//number of unique vertical alignment points (number of non-blank lines)
				double n=verticalAP.size();//sum of all non-blank lines
				double RMalignment=(nvap+nhap)*100/(2*n);
				RMalignment=RMalignment/100;
				RMalignment=1-RMalignment;
				
				//RMspacing 
				ArrayList<Integer> spacingAP=new ArrayList<>();//unique distances between horizontal and vertical alignment points
				int difference;
				//calculation of the unique distances between the horizontal alignment points
				for(int i=0;i<horizontalAP.size()-1;i++) {
					for(int j=i+1;j<horizontalAP.size();j++) {
						difference=horizontalAP.get(j)-horizontalAP.get(i);
						if(!spacingAP.contains(difference)) {
							spacingAP.add(difference);
						}
					}
				}
				//calculation of the unique distances between the vertical alignment points
				for(int i=0;i<verticalAP.size()-1;i++) {
					for(int j=i+1;j<verticalAP.size();j++) {
						difference=verticalAP.get(j)-verticalAP.get(i);
						if(!spacingAP.contains(difference)) {
							spacingAP.add(difference);
						}
					}
				}
				double nspacing=spacingAP.size();//number of unique distances
				double RMspacing=(nspacing-1)*100/(2*(n-1));
				RMspacing=RMspacing/100;
				RMspacing=1-RMspacing;
				RM=(Math.abs(RMalignment)+Math.abs(RMspacing))/2;
			}else {
				RM=1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double getRM() {
		return RM;
	}
	
}
