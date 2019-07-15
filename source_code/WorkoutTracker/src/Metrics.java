//Sam Alcosser
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Metrics {

    JFrame frame;
	User cUser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Metrics window = new Metrics(cUser);
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Metrics(User cUser) {
		this.cUser = cUser;
		initialize(cUser);
	}
	
	//function from an online source to find the index in a list of integers that has the largest value
	public  static int indexOfMax(int[] arr) {
	    if (arr.length == 0) {
	        return -1;
	    }

	    int max = arr[0];
	    int maxIndex = 0;

	    for (int i = 1; i < arr.length; i++) {
	        if (arr[i] > max) {
	            maxIndex = i;
	            max = arr[i];
	        }
	    }

	    return maxIndex;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser) {
		
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.getContentPane().setLayout(null);
		
		//long list of sessions needed to reference over the course of the code
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
		List<exercises> exList = sessionE.createQuery("from exercises").getResultList(); //resources
		List<Workout> wOId = sessionWO.createQuery("from Workout w where w.userId = " + cUser.getId()).getResultList();
		List<exerciseDone> eDones = new ArrayList<>();
		for(int b = 0;b<wOId.size();b++) {
			List<exerciseDone> eDonesW = sessionED.createQuery("from exerciseDone e where e.wO.workoutid = " +wOId.get(b).getWorkoutid()).getResultList();
			for(int a = 0;a<eDonesW.size(); a++) {
				eDones.add(eDonesW.get(a));
			}
		}
		//resources
		ArrayList<exerciseDone> maxesList = new ArrayList<>(); //holding the highest rep count of each exercise
		ArrayList<exerciseDone> currEx = new ArrayList<>(); // holding the distinct exercises as they are compared
		
		for(int i = 0; i<exList.size();i++) { //going through each exercise
			int currE = exList.get(i).getIdExercises(); //getting the PK to compare to 
			
			for(int j = 0;j<eDones.size();j++) { //going through each of the exercises done
				if(eDones.get(j).getExerciseId() == currE) {
					currEx.add(eDones.get(j));
					continue;
				}
				if(currEx.size() >= 1) {
					
				int[] mReps = new int[currEx.size()];	//array to hold the total reps of each exercise done
				for(int k = 0; k<currEx.size();k++) {
					mReps[k] = ( (currEx.get(k).getReps())*(currEx.get(k).getSets() )); //computing
					
				}
				int bInmReps = indexOfMax(mReps);	//getting the index of the exercise with the most reps out of the list
				System.out.println(mReps[bInmReps]);
				System.out.println("reps"+ currEx.get(bInmReps).getReps());
				maxesList.add(currEx.get(indexOfMax(mReps)));
				}
				else {
					continue;
				}
				currEx.clear();
				
			}
			
		}
		//making a string list of all of the data so that it can be later used in a list
		String[] listVersionMax = new String[maxesList.size()];
		for(int n = 0;n<maxesList.size();n++) {
			int exId =  maxesList.get(n).getExerciseId();
			List<exercises> exName = sessionE.createQuery("from exercises e where e.idExercises = " + exId).getResultList();
			listVersionMax[n] = new String(exName.get(0).getName().toString() + " | sets: "  + 
					maxesList.get(n).getSets() + " | reps: " + maxesList.get(n).getReps() + " | total reps: " + (maxesList.get(n).getReps() * maxesList.get(n).getSets()) +
					" | weight: " + maxesList.get(n).getWeight() + " lbs"
					);
		}
		
		
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	//simple back button that returns back to the homepage and closes the sessions
				frame.setVisible(false);
				Homepage home = new Homepage(cUser);
				home.frame.setVisible(true);
				sessionE.close();
				sessionED.close();
				sessionWO.close();
			}
		});
		btnBack.setBounds(0, 0, 89, 23);
		frame.getContentPane().add(btnBack);
		String[] exListNames = new String[exList.size()];
		for(int i = 0; i<exList.size(); i++) {
			exListNames[i] = exList.get(i).getName().toString();
		}
		System.out.println(exList.size());
		
		JComboBox comboBox = new JComboBox(exListNames);
		comboBox.setBounds(45, 126, 171, 23);
		frame.getContentPane().add(comboBox);
		
		//extra function that allows the user to see the max weight they used, and if there wasnt one, then the error is caught by a try catch statement
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selected = comboBox.getSelectedItem().toString();
				List<exercises> name = sessionE.createQuery("from exercises e where e.name = '"+ selected + "'").getResultList();
				int idNo = name.get(0).getIdExercises();
				List<Double> mWeight = sessionED.createQuery("select max(weight) from exerciseDone f where f.exerciseId = " +idNo ).getResultList();
				
				try {
					double max =  mWeight.get(0);
					JOptionPane.showMessageDialog(frame, "max weight was " + max + " lbs.");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "No exercise recorded.");
				}
				
			}
		});
		btnSelect.setBounds(316, 126, 89, 23);
		frame.getContentPane().add(btnSelect);
		
		JLabel lblMaxForSelected = new JLabel("Max Weight For Selected Workout");
		lblMaxForSelected.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxForSelected.setBounds(107, 97, 277, 14);
		frame.getContentPane().add(lblMaxForSelected);
		
		JLabel lblTheMaximumReps = new JLabel("The maximum reps for each exercise recorded");
		lblTheMaximumReps.setHorizontalAlignment(SwingConstants.CENTER);
		lblTheMaximumReps.setBounds(128, 285, 317, 14);
		frame.getContentPane().add(lblTheMaximumReps);
		

		JList list = new JList(listVersionMax);
		list.setBounds(63, 55, 485, 192);
		frame.getContentPane().add(list);
		frame.setBounds(100, 100, 639, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(63, 336, 447, 150);
		frame.getContentPane().add(scrollPane);
		
}
}
