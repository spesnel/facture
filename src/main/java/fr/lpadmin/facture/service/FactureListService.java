package fr.lpadmin.facture.service;

import java.util.List;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.exception.AppRuntimeException;

@Service
public class FactureListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FactureListService.class);

    private final String inputDir;

    public FactureListService(@Value("${app.pdf.input.dir}") String inputDir) {
        LOGGER.info("FactureListService initialized with inputDir: {}", inputDir);
        this.inputDir = inputDir;
    }

    public List<File> listFactures() {
        File input = new File(inputDir);
        if(!input.exists()){
            throw new AppRuntimeException("Input directory does not exist: " + inputDir);
        }

        return List.of(input.list())
                   .stream()
                   .filter(name -> name.toLowerCase().endsWith(".pdf"))
                   .map(name -> new File(input, name))
                   .toList();

    }

}
