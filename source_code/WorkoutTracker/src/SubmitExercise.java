//Sam Alcosser
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SubmitExercise {

	JFrame frame;
	private JTextField textField;
	User cUser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubmitExercise window = new SubmitExercise();
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
	public SubmitExercise(User cUser) {
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
		
		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(exercises.class)
				.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		textField = new JTextField();
		textField.setBounds(107, 102, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblSubmitNewExercise = new JLabel("Submit New Exercise");
		lblSubmitNewExercise.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubmitNewExercise.setBounds(126, 21, 168, 14);
		frame.getContentPane().add(lblSubmitNewExercise);
		
		
		List<String> mGroup = session.createQuery("select distinct e.mGroup from exercises e").getResultList(); //getting all individual muscle groups with a fancy SQL/HQL query
		String[] mGroups = new String[mGroup.size()];
		for(int i = 0;i<mGroup.size();i++) {
			mGroups[i] = mGroup.get(i);
		}
		JComboBox comboBox = new JComboBox(mGroups);
		comboBox.setBounds(261, 102, 127, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblExerciseName = new JLabel("Exercise Name");
		lblExerciseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblExerciseName.setBounds(96, 77, 112, 14);
		frame.getContentPane().add(lblExerciseName);
		
		JLabel lblMuscleGroup = new JLabel("Muscle Group");
		lblMuscleGroup.setHorizontalAlignment(SwingConstants.CENTER);
		lblMuscleGroup.setBounds(285, 77, 88, 14);
		frame.getContentPane().add(lblMuscleGroup);
		JButton btnNewButton = new JButton("Submit Workout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> nName = session.createQuery("from exercises e where e.name = '" + textField.getText() + "'").getResultList();
				if(nName.size() >= 1) {
					JOptionPane.showMessageDialog(frame, "An exercise of that name already exists");
				}
				else {
					exercises nEx = new exercises(textField.getText(), comboBox.getSelectedItem().toString());
					session.save(nEx);
					session.getTransaction().commit();
					JOptionPane.showMessageDialog(frame, "Thank you for submitting the exercise");
					frame.setVisible(false);
					frame.dispose();
					Homepage reHome = new Homepage(cUser);
					reHome.frame.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(167, 150, 127, 23);
		frame.getContentPane().add(btnNewButton);
	}
	
}
