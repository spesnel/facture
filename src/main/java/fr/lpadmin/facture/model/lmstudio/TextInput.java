package fr.lpadmin.facture.model.lmstudio;

public class TextInput extends QueryInput {
    
    private String content;

    public TextInput() {
        this(null);
    }

    public TextInput(String content) {
        this.setType("text");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
