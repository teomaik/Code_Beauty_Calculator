package CAMs;

import java.io.File;
import java.util.Scanner;

public class Symmetry {
	String filename;
	int allLines;
	int maxLength;
	double SYM;
	
	public Symmetry(int maxLineLength, int NumOfLines, String name) throws Exception {
		maxLength=maxLineLength;
		allLines=NumOfLines;
		filename=name;
		
		int xc=maxLength/2;
		int yc=allLines/2;
		String currentLine;
		int lineNumber=0;
		int tabs;//spaces in front of the line
		int xij;//center of line i in the quadrant j
		int[] X= {0,0,0,0};//where X={Xul,Xur,Xll,Xlr}
		int[] H= {0,0,0,0};//where H={Hul,Hur,Hll,Hlr}
		int[] B= {0,0,0,0};//where B={Bul,Bur,Bll,Blr}
		
		File file=new File(filename);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			lineNumber++;
			if(currentLine.length()>0) {
				tabs=0;
				if(lineNumber<=yc) {//above the center
					if(currentLine.length()<=xc) {//upper left quadrant 
						while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
							tabs++;
						}
						xij=(currentLine.length()+tabs)/2;
						X[0]=X[0]+Math.abs(xij-xc);//Xj=Σ|xij-xc|
						B[0]=B[0]+currentLine.length()-tabs;//sum of the lengths of the lines in the upper left quadrant
						H[0]=H[0]+1;//sum of the heights of the lines in the upper left quadrant
					}else {//in both the upper left and upper right quadrants
						while(currentLine.charAt(tabs)==' ') {
							tabs++;
						}
						xij=(xc+tabs)/2;//center of the line in the upper left quadrant
						X[0]=X[0]+Math.abs(xij-xc);
						B[0]=B[0]+xc-tabs;
						H[0]=H[0]+1;
						
						xij=(xc+currentLine.length())/2;//center of the line in the upper right quadrant
						X[1]=X[1]+Math.abs(xij-xc);//we store the difference of the centers in xur
						B[1]=B[1]+currentLine.length()-xc;//the length of the line in the upper right quadrant
						H[1]=H[1]+1;
					}
				}else {
					if(currentLine.length()<=xc) {//lower left
						while(currentLine.charAt(tabs)==' ' && tabs<currentLine.length()-1) {
							tabs++;
						}
						xij=(currentLine.length()+tabs)/2;
						X[2]=X[2]+Math.abs(xij-xc);//Xj=Σ|xij-xc|
						B[2]=B[2]+currentLine.length()-tabs;//sum of the lengths of the lines in the lower left quadrant
						H[2]=H[2]+1;//sum of the heights of the lines in the lower left quadrant
					}else {
						while(currentLine.charAt(tabs)==' ') {
							tabs++;
						}
						xij=(xc+tabs)/2;//center of the line in the lower left quadrant
						X[2]=X[2]+Math.abs(xij-xc);
						B[2]=B[2]+xc-tabs;
						H[2]=H[2]+1;
						
						xij=(xc+currentLine.length())/2;//center of the line in the lower right quadrant
						X[3]=X[3]+Math.abs(xij-xc);//
						B[3]=B[3]+currentLine.length()-xc;//sum of the lengths of the lines in the lower right quadrant
						H[3]=H[3]+1;
					}
				}
				
			}
		}
		reader.close();
		
		double[] Xnorm=normalize(X);
		double[] Hnorm=normalize(H);
		double[] Bnorm=normalize(B);
		
		double SYMvertical=2*(Math.abs(Xnorm[0]-Xnorm[1])+Math.abs(Xnorm[2]-Xnorm[3]))+Math.abs(Hnorm[0]-Hnorm[1])+Math.abs(Hnorm[2]-Hnorm[3])+Math.abs(Bnorm[0]-Bnorm[1])+Math.abs(Bnorm[2]-Bnorm[3]);
		SYMvertical=SYMvertical/8;
		double SYMhorizontal=2*(Math.abs(Xnorm[0]-Xnorm[2])+Math.abs(Xnorm[1]-Xnorm[3]))+Math.abs(Hnorm[0]-Hnorm[2])+Math.abs(Hnorm[1]-Hnorm[3])+Math.abs(Bnorm[0]-Bnorm[2])+Math.abs(Bnorm[1]-Bnorm[3]);
		SYMhorizontal=SYMhorizontal/8;
		double SYMradial=2*(Math.abs(Xnorm[0]-Xnorm[3])+Math.abs(Xnorm[1]-Xnorm[2]))+Math.abs(Hnorm[0]-Hnorm[3])+Math.abs(Hnorm[1]-Hnorm[2])+Math.abs(Bnorm[0]-Bnorm[3])+Math.abs(Bnorm[1]-Bnorm[2]);
		SYMradial=SYMradial/8;
		
		SYM=1-(Math.abs(SYMvertical)+Math.abs(SYMhorizontal)+Math.abs(SYMradial))/3;

	}
	
	public double[] normalize(int[] temp) {
		double[] result= {0,0,0,0};
		int max=-1;
		int min=1000;
		
		for(int i=0;i<4;i++) {
			if(temp[i]>max) {
				max=temp[i];
			}
			if(temp[i]<min) {
				min=temp[i];
			}
		}
		for(int i=0;i<4;i++) {
			if(max!=min) {
				result[i]=(temp[i]-min)*100/(max-min);
				result[i]=result[i]/100;
			}
			
		}
		
		return result;
	}

	public double getSYM() {
		return SYM;
	}
	
}