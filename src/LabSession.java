import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class LabSession {

	private String groupName;
	private long length;
	private DayOfWeek day;
	private LocalTime startTime;

	private String dayFormatted;

	public LabSession(String groupName, long length, DayOfWeek day, LocalTime startTime) {
		this.groupName = groupName;
		this.length = length;
		this.day = day;
		this.startTime = startTime;

		dayFormatted = getDayFormatted();
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

	public String getDayFormatted() {
		return day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
		dayFormatted = getDayFormatted();
	}

	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
}