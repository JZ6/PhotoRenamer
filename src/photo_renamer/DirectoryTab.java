package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DirectoryTab extends JPanel {
	
	/**
	 * A JPanel tab where all directory traversing and picture file selection is done.
	 */
	private static final long serialVersionUID = 6156875600849221360L;

	/**
	 * The JPanel where a JFileChooser is placed.
	 */
	public DirectoryTab(JTabbedPane tabs, PictureTab pictureTab) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		
		//Filter only selects picture files
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", "png", "jpg", "jpeg");
		JFileChooser select = new JFileChooser();
		select.setFileFilter(filter);
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == (JFileChooser.APPROVE_SELECTION)) {
					File pic = select.getSelectedFile();
					pictureTab.selected(pic);
					//switch to picture tab.
					tabs.setSelectedIndex(1);
				}
			}
		});		
		add(select);
	}
}
