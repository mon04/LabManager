import javafx.collections.ObservableList;

public class Problem {

	private String title;
	private String set;
	private boolean hidden;
	private ObservableList<String> files;
	private ProblemDescription description;

	public Problem(String title, String set, boolean hidden, ObservableList<String> files, ProblemDescription description) {
		this.title = title;
		this.set = set;
		this.hidden = hidden;
		this.files = files;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public ObservableList<String> getFiles() {
		return files;
	}

	public void setFiles(ObservableList<String> files) {
		this.files = files;
	}

	public ProblemDescription getDescription() {
		return description;
	}

	public void setDescription(ProblemDescription description) {
		this.description = description;
	}
}