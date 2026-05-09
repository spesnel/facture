package fr.lpadmin.facture.model.lmstudio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {
    @JsonProperty("model_instance_id")
    private String modelInstanceId;

    private List<AnswerOutput> output;

    private AnswerStats stats;

    @JsonProperty("response_id")
  private String responseId;

    public String getModelInstanceId() {
        return modelInstanceId;
    }

    public void setModelInstanceId(String modelInstanceId) {
        this.modelInstanceId = modelInstanceId;
    }

    public AnswerStats getStats() {
        return stats;
    }

    public void setStats(AnswerStats stats) {
        this.stats = stats;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public List<AnswerOutput> getOutput() {
        return output;
    }

    public void setOutput(List<AnswerOutput> output) {
        this.output = output;
    }
}
