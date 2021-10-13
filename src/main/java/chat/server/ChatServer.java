package chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天服务端
 * 要首先启动得？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？测试过了，客户端没有收到“欢迎进入聊天室，请注意隐私安全”得消息，服务端也没有广播到其他客户端
 */
public class ChatServer {
    public void startServer() throws IOException {
        //1、创建Selector选择器
        Selector selector = Selector.open();

        //2、创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //3、为ServerSocketChannel绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9000));
        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //4、把Channel注册到Selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动成功了！");

        //5、循环等待有新连接接入
        //while (true)
        for (;;){
            //获取channel数量
            int readChannels = selector.select();
            if (readChannels==0){
                continue;
            }
            //获取可用Channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //移除set集合当前
                iterator.remove();

                //6、根据就绪状态，调用对应方法实现具体业务操作
                //6.1、如果accept状态
                if (selectionKey.isAcceptable()){
                    acceptOperator(serverSocketChannel,selector);
                }
                //6.2、如果可读状态
                if (selectionKey.isReadable()){
                    readOperator(selector,selectionKey);
                }
            }
        }

    }

    /**
     * 处理可读状态操作
     * @param selector
     * @param selectionKey
     */
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        //1、从SelectionKey中获取已经就绪得Channel通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        //2、创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //3、循环读取客户端发送过来得消息
        int readLen = channel.read(byteBuffer);
        String message = "";
        if (readLen>0){
            byteBuffer.flip();

            message+= Charset.forName("UTF-8").decode(byteBuffer);
        }

        //4、将Channel注册到选择器上，监听可读状态???为什么要注册
        channel.register(selector,SelectionKey.OP_READ);

        //5、把客户端发送消息，广播到其他客户端
        if (message.length()>0){
            //广播给其他客户端
            System.out.println(message);
            castOtherClient(message,selector,channel);
        }
    }

    /**
     * 广播给其他客户端
     * @param message
     * @param selector
     * @param channel
     */
    private void castOtherClient(String message, Selector selector, SocketChannel channel) throws IOException {
        //1、获取所有得已经接入得客户端Channel
        Set<SelectionKey> selectionKeys = selector.keys();

        //2、循环向所有得Channel广播消息
        for (SelectionKey key :
                selectionKeys) {
            //获取每个Channel
            Channel tarChannel = key.channel();
            //不需要给自己发送
            if (tarChannel instanceof SocketChannel && tarChannel != channel){
                ((SocketChannel) tarChannel).write(Charset.forName("UTF-8").encode(message));
            }
        }
    }


    /**
     * 处理接受状态操作
     * @param serverSocketChannel
     * @param selector
     */
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        //1、接入状态，创建SocketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();

        //2、把SocketChannel设置为非阻塞模式
        socketChannel.configureBlocking(false);

        //3、把Channel注册到Selector上，并且监听可读状态
        socketChannel.register(selector,SelectionKey.OP_READ);//？？？为什么还要注册到Selector上监听，不是已经获取到客户端得SocketChannel了吗？

        //4、客户端回复信息
        socketChannel.write(Charset.forName("UTF-8").encode("欢迎进入聊天室，请注意隐私安全"));
    }

    /**
     * 启动主方法
     * @param args
     */
    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
