package abd.p1.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answered_questions")
public class AnsweredQuestion
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int answerId;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Opcion option;
	
	private int relevance;
	
	public AnsweredQuestion()
	{
	}

	public int getRelevance() {
		return relevance;
	}
	
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}

	public int getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Opcion getOption() {
		return option;
	}

	public void setOption(Opcion option) {
		this.option = option;
	}
}