//Sam ALcosser
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.persistence.LockModeType;
import javax.security.auth.login.LoginContext;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;

public class Tracker {

	JFrame frame;
	 User cUser;
	 Workout w;
	 private JTextField weight;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tracker window = new Tracker(cUser);
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
	public Tracker(User cUser, Workout w) {
		this.w = w;
		this.cUser = cUser;
		initialize(cUser, w);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser, Workout w) {
		
		
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 628, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Building the resources needed for this very complex application
		
		SessionFactory factoryExerciseDone = new Configuration()
				.configure()
				.addAnnotatedClass(exerciseDone.class)
				.buildSessionFactory();
		//create session
		Session sessionED = factoryExerciseDone.getCurrentSession();
		sessionED.beginTransaction();
		
		SessionFactory factoryExercises = new Configuration()
				.configure()
				.addAnnotatedClass(exercises.class)
				.buildSessionFactory();
		//create session
		Session sessionE = factoryExercises.getCurrentSession();
		sessionE.beginTransaction();
		
		SessionFactory factoryWO = new Configuration()
				.configure()
				.addAnnotatedClass(Workout.class)
				.buildSessionFactory();
		//create session
		Session sessionWO = factoryWO.getCurrentSession();
		sessionWO.beginTransaction();
		System.out.println(w.getWorkoutid());
		//connecting to the newly created workout
		try {
			int currWOID = w.getWorkoutid();
			List<Workout> wO = sessionWO.createQuery("from Workout w where w.workoutid = " + currWOID, Workout.class).getResultList();
			Workout wOCurrent = wO.get(0);
		}
		catch(Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
			System.out.println("ERROR");
		}
		//this code is repeated from the try catch statement for stability reasons
		int currWOID = w.getWorkoutid();
		List<Workout> wO = sessionWO.createQuery("from Workout w where w.workoutid = " + currWOID, Workout.class).getResultList();
		Workout wOCurrent = wO.get(0);
		sessionWO.close();
		//useful to debug the code, uncomment lines 119 and 120 to debug
		//System.out.println("length of the query of the workouts of id: "+ w.getWorkoutid()+ "is" + wO.size());
		//System.out.println(w.toString());
		
		
		//getting and constructing a list of the exercise names
		List<exercises> exList = sessionE.createQuery("from exercises").getResultList();
		String[] exListNames = new String[exList.size()];
		for(int i = 0; i<exList.size(); i++) {
			exListNames[i] = exList.get(i).getName().toString();
		}
		JLabel lblTracker = new JLabel("Tracker");
		lblTracker.setForeground(Color.WHITE);
		lblTracker.setHorizontalAlignment(SwingConstants.CENTER);
		lblTracker.setBounds(234, 19, 117, 14);
		frame.getContentPane().add(lblTracker);
		
		JLabel lblWorkout = new JLabel("Exercise");
		lblWorkout.setForeground(Color.WHITE);
		lblWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkout.setBounds(72, 66, 92, 23);
		frame.getContentPane().add(lblWorkout);
		
		JLabel lblNewLabel = new JLabel("Reps");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(271, 66, 92, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sets");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(181, 66, 113, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox excerciseSelector = new JComboBox(exListNames);
		excerciseSelector.setBounds(55, 97, 132, 23);
		frame.getContentPane().add(excerciseSelector);
		
		JSpinner repsCount = new JSpinner();
		repsCount.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		repsCount.setBounds(281, 94, 51, 30);
		frame.getContentPane().add(repsCount);
		
		JSpinner setsCount = new JSpinner();
		setsCount.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		setsCount.setBounds(207, 94, 46, 30);
		frame.getContentPane().add(setsCount);
		
		JTextField impMass = new JTextField();
		impMass.setHorizontalAlignment(SwingConstants.RIGHT);
		impMass.setText("0");
		impMass.setBounds(427, 94, 81, 29);
		frame.getContentPane().add(impMass);
		impMass.setColumns(10);
		
		JButton btnAddExercise = new JButton("Add Exercise");
		btnAddExercise.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			double imperialMass = Double.valueOf(impMass.getText());
			List<exercises> exIdFinder = sessionE.createQuery("from exercises e where e.name = '"+ excerciseSelector.getSelectedItem().toString() + "'").getResultList();
			int exId = exIdFinder.get(0).getIdExercises();
			int sets = (int) setsCount.getValue();
			int reps = (int) repsCount.getValue();
			exerciseDone nExD = new exerciseDone(exId, reps, sets,wOCurrent,imperialMass);
			//System.out.println(nExD.toString()); 			//debugging line, uncomment to debug
			
			sessionED.save(nExD);
			}
		});
		btnAddExercise.setBounds(207, 167, 173, 31);
		frame.getContentPane().add(btnAddExercise);
		
		JButton btnSaveWorkout = new JButton("Save Workout");
		btnSaveWorkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//saving the workout, using a try catch block for stability
				
				try {
					sessionED.getTransaction().commit();
					
				}catch(Exception n) {
					System.out.println("error:**********");
					n.printStackTrace();
					System.out.println("***********");
				}
				JOptionPane.showMessageDialog(frame, "Workout submitted");	//going back to the mome
				frame.setVisible(false);
				frame.dispose();
				Homepage reHome = new Homepage(cUser);
				reHome.frame.setVisible(true);
				sessionED.close();
				sessionE.close();
				sessionWO.close();
			}
		});
		btnSaveWorkout.setBounds(217, 216, 156, 31);
		frame.getContentPane().add(btnSaveWorkout);
		
		JLabel lblNewLabel_2 = new JLabel("weight (lbs)");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(417, 66, 113, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNoteIfEntering = new JLabel("note: if entering a run or biking, please leave the reps and sets columns blank and use the weight column to track milage");
		lblNoteIfEntering.setForeground(Color.WHITE);
		lblNoteIfEntering.setFont(new Font("Trebuchet MS", Font.PLAIN, 9));
		lblNoteIfEntering.setBounds(29, 108, 528, 90);
		frame.getContentPane().add(lblNoteIfEntering);
		
	
	}
}
