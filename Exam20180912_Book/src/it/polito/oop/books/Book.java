package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {
	private HashMap<String,Topic> Topics=new HashMap<String,Topic>();
	private HashMap<String,Question> Questions=new HashMap<String,Question>();
	private List<TheoryChapter> TheoryChapters=new ArrayList<TheoryChapter>();
	private List<ExerciseChapter> ExerciseChapters=new ArrayList<ExerciseChapter>();
	
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
		TheoryChapter ret=new TheoryChapter(title,numPages,text);
		TheoryChapters.add(ret);
        return ret;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter ret=new ExerciseChapter(title, numPages);
		ExerciseChapters.add(ret);
        return ret;
	}

	public List<Topic> getAllTopics() {
		return Topics.values().stream().filter(s->{
				return TheoryChapters.stream().filter(a->a.getTopics().contains(s)).findFirst().isPresent()||
					ExerciseChapters.stream().filter(a->a.getTopics().contains(s)).findFirst().isPresent();
		}).distinct().sorted((a,b)->a.toString().compareTo(b.toString())).
		collect(Collectors.toList());
	}

	public boolean checkTopics() {
		for(ExerciseChapter x:ExerciseChapters) {
			if(x.getTopics().stream().filter(s->{
					return !TheoryChapters.stream().filter(a->a.getTopics().
							contains(s)).findFirst().isPresent();
				}).findFirst().isPresent())
				return false;
		}
		return true;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        return new Assignment(ID,chapter);
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return Questions.values().stream().collect(Collectors.
    			groupingBy(Question::numAnswers,Collectors.toList()));
    }
}
