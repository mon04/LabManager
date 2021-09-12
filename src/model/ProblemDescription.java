package model;

public class ProblemDescription {

	private String body;
	private String[] images;
	private String[] notes;
	private String[] codeSamples;
	private String[][] sampleIO;

	public ProblemDescription(String body, String[] images, String[] notes, String[] codeSamples, String[][] sampleIO) {
		this.body = body;
		this.images = images;
		this.notes = notes;
		this.codeSamples = codeSamples;
		this.sampleIO = sampleIO;
	}

	public ProblemDescription() {

		this("", new String[0], new String[0], new String[0], new String[0][]);

	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String[] getNotes() {
		return notes;
	}

	public void setNotes(String[] notes) {
		this.notes = notes;
	}

	public String[] getCodeSamples() {
		return codeSamples;
	}

	public void setCodeSamples(String[] codeSamples) {
		this.codeSamples = codeSamples;
	}

	public String[][] getSampleIO() {
		return sampleIO;
	}

	public void setSampleIO(String[][] sampleIO) {
		this.sampleIO = sampleIO;
	}
}