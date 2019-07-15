//Sam Alcosser
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class SignUp {

	JFrame frame;
	private JTextField fullName;
	private JTextField username;
	private JTextField email;
	private JPasswordField password;
	private JPasswordField passConfirm;
//make it so that you cant make a username of the same thing as someone else
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
					window.frame.setVisible(true);

					window.frame.setSize(800,600);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setBounds(100, 100, 798, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New User Sign Up");
		lblNewLabel.setBounds(336, 13, 121, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblFullName = new JLabel("full name");
		lblFullName.setBounds(229, 56, 56, 16);
		frame.getContentPane().add(lblFullName);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(215, 103, 70, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblEmail = new JLabel("email");
		lblEmail.setBounds(229, 164, 56, 16);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(215, 240, 70, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("confirm password");
		lblConfirmPassword.setBounds(174, 302, 111, 16);
		frame.getContentPane().add(lblConfirmPassword);
		
		fullName = new JTextField();
		fullName.setBounds(297, 53, 116, 22);
		frame.getContentPane().add(fullName);
		fullName.setColumns(10);
		
		
		username = new JTextField();
		username.setBounds(297, 100, 116, 22);
		frame.getContentPane().add(username);
		username.setColumns(10);
		String user_name = username.getText().toString();
		
		email = new JTextField();
		email.setBounds(297, 161, 116, 22);
		frame.getContentPane().add(email);
		email.setColumns(10);
		
		
		password = new JPasswordField();
		password.setBounds(296, 237, 117, 22);
		frame.getContentPane().add(password);
		
		passConfirm = new JPasswordField();
		passConfirm.setBounds(297, 299, 116, 22);
		frame.getContentPane().add(passConfirm);

		//create session factory
		SessionFactory factory = new Configuration()
														.configure()
														.addAnnotatedClass(User.class)
														.buildSessionFactory();
	//create session
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		JButton btnCheckUsernameAvailibility = new JButton("check username availibility");
		btnCheckUsernameAvailibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//query database for the username
				//give an alert pane saying whether or not there is a user by that name in the system
				List<User> uOfName = session.createQuery("from User u where u.username = '"+username.getText().toString()+"'",User.class).getResultList();
				System.out.println(user_name);
				for(User u: uOfName) {
					System.out.println(u);
				}
				//JOptionPane.showMessageDialog(Login, "Password incorrect.");
				if(uOfName.size() >= 1) {
					JOptionPane.showMessageDialog(frame, "username taken");
					for(User u: uOfName) {
						System.out.println(u);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "This username is not taken.");
					for(User u: uOfName) {
						System.out.println(u);
					}
				}
			}
		});
		btnCheckUsernameAvailibility.setBounds(476, 88, 209, 46);
		frame.getContentPane().add(btnCheckUsernameAvailibility);
		//actually signing up
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//do stuff
				List<User> uOfName = session.createQuery("from User u where u.username = '"+username.getText().toString()+"'",User.class).getResultList();
				//check username availibility
				if(uOfName.size() >= 1) {
					JOptionPane.showMessageDialog(frame, "username taken");
					for(User u: uOfName) {
						System.out.println(u);
					}
				}
				else { //catching if any field is not filled out
					if(new String(password.getPassword()).equals(new String(passConfirm.getPassword()))) {
						if(
								(fullName.getText().toString().equalsIgnoreCase(null))
								|| (fullName.getText().toString().equalsIgnoreCase(""))
								|| (username.getText().toString().equalsIgnoreCase(null))
								|| (username.getText().toString().equalsIgnoreCase(""))
								|| (email.getText().toString().equalsIgnoreCase(null))
								|| (email.getText().toString().equalsIgnoreCase(""))
								|| (new String(password.getPassword()).equalsIgnoreCase(null))
								|| (new String(password.getPassword()).equalsIgnoreCase(""))
										){
						
							JOptionPane.showMessageDialog(frame, "Please fill out all fields");
								 }
						else {
						User nUser = new User(fullName.getText().toString(), username.getText().toString(),email.getText().toString(),new String(password.getPassword()));
						session.save(nUser);
						session.getTransaction().commit();
						JOptionPane.showMessageDialog(frame, "Welcome new user!");
						frame.setVisible(false);
						Homepage home = new Homepage(nUser);
						home.frame.setVisible(true);
						}
					}
					else{
						JOptionPane.showMessageDialog(frame, "Passwords do not match");
						System.out.println("password: " + new String(password.getPassword()) + "passconfirm: " + new String(passConfirm.getPassword()));
					}
				}
				
			}
		});
		btnSignUp.setBounds(302, 387, 97, 25);
		frame.getContentPane().add(btnSignUp);
		
		
	}
}
