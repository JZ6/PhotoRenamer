package photo_renamer;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class PhotoRenamer extends JFrame {
	
	/**
	 * Main JFrame class that contains all the components that make up the Photo Renamer GUI.
	 */
	private static final long serialVersionUID = -3493770914748824960L;
	/** The size of the screen */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * The main JTabbedPane with two tabs, PictureTab and DirectoryTab.
	 */
	public PhotoRenamer(){
		
		// Using Model–view–controller (MVC) design pattern
		
		JTabbedPane tabs = new JTabbedPane();
		
		//PictureTab is a view that refreshes based on changes in the model.
		PictureTab pictureTab = new PictureTab();
		//PictureTab is also uses the Singleton	Design Pattern.
		
		//DirectoryTab is a controller that sends a chosen file in order to update the view PictureTab
		DirectoryTab directoryTab = new DirectoryTab(tabs, pictureTab);
		//DirectoryTab takes a single instance of PictureTab instead of creating a new one.
		
		tabs.addTab("Choose A Picture", directoryTab);
		tabs.addTab("Tag the Picture", pictureTab);
		add(tabs);
	}
		
	/**
	 * Main method for PhotoRenamer, packs everything and runs in a proper size.
	 */
	public static void main(String[] args) {
		PhotoRenamer pr = new PhotoRenamer();
		pr.pack();
		Dimension size = new Dimension(screenSize.width/2,screenSize.height/2);
		pr.setMinimumSize(size);
		pr.setVisible(true);
	}
}
