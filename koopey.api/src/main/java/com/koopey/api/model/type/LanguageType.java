package com.koopey.api.model.type;

public enum LanguageType {
    
     CHINES("cn"), 
     DUTCH("nl"),
     ENGLISH("en"),
     FRENCH ("fr"),
     ITALIAN ("it"),
     GERMAN("de"),
     PORTUGUESE ( "pt") ,   
     SPANISH (  "es");

     public final String type;

     private LanguageType(String type) {
         this.type = type;
     }
   /* public final static String CHINES = "cn";  
    public final static String DUTCH = "nl";
    public final static String ENGLISH = "en";
    public final static String FRENCH = "fr";
    public final static String ITALIAN = "ir";
    public final static String GERMAN = "de";
    public final static String PORTUGUESE = "pt";    
    public final static String SPANISH = "es"; */
  
}
