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
        List<MqttSenderThread> threadList = new ArrayList<MqttSenderThread>();
        for (int i = 0; i < 8; i++) {
            MqttSenderThread thread = new MqttSenderThread("tcp://192.168.1.236:1883", "test/rio", payload, 125000);
            threadList.add(thread);
            thread.start();
            log.info((i + 1) + " thread is run");
        }
        for (Thread thread : threadList) {
            while (thread.isAlive()) {
                continue;
            }
        }
    }
}
