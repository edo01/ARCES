/*
 * Install and add to your project the org.eclipse.paho.client.mqttv3 library
 * This class is usefull in order to create a loop listener of a given topic.
 * It's possible creating some Thread which runs a SubscriptionAlarmGenerator on
 * different topics.
 */
package it.unibo.arces.wot.sepa.apps.alarmgenerator.control;

import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Point;
import it.unibo.arces.wot.sepa.apps.alarmgenerator.model.Interpolation;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Edoardo Carrà
 */
public class SubscriptionAlarmGenerator implements Runnable{

    /**
     * The topic with which the sensor publishes.
     */
    private final String topic;
    private final Alarm alarm;
    private final Interpolation function;
    //to calculate the measures saved.
    private int saved = 0;
    private Vector v;

    /**
     * 
     * @param topic of the subscription.
     * @param alarm to set on the sensor measures.
     */
    public SubscriptionAlarmGenerator(String topic, Alarm alarm) {
        this.topic = topic;
        this.alarm = alarm;
       function = new Interpolation();
        v = new Vector();
    }
    
    @Override
    public void run() {
            String broker       = "tcp://giove.arces.unibo.it:52877";
            String clientId     = "Alarm for "+topic;

            try {
                MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);


                System.out.println("Connecting to broker: "+broker);
                sampleClient.connect(connOpts);
                System.out.println("Connected");
                sampleClient.setCallback(new MqttCallback() {

                    @Override
                    public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker 
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println(topic + ": " + message);
                        saved++;
                        if (saved==alarm.getRange()) {
                            function.setPoints(v);
                            alarm.isAnAlarm(function);
                            saved=0;
                        }else{
                            v.add(new Point(saved-1,Double.parseDouble(message.toString())));
                        }
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete 
                    }
                });
                sampleClient.subscribe(topic, 1);
            } catch(MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }
        do {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SubscriptionAlarmGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
        } while (true);        
    }    
}
