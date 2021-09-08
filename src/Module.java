import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Module {
	
	private String title;
	private String code;
	private ObservableList<LabSession> labSessions;
	private ArrayList<Week> weeks;
	private DayOfWeekAndTime problemsReleased;
	private DayOfWeekAndTime caEvaluationEnds;
	private LocalDateTime problemsDisappear;

	public Module(String title, String code, ObservableList<LabSession> labSessions, ArrayList<Week> weeks, DayOfWeekAndTime problemsReleased, DayOfWeekAndTime caEvaluationEnds, LocalDateTime problemsDisappear) {
		this.title = title;
		this.code = code;
		this.labSessions = labSessions;
		this.weeks = weeks;
		this.problemsReleased = problemsReleased;
		this.caEvaluationEnds = caEvaluationEnds;
		this.problemsDisappear = problemsDisappear;
	}

	public Module(String code) {
		this();
		this.code = code;
	}


	public Module() {
		this(
				"",
				"DEFAULT_CODE",
				FXCollections.observableArrayList(),
				new ArrayList<Week>(),
				new DayOfWeekAndTime(DayOfWeek.MONDAY, LocalTime.of(9, 0)),
				new DayOfWeekAndTime(DayOfWeek.FRIDAY, LocalTime.of(21,0)),
				LocalDateTime.of(2021, 8, 31, 23, 59)
		);
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

		String fullTitle = code;

		if(title.length() > 0) {
			fullTitle += String.format(" - %s", title);
		}

		return fullTitle;
	}

	public ArrayList<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(ArrayList<Week> weeks) {
		this.weeks = weeks;
	}

	public ObservableList<LabSession> getLabSessions() {
		return labSessions;
	}

	public void setLabSessions(ObservableList<LabSession> labSessions) {
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