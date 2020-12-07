package mqtt.trafficlights.process;


import com.google.gson.Gson;
import mqtt.trafficlights.model.TrafficLights;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.UUID;

public class Producer {
    private final static Logger logger = LoggerFactory.getLogger(Producer.class);
    private static String BROKER_IP = "155.185.228.19";
    private static String BROKER_PORT = "7883";
    private static String BROKER_TOPIC = "/iot/user/257211/";
    private static String BROKER_USER = "257211";
    private static String BROKER_URL = "tcp://" + BROKER_IP + ":" + BROKER_PORT;
    private static final String SENSOR_TOPIC = "trafficlights";

    private static final String MQTT_USERNAME = "257211";
    private static final String MQTT_PASSWORD = "gazkiijp";


    private static Gson gson = new Gson();

    public static void main(String[] args){
        logger.info("Producer Started ...");
        try {
            String clientID = UUID.randomUUID().toString();
            MqttClientPersistence persistence = new MemoryPersistence();
            IMqttClient client = new MqttClient(BROKER_URL, clientID, persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTT_USERNAME);
            options.setPassword(new String(MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            Scanner scan = new Scanner(System.in);

            client.connect(options);

            logger.info("Producer is connected!\nClient ID: {}", clientID);

            String payload;
            //for(int i = 0; i< 100; i++){

              //  Thread.sleep(15000);

                //publishData(client, BROKER_TOPIC + SENSOR_TOPIC, payload);
                //if (payload == "false")
                 //   payload = "true";
               // else
                //    payload = "false";

            //}
            while(true){
                logger.info("type 'false' to enter YellowBlink mode, or 'true' to return normal Cycle. 'x' for exit cycle");
                payload = scan.next();
                publishData(client, BROKER_TOPIC + SENSOR_TOPIC, payload);
                logger.info("payload is now set to: {}", payload);
                if(payload == "x")
                    break;
            }


            client.disconnect();
            client.close();

            logger.info("Disconnected!");


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void publishData(IMqttClient mqttClient, String topic, String msg) throws MqttException {

        logger.debug("Publishing to Topic {} Data {}", topic, msg);

        if(mqttClient.isConnected() && msg != null && topic != null){
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(1);
            message.setRetained(false);
            mqttClient.publish(topic, message);
            logger.debug("data correctly published!");
        }
        else{
            logger.error("Error: topic or message are null or MqttClient is not connected");
        }

    }

}
