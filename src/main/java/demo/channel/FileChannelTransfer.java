package demo.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel之间进行传输
 */
public class FileChannelTransfer {
    public static void main(String[] args) throws Exception {
        //创建两个FileChannel
        RandomAccessFile fromFile = new RandomAccessFile("C:\\Users\\kusha\\Desktop\\NIO\\src\\main\\resources\\test.txt","rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("C:\\Users\\kusha\\Desktop\\NIO\\src\\main\\resources\\test_transferFrom.txt","rw");
        FileChannel toChannel = toFile.getChannel();

        //fromChannel to toChannel
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel,position,size);
        //fromChannel.transferTo(position,size,toChannel);
        fromFile.close();
        toFile.close();

        System.out.println("结束");
    }
}
