package fr.lpadmin.facture.constant;

public class Prompt {
    
    public static final String PROMPT__ = """
        Analyse le contenu PDF sous forme de texte et la version image associée pour extraire les informations suivantes au format JSON.

## CHAMP A EXTRAIRE et REGLE D'EXTRACTION
 - Numéro de la facture ou référence de la facture : 
   - il peut etre précédé par "Numéro de facture", "Référence", "Facture N°", "N°", "N° de facture", "Ref" ou "Réf" et suivi de deux points ou d'un espace
   - il peut etre mentionné comme la référence à indiquer pour le paiement
 - Date de facture : 
   - pour le format de retour, se limiter à la date formatter sous la forme jj/mm/aaaa
 - Montant total TTC ou net à payer : 
   - il peut etre precede de montant de cotisation et suivi de euros ou €
   - renvoyer la valeur sans l'unité
 - Qui a émis la facture
   - à retourner dans le champ "nomFournisseur"
   - attention ne pas confondre avec le destinataire de la facture
   - En général "Urban Nation & Gicart - Renaud, Architectes" ou UNAGR est le destinaiatire, donc pas l'émetteur
   - En général, il s'agit de la société mentionné en haut à gauche des facture sur la version image


## FORMAT DE LA REPONSE
The JSON format should be as follows:
{
    "numeroFacture": "string",
    "dateFacture": "string",
    "montantTotalTTC": "string",
    "nomFournisseur": "string"
}

## POINT D'ATTENTION
Attention :
    - Si vous ne pouvez pas trouver l'une des informations, mettez une chaîne vide pour sa valeur.
    - Si vous ne pouvez pas analyser l'image, répondez "Je ne peux pas analyser l'image".
    - Ne pas confondre un code postal d'adresse avec un numéro de facture, un montant ou une date, les codes postaux sont inclus dans les blocs adresse à ignorer.
""";

    public static final String PROMPT = """
Réponds aux questions suivantes. Retourne les réponses au format JSON.
- Quel est le numéro de la facture ? 
  - à retourner dans le champ "numeroFacture"
- Quelle est la date d'émission de la facture ? 
  - à retourner dans le champ "dateFacture" au format jj/mm/aaaa si possible
  - se limiter au jour, en pas retourner l'heure
- Quel est le montant total TTC ou net à payer ? 
  - à retourner dans le champ "montantTotalTTC"
  - c'est le montant total à payer, il correspond au montant HT + TVA, il peut être précédé de "montant à payer", "montant total", "total TTC" ou "total à payer" et suivi de "euros" ou du symbole "€"
- Quelle est la référence de paiement ? 
  - à retourner dans le champ "referencePaiement", c'est la référence à préciser lors du paiement, il est souvent entouré de +++, s'il n'est pas trouvé retournez une chaîne vide

## FORMAT DE LA REPONSE
The JSON format should be as follows:
{
    "numeroFacture": "string",
    "dateFacture": "string",
    "montantTotalTTC": "string",
    "referencePaiement": "string"
}

## POINT D'ATTENTION
Attention :
    - Si vous ne pouvez pas trouver l'une des informations, mettez une chaîne vide pour sa valeur.
    - Ne pas confondre un code postal d'adresse avec un numéro de facture, un montant ou une date, les codes postaux sont inclus dans les blocs adresse à ignorer.
    - Ne pas renvoyer le montant HT mais le montant TTC, même si le montant HT est plus facilement identifiable, le montant TTC est celui qui correspond au montant à payer
    - ne pas renvoyer la date d'échéance mais la date d'émission de la facture, la date d'échéance est la date à laquelle le paiement doit être effectué, elle ne
""";


