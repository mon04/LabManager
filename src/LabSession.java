import java.time.DayOfWeek;
import java.time.LocalTime;

public class LabSession {

	private String groupName;
	private long length;
	private DayOfWeek day;
	private LocalTime startTime;

	public LabSession(String groupName, long length, DayOfWeek day, LocalTime startTime) {
		this.groupName = groupName;
		this.length = length;
		this.day = day;
		this.startTime = startTime;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
}