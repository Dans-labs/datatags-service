package nl.knaw.dans.datatags.service.core.model;

import java.io.Serializable;

public class QA implements Serializable {
    private String question;
    private String answer;
    private String note;

    public QA() {
    }

    public QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public QA(String question, String answer, String note) {
        this.question = question;
        this.answer = answer;
        this.note = note;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
