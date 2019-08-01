import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class maxPreview {

	JFrame frame;
	String[] listData;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		

			public void run() {
				try {
					maxPreview window = new maxPreview(listData);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public maxPreview(String[] listData) {
		this.listData = listData;
		initialize(listData);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] listData) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JList list = new JList(listData);
		list.setBounds(93, 55, 217, 126);
		frame.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnNewButton.setBounds(140, 186, 131, 31);
		frame.getContentPane().add(btnNewButton);
	}

}