    public static final String PROMPT___ = """
Extrait les informations suivantes au format JSON.

## CHAMP A EXTRAIRE et REGLE D'EXTRACTION
 - Numéro de la facture : 
   - à retourner dans le champ "numeroFacture"
 - Date de facture : 
   - la date d'émission de la facture
   - formatte la date au format jj/mm/aaaa si possible
   - à retourner dans le champ "dateFacture"
 - Montant Total TTC à payer :
   - à retourner dans le champ "montantTotalTTC" 
   - correspond au montant HT + TVA
   - ATTENTION la virgule est le séparateur décimal en français, il faut la remplacer par un point dans la valeur retournée
 - la reférence de paiement : 
   - c'est la référence à préciser lors du paiement, 
   - il est souvent entouré de +++
   - Ne pas confondre avec le compte bancaire
   - s'il n'est pas trouvé retournez une chaîne vide
   - à retourner dans le champ "referencePaiement"


## FORMAT DE LA REPONSE
The JSON format should be as follows:
{
    "numeroFacture": "string",
    "dateFacture": "string",
    "montantTotalTTC": "string",
    "referencePaiement": "string"
}

## POINT D'ATTENTION
Attention :
    - Si vous ne pouvez pas trouver l'une des informations, mettez une chaîne vide pour sa valeur.
    - Ne pas confondre un code postal d'adresse avec un numéro de facture, un montant ou une date, les codes postaux sont inclus dans les blocs adresse à ignorer.
    - Ne pas renvoyer le montant HT mais le montant TTC, même si le montant HT est plus facilement identifiable, le montant TTC est celui qui correspond au montant à payer
    - ne pas renvoyer la date d'échéance mais la date d'émission de la facture, la date d'échéance est la date à laquelle le paiement doit être effectué, elle ne
""";


public static final String _PROMPT = """
        Analyse le contenu PDF sous forme de texte et la version image associée pour extraire les informations suivantes au format JSON.

## CHAMP A EXTRAIRE et REGLE D'EXTRACTION
 - Numéro de la facture : 
   - il peut etre précédé par "Numéro de facture", "Référence", "Facture N°", "N°", "N° de facture", "Ref" ou "Réf" et suivi de deux points ou d'un espace
   - si il y a plusieurs références, privilégier celle indiqué numéro de facture
 - Date de facture 
 - Montant total TTC ou net à payer : 
   - il peut etre precede de montant de cotisation et suivi de euros ou €
   - si plusieurs montants sont trouvés, privilégier le montant total net à payer, la plus grande valeurs
 - la reférence de paiement : 
   - c'est le numero a préciser lors du paiement, 
   - si il y a plusieurs références, privilégier celle indiqué référence de paiement ou référence client
   - il est souvent entouré de +++
   - s'il n'est pas trouvé retournez une chaîne vide


## FORMAT DE LA REPONSE
The JSON format should be as follows:
{
    "numeroFacture": "string",
    "dateFacture": "string",
    "montantTotalTTC": "string",
    "referencePaiement": "string",
}

## POINT D'ATTENTION
Attention :
    - Si vous ne pouvez pas trouver l'une des informations, mettez une chaîne vide pour sa valeur.
    - Si vous ne pouvez pas analyser l'image, répondez "Je ne peux pas analyser l'image".
    - Ne pas confondre un code postal d'adresse avec un numéro de facture, un montant ou une date, les codes postaux sont inclus dans les blocs adresse à ignorer.
""";


public static final String PROMPT_ = """
Tu es un assistant spécialisé dans l’analyse de factures françaises ou étrangères.

## Ta mission :
extraire automatiquement les informations importantes depuis une image de facture.

## Consignes importantes :
- Retourner UNIQUEMENT un JSON valide
- Ne jamais ajouter d’explication
- Si une information est absente, retourner null
- Conserver exactement les valeurs trouvées
- Ne pas reformater les numéros
- Les montants doivent être retournés avec un point décimal
- Les dates doivent être au format YYYY-MM-DD si possible
- Si plusieurs montants existent, privilégier le TOTAL TTC à payer
- Si plusieurs références existent, privilégier la référence de paiement / référence client / référence SEPA
- Détecter correctement les factures même si le format varie

## Champs à extraire :
- l'émetteur de la facture vers le champs "nomFournisseur"
- la date de la facture vers le champs "dateFacture"
- le montant total TTC vers le champs "montantTotalTTC"
- le numéro de la facture vers le champs "numeroFacture"
- la référence de paiement vers le champs "referencePaiement"

## Format de sortie attendu :
{
    "numeroFacture": "string",
    "dateFacture": "string",
    "montantTotalTTC": "string",
    "nomFournisseur": "string"
}

Voici le document à analyser :
        """;


}
