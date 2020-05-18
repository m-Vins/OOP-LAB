package diet;

import java.time.LocalTime;

public class TimeTable implements Comparable<TimeTable>{
	private LocalTime start;
	private LocalTime finish;
	
	
	public TimeTable(String Start,String Finish) {
		this.start=LocalTime.parse(Start);
		this.finish=LocalTime.parse(Finish);
	}
	
	public LocalTime getStart() {
		return this.start;
	}
	
	public LocalTime getFinish() {
		return this.finish;
	}


	
	public boolean checkTime(LocalTime time) {
		if(time.equals(this.getStart()))
			return true;
		if(start.isBefore(time)&&finish.isBefore(time))
			return true;
		return false;
	}

	@Override
	public int compareTo(TimeTable arg0) {
		return this.getStart().compareTo(arg0.getStart());
	}
}
