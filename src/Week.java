import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Week {
	
	private LocalDate weekBegins;
	private ArrayList<Problem> problemSet;

	public Week(LocalDate weekBegins, ArrayList<Problem> problemSet) {
		this.weekBegins = weekBegins;
		this.problemSet = problemSet;
	}

	public LocalDate getWeekBegins() {
		return weekBegins;
	}

	public void setWeekBegins(LocalDate weekBegins) {
		this.weekBegins = weekBegins;
	}

	public ArrayList<Problem> getProblemSet() {
		return problemSet;
	}

	public void setProblemSet(ArrayList<Problem> problemSet) {
		this.problemSet = problemSet;
	}
}