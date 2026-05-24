package fr.lpadmin.facture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.Test;

import fr.lpadmin.facture.exception.AppRuntimeException;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

class FactureXmlParserServiceTest {

    private final FactureXmlParserService service = new FactureXmlParserService();

    @Test
    void processFacture_shouldValidateAndParseValidUblInvoice() {
        File xmlFile = testFile("/invoice/UBL-Invoice-2.0-Example.xml");

        InvoiceType invoice = service.processFacture(xmlFile);

        assertNotNull(invoice);
        assertNotNull(invoice.getID());
        assertEquals("A00095678", invoice.getID().getValue());
        assertNotNull(invoice.getIssueDate());
        assertTrue(invoice.getIssueDate().getValue() instanceof XMLGregorianCalendar);
    }

    @Test
    void processFacture_shouldFailOnMissingFile() {
        File missing = new File("/tmp/does-not-exist-invoice.xml");

        assertThrows(AppRuntimeException.class, () -> service.processFacture(missing));
    }

    public static File testFile(String resourcePath) {
        URL resource = FactureXmlParserServiceTest.class.getResource(resourcePath);
        assertNotNull(resource, "Resource not found: " + resourcePath);
        try {
            return Path.of(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid test resource URI: " + resourcePath, e);
        }
    }
}
