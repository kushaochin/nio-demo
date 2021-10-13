package chat.client;

import sun.awt.windows.ThemeReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ChatClient {
    public void startClient(String name) throws IOException {
        //连接服务器
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9000));

        //向服务器端发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String msg = scanner.next();
            if (msg.length()>0){
                socketChannel.write(Charset.forName("UTF-8").encode(name+":"+msg));
            }
        }

        //接收服务器响应得数据
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //创建线程
        new Thread(new ClientThread(selector)).start();

    }

    public static void main(String[] args) {

    }
}
