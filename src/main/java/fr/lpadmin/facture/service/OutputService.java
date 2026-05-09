package fr.lpadmin.facture.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.exception.AppRuntimeException;
import fr.lpadmin.facture.model.ExtExtractData;
import fr.lpadmin.facture.model.OutputData;

@Service
public class OutputService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutputService.class);

    private static final int COL_FILENAME = 0;
    private static final int COL_FOURNISSEUR = 2;
    private static final int COL_DATE_FACTURE = 8;
    private static final int COL_MONTANT_TOTAL_TTC = 3;
    private static final int COL_NUMERO_FACTURE = 6;
    private static final int COL_REFERENCE_PAIEMENT = 7;

    private final String templateFile;
    private final String outputFile;

    public OutputService(
        @Value("${app.template.file}") String templateFile, 
        @Value("${app.output.file}") String outputFile) {
        this.templateFile = templateFile;
        this.outputFile = outputFile;
    }
    
    public OutputData openFile() {
        //copy template file binary to output file
        File tpl = new File(templateFile);
        File out = new File(outputFile);
        try(FileInputStream fis = new FileInputStream(tpl); FileOutputStream fos = new FileOutputStream(out)){
            IOUtils.copy(fis, fos);
        } catch (Exception e) {
            throw new AppRuntimeException("Error copying template file", e);
        }

        return openFile(out);
    }

    public OutputData openFile(File file){
        // open file xls file with POI
        if(file.exists()) {

            // open file
            try(FileInputStream fis = new FileInputStream(file)) {
                LOGGER.info("Opening file: {}", file.getAbsolutePath());
                
                Workbook workbook = new XSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(0);
                OutputData outputData = new OutputData();
                outputData.setWorkbook(workbook);
                outputData.setSheet(sheet);
                outputData.setCurrentRow(sheet.getLastRowNum() + 1);
                return outputData;
            } catch (Exception e) {
                throw new AppRuntimeException("Error opening file", e);
            }
        } else {
        //     Workbook workbook = new XSSFWorkbook();
        //     Sheet sheet = workbook.createSheet("Users");

        //     // Create header row
        //     Row header = sheet.createRow(0);
        //     header.createCell(0).setCellValue("filename");
        //     header.createCell(1).setCellValue("dateFacture");
        //     header.createCell(2).setCellValue("montantTotalTTC");
        //     header.createCell(3).setCellValue("numeroFacture");

        //     OutputData outputData = new OutputData();
        //     outputData.setWorkbook(workbook);
        //     outputData.setSheet(sheet);
        //     return outputData;
        throw new AppRuntimeException("file not found: " + file.getAbsolutePath());
        }
    }

    public void saveFile(OutputData output) throws Exception {
        saveFile(output, new File(outputFile));
        //output.getWorkbook().close();
    }

    public void saveFile(OutputData output, File file) throws Exception {
        // save file xls file with POI
        try(FileOutputStream fos = new FileOutputStream(file)){
            output.getWorkbook().write(fos);
            output.getWorkbook().close();
        }
    }

    public void addLine(OutputData output, ExtExtractData data){
        // add line to xls file with POI
        // Add a new row
        Row row = output.getSheet().createRow(output.getCurrentRow());
        row.createCell(COL_FILENAME).setCellValue(data.filename());
        row.createCell(COL_FOURNISSEUR).setCellValue(extract(data.filename()));
        row.createCell(COL_DATE_FACTURE).setCellValue(data.extractData().getDateFacture());
        row.createCell(COL_MONTANT_TOTAL_TTC).setCellValue(montant(data.extractData().getMontantTotalTTC()));
        row.createCell(COL_NUMERO_FACTURE).setCellValue(data.extractData().getNumeroFacture());
        row.createCell(COL_REFERENCE_PAIEMENT).setCellValue(data.extractData().getReferencePaiement());
        output.incrementCurrentRow();

    }


    private String extract(String filename) {
        // extract fournisseur from filename
        String[] parts = filename.split("_");
        if(parts.length > 1) {
            return parts[0];
        } else {
            parts = filename.split(" ");
            if(parts.length > 1) {
                return parts[0];
            }
        }
        return "";
    }

    private String montant(String inputMontant) {
        // replace , by . in montant
        if(inputMontant == null) {
            return "";
        }
        String montant = inputMontant.trim().replace(".", ",");
        if(montant.startsWith("€")) {
            montant = montant.substring(1).trim();
        }
        if(montant.endsWith("€")) {
            montant = montant.substring(0, montant.length() - 1).trim();
        }
        return montant;
    }
}   
