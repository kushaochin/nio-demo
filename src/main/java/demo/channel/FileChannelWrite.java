package demo.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 使用FileChannel写入文件内容
 */
public class FileChannelWrite {
    public static void main(String[] args) throws Exception {
        //创建FileChannel
        RandomAccessFile raFile = new RandomAccessFile("C:\\Users\\kusha\\Desktop\\NIO\\src\\main\\resources\\test.txt","rw");
        FileChannel channel = raFile.getChannel();

        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String newData = "write test";
        buffer.clear();

        //写入内容
        buffer.put(newData.getBytes());
        buffer.flip();

        //FileChannel完成最终实现
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }

        channel.close();
    }
}
