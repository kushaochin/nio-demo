package demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 如何使用一个非阻塞的accept()方法来监听新进的连接
 */
public class ServerSocketChannelListening {
    public static void main(String[] args) throws Exception {
        //端口号
        int port = 8888;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello ServerSocketChannel".getBytes());

        //ServerSocketChannel
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //绑定
        ssChannel.socket().bind(new InetSocketAddress(port));

        //设置为非阻塞模式
        ssChannel.configureBlocking(true);

        //监听新连接传入
        while (true){
            System.out.println("waiting for connections");
            SocketChannel sChannel = ssChannel.accept();
            if (sChannel==null){
                System.out.println("没有连接传入");
                Thread.sleep(2000);
            }else{

                System.out.println("incoming connection from :"+sChannel.socket().getRemoteSocketAddress());
                buffer.rewind();//指针0
                sChannel.write(buffer);
                sChannel.close();
            }
        }
    }
}
