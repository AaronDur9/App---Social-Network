package abd.p1.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_invite_messages")
public class QuestionInviteMessage extends Message
{
	@ManyToOne
	private Pregunta questionInvite; //Question that the Message invites to answer.

	
	public QuestionInviteMessage() {
		
	}
	
	public QuestionInviteMessage(Message msg, Pregunta questionInvite)
	{
		super(msg.getSource(), msg.getTarget(), msg.getMsgTimestamp(), msg.isRead());
		this.questionInvite = questionInvite;
	}

	public Pregunta getQuestionInvite() {
		return questionInvite;
	}

	public void setQuestionInvite(Pregunta questionInvite) {
		this.questionInvite = questionInvite;
	}
}