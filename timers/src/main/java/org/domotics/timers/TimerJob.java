package org.domotics.timers;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TimerJob implements Job {
    private static final String TOPIC = "domotics/commands";
    private static final String BROKER_HOST = "192.168.1.41";
    private static final int BROKER_PORT = 1883;
    private static final String CLIENT_ID = "domus.hub";
    private static final int QOS = 0;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String message = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("message");
        publish(message);
    }

    public void publish(String msg){
        try {
            String broker_url = "tcp://" + BROKER_HOST + ":" + BROKER_PORT;
            MqttClient client = new MqttClient(broker_url, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            client.connect(mqttConnectOptions);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(QOS);
            client.publish(TOPIC, message);
            client.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
