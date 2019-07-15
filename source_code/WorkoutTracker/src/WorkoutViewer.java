//Sam Alcosser
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class WorkoutViewer {

	JFrame frame;
	User cUser;
	Workout sWo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkoutViewer window = new WorkoutViewer(cUser, sWo);
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
	public WorkoutViewer(User cUser, Workout sWo) {
		initialize(cUser, sWo);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser, Workout sWo) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setBounds(100, 100, 624, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//sessions are necessary to query the correct information
		SessionFactory factoryPrevWorkouts = new Configuration()
				.configure()
				.addAnnotatedClass(exerciseDone.class)
				.buildSessionFactory();
		//create session
		Session sessioneDones = factoryPrevWorkouts.getCurrentSession();
		sessioneDones.beginTransaction();
		SessionFactory factoryE = new Configuration()
				.configure()
				.addAnnotatedClass(exercises.class)
				.buildSessionFactory();
		//create session
		Session sessionEx = factoryE.getCurrentSession();
		sessionEx.beginTransaction();
		//using PKs to get the right exercises from the right workout
		int key = sWo.getWorkoutid();
		
		//getting all the exercises done in this workout, then making a string array that for all intents and purposes does a toString on each of the exercises done
		List<exerciseDone> eDones = sessioneDones.createQuery("from exerciseDone e where workoutid = " + key).getResultList();
		String[] eDList = new String[eDones.size()];
		for(int i = 0;i<eDones.size();i++) {
			int exCode = eDones.get(i).getExerciseId();
			List<exercises> exNames =  sessionEx.createQuery("from exercises e where idExercises = "+ exCode).getResultList();
			
			eDList[i] = new String("exercise:  " + exNames.get(0).getName() +" | sets: " + eDones.get(i).getSets()  +  " | reps: " + eDones.get(i).getReps())  ;
			
			System.out.println(eDList.length);
		}
		JList list = new JList(eDList);
		list.setVisibleRowCount(120);
		list.setBounds(60, 33, 400, 359);
		frame.getContentPane().add(list);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Homepage home = new Homepage(cUser);
				home.frame.setVisible(true);
				sessioneDones.close();
				sessionEx.close();
			}
		});
		btnBack.setBounds(0, 0, 89, 23);
		frame.getContentPane().add(btnBack);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(834, 58, -22, 133);
		frame.getContentPane().add(scrollBar);
	}
}
