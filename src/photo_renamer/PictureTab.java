package photo_renamer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.FileNameOperations;
import backend.FileNode;
import backend.Tagger;

public class PictureTab extends JPanel {
	
	/**
	 *  The JPanel where all things related to the picture are placed.
	 */
	private static final long serialVersionUID = 2628687305294860185L;
	
	/** The pictureNode representation of the current picture.*/
	private FileNode pictureNode;
	/** The class Tagger used by this picture */
	private Tagger tagger;
	/** The class FileNameOperations used by this picture */
	private FileNameOperations fileName;
	
	/** Default displayed String */
	private String init = "Please select a picture first!";
	/** The JLabels where information is displayed.*/
	private JLabel title, picture;
	
	/**
	 * The JPanel where the picture and it's relating functions JPanel are placed.
	 */
	public PictureTab() {
		//Single instance of Tagger and FileNameOperations objects for the entire program.
		this.tagger = new Tagger();
		this.fileName = new FileNameOperations();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//All black picture placeholder
		picture = new JLabel(init, SwingConstants.CENTER);
		picture.setBackground(Color.BLACK);
		picture.setBorder(BorderFactory.createRaisedBevelBorder());
		picture.setOpaque(true);
		Dimension size = new Dimension(PhotoRenamer.screenSize.width/2,PhotoRenamer.screenSize.height/2);
		picture.setPreferredSize(size);
		
		//Save changes
		rename =new JButton("Save and Rename");
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File renamed = pictureNode.renameFile();
				selected(renamed);
			}
		});	
		
		rename.setEnabled(false);
		// # Aesthetics, enable if button looks weird
		//rename.setBorder(BorderFactory.createCompoundBorder((BorderFactory.createMatteBorder(0, 36 , 0, 36, Color.LIGHT_GRAY)), BorderFactory.createRaisedBevelBorder()));
		
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.weightx = 1; 
		c.weighty = 1; 
		c.gridwidth = 1;
		add(picture, c);
		
		c.gridx = 2;
		c.weightx = 0; 
		add(TagOperations(), c);
		
		c.gridx = 1;
		c.gridy ++;
		c.weighty = 0; 
		c.gridwidth = 2;
		c.ipady = 6;
		c.anchor = GridBagConstraints.PAGE_END;
		add(rename, c);
	}
	
	/** Three JComboBoxes that hold a list of information.*/
	private JComboBox<String> listTags, currentTags, pastNames;
	/** JButtons that can be clicked and run a command*/
	private JButton removeTag, tag, removeFromTags, add, revertName, rename;
	
	/**
	 *  The JPanel where the picture functions are placed.
	 *
	 * @return the created JPanel with all picture operations added.
	 */
	private JPanel TagOperations(){
		JPanel result = new JPanel();
		result.setLayout(new GridBagLayout());
		
		title = new JLabel(init, SwingConstants.CENTER);
		title.setBorder(BorderFactory.createCompoundBorder((BorderFactory.createMatteBorder(3, 6 , 3, 6, Color.LIGHT_GRAY)),
															BorderFactory.createRaisedBevelBorder()));
		title.setOpaque(true);
		title.setBackground(Color.LIGHT_GRAY);
			
		JLabel current = new JLabel("A dropdown list of all tags on current picture:");
		currentTags = new JComboBox<String>();
		currentTags.addItem(init);
		removeTag = new JButton("Remove tag from picture");
		removeTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTag = (String) currentTags.getSelectedItem();
				currentTags.removeItem(selectedTag);
				pictureNode.removeTagsFromFile(selectedTag);
			}
		});	
		
		JLabel tagInstructions = new JLabel("Please select or enter a new tag to add or remove:");
		List<String> taglist = this.tagger.getAvailableTags();
		String[] availableTags = taglist.toArray(new String[taglist.size()]);
		listTags = new JComboBox<String>(availableTags);
		listTags.setMaximumSize(new Dimension(3600, 36));
		listTags.setEditable(true);
		
		tag =new JButton("Tag");
		tag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTag = (String) listTags.getSelectedItem();
				pictureNode.addTagsToFile(selectedTag);
				if (((DefaultComboBoxModel<String>) currentTags.getModel()).getIndexOf(selectedTag) == -1){
					currentTags.addItem(selectedTag);
				}
			}
		});	
		
		add =new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTag = (String) listTags.getSelectedItem();
				if (((DefaultComboBoxModel<String>) listTags.getModel()).getIndexOf(selectedTag) == -1){
					listTags.addItem(selectedTag);
					tagger.addtag(selectedTag);
				}
			}
		});	
		
		
		removeFromTags =new JButton("Remove");
		removeFromTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTag = (String) listTags.getSelectedItem();
				if (((DefaultComboBoxModel<String>) listTags.getModel()).getIndexOf(selectedTag) != -1){
					listTags.removeItem(selectedTag);
					tagger.removetag(selectedTag);
				}
			}
		});	
		

		JLabel past = new JLabel("A dropdown list of the Name history of this picture:");
		pastNames = new JComboBox<String>();
		pastNames.addItem(init);
		
		revertName =new JButton("Revert picture to this name");
		revertName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedName = (String) pastNames.getSelectedItem();
				File reverted = fileName.rename(pictureNode.getFile(), selectedName);
				selected(reverted);
			}
		});	

		//Disable everything until a picture is selected.
		removeTag.setEnabled(false);
		tag.setEnabled(false);
		add.setEnabled(false);
		removeFromTags.setEnabled(false);
		revertName.setEnabled(false);
		
		listTags.setEnabled(false);
		currentTags.setEnabled(false);
		pastNames.setEnabled(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridy = 0;
		c.ipady = 6;
		result.add(title,c);
		c.ipady = 0;
		
		c.gridy ++;
		c.weightx = 1;
		c.weighty = 0.5;
		result.add(new JLabel(" "),c);
		c.weightx = 0;
		c.weighty = 0;

		c.gridy ++;
		result.add(current,c);
		c.gridy ++;
		result.add(currentTags,c);
		c.gridy ++;
		result.add(removeTag,c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy ++;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		result.add(new JLabel(" "),c);
		c.weightx = 0;
		c.weighty = 0;
		
		c.gridy ++;
		result.add(tagInstructions,c);
		c.insets = new Insets(0,0,0,0);
		c.gridy ++;
		result.add(listTags,c);
		c.gridy ++;
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.gridwidth = 1;
		result.add(tag,c);
		c.gridx = 1;
		result.add(add,c);
		c.gridx = 2;
		result.add(removeFromTags,c);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridy ++;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		result.add(new JLabel(" "),c);
		
		c.weighty = 0;
		c.weightx = 0;
		c.insets = new Insets(12,0,0,0);
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy ++;
		c.gridx = 0;
		result.add(past,c);
		c.insets = new Insets(0,0,0,0);
		c.gridy ++;
		result.add(pastNames,c);
		c.gridy ++;
		result.add(revertName,c);
		
		c.gridy ++;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 0.5;
		result.add(new JLabel(" "),c);
		c.weightx = 0;
		c.weighty = 0;
		 
		result.setBorder(BorderFactory.createLoweredBevelBorder());
		return result;
	}
	
	/**
	 * Updates PictureTab GUI with newly selected picture.
	 *
	 * @param pic
	 *           selected picture file
	 */
	public void selected(File pic){
		//Create new FileNode for selected picture.
		this.pictureNode  = new FileNode(pic,this.tagger,this.fileName);
		
		//Enable everything
		listTags.setEnabled(true);
		currentTags.setEnabled(true);
		pastNames.setEnabled(true);
		
		removeTag.setEnabled(true);
		tag.setEnabled(true);
		removeFromTags.setEnabled(true);
		add.setEnabled(true);
		revertName.setEnabled(true);
		rename.setEnabled(true);
		
		//Clear old items.
		currentTags.removeAllItems();
		pastNames.removeAllItems();
		
		//Repopulate
		for (String t : this.pictureNode.getTags()){
			currentTags.addItem(t);
		}
		for (String n : this.pictureNode.getPastNames()){
			pastNames.addItem(n);
		}
		
		title.setText(pic.getName());
		picture.setText("");
		ImageIcon image = new ImageIcon(new ImageIcon(pic.getAbsolutePath()).getImage().getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH));
		picture.setIcon(image);
	}
}
