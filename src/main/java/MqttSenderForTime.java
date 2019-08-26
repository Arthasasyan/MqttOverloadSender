import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

@Slf4j
public class MqttSenderForTime {
    private MqttAsyncClient client;

    public MqttSenderForTime(String host) throws Exception {
        this.client = new MqttAsyncClient(host, MqttAsyncClient.generateClientId());
    }

    public int sendForTime(String topic, byte[] payload, long millisTime) throws Exception {
        client.connect();
        while (!client.isConnected()) {
            log.info("Client " + client.getClientId() + " is connecting to " + client.getCurrentServerURI());
            Thread.sleep(1000);
        }
        int sent;
        long startTime = System.currentTimeMillis();
        for (sent = 0; System.currentTimeMillis() - startTime < millisTime; sent++) {
            client.publish(topic, payload, 0, false);
            log.info("Client " + client.getClientId() + " sent " + (sent + 1) + " messages");
        }
        client.disconnect();
        return sent;
    }
}
