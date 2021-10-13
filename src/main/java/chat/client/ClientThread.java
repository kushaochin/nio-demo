package chat.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientThread implements Runnable{

    private Selector selector;

    public ClientThread(Selector selector){
        this.selector = selector;
    }

    @Override
    public void run(){
        try {
            //while (true)
            for (; ; ) {
                //获取channel数量
                int readChannels = selector.select();
                if (readChannels == 0) {
                    continue;
                }
                //获取可用Channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //移除set集合当前
                    iterator.remove();

                    //6、根据就绪状态，调用对应方法实现具体业务操作
                    //如果可读状态
                    if (selectionKey.isReadable()) {
                        readOperator(selector, selectionKey);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
            System.out.println(message);
        }
    }
}
