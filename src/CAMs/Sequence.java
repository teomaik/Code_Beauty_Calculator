package CAMs;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Sequence {
	String filename;
	int maxLine;
	int allLines;
	double SQM;
	
	public Sequence(int maxLineLength, int NumOfLines, String name) throws Exception {
		filename=name;
		maxLine=maxLineLength;
		allLines=NumOfLines;
		
		SQM=0;
		String currentLine;
		int tabs;
		int lineNumber=0;
		int xc=maxLine/2;
		int yc=allLines/2;
		int[] w= {0,0,0,0};//weights in quadrants, w={wul,wur,wll,wlr}
		
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
				if(lineNumber<=yc) {//upper half
					if(currentLine.length()<=xc) {//upper left
						w[0]=w[0]+currentLine.length()-tabs;
					}else {//upper left and upper right
						w[0]=w[0]+xc-tabs;
						w[1]=w[1]+currentLine.length()-xc;
					}
				}else {//lower half
					if(currentLine.length()<=xc) {//lower left
						w[2]=w[2]+currentLine.length()-tabs;
					}else {//lower left and lower right
						w[2]=w[2]+xc-tabs;
						w[3]=w[3]+currentLine.length()-xc;
					}
				}
			}
		}
		reader.close();
		
		w[0]=w[0]*4;
		w[1]=w[1]*3;
		w[2]=w[2]*2;
		w[3]=w[3]*1;
		
		int vul=0;
		int vur=0;
		int vll=0;
		int vlr=0;
		int wul=w[0];
		int wur=w[1];
		int wll=w[2];
		int wlr=w[3];
		Arrays.sort(w);
		
		for(int i=0;i<4;i++) {//scores based on the weights, the higher the weight, the higher the score
			if(w[i]==wul) {
				vul=i+1;
			}else if(w[i]==wur) {
				vur=i+1;
			}else if(w[i]==wll) {
				vll=i+1;
			}else {
				vlr=i+1;
			}
		}
		
		int qul=4;
		int qur=3;
		int qll=2;
		int qlr=1;
		
		double sigma=0;
		sigma=Math.abs(qul-vul)+Math.abs(qur-vur)+Math.abs(qll-vll)+Math.abs(qlr-vlr);
		sigma=sigma/8;
		SQM=1-sigma;	
	}

	public double getSQM() {
		return SQM;
	}
	
}
