package org.beccaria.hub;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.logging.Logger;

public class MessagePublisher {
    private static final Logger logger = Logger.getLogger("org.domotics");
    private static final String TOPIC = "domotics/commands";
    private static final String BROKER_HOST = "192.168.1.41";
    private static final int BROKER_PORT = 1883;
    private static final String CLIENT_ID = "domus.hub";
    private static final int QOS = 0;

    MemoryPersistence persistence;
    String broker;
    String topic;
    int port;

    public MessagePublisher(){
        MemoryPersistence persistence = new MemoryPersistence();
        broker = BROKER_HOST;
        port = BROKER_PORT;
        topic = TOPIC;
    }

    public MessagePublisher setBrokerHost(String host){
        this.broker = host;
        return this;
    }

    public MessagePublisher setBrokerPort(int port){
        this.port = port;
        return this;
    }

    public MessagePublisher setTopic(String topic){
        this.topic = topic;
        return this;
    }

    public void publish(String msg){
        try {
            String broker_url = "tcp://" + broker + ":" + port;
            MqttClient client = new MqttClient(broker_url, CLIENT_ID, persistence);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);

            logger.info("Connecting to broker: "+broker);
            client.connect(mqttConnectOptions);

            logger.info("Connected");
            logger.info("Publishing message: " + msg + " on topic: " + topic);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(QOS);
            client.publish(topic, message);
            logger.info("Message published");
            client.disconnect();
            logger.info("Disconnected");
        } catch(Exception e) {
            logger.info(e.getLocalizedMessage());
        }
    }
}
