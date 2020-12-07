package mqtt.trafficlights.process;

import mqtt.trafficlights.model.TrafficLights;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Consumer {

    private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    //IP Address of the target MQTT Broker
    private static String BROKER_ADDRESS = "155.185.228.19";
    private static String BROKER_PORT = "7883";
    private static String BROKER_TOPIC = "/iot/user/257211/";
    private static String BROKER_USER = "257211";
    private static String BROKER_URL = "tcp://" + BROKER_ADDRESS + ":" + BROKER_PORT;
    private static final String SENSOR_TOPIC = "trafficlights";

    private static final String MQTT_USERNAME = "257211";
    private static final String MQTT_PASSWORD = "gazkiijp";


    //PORT of the target MQTT Broker
    private static String rcv = "true";
    private static TrafficLights traff = new TrafficLights();


    public static void main(String [ ] args) {

        logger.info("MQTT Consumer Tester Started ...");

        try{


            String clientId = UUID.randomUUID().toString();

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient client = new MqttClient(
                    BROKER_URL, //Create the URL from IP and PORT
                    clientId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTT_USERNAME);
            options.setPassword(new String(MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            client.connect(options);

            logger.info("Connected ! Client Id: {}", clientId);

            client.subscribe(BROKER_TOPIC+SENSOR_TOPIC, (topic, msg) -> {

                byte[] payload = msg.getPayload();
                rcv = new String(payload.toString());
                logger.info("Message Received ({}) Message Received: {}", topic, new String(payload));
            });
            while(true){
                if(rcv == "true")
                    normalCycle();
                else if(rcv == "false")
                    yellowBlink();

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void normalCycle() throws InterruptedException {
        traff.setRedState();
        logger.info(traff.toString() + "setting red state");
        Thread.sleep(4000);
        traff.setGreenState();
        logger.info(traff.toString()+ "setting green state");
        Thread.sleep(5000);
        traff.setYellowState();
        logger.info(traff.toString()+ "setting yellow state");
        Thread.sleep(1000);
    }
    public static void yellowBlink() throws InterruptedException {
        traff.setYellowBlink();
        logger.info(traff.toString()+ "setting YELLOWBLINK state");
        Thread.sleep(1000);
        traff.setOff();
        logger.info(traff.toString());
        Thread.sleep(1000);
    }
}

