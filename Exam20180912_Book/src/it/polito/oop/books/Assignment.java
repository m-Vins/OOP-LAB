package it.polito.oop.books;

import java.util.List;


public class Assignment {
	private String Id;
	private ExerciseChapter Chapter;
	private double score=0;

    public Assignment(String iD, ExerciseChapter chapter) {
    	this.Id=iD;
    	this.Chapter=chapter;
	}

	public String getID() {
        return Id;
    }

    public ExerciseChapter getChapter() {
        return Chapter;
    }

    public double addResponse(Question q,List<String> answers) {
    	double n=q.getCorrectAnswers().size()+q.getIncorrectAnswers().size();
    	double fp=(double) answers.stream().filter(s->q.getIncorrectAnswers().contains(s)).count();
    	double fn=(double) q.getCorrectAnswers().size()-answers.stream().filter(s->q.getCorrectAnswers().contains(s)).count();
        double ret=n-fp-fn;
        ret= ret/n;
        score+=ret;
        return ret;
    }
    
    public double totalScore() {
        return score;
    }

}
