import java.awt.EventQueue;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;


import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PersonalBestTracker {

	JFrame frame;
	User cUser;
	private JTable table;
	private JTextField weightUsed;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalBestTracker window = new PersonalBestTracker(cUser);
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
	public PersonalBestTracker(User cUser) {
		this.cUser = cUser;
		initialize(cUser);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User cUser) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.getContentPane().setLayout(null);
		
		SessionFactory factoryE = new Configuration()
				.configure()
				.addAnnotatedClass(exercises.class)
				.buildSessionFactory();
		//create session
		Session sessionE = factoryE.getCurrentSession();
		sessionE.beginTransaction();
		
		SessionFactory factoryU = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		//create session
		Session sessionU = factoryU.getCurrentSession();
		sessionU.beginTransaction();
		
		
		List<exercises> exercises = sessionE.createQuery("from exercises").getResultList();
		String[] exNames = new String[exercises.size()];
		for(int i = 0; i<exercises.size();i++) {
			exNames[i] = exercises.get(i).getName().toString();
		}
		
		JComboBox exerciseSel = new JComboBox(exNames);
		exerciseSel.setBounds(115, 96, 118, 29);
		frame.getContentPane().add(exerciseSel);
		
		
		String[] repCount = {"1","5","10","15"};
		
		JComboBox repSel = new JComboBox(repCount);
		repSel.setBounds(250, 96, 40, 29);
		frame.getContentPane().add(repSel);
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Reps");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(139, 64, 82, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Exercise");
		lblNewLabel_2.setBounds(231, 64, 82, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("This is designed to show you your max weight for an exercise at different reps");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(17, 0, 392, 45);
		frame.getContentPane().add(lblNewLabel_3);
		
		
		
		//table = new JTable({"","","",""}, {1,5,10,15} );
		//table.setBounds(61, 144, 261, 92);
		//frame.getContentPane().add(table);
		
		weightUsed = new JTextField();
		weightUsed.setBounds(17, 96, 75, 29);
		frame.getContentPane().add(weightUsed);
		weightUsed.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Weight");
		lblNewLabel.setBounds(10, 64, 82, 23);
		frame.getContentPane().add(lblNewLabel);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Calculate and Record");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double rmx1;
				double rmx5;
				double rmx10;
				double rmx15;
				while(weightUsed.getText() == "") {
					
				}
				
				try {
					double weightU = Double.valueOf(weightUsed.getText());;

					
					
					String reps = repSel.getSelectedItem().toString();
					if(reps == "1") {
						rmx1 = weightU;
						rmx5 = rmx1 * .95;
						rmx10 = rmx1 * .8;
						rmx15 = rmx1 * .6;
					}else if(reps == "5") {
						rmx1 = weightU * 1.05;
						rmx5 = weightU;
						rmx10 = rmx1 * .84;
						rmx15 = rmx1 * .63;
					}else if(reps == "10") {
						rmx10 = weightU;
						rmx1 = rmx10 * 1.2;
						rmx5 = rmx10 * 1.14;
						rmx15 = rmx10 * .72;
					}else {
						rmx15 = weightU;
						rmx1 = rmx15 * 1.4;
						rmx5 = rmx15 * 1.33;
						rmx10 = rmx15 * 1.12;
					}
				double[] maxes = {rmx1, rmx5, rmx10, rmx15};
				String[] readableMaxes = { "1 Rep Max: " + Double.toString(rmx1), "5 Rep Max: " + Double.toString(rmx5), "10 Rep Max:" + Double.toString(rmx10), "15 Rep Max: " + Double.toString(rmx15)};
				maxPreview preview = new maxPreview(readableMaxes); 
				preview.frame.setVisible(true);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame,"Please enter a weight.");
					e.printStackTrace();
				}
				
			
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(125, 145, 190, 59);
		frame.getContentPane().add(btnNewButton);
		
		
		
		}
	}

