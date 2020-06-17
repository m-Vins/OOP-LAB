package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {
	private String Title;
	private int numPages;
	private List<Question> Questions=new ArrayList<Question>();
	
	public ExerciseChapter(String title,int num) {
		this.Title=title;
		this.numPages=num;
	}
	
    public List<Topic> getTopics() {
        return Questions.stream().map(Question::getMainTopic).distinct().
        		sorted((a,b)->a.toString().compareTo(b.toString())).
        		collect(Collectors.toList());
	};
	

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String newTitle) {
    	this.Title=newTitle;
    }

    public int getNumPages() {
        return this.numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages=newPages;
    }
    

	public void addQuestion(Question question) {
		Questions.add(question);
	}	
}
