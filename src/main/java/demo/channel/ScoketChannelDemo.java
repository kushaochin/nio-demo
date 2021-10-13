package demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ScoketChannelDemo {

    public static void main(String[] args) throws Exception {
        //创建SocketChannel
        //第一种方式
        //SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com",80));
        //第二种方式
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("www.baidu.com",80));

        //设置是否阻塞
        socketChannel.configureBlocking(false);

        //读操作
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.read(buffer);
        socketChannel.close();
        System.out.println("read over");
    }
}
