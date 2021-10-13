package chat.client;

import java.io.IOException;

/**
 * b客户端
 * 其次启动
 */
public class BClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("b");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
