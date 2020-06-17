package it.polito.oop.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book {
	private HashMap<String,Topic> Topics=new HashMap<String,Topic>();
	private HashMap<String,Question> Questions=new HashMap<String,Question>();
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		if(keyword.equals("")||keyword==null)
			throw new BookException();
		if(Topics.containsKey(keyword))
			return Topics.get(keyword);
		Topic t=new Topic(keyword);
		Topics.put(keyword, t);
	    return t;
	}

	public Question createQuestion(String question, Topic mainTopic) {
		Question ret=new Question(question,mainTopic);
		this.Questions.put(question, ret);

		return ret;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        return null;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        return null;
	}

	public List<Topic> getAllTopics() {
        return null;
	}

	public boolean checkTopics() {
        return false;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        return null;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return null;
    }
}
