/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricstationclient;

import be.vives.oop.mqtt.chatservice.IMqttMessageHandler;
import be.vives.oop.mqtt.chatservice.MqttChatService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable, IMqttMessageHandler {
    
    @FXML private Button send;
    @FXML private TextArea message;
    @FXML private TextArea Data;
    
    // Allows us to use the wrapper for sending chat messages via MQTT
    private MqttChatService chatService;
    int maxLine = 1;
    
    @FXML
    private void handleSend(ActionEvent event) {
        // Use the sendMessage() method to send a message to an mqtt channel
       
        chatService.sendMessage(message.getText());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a chat service and allow this class to receive messages
        chatService = new MqttChatService();
        chatService.setMessageHandler(this);
        
        // When the user closes the window we need to disconnect the client
        
        //disconnectClientOnClose();
    }    

    // This method is called if a chat message is received from mqtt
    @Override
    public void messageArrived(String channel, String message) {
       
        if (maxLine < 10){
            Data.setText(message +'\n'+ Data.getText());
            maxLine++;
            
        } else {
            Data.setText(message);
            maxLine =1;
        }
     
      
 
        
        System.out.println("Received chat message (on channel = " + channel
                + "): " + message);
    }
    /*
    private void disconnectClientOnClose() {
        // Source: https://stackoverflow.com/a/30910015
        send.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).setOnCloseRequest((event) -> {
                            chatService.disconnect();
                        });
                    }
                });
            }
        });
    }
*/
}