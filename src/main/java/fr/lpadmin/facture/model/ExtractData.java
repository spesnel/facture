package fr.lpadmin.facture.model;

public class ExtractData {
    private String dateFacture;
    private String montantTotalTTC;
    private String numeroFacture;
    private String referencePaiement;

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getMontantTotalTTC() {
        return montantTotalTTC;
    }

    public void setMontantTotalTTC(String montantTotalTTC) {
        this.montantTotalTTC = montantTotalTTC;
    }

    public String getReferencePaiement() {
        return referencePaiement;
    }

    public void setReferencePaiement(String referencePaiement) {
        this.referencePaiement = referencePaiement;
    }

    @Override
    public String toString() {
        return "ExtractData{" +
                "dateFacture='" + dateFacture + '\'' +
                ", montantTotalTTC='" + montantTotalTTC + '\'' +
                ", numeroFacture='" + numeroFacture + '\'' +
                ", referencePaiement='" + referencePaiement + '\'' +
                '}';
    }
}
