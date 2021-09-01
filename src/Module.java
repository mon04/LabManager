import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Module {
	
	private String title;
	private String code;
	private ArrayList<LabSession> labSessions;
	private ArrayList<Week> weeks;
	private DayOfWeekAndTime problemsReleased;
	private DayOfWeekAndTime caEvaluationEnds;
	private LocalDateTime problemsDisappear;

	public Module(String title, String code, ArrayList<LabSession> labSessions, ArrayList<Week> weeks, DayOfWeekAndTime problemsReleased, DayOfWeekAndTime caEvaluationEnds, LocalDateTime problemsDisappear) {
		this.title = title;
		this.code = code;
		this.labSessions = labSessions;
		this.weeks = weeks;
		this.problemsReleased = problemsReleased;
		this.caEvaluationEnds = caEvaluationEnds;
		this.problemsDisappear = problemsDisappear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullTitle() {

		if(code.length() > 0 && title.length() > 0) {
			return String.format("%s - %s", code, title);
		}
		else if(code.length() > 0) {
			return code;
		}
		else if(title.length() > 0) {
			return title;
		}
		return "UNTITLED MODULE";
	}

	public ArrayList<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(ArrayList<Week> weeks) {
		this.weeks = weeks;
	}

	public ArrayList<LabSession> getLabSessions() {
		return labSessions;
	}

	public void setLabSessions(ArrayList<LabSession> labSessions) {
		this.labSessions = labSessions;
	}

	public DayOfWeekAndTime getProblemsReleased() {
		return problemsReleased;
	}

	public void setProblemsReleased(DayOfWeekAndTime problemsReleased) {
		this.problemsReleased = problemsReleased;
	}

	public DayOfWeekAndTime getCaEvaluationEnds() {
		return caEvaluationEnds;
	}

	public void setCaEvaluationEnds(DayOfWeekAndTime caEvaluationEnds) {
		this.caEvaluationEnds = caEvaluationEnds;
	}

	public LocalDateTime getProblemsDisappear() {
		return problemsDisappear;
	}

	public void setProblemsDisappear(LocalDateTime problemsDisappear) {
		this.problemsDisappear = problemsDisappear;
	}
}