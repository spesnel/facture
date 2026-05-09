package fr.lpadmin.facture.model.lmstudio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerStats {
  
    @JsonProperty("input_tokens")
    private int inputTokens;
    @JsonProperty("total_output_tokens")
    private int totalOutputTokens;
    @JsonProperty("reasoning_output_tokens")
    private int reasoningOutputTokens;
    @JsonProperty("tokens_per_second")
    private double tokensPerSecond;
    @JsonProperty("time_to_first_token_seconds")
    private double timeToFirstTokenSeconds;

    public int getInputTokens() {
        return inputTokens;
    }

    public void setInputTokens(int inputTokens) {
        this.inputTokens = inputTokens;
    }

    public int getTotalOutputTokens() {
        return totalOutputTokens;
    }

    public void setTotalOutputTokens(int totalOutputTokens) {
        this.totalOutputTokens = totalOutputTokens;
    }

    public int getReasoningOutputTokens() {
        return reasoningOutputTokens;
    }

    public void setReasoningOutputTokens(int reasoningOutputTokens) {
        this.reasoningOutputTokens = reasoningOutputTokens;
    }

    public double getTokensPerSecond() {
        return tokensPerSecond;
    }

    public void setTokensPerSecond(double tokensPerSecond) {
        this.tokensPerSecond = tokensPerSecond;
    }

    public double getTimeToFirstTokenSeconds() {
        return timeToFirstTokenSeconds;
    }

    public void setTimeToFirstTokenSeconds(double timeToFirstTokenSeconds) {
        this.timeToFirstTokenSeconds = timeToFirstTokenSeconds;
    }
    
}
