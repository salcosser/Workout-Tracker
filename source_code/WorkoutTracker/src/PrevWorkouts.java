//Sam Alcosser
import java.awt.EventQueue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class PrevWorkouts {

	JFrame frame;
	User cUser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrevWorkouts window = new PrevWorkouts(cUser);
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
	public PrevWorkouts(User cUser) {
		initialize(cUser);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser) {
		SessionFactory factoryPrevWorkouts = new Configuration()	//creating the session factory and session to connect to the workout table
				.configure()
				.addAnnotatedClass(Workout.class)
				.buildSessionFactory();
		//create session
		Session sessionPWorkouts = factoryPrevWorkouts.getCurrentSession();
		sessionPWorkouts.beginTransaction();
		List<Workout> wOs = sessionPWorkouts.createQuery("from Workout w where w.userId="+ cUser.getId()).getResultList();	//simply listing all the workouts the user has done by date
		Date[] wOList = new Date[wOs.size()];
		for(int i = 0; i<wOs.size();i++) {
			wOList[i] = wOs.get(i).getDate();
			
		}
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.getContentPane().setLayout(null);
		
		JLabel test = new JLabel(cUser.getUsername().toString());
		test.setHorizontalAlignment(SwingConstants.CENTER);
		test.setBounds(10, 211, 185, 59);
		frame.getContentPane().add(test);
		
		JList list = new JList(wOList);
		list.setBounds(97, 261, 342, 167);
		frame.getContentPane().add(list);
		list.setVisibleRowCount(500);
		frame.setBounds(100, 100, 500, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(84, 49, 344, 169);
		frame.getContentPane().add(scrollPane);
		
		JButton btnSelectWorkout = new JButton("Select Workout");
		btnSelectWorkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	//gets the selected workout and sends it along with the current user to the workout viewer page
				int sWo = list.getSelectedIndex();
				 List<Workout>selWorkouts=  sessionPWorkouts.createQuery("from Workout w where workoutid = " + wOs.get(sWo).getWorkoutid()).getResultList();
				 Workout sWorkout = selWorkouts.get(0);
				 frame.setVisible(false);
				 WorkoutViewer wOView = new WorkoutViewer(cUser, sWorkout);
				 sessionPWorkouts.close();
				 wOView.frame.setVisible(true);
			}
		});
		btnSelectWorkout.setBounds(217, 229, 122, 23);
		frame.getContentPane().add(btnSelectWorkout);
		
		
		
		
		
		
		
		
		
		
	}
}
