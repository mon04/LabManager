package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Problem {

	private String title;
	private String language;
	private String type;
	private boolean hidden;
	private ObservableList<String> files;
	private ProblemDescription description;

	public Problem(String title, String language, String type, boolean hidden, ObservableList<String> files, ProblemDescription description) {
		this.title = title;
		this.language = language;
		this.type = type;
		this.hidden = hidden;
		this.files = files;
		this.description = description;
	}

	public Problem(String title, String language, String type, boolean hidden, String file, ProblemDescription description) {
		ObservableList<String> files = FXCollections.observableArrayList();
		files.add(file);
		this.title = title;
		this.language = language;
		this.type = type;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getType() {
		return type;
	}

	public void setType(String set) {
		this.type = type;
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