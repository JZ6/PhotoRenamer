package backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class for operations regarding reading and writing to a file.
 */
public class Logger {
	
	/** A string that represents a new line*/
	public static final String newLine = System.getProperty("line.separator");

	/**
	 * Write String in to File name, by either appending or overwriting according to boolean append.
	 *
	 * @param name
	 *            the file to write to.
	 * @param in
	 *            the String to be written in name.
	 * @param append
	 *            whether to append to or overwrite the originial file.
	 */
	public static void write (File name , String in , boolean append)  {
		try{
			FileWriter op = new FileWriter(name, append);
			op.write(in);
			op.close();
		}catch (IOException  ex){
			System.out.println("File" + name + "is not availible for writing!");
		}
	}
	
	/**
	 * Read File name into a scanner.
	 *
	 * @param name
	 *            the file to read from.
	 *            
	 * @return Scanner of File name.
	 */
	public static Scanner read(File name) {
		Scanner inf = null;
		try{
			inf = new Scanner(name);
		}catch (IOException  ex){
			System.out.println("File to be read is missing!");
		}
		return inf;
	}

}
