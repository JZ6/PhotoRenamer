package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class that contains methods pertaining to the operations associated with a file's name.
 */
public class FileNameOperations {
	
	/** The name of the Log file. */
	static final File logFile = new File("logs.txt");
	/** The file where all name information are stored. */
	static final File nameFile = new File("names.txt");
	/**All name information for modified files, mapped from the current name to a list of past names*/
	private Map<String, List<String>> names;

	/**
	 * A FileName class with all past information retrieved from nameFile.
	 * If nameFile does not exist, create it.
	 */
	public FileNameOperations(){
		this.names = new HashMap<String, List<String>>();
		try {
			if(nameFile.exists()){
				BufferedReader nf = new BufferedReader(new FileReader(nameFile));
				String name = nf.readLine();
				while (name != null){
					List<String> data =  new LinkedList<String>(Arrays.asList(name.split(",")));
					this.names.put(data.get(0),data.subList(1, data.size()) );
					name = nf.readLine();
				}
				nf.close();
			}else{
				nameFile.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Renames File oldName to a newName, then returns said File
	 *
	 * @param oldName
	 *            file to be renamed
	 * @param newName
	 *            string of new name
	 * @return renamed File
	 */
	public File rename(File oldName, String newName) {
		String end = oldName.getName();
		String path = oldName.getAbsolutePath().substring(0 ,oldName.getAbsolutePath().length() - end.length());
		File newFile = new File(path + newName);
		boolean valid = oldName.renameTo(newFile);
		
		if (valid){
			//Add old name to pastnames list, use newname as key
			List<String> pastNames = getNameHistory(oldName);
			pastNames.add(0,oldName.getName());
			this.names.remove(oldName.getName());
			this.names.put(newName, pastNames );
			
			// Write to log
			File init = new File("logs.txt");
			if(!init.exists()){
				String initstring = "This is the log file for PhotoRenamer:" 
								  + Logger.newLine 
								  + "Format: old name, new name, date"
								  + Logger.newLine ;
				Logger.write(init, initstring ,false);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date dat = new Date();
			String date = dateFormat.format(dat);
			String record = oldName.getName() + " , " + newName + " , " + date + Logger.newLine;
			Logger.write(logFile, record, true);
			
			//record to namefile
			boolean firstwrite = false;
			for (Entry<String, List<String>> entry : this.names.entrySet()) {
			    String key = entry.getKey();	
			    List<String> value = entry.getValue();
			    String v = Arrays.toString(value.toArray()).replace("[", "").replace("]", "").replaceAll("\\s+","");  
			    Logger.write(nameFile, key + "," + v + Logger.newLine, firstwrite);
			    firstwrite = true ;
			}
		}
		return newFile ;
	}
	
	/**
	 * Return a List<String> of all past names of File f.
	 *
	 * @param f
	 *            the file name to get past names
	 * @return List<String> of all past names of File f
	 */
	public List<String> getNameHistory(File f) {
		List<String> pastNames = this.names.get(f.getName());
		if (pastNames == null){
			pastNames =  new LinkedList<String>();
		}
		return pastNames;
	}
	
}
