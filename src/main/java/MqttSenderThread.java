import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

@Slf4j
public class MqttSenderThread extends Thread {
    private final byte[] payload;
    private final int messages;
    private final String topic;
    private final MqttAsyncClient client;

    public MqttSenderThread(String host, String topic, byte[] payload, int messages) throws MqttException {
        this.messages = messages;
        this.payload = payload;
        this.topic = topic;
        this.client = new MqttAsyncClient(host, MqttAsyncClient.generateClientId());
    }

    @Override
    public void run() {
        try {
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setMaxInflight(65535);
            client.connect(connectOptions);
            while (!client.isConnected()) {
                log.info("Client " + client.getClientId() + " is connecting to " + client.getCurrentServerURI());
                sleep(1000);
            }
            for (int i = 0; i < messages; i++) {
                client.publish(topic, payload, 0, false);
                log.info("Client " + client.getClientId() + " is publishing " + (i + 1) + " message to " + topic);
            }
            log.info(messages + " messages are sent by client " + client.getClientId());
            while(client.getInFlightMessageCount() != 0) {
                log.info("Client " + client.getClientId() + " waiting for " + client.getInFlightMessageCount() + " messages to be delivered.");
            }
            client.disconnect();
            while (client.isConnected()) {
                log.info("Client " + client.getClientId() + " is disconnecting");
                sleep(1000);
            }
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            throw new RuntimeException(e);
        }
        interrupt();
    }
}
