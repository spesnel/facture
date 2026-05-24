package fr.lpadmin.facture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;


import fr.lpadmin.facture.model.ExtExtractData;
import fr.lpadmin.facture.model.ExtractData;

class FactureXmlExtractServiceTest {

    private final FactureXmlParserService parserService = new FactureXmlParserService();
    private final FactureXmlExtractService extractService = new FactureXmlExtractService(parserService);

    @Test
    void processFacture_shouldExtractData() throws IOException {
        File xmlFile = FactureXmlParserServiceTest.testFile("/invoice/UBL-Invoice-2.0-Example.xml");


        ExtExtractData extractData = extractService.processFacture(xmlFile);
        ExtractData data = extractData.extractData();

        assertNotNull(data);
        assertEquals("A00095678", data.getNumeroFacture());
        assertEquals("21/06/2005", data.getDateFacture());
        assertEquals("107.50", data.getMontantTotalTTC());
        assertNull(data.getReferencePaiement());
    }

}
