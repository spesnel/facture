package fr.lpadmin.facture.model.lmstudio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {
    private String model;
    private List<QueryInput> input = new ArrayList<>();
    @JsonProperty("system_prompt")
    private String systemPrompt;
    private boolean stream = false;
    private boolean store = true;
    @JsonProperty("previous_response_id")
    private String previousResponseId;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<QueryInput> getInput() {
        return input;
    }

    public void setInput(List<QueryInput> input) {
        this.input = input;
    }

    public void addInput(QueryInput queryInput) {
        this.input.add(queryInput);
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public String getPreviousResponseId() {
        return previousResponseId;
    }

    public void setPreviousResponseId(String previousResponseId) {
        this.previousResponseId = previousResponseId;
    }
    
}
