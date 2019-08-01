//Sam Alcosser
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "personal_best") // connecting to the table
public class PersonalBest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id", nullable=false, updatable=false)
	private User use;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUse() {
		return use;
	}
	public void setUse(User use) {
		this.use = use;
	}
	public exercises getExer() {
		return exer;
	}
	public void setExer(exercises exer) {
		this.exer = exer;
	}
	public double getMw1() {
		return mw1;
	}
	public void setMw1(double mw1) {
		this.mw1 = mw1;
	}
	public double getMw5() {
		return mw5;
	}
	public void setMw5(double mw5) {
		this.mw5 = mw5;
	}
	public double getMw10() {
		return mw10;
	}
	public void setMw10(double mw10) {
		this.mw10 = mw10;
	}
	public double getMw15() {
		return mw15;
	}
	public void setMw15(double mw15) {
		this.mw15 = mw15;
	}
	@ManyToOne(optional=false)
	@JoinColumn(name="exercise_id", nullable=false, updatable=false)
	private exercises exer;
	@Column(name="1_rep_max_weight")
	private double mw1;
	@Column(name="5_rep_max_weight")
	private double mw5;
	@Column(name="10_rep_max_weight")
	private double mw10;
	@Column(name="15_rep_max_weight")
	private double mw15;
	
	
}
