package fr.lpadmin.facture.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import fr.lpadmin.facture.exception.AppRuntimeException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.ObjectFactory;

@Service
public class FactureXmlParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactureXmlParserService.class);
    private static final String UBL_INVOICE_XSD_CLASSPATH = "/ubl/xsd/maindoc/UBL-Invoice-2.0.xsd";


    public FactureXmlParserService() {
    }


    public InvoiceType processFacture(File facture) {
        if (facture == null || !facture.exists() || !facture.isFile()) {
            throw new AppRuntimeException("Facture XML introuvable: " + facture);
        }

        validateAgainstInvoiceSchema(facture);

        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(facture), InvoiceType.class).getValue();
        } catch (JAXBException e) {
            throw new AppRuntimeException("Erreur lors du parsing JAXB de la facture: " + facture.getName(), e);
        }
    }

    private void validateAgainstInvoiceSchema(File facture) {
        try (InputStream inputStream = new FileInputStream(facture)) {
            URL schemaUrl = FactureXmlParserService.class.getResource(UBL_INVOICE_XSD_CLASSPATH);
            if (schemaUrl == null) {
                throw new AppRuntimeException("Schéma UBL introuvable dans le classpath: " + UBL_INVOICE_XSD_CLASSPATH);
            }

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaUrl);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(inputStream));
            LOGGER.debug("Facture XML valide selon UBL Invoice 2.0: {}", facture.getAbsolutePath());
        } catch (SAXException e) {
            LOGGER.warn("Le XML de facture ne respecte pas le XSD UBL Invoice 2.0  : {}", e.getMessage());
        } catch (Exception e) {
            throw new AppRuntimeException("Erreur lors de la validation XML de la facture", e);
        }
    }

    
}
