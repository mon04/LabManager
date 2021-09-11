import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Week {
	
	private LocalDate weekBegins;
	private ObservableList<Problem> problemSet;

	public Week(LocalDate weekBegins, ObservableList<Problem> problemSet) {
		this.weekBegins = weekBegins;
		this.problemSet = problemSet;
	}

	public LocalDate getWeekBegins() {
		return weekBegins;
	}

	public void setWeekBegins(LocalDate weekBegins) {
		this.weekBegins = weekBegins;
	}

	public ObservableList<Problem> getProblemSet() {
		return problemSet;
	}

	public void setProblemSet(ObservableList<Problem> problemSet) {
		this.problemSet = problemSet;
	}
}