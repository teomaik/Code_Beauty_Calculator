import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		String directory="C:\\Users\\...\\eclipse-workspace\\CodeBeauty\\Project";//folder that contains the project we are studying
		String filename="C:\\Users\\...\\eclipse-workspace\\CodeBeauty\\normalized.java";//file that contains the normalized class (after replacing tabs with spaces)
		String measuresPath="C:\\Users\\...\\eclipse-workspace\\CodeBeauty\\measures\\";//folder in which the file with the results will be stored
		String javafiles="C:\\Users\\...\\eclipse-workspace\\CodeBeauty\\javaFiles.txt";//file in which we store the paths of all java files contained in the project
		String projectName="";//name of the under study project
		
		File dir = new File(directory);
		
		File listOfFiles=new File(javafiles);//file in which the paths of all java files will be stored
		FileWriter fwriter;
		File fmeasures=new File(measuresPath+projectName+"_metrics.csv");//file in which the results of the analysis will be stored
		FileWriter writer=new FileWriter(fmeasures);
		
		String[] csvMatrix = new String[10];
		csvMatrix[0]="Class";
		csvMatrix[1]="Balance";
		csvMatrix[2]="Equilibrium";
		csvMatrix[3]="Overall Density";
		csvMatrix[4]="Regularity";
		csvMatrix[5]="Rhythm";
		csvMatrix[6]="Sequence";
		csvMatrix[7]="Simplicity";
		csvMatrix[8]="Symmetry";
		csvMatrix[9]="DCM";
		for(int i=0;i<9;i++) {
			writer.write(csvMatrix[i]);
			writer.write(",");
		}
		writer.write(csvMatrix[9]);
		writer.write("\n");
		fwriter = new FileWriter(listOfFiles);
		findFiles(dir.listFiles(),fwriter);//calls the method findFiles to find all code files
		fwriter.close();
		
		Scanner freader=new Scanner(listOfFiles);
			
		while(freader.hasNext()) {//for each code file contained in the project
			String fname=freader.nextLine();//reads the path of the file
			File f=new File(fname);//finds the file
				
			int lines=0;//number of lines of file f
			String currentLine;//the current line
			int linelength;//length of current line
			int maxlength=0;//maximum line length in file

			codeConverter cc=new codeConverter(f.getAbsolutePath(), filename);//class that replaces each tab character in the file with four spaces
			File file=new File(filename);//the converted file
			Scanner reader=new Scanner(file);
					
			while(reader.hasNext()) {
				currentLine=reader.nextLine();
				lines++;
				linelength=currentLine.length();
				if(linelength>maxlength) {
					maxlength=linelength;
				}
			}
			reader.close();
					
			//Creation of objects for the calculation of the measures
			Balance BM=new Balance(maxlength, lines, filename);
			Equilibrium EM=new Equilibrium(maxlength, lines, filename);
			OverallDensity density=new OverallDensity(maxlength, lines, filename);
			Regularity reg=new Regularity(lines, filename);
			Rhythm rhm=new Rhythm(maxlength, lines, filename);
			Sequence sqm=new Sequence(maxlength, lines, filename);
			Simplicity s=new Simplicity(maxlength, lines, filename);
			Symmetry sym=new Symmetry(maxlength, lines, filename);
			DCM dcm=new DCM(maxlength, lines, filename);

			//Formation of the name of the class that will be contained in the csv file
			//The name is the path to this file inside the project, each folder name separated by '/'
			String measurefilename="";//name of the file that will be stored under the class column
			String tempname=fname;//name of the under study file
			File tempfile=new File(tempname);
			while(true) {
				if(tempfile.getParentFile().getName().equals(projectName)) {//if the previous folder is the one that contains the whole project
					break;
				}else {
					measurefilename=tempfile.getParentFile().getName()+"/"+measurefilename;//stores the parent file
					tempname=tempfile.getParentFile().getAbsolutePath();//the new path is the path of the previous file
					tempfile=new File(tempname);
				}
			}
			        
			csvMatrix[0]="/"+measurefilename+f.getName();
			csvMatrix[1]=String.valueOf(BM.getBM());
			csvMatrix[2]=String.valueOf(EM.getEM());
			csvMatrix[3]=String.valueOf(density.getDensity());
			csvMatrix[4]=String.valueOf(reg.getRM());
			csvMatrix[5]=String.valueOf(rhm.getRHM());
			csvMatrix[6]=String.valueOf(sqm.getSQM());
			csvMatrix[7]=String.valueOf(s.getSMM());
			csvMatrix[8]=String.valueOf(sym.getSYM());
			csvMatrix[9]=String.valueOf(dcm.getDCM());		
			
			for(int i=0;i<9;i++) {
				writer.write(csvMatrix[i]);
			    writer.write(",");
			}
			writer.write(csvMatrix[9]);
			writer.write("\n");
		}
			writer.close();
			freader.close();		
	}
	
	
	public static void findFiles(File[] files, FileWriter writer) throws Exception {
        for (File f: files) {
            if (f.isDirectory()) {
                findFiles(f.listFiles(), writer); //calls the same method with the folder as input
            } else {
                if(f.getAbsolutePath().endsWith(".java")) {//if the file is java file
                	writer.write(f.getAbsolutePath());//adds the file's path in 'javaFiles'
                	writer.write("\n");
                }
            }
        }
    }

}
