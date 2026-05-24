package fr.lpadmin.facture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.exception.AppRuntimeException;

@Service
public class PromptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromptService.class);

    private final String promptFile;
    private String prompt;
    private boolean includeImg;
    private boolean includeText;

    public PromptService(@Value("${app.prompt.file}") String promptFile,
        @Value("${app.prompt.includeImg}") boolean includeImg,
        @Value("${app.prompt.includeText}") boolean includeText){
        LOGGER.info("PromptService initialized with promptFile: {}", promptFile);
        this.promptFile = promptFile;
        this.includeImg = includeImg;
        this.includeText = includeText;

        loadPrompt();
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isIncludeImg() {
        return includeImg;
    }

    public boolean isIncludeText() {
        return includeText;
    }

    private void loadPrompt() {
        try {
            // if promptFile start with classpath:, remove prefix and read prompt from resource file in classpath
            if (promptFile.startsWith("classpath:")) {
                String resourcePath = promptFile.substring("classpath:".length());
                prompt = new String(this.getClass().getResourceAsStream(resourcePath).readAllBytes());
            } else {
                // read prompt from file system
                prompt = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(promptFile)));
            }
            LOGGER.info("Prompt loaded successfully from file: {}", promptFile);
        } catch (Exception e) {
            LOGGER.error("Error loading prompt from file {}: {}", promptFile, e.getMessage());
            throw new AppRuntimeException("Failed to load prompt from file: " + promptFile, e);
        }
    }

}
