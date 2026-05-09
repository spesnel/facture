package fr.lpadmin.facture.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.lpadmin.facture.constant.Prompt;
import fr.lpadmin.facture.model.ExtExtractData;
import fr.lpadmin.facture.model.ExtractData;
import fr.lpadmin.facture.model.lmstudio.Answer;
import fr.lpadmin.facture.model.lmstudio.ImageInput;
import fr.lpadmin.facture.model.lmstudio.Query;
import fr.lpadmin.facture.model.lmstudio.TextInput;
import tools.jackson.databind.ObjectMapper;

@Service
public class FactureExtractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactureExtractService.class);


    private final LmStudioService lmStudioService;
    private final PdfService pdfService;

    public FactureExtractService(LmStudioService lmStudioService,
    PdfService pdfService
    ) {
        this.lmStudioService = lmStudioService;
        this.pdfService = pdfService;
    }

    public ExtExtractData processFacture(File facture) throws IOException {
        
        String prompt = Prompt.PROMPT;

            // String img = pdfService.toImage(facture);
            //String msg = lmStudioService.chatOnImg(prompt, img).getOutput().get(0).getContent();
            //String content = "Ce bloc contient le contenu texte du PDF sur lequel porte le prompt. il sera suivi par l'image\n\n" + 
            String content = pdfService.toString(facture);
            LOGGER.debug("Extracted content: {}", content);

        String msgQuery = prompt + "\n\n" 
        +"****** START INVOICE CONTENT ******\n\n"
        +content
        +"****** END INVOICE CONTENT ******\n\n";

            Answer answer;
            String msg;

//             Query query = new Query();
//             query.setSystemPrompt("reponds a la question en renvoyant la valeur demandée en fonction de l'image fourni");
//             query.setStore(false);
//             query.addInput(new TextInput("qui a emis la facture  (limite toi au nom de la société) ?"));
// //            query.addInput(new TextInput(content));
//             query.addInput(new ImageInput(img, true));
//             answer = lmStudioService.chat(query);
//             String msg = answer.getOutput().get(0).getContent();
//             String supplier = clean(msg);
//             LOGGER.info("Supplier=[{}]", supplier);


Query query = new Query();
query.addInput(new TextInput(msgQuery));
query.setStore(false);
answer = lmStudioService.chat(query);
            msg = answer.getOutput().get(0).getContent();
        msg = clean(msg);
        LOGGER.info("Cleaned message: {}", msg);

    // answer = lmStudioService.chatOnText(prompt, content);
    //         msg = answer.getOutput().get(0).getContent();
    //     msg = clean(msg);
    //     LOGGER.info("Cleaned message: {}", msg);

        //     answer = lmStudioService.chatOnTextImg(prompt, content, img);
        //     msg = answer.getOutput().get(0).getContent();
        // msg = clean(msg);
        // LOGGER.info("Cleaned message: {}", msg);

//         Query query = new Query();
// query.addInput(new ImageInput(img, true));
// query.addInput(new TextInput("Complete la réponse précédente en t'aidant de l'image pour confirmer/ informer les valeurs fournit. Pour l'emettrur de la facture il est souvent mentionné en haut à gauche de la facture."));
// query.setPreviousResponseId(answer.getResponseId());
//         answer = lmStudioService.chat(query);
// String msg2 = answer.getOutput().get(0).getContent();
//         msg2 = clean(msg2);
//         LOGGER.info("Cleaned message 2: {}", msg2);

        ObjectMapper mapper = new ObjectMapper();
        ExtractData data = mapper.readValue(msg, ExtractData.class);    
        LOGGER.error(data.toString());
        // data.setNomFournisseur(supplier);
        return new ExtExtractData(facture.getName(), data);
    }

    private String clean(String input) {
        // remove all char before {
        String msg = input;
        int index = msg.indexOf('{');
        if (index != -1) {
            msg = msg.substring(index).trim();
        }
        // remove all char after }
        index = msg.lastIndexOf('}');
        if (index != -1) {
            msg = msg.substring(0, index + 1).trim();
        }
        return msg;
    }

    
}
