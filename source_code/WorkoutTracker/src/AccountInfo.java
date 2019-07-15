//Sam Alcosser
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AccountInfo {

	JFrame frame;
	User cUser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountInfo window = new AccountInfo(cUser);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This application gets passed the user object to reference for the different functions
	 */
	public AccountInfo(User cUser) {
		initialize(cUser);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel numLbl = new JLabel("account id number");
		numLbl.setBounds(10, 26, 113, 14);
		frame.getContentPane().add(numLbl);
		
		int id = cUser.getId();
		JLabel num = new JLabel(""+ id + "");
		num.setBounds(167, 26, 46, 14);
		frame.getContentPane().add(num);
		
		JLabel fullNmLbl = new JLabel("Full Name");
		fullNmLbl.setBounds(10, 54, 101, 14);
		frame.getContentPane().add(fullNmLbl);
		
		JLabel uNamelbl = new JLabel("username");
		uNamelbl.setBounds(10, 86, 101, 14);
		frame.getContentPane().add(uNamelbl);
		
		JLabel emailLbl = new JLabel("email");
		emailLbl.setBounds(10, 130, 46, 14);
		frame.getContentPane().add(emailLbl);
		
		JLabel injury = new JLabel("injury");
		injury.setBounds(10, 175, 46, 14);
		frame.getContentPane().add(injury);
		
		JLabel fullName = new JLabel(cUser.getFullName().toString());
		fullName.setBounds(167, 54, 185, 14);
		frame.getContentPane().add(fullName);
		
		JLabel username = new JLabel(cUser.getUsername().toString());
		username.setBounds(167, 86, 185, 14);
		frame.getContentPane().add(username);
		
		JLabel email = new JLabel(cUser.getEmail().toString());
		email.setBounds(167, 130, 185, 14);
		frame.getContentPane().add(email);
		
		if((cUser.getInjury().toString() != null) && (cUser.getInjury().toString() != "")) {
			JLabel injval = new JLabel(cUser.getInjury().toString());
			injval.setBounds(167, 175, 185, 14);
			frame.getContentPane().add(injval);
		}
		
		
		//simple function that creates a working back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Homepage home = new Homepage(cUser);
				home.frame.setVisible(true);
				
			}
		});
		btnBack.setBounds(0, 238, 89, 23);
		frame.getContentPane().add(btnBack);
	}
}
