/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.Logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author amy.antonucci
 */
public class LoggerInfo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger log = Logger.getLogger("log.txt");

        try {
            //The following four lines write the log text to a file. Otherwise it will print only to the console.
            FileHandler fh = new FileHandler("log.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
            //change the following line to change what gets logged.
            // Here is the descending list:
//        SEVERE (highest)
//        WARNING
//        INFO
//        CONFIG
//        FINE
//        FINER
//        FINEST
//      So if you set the following line to log.setLevel(Level.INFO), only logs that have levels SEVERE, WARNING, or INFO will actually get logged.
//      Great for debugging! You could set it to FINEST, and then when you put the code into production, set it to INFO or WARNING, for instance, so that you
//      don't get the debugging log info in your text file
        } catch (IOException ex) {
            Logger.getLogger(LoggerInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LoggerInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        log.setLevel(Level.CONFIG); //change this line to see how the output changes!

        log.severe("Oh no! Bad Things are happening!");
        log.log(Level.SEVERE, "More Severe things are happening!");

        log.warning("This is just a warning");
        log.log(Level.WARNING, "Here is another warning");

        log.info("This message is informational");
        log.log(Level.INFO, "So is this one");

        log.config("Something needs to be reconfigured");
        log.log(Level.CONFIG, "Something else needs to be reconfigured");

        log.fine("Here is a fine-level debug line");
        log.log(Level.FINE, "Here is another fine-level debug line");

        log.finer("Here is a finer-level debug line");
        log.log(Level.FINE, "Here is another finer-level debug line");

        log.finest("Here is a finest-level debug line");
        log.log(Level.FINEST, "Here is another finest-level debug line");
    }

}
