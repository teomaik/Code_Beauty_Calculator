import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Simplicity {
	String filename;
	int maxLine;
	int allLines;
	double SMM;
	
	public Simplicity(int maxLineLength, int NumOfLines, String name) throws Exception {
		filename=name;
		maxLine=maxLineLength;
		allLines=NumOfLines;
		
		int nvap=0;//number of unique vertical alignment points
		int nhap=0;//number of unique horizontal alignment points
		int n=0;//number of lines
		String currentLine;
		int tabs;
		ArrayList<Integer> horizontalAP=new ArrayList<>();//here we add the unique horizontal alignment points
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			tabs=0;
			if(currentLine.length()>0) {
				nvap++;
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
					tabs++;
				}
				if(!horizontalAP.contains(tabs)) {
					horizontalAP.add(tabs);
				}
			}
			
		}
		reader.close();
		nhap=horizontalAP.size();
		n=nvap;
		SMM=3.0/(nhap+nvap+n);
	}

	public double getSMM() {
		return SMM;
	}
	
}
