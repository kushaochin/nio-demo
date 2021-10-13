package chat.client;

import java.io.IOException;

/**
 * a客户端
 * 其次启动
 */
public class AClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
