package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Topic {
	private String keyword;
	private Set<Topic> subTopics=new HashSet<Topic>();
	

	public Topic(String keyword) {
		this.keyword=keyword;
	}

	public String getKeyword() {
        return this.keyword;
	}
	
	@Override
	public String toString() {
	    return this.keyword;
	}

	public boolean addSubTopic(Topic topic) {
		return subTopics.add(topic);
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
		List<Topic> ret=new ArrayList<Topic>(subTopics);
		ret.sort((a,b)->a.toString().compareTo(b.toString()));
        return ret;
	}
}
