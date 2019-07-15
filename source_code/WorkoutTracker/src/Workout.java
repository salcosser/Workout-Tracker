//Sam Alcosser
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name="workouts")		//connecting to the right table
public class Workout{
	
	//connecting the variables to the correct columns
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="workoutid")
	private int workoutid;
	
	@Column(name="date")
	private Date date;
	
	
	
	@Column(name="users_id")
	private int userId;
	
	//auto generated setters and getters
	public int getWorkoutid() {
		return workoutid;
	}
	public void setWorkoutid(int workoutid) {
		this.workoutid = workoutid;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	Workout(){}
	//auto generated constructor and tostring methods
	public Workout(Date date, int userId) {
		super();
		this.date = date;
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Workout [workoutid=" + workoutid + ", date="
				+ date +  ", userId=" + userId + "]";
	}
	
}
