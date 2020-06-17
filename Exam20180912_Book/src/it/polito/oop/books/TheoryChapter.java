package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {
	private String title;
	private int numPage;
	private String Text;
	private List<Topic> Topics=new ArrayList<Topic>();
	
	public TheoryChapter(String title,int NumPage,String Text) {
		this.title=title;
		this.numPage=NumPage;
		this.Text=Text;
	}

    public String getText() {
		return this.Text;
	}

    public void setText(String newText) {
    	this.Text=newText;
    }


	public List<Topic> getTopics() {
        return this.Topics.stream().distinct().
        		sorted((a,b)->a.toString().compareTo(b.toString())).
        		collect(Collectors.toList());
	}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
    	this.title=newTitle;
    }

    public int getNumPages() {
        return this.numPage;
    }
    
    public void setNumPages(int newPages) {
    	this.numPage=newPages;
    }
    
    public void addTopic(Topic topic) {
    	Topics.add(topic);
    	Topics.addAll(topic.getSubTopicR(new ArrayList<Topic>()));
    }
    
}
