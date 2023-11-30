import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class codeConverter {
	String filename;
	String outputname;
	
	public codeConverter(String name, String output) throws Exception {
		filename=name;
		outputname=output;
		String currentLine;
		char c;
		
		File file=new File(filename);//java file with tab characters
		File outputfile=new File(outputname);//file 'normalized.java' which contains the lines of the above file after the elimination of tab characters
		FileWriter writer=new FileWriter(outputfile);
		Scanner reader=new Scanner(file);
		while(reader.hasNext()) {
			currentLine=reader.nextLine();
			for(int i=0;i<currentLine.length();i++) {
				c=currentLine.charAt(i);
				if(c=='\t') {//if the current character is tab character
					writer.write("    ");//replace by 4 spaces
				}else {
					writer.write(c);
				}
			}
			writer.write("\n");
		}
		reader.close();
		writer.close();
	}

}
