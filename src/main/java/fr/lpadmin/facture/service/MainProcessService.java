package fr.lpadmin.facture.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import fr.lpadmin.facture.model.ExtExtractData;
import fr.lpadmin.facture.model.OutputData;

@Service
public class MainProcessService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainProcessService.class);

    
    private final FactureListService factureListService;
    private final FactureExtractService factureExtractService;
    private final OutputService outputService;

    public MainProcessService(
        FactureListService factureListService, FactureExtractService factureExtractService,
    OutputService outputService
    ) {
        this.factureListService = factureListService;
        this.factureExtractService = factureExtractService;

        this.outputService = outputService;
    }

    public void processFactures() throws Exception{
        
        LOGGER.info("Starting factures processing...");
        OutputData outputData = outputService.openFile();
        factureListService.listFactures().forEach(facture -> processFacture(facture, outputData));
        outputService.saveFile(outputData);
        LOGGER.info("Finished factures processing.");
    }

    public void processFacture(File facture, OutputData outputData) {
        try {
            LOGGER.info("Processing facture: {}", facture.getName());
            ExtExtractData extExtractData = factureExtractService.processFacture(facture);
            outputService.addLine(outputData, extExtractData);

            LOGGER.info("Finished facture processing.");
        } catch (Exception e) {
            LOGGER.error("Error processing facture {}: {}", facture.getName(), e.getMessage(), e);
        }
    }


}
