package backend;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A picture File wrapped in FileNode.
 */
public class FileNode {
	
	/** The name of this node. */
	private String name;
	/** The picture this node represents. */
	private File file;
	/** The object Tagger used by this picture */
	private Tagger tagger;
	/** The list of string tags on this picture */
	private List<String> tags;
	/** The class Filename used by for this picture */
	private FileNameOperations nameOperations;
	
	/**
	 * An object node created from chosen picture
	 *
	 * @param file
	 *            the file
	 * @param t
	 *            the Tagger class for this object.
	 * @param f
	 *            the FileName class for this object.
	 */
	public FileNode(File file, Tagger t, FileNameOperations f) {
		this.name = file.getName();
		this.tagger = t ;
		this.nameOperations = f;
		this.file = file;
		String n = file.getName();
		if (n.indexOf('@') >= 0){	 //If the file has been tagged previously
			String tagList = n.substring(n.indexOf('@'), n.lastIndexOf('.')).trim();
			this.tags = new LinkedList<String>(Arrays.asList(tagList.split("@")));
			this.tags.remove(0); 					//Remove leading empty string
			for (String newTags : this.tags){
				if (!this.tagger.getAvailableTags().contains(newTags)){
					this.tagger.addtag(newTags);
				}
			}
			
		}else{
			this.tags = new LinkedList<String>();
		}
	}
	
	/**
	 * Return the name of the picture represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return a string list of all past names of this picture.
	 *
	 * @return past names of picture
	 */
	public List<String> getPastNames() {
		return this.nameOperations.getNameHistory(new File(this.name));
	}
	
	/**
	 * Return the picture file represented by this node.
	 *
	 * @return picture file of this node
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Return a list of all current tags of this node.
	 *
	 * @return tags of this node
	 */
	public List<String> getTags() {
		return this.tags;
	}
	
	/**
	 * Return a list of all current available tags.
	 *
	 * @return all current available tags
	 */
	public List<String> getAvailableTags() {
		return this.tagger.getAvailableTags();
	}
	
	/**
	 * Remove t from list of all current available tags.
	 */
	public void removeFromAvailableTags(String t) {
		this.tagger.removetag(t);;
	}
	
	/**
	 * Return the FileName class of this Node.
	 *
	 * @return FileName class used by this Node.
	 */
	public FileNameOperations getNameOperations() {
		return this.nameOperations;
	}
	
	/**
	 * Set the tags for the picture in this node to tags.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Add a tag to the picture in this node.
	 */
	public void addTagsToFile(String t){
		if (!this.tags.contains(t)){
			this.tags.add(t);
		}
	}
	
	/**
	 * Remove a tag to the picture in this node.
	 */
	public void removeTagsFromFile(String t){
		if (this.tags.contains(t)){
			this.tags.remove(t);
		}
	}
	
	/**
	 * Rename the picture file according to its tags.
	 * 
	 * @return renamed File
	 */
	public File renameFile(){
		
		String oldName = this.file.getName();
		String newName = oldName.substring(0, oldName.lastIndexOf('.'));
		if (newName.indexOf('@') >= 0){
			newName = newName.substring(0, newName.indexOf("@"));
		}
		for (String t : this.tags){
			newName = newName +"@" +t;
		}
		newName = newName + oldName.substring(oldName.lastIndexOf('.'));
		this.name = newName;
		this.file = this.nameOperations.rename(this.file, newName);
		return this.file;
	}
	
	/**
	 * This method is for code that tests this class.
	 * 
	 * @param args
	 *            the command line args.
	 */
	/*
	public static void main(String[] args) {
		Testing
		File testFile = new File("test.txt");
		FileNode x = new FileNode(testFile);
		x.addTagsToFile("2");
		System.out.println(x.renameFile());
		
	}*/ 
	
}
