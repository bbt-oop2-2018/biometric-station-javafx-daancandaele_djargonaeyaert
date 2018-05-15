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
    final NumberAxis xAxis = new NumberAxis(5, 20, 1);

    private double beat = 0;
    private double temp = 0;
    private double accel = 0;
    private double previousBeat = 0;
    private double previousTemp = 0;
    private double previousAccel = 0;
    
    private String tempTopic = "temp";
    private String heartTopic = "heart";
    private String accelTopic = "accel";
    @FXML
    private LineChart heartBeatChart;
    private XYChart.Series heartBeatValues;

    @FXML
    private void generateRandomDataHandler(ActionEvent event) {
        System.out.println("You clicked me!");
        heartBeatValues.getData().remove(0, heartBeatValues.getData().size() - 5);
        xAxis.setLowerBound(5);

        //xAxis.setLowerBound(xValue-5);
        //   xAxis.setUpperBound(xValue-1);
    }

    // Allows us to use the wrapper for sending chat messages via MQTT
    private MqttChatService chatService;
    int maxLine = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create a chat service and allow this class to receive messages
        chatService = new MqttChatService("dashboard", tempTopic);
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
            chatService.switchChannel(heartTopic);
        if (channel.equals(heartTopic) && (beat != previousBeat)) {
            //int beat = Integer.parseInt(message);
            System.out.println("beat");


            previousBeat = beat;
            beat = Double.parseDouble(message);
            heartBeatValues.getData().add(new XYChart.Data(xValue++, beat));
        }
        chatService.switchChannel(tempTopic);
            if(channel.equals(tempTopic) ){
                previousTemp = temp;
                temp = Double.parseDouble(message);
                heartBeatValues.getData().add(new XYChart.Data(xValue++, temp));
            }  
            
            chatService.switchChannel(accelTopic);
            if(channel.equals(tempTopic)&& (accel != previousAccel)){
                previousAccel = accel;
                accel = Double.parseDouble(message);
                heartBeatValues.getData().add(new XYChart.Data(xValue++, accel));
            }
            
        System.out.println(channel);
        System.out.println(message);
    }

}
