package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProblemSet {

    ObservableList<Problem> problems;

    public ProblemSet(ObservableList<Problem> problems) {

        this.problems = problems;
    }

    public ProblemSet() {
        this(FXCollections.observableArrayList());
    }

    public ObservableList<Problem> getProblems() {
        return problems;
    }

    public void setProblems(ObservableList<Problem> problems) {
        this.problems = problems;
    }

    public String getInfo() {

        if(problems.size() < 1) {
            return "None";
        }

        String info = "";

        for(String type: getAllTypes()) {

            int count = getNumberOfType(type);
            info = info + (String.format("%s (%d), ", type, count));
        }

        if(info.length() > 1) {
            info = info.substring(0, info.length() -2);
        }

        return info;
    }

    public ArrayList<String> getAllTypes() {

        ArrayList<String> typesFound = new ArrayList<String>();

        for(Problem p: problems) {

            String type = p.getType();

            if(!(typesFound.contains(type))) {
                typesFound.add(type);
            }
        }

        return typesFound;
    }

    public int getNumberOfType(String type) {

        int count = 0;

        for(Problem p: problems) {

            if(p.getType().equals(type)) {
                count++;
            }
        }

        return count;
    }

    public String toString() {

        return getInfo();
    }

}
