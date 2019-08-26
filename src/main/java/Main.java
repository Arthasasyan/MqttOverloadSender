import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        String stringPayload = "[\n" +
                "  {\n" +
                "    \"tags\": [\n" +
                "      {\n" +
                "        \"ns\": \"TOP Server\",\n" +
                "        \"s\": \"Channel1.Device1.Random(1000,1,985)\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ns\": \"Simulate\",\n" +
                "        \"s\": \"Random-985\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"time\": \"2019-08-16T01:11:58.4346677Z\",\n" +
                "    \"type\": \"int32\",\n" +
                "    \"value\": 842\n" +
                "  },\n" +
                "  {\n" +
                "    \"tags\": [\n" +
                "      {\n" +
                "        \"ns\": \"TOP Server\",\n" +
                "        \"s\": \"Channel1.Device1.Random(1000,1,989)\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ns\": \"Simulate\",\n" +
                "        \"s\": \"Random-989\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"time\": \"2019-08-16T01:11:58.4346677Z\",\n" +
                "    \"type\": \"int32\",\n" +
                "    \"value\": 720\n" +
                "  }\n" +
                "]\n";
        byte[] payload = stringPayload.getBytes();
        String host = "tcp://192.168.1.236:1883";
        String topic = "test/rio";
        /*List<MqttSenderThread> threadList = new ArrayList<MqttSenderThread>();
        int clients = 8;
        for (int i = 0; i < clients; i++) {
            MqttSenderThread thread = new MqttSenderThread(host, topic, payload,
                    1000000/clients);
            threadList.add(thread);
            thread.start();
            log.info((i + 1) + " thread is run");
        }
        for (Thread thread : threadList) {
            while (thread.isAlive()) {
            }
        }*/
        long time = 1000;
        MqttSenderForTime senderForTime = new MqttSenderForTime(host);
        int sent = senderForTime.sendForTime(topic, payload, time);
        log.info(sent + " messages was sent for " + time + " millis");
    }
}
