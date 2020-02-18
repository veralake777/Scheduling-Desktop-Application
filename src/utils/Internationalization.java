package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {
    private static Locale currentLocale;
    private static Locale localeFR;


    public static Locale getLocaleFR() {
        return localeFR;
    }

    public static void setLocaleToFR() {
        // For users working out of London - French
        Locale france = new Locale("fr", "FR");
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("ENTER and language code: fr");
//        String languageCode = keyboard.nextLine();
//        if(languageCode.equals("fr")){
//            Locale.setDefault(france);
//        } else{
//            // TODO add language not supported alert
//            System.out.println("Language not supported.");
//        }


        ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")){
            localeFR = france;
        }
    }

    public static Locale getCurrentLocale(){
//        System.out.println(currentLocale.getDisplayLanguage());
//        System.out.println(currentLocale.getDisplayCountry());
//
//        System.out.println(currentLocale.getLanguage());
//        System.out.println(currentLocale.getCountry());
//
//        System.out.println(System.getProperty("user.country"));
//        System.out.println(System.getProperty("user.language"));
        if(currentLocale.getLanguage().equals("fr")){
            return localeFR;
        } else {
            return currentLocale;
        }
}

    public static void setCurrentLocale(){
        currentLocale = Locale.getDefault();
    }
}
