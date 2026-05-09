package fr.lpadmin.facture.model;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;

public class OutputData {

    private Workbook workbook;
    private Sheet sheet;
    private int currentRow = 1;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void incrementCurrentRow() {
        this.currentRow++;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}