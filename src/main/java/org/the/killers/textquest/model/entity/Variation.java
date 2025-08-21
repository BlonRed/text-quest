package org.the.killers.textquest.model.entity;

import java.io.Serializable;

public class Variation implements Serializable {
    private String answer;
    private String next;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
