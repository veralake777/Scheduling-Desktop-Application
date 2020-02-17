package utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Internationalization {
    private static Locale locale;


    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale() {
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
        locale = france;

        ResourceBundle rb = ResourceBundle.getBundle("utils/Nat", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("welcome"));
            System.out.println(rb.getString("scheduling-desktop-application"));
        }
    }
}
