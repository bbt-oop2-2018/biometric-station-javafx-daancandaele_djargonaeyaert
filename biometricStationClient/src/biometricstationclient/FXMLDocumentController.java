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
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class FXMLDocumentController implements Initializable, IMqttMessageHandler {
     double same = 100;
     final NumberAxis xAxis = new NumberAxis(5,20,1);
   @FXML private LineChart heartBeatChart;
   private XYChart.Series heartBeatValues;
    
   @FXML
    private void generateRandomDataHandler(ActionEvent event) {
        System.out.println("You clicked me!");
        heartBeatValues.getData().remove(0, heartBeatValues.getData().size() - 5);
        
        
        
        xAxis.setLowerBound(xValue-5);
        xAxis.setUpperBound(xValue-1);
    }
    
    // Allows us to use the wrapper for sending chat messages via MQTT
    private MqttChatService chatService;
    int maxLine = 1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a chat service and allow this class to receive messages
        chatService = new MqttChatService();
        chatService.setMessageHandler(this);
        heartBeatValues = new XYChart.Series();
        heartBeatValues.setName("Temperature (in Celcius)");
        heartBeatChart.getData().add(heartBeatValues);
        
        
        // When the user closes the window we need to disconnect the cl
        //disconnectClientOnClose();
    }
    
    private int xValue = 1;
    // This method is called if a chat message is received from mqtt
    @Override
    public void messageArrived(String channel, String message) {
       
        
            
            //int beat = Integer.parseInt(message);
            double beat = Double.parseDouble(message);
            
            
            
            if (beat == same) {
            
            
            }else{
            
            heartBeatValues.getData().add(new XYChart.Data(xValue++, beat));
            same = beat;
            }
            
            
            
            
            
            
            
        
     
      
 
        
        System.out.println(message);
    }
    
    
  
    
    
}