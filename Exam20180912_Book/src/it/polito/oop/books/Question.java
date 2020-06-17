package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {
	private String question;
	private Topic maintopic;
	private Collection<Answer> Answers=new ArrayList<Answer>();
	
	public class Answer{
		private String Answer;
		private boolean correct;
		
		
		public Answer(String answer,boolean correct) {
			this.Answer=answer;
			this.correct=correct;
		}
		
		public String getAnswer() {
			return Answer;
		}
		public void setAnswer(String answer) {
			Answer = answer;
		}
		public boolean isCorrect() {
			return correct;
		}
		public void setCorrect(boolean correct) {
			this.correct = correct;
		}
		
		@Override
		public String toString() {
			return this.Answer;
		}
		
	}
	
	public Question(String question,Topic maintopic) {
		this.question=question;
		this.maintopic=maintopic;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public Topic getMainTopic() {
		return this.maintopic;
	}

	public void addAnswer(String answer, boolean correct) {
		this.Answers.add(new Answer(answer,correct));
	}
	
    @Override
    public String toString() {
        return this.question+" ("+this.maintopic+")";
    }

	public long numAnswers() {
	    return this.Answers.size();
	}

	public Set<String> getCorrectAnswers() {
		return this.Answers.stream().filter(s->s.isCorrect()).
				collect(Collectors.mapping(s->s.toString(),Collectors.toSet()));
	}

	public Set<String> getIncorrectAnswers() {
		return this.Answers.stream().filter(s->!s.isCorrect()).
				collect(Collectors.mapping(s->s.toString(),Collectors.toSet()));
	}
}
