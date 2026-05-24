package fr.lpadmin.facture.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.model.ExtExtractData;
import fr.lpadmin.facture.model.ExtractData;
import fr.lpadmin.facture.model.lmstudio.Answer;
import fr.lpadmin.facture.model.lmstudio.ImageInput;
import fr.lpadmin.facture.model.lmstudio.Query;
import fr.lpadmin.facture.model.lmstudio.TextInput;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentMeansType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

@Service
public class FactureXmlExtractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactureXmlExtractService.class);


    private final FactureXmlParserService factureXmlParserService;

    public FactureXmlExtractService(FactureXmlParserService factureXmlParserService) {
        this.factureXmlParserService = factureXmlParserService;
    }


    public ExtExtractData processFacture(File facture) throws IOException {
        
        InvoiceType invoice = factureXmlParserService.processFacture(facture);
        ExtractData data = new ExtractData();
        data.setNumeroFacture(getNumeroFacture(invoice));
        data.setMontantTotalTTC(getTotal(invoice));
        data.setReferencePaiement(getReferencePaiement(invoice));
        data.setDateFacture(formatDate(invoice.getIssueDate().getValue()));

        LOGGER.debug("from XML {}", data.toString());
        return new ExtExtractData(facture.getName(), data);
    }

    private String getNumeroFacture(InvoiceType invoice) {
        if (invoice.getID() != null) {
            return invoice.getID().getValue();
        }
        return null;
    }

    private String getTotal(InvoiceType invoice) {
        if (invoice.getLegalMonetaryTotal() != null && invoice.getLegalMonetaryTotal().getPayableAmount() != null) {
            return invoice.getLegalMonetaryTotal().getPayableAmount().getValue().toString();
        }
        return null;
    }

    private String getReferencePaiement(InvoiceType invoice) {
        if (invoice.getPaymentMeans() != null && !invoice.getPaymentMeans().isEmpty()) {
            final PaymentMeansType paymentMeans = invoice.getPaymentMeans().get(0);
            if (paymentMeans.getInstructionID() != null) {
                return paymentMeans.getInstructionID().getValue();
            }
        }
        return null;
    }

    private String formatDate(XMLGregorianCalendar date) {
        // formatter la date au format souhaité, par exemple "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date.toGregorianCalendar().getTime());
    }


}
