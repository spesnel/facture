package fr.lpadmin.facture.model.lmstudio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageInput extends QueryInput {
    
    @JsonProperty("data_url")
    private String dataurl;

    public ImageInput() {
        this.setType("image");
    }

    public ImageInput(String dataurl) {
        this(dataurl, false);
    }

    public ImageInput(String dataurl, boolean prefixPngBase64) {
        this.setType("image");
        if(prefixPngBase64) {
            this.dataurl = "data:image/png;base64," + dataurl;
        } else {
            this.dataurl = dataurl;
        }
    }


    public String getDataurl() {
        return dataurl;
    }

    public void setDataurl(String dataurl) {
        this.dataurl = dataurl;
    }
}
