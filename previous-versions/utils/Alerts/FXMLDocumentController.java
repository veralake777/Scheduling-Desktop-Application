/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author amy.antonucci
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION, "You clicked me!", ButtonType.APPLY);
         alert.showAndWait()
                 .filter(res -> res == ButtonType.OK)
                 .ifPresent(res -> sayHello());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void sayHello() {
        System.out.println("Hello from the sayHello method!");
    }
}
