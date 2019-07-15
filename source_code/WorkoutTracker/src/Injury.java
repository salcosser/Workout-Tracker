//Sam Alcosser
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Injury {

	JFrame frame;
	 User cUser;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Injury window = new Injury(cUser);
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
	public Injury(User cUser) {
		this.cUser = cUser;
		initialize(cUser);
	}
	String[] mGroups = {"back", "chest", "abs", "leg(s)", "arms"};
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblInjuryReport = new JLabel("Injury Report");
		springLayout.putConstraint(SpringLayout.NORTH, lblInjuryReport, 11, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblInjuryReport, 177, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblInjuryReport, 280, SpringLayout.WEST, frame.getContentPane());
		lblInjuryReport.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblInjuryReport);
		
		JList list = new JList(mGroups); //listing the different muscle groups
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		springLayout.putConstraint(SpringLayout.SOUTH, list, 160, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, list, 79, SpringLayout.WEST, frame.getContentPane());
		list.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		list.setBackground(new Color(255, 69, 0));
		springLayout.putConstraint(SpringLayout.NORTH, list, 19, SpringLayout.SOUTH, lblInjuryReport);
		springLayout.putConstraint(SpringLayout.WEST, list, 28, SpringLayout.WEST, frame.getContentPane());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("Report Injury");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String inj = (String)list.getSelectedValue();
			
			SessionFactory factory = new Configuration() //connecting to the user table 
					.configure()
					.addAnnotatedClass(User.class)
					.buildSessionFactory();
			//create session
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			Transaction tx = session.getTransaction();
			//selecting the right user, and then assigning the injury to that user
			List<User> uUsers = session.createQuery("from User u where u.username = '"+ cUser.getUsername() + "'").getResultList();
			User uUser = uUsers.get(0);
			uUser.setInjury(inj);
			session.update(uUser);
			tx.commit();
			JOptionPane.showMessageDialog(frame, "Injury submitted");
			frame.setVisible(false);
			Homepage home = new Homepage(cUser);
			home.frame.setVisible(true);
			session.close();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 47, SpringLayout.SOUTH, lblInjuryReport);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 42, SpringLayout.EAST, list);
		frame.getContentPane().add(btnNewButton);
		
	}
}
