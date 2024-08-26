package CAMs;

import java.io.File;
import java.util.Scanner;

public class DCM {
	String filename;
	int maxlength;
	int allLines;
	double dcm;
	
	public DCM(int mlength, int lines, String name) throws Exception {
		maxlength=mlength;
		allLines=lines;
		filename=name;
		
		double COMx=0;//(COMx, COMy) the center of mass
		double COMy=0;
		int denom=0;//The sum of the masses of the lines
		String currentLine;
		int tabs;
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		int m, x;
		int y=0;
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			y++;
			tabs=0;
			if(currentLine.length()>0) {
				while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
					tabs++;
				}
			}
			
			m=currentLine.length()-tabs;//The length of the line without the front tabs
			x=(currentLine.length()+tabs)/2;//The center of the line
			COMx=COMx+m*x;
			COMy=COMy+m*y;
			denom=denom+m;
			
		}
		reader.close();
		
		COMx=COMx/denom;//COMx=Σ(mi*xi)/Σ(mi)
		COMy=COMy/denom;//COMy=Σ(mi*yi)/Σ(mi)
		
		double normx=COMx/maxlength;//normalization of COMx and COMy
		double normy=COMy/allLines;
		
		dcm=Math.sqrt(Math.pow((0.5-normx), 2)+Math.pow((0.5-normy), 2));
		
	}
	
	public double getDCM() {
		return dcm;
	}
	
}
