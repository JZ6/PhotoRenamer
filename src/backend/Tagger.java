package backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class for operations regarding adding and removing tags to a picture FileNode.
 */
public class Tagger {
	
	/** The file where all tag information are stored. */
	static private final File tagFile = new File("tags.txt");
	/** A list of all String tags */
	private List<String> tags;

	/**
	 * A Tagger class with all past tag information retrieved from tagFile.
	 * If tagFile does not exist, create it.
	 */
	public Tagger () {
		Scanner in = null;
		if(tagFile.exists()){
			in = Logger.read(tagFile).useDelimiter(",");
		}else{
			try {
				tagFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<String> t = new ArrayList<String>(); ;
		if (in != null){
			while (in.hasNext()){
				t.add(in.next().trim());	
			}
		}
		this.tags = t;
	}
	
	/**
	 * Return a string list of all available tags.
	 *
	 * @return string list  all available tags
	 */
	public List<String> getAvailableTags() {
		return this.tags;
	}

	/**
	 * Add a new tag to string list of all available tags.
	 */
	public void addtag(String t){
		if (!this.tags.contains(t)){
			this.tags.add(t);
			String tagstring = String.join(",",this.tags);
			Logger.write(tagFile, tagstring , false);
		}
		
	}
	
	/**
	 * Remove a tag from string list of all available tags.
	 */
	public void removetag(String t){
		if (this.tags.contains(t)){
			this.tags.remove(t);
			String tagstring = String.join(",",this.tags);
			Logger.write(tagFile, tagstring , false);
		}
	}
	
	/*public static void main(String[] args) {
		
		Tagger t = new Tagger();
		System.out.println(t.getTags());
		t.addtag("6");
		System.out.println(t.getTags());
		t.removetag("6");
		System.out.println(t.getTags());
		
	}*/ // For testing 

}
