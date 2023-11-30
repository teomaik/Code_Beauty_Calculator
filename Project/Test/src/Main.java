import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String input;
		int vbytes;
		boolean flag=true; //Variable that shows whether there is an error in a byte
		String number="";
		char c;
		Scanner myScanner=new Scanner(System.in);
		
		System.out.print("Insert a variable byte: ");
		input=myScanner.nextLine();
		if(input.length()%8!=0) {
			System.out.println("Not a Variable Byte -> Wrong length");
		}else {
			vbytes=input.length()/8;		//Number of bytes/blocks
			int position=0;					//Position of the first bit of each block
			for(int i=0;i<vbytes-1;i++) {	//Loop that scans all bytes except the last one
				c=input.charAt(position);	//Reads the first bit of the current byte
				if(c!='0') {				//If the first bit of the current byte is not '0'
					System.out.println("Not valid Variable Byte -> Error in the first bit of the byte " + (i+1));
					flag=false;
					break;
				}
				for(int j=position+1;j<position+8;j++) {	//Reads all the 7 bits of the current byte (all except for the first one)
					c=input.charAt(j);
					number=number+c;
				}
				position=position+8;		 				//Position of the 1st bit of each byte
			}
			if(input.charAt(position)!='1') {				//If the first bit of the final byte is not '1'
				System.out.println("Invalid Variable Byte -> Error in the first bit of the final byte");
				flag=false;
			}
			if(flag==true) { //No errors in bytes
				for(int j=position+1;j<position+8;j++) { //Scans the final byte
						c=input.charAt(j);
						number=number+c;
				}
				System.out.println("Number in Binary form");
				System.out.println(number);	
				System.out.println("Number in Decimal form");
				long decNumber=Long.parseLong(number, 2);
				System.out.println(decNumber);
			}
		}

	}

}