package utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Internationalization {
    private static Locale currentLocale;
    private static Locale localeFR;


    public static Locale getLocaleFR() {
        return localeFR;
    }

    public static void setLocaleToFR() {
        Locale france = new Locale("fr", "FR");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("ENTER and language code: fr");
        String languageCode = keyboard.nextLine();
        if(languageCode.equals("fr")){
            Locale.setDefault(france);
        } else{
            // TODO add language not supported alert
            System.out.println("Language not supported.");
        }
        localeFR = france;

        ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("welcome"));
            System.out.println(rb.getString("scheduling-desktop-application"));
        }
    }

    public static void getCurrentLocale(){
        System.out.println(currentLocale.getDisplayLanguage());
        System.out.println(currentLocale.getDisplayCountry());

        System.out.println(currentLocale.getLanguage());
        System.out.println(currentLocale.getCountry());

        System.out.println(System.getProperty("user.country"));
        System.out.println(System.getProperty("user.language"));
    }

    public static void setCurrentLocale(){
        currentLocale = Locale.getDefault();
    }
}
