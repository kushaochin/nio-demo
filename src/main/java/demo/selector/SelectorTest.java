package demo.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

    public static void main(String[] args) throws Exception {
        //创建Selector
        Selector selector = Selector.open();

        //创建通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //设置通道为非阻塞模式
        socketChannel.configureBlocking(false);
        //绑定连接
        socketChannel.bind(new InetSocketAddress(9999));

        //将通道注册到Selector伤
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //查询已经就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //遍历集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey key = iterator.next();
            //判断key的就绪状态操作
            if (key.isAcceptable()){

            }else if (key.isConnectable()){

            }else if (key.isReadable()){

            }else if (key.isWritable()){

            }

            iterator.remove();
        }

    }
}
