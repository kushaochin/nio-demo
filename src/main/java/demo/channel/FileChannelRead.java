package demo.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel读取文件内容
 */
public class FileChannelRead {
    public static void main(String[] args) throws Exception {
        //创建FileChannel
        RandomAccessFile raFile = new RandomAccessFile("C:\\Users\\kusha\\Desktop\\NIO\\src\\main\\resources\\test.txt","rw");
        FileChannel channel = raFile.getChannel();

        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读取数据到buffer中
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1){
            System.out.println("读取了："+bytesRead);

            buffer.flip();

            while (buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }

            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        raFile.close();
        System.out.println("结束了");
    }
}
