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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable, IMqttMessageHandler {

    //double same = 100;
   // final NumberAxis xAxis = new NumberAxis(5, 20, 1);

    private double beat = 0;
    private double temp = 0;
    //private double accelx = 0;
   // private double accely = 0;
   // private double accelz = 0;
    
    
    

    private final String tempTopic = "temp";
    private final String heartTopic = "heart";
    private final String accelxTopic = "Xaccel";
    private final String accelyTopic = "Yaccel";
    private final String accelzTopic = "Zaccel";
    @FXML
    private LineChart<String, Number> heartBeatChart;
   // private LineChart heartBeatChart;
    private XYChart.Series heartBeatValues;

    @FXML
    private LineChart tempChart;
    private XYChart.Series tempValues;
  /*  
    @FXML
    private LineChart accelxChart;
    private XYChart.Series accelxValues;
    
    @FXML
    private LineChart accelyChart;
    private XYChart.Series accelyValues;
    
    @FXML
    private LineChart accelzChart;
    private XYChart.Series accelzValues;
    */
    @FXML
    private TextArea displayData;

    @FXML
    private void generateRandomDataHandler(ActionEvent event) {
        System.out.println("You clicked me!");
         //heartBeatValues.getData().remove(0);
         displayData.setText("temp: " +"Data" +'\n'+"heart Beat: " +"data"+'\n'+"x: "+"data"+'\n'+"y: "+"data"+'\n'+"z: "+"data");
       // heartBeatValues.add().remove(0);
        //xAxis.setLowerBound(5);

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
        chatService = new MqttChatService("dashboard1", heartTopic);
        chatService.setMessageHandler(this);
        heartBeatValues = new XYChart.Series();
        tempValues = new XYChart.Series();
      //  accelxValues = new XYChart.Series();
       // accelyValues = new XYChart.Series();
       // accelzValues = new XYChart.Series();
        heartBeatValues.setName("Heart Beat (in BPM)");
        tempValues.setName("Temperature (in Celcius)");
       // accelxValues.setName("accel (x)");
       // accelyValues.setName("accel (y)");
        //accelzValues.setName("accel (z)");
        heartBeatChart.getData().add(heartBeatValues);
        tempChart.getData().add(tempValues);
       // accelxChart.getData().add(accelxValues);
       // accelyChart.getData().add(accelyValues);
       // accelzChart.getData().add(accelzValues);
        disconnectClientOnClose();
        

        // When the user closes the window we need to disconnect the cl
        //disconnectClientOnClose();
    }

    private int xValueBeat = 0;
    private int xValueTemp = 0;
   // private int xValueAccelx = 0;
   // private int xValueAccely = 0;
   // private int xValueAccelz = 0;
    

    // This method is called if a chat message is received from mqtt
    @Override
    public void messageArrived(String channel, String message) {
        
            chatService.switchChannel(heartTopic);
            if (channel.equals(heartTopic)) {
                //int beat = Integer.parseInt(message);

                // previousBeat = beat;
                beat = Double.parseDouble(message);
                heartBeatValues.getData().add(new XYChart.Data(xValueBeat, beat));
                xValueBeat++;
                if(xValueBeat > 10){
                
                heartBeatValues.getData().remove(0);
                }
                System.out.println("beat" + beat);
                chatService.switchChannel(tempTopic);
            }

            //chatService.switchChannel(tempTopic);
            if (channel.equals(tempTopic)) {
                //  previousTemp = temp;
                temp = Double.parseDouble(message);
                System.out.println("temp" + temp);
               tempValues.getData().add(new XYChart.Data(xValueTemp++, temp));
                chatService.switchChannel(accelxTopic);
            }

            //chatService.switchChannel(accelTopic);
            if (channel.equals(accelxTopic)) {
                
               // accelx = Double.parseDouble(message);
               // System.out.println("accelX" + accelx);
               // accelxValues.getData().add(new XYChart.Data(xValueAccelx++, accelx));
                chatService.switchChannel(accelyTopic);
            }
            
            if (channel.equals(accelyTopic)) {
                
              //  accely = Double.parseDouble(message);
               // System.out.println("accelY" + accely);
               // accelyValues.getData().add(new XYChart.Data(xValueAccely++, accely));
                chatService.switchChannel(accelzTopic);
            }
            
            if (channel.equals(accelzTopic)) {
                
               // accely = Double.parseDouble(message);
                //System.out.println("accelZ" + accelz);
              //  accelzValues.getData().add(new XYChart.Data(xValueAccelz++, accelz));
                chatService.switchChannel(heartTopic);
            }
            
            
            
        
       // System.out.println(channel);
       // System.out.println(message);
    }
    
    private void disconnectClientOnClose() {
        // Source: https://stackoverflow.com/a/30910015
        displayData.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).setOnCloseRequest((event) -> {
                            chatService.disconnect();
                            System.exit(1);
                        });
                    }
                });
            }
        });
    }
    
    
    
    
    
    

}
