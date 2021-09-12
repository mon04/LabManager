import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Week {
	
	private LocalDate weekBegins;
	private ProblemSet problemSet;

	public Week(LocalDate weekBegins, ProblemSet problemSet) {
		this.weekBegins = weekBegins;
		this.problemSet = problemSet;
	}

	public LocalDate getWeekBegins() {
		return weekBegins;
	}

	public void setWeekBegins(LocalDate weekBegins) {
		this.weekBegins = weekBegins;
	}

	public ProblemSet getProblemSet() {
		return problemSet;
	}

	public void setProblemSet(ProblemSet problemSet) {

		this.problemSet = problemSet;
	}

}