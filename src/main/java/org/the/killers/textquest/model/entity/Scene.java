package org.the.killers.textquest.model.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Scene implements Serializable {
    private String code;
    private String text;
    private List<Variation> variations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }
}
