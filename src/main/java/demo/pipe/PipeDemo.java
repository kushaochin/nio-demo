package demo.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

/**
 * 两个线程之间的单向数据连接
 */
public class PipeDemo {
    public static void main(String[] args) throws Exception {
        //1、获取管道
        Pipe pipe = Pipe.open();

        //2、获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();

        //3、创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello pipe".getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        //4、写入数据
        sinkChannel.write(buffer);

        //5、获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();

        //6、创建缓冲区，读取数据
        ByteBuffer readedBuffer = ByteBuffer.allocate(1024);
        buffer.clear();
        int readLen = sourceChannel.read(readedBuffer);
        System.out.println(new String(readedBuffer.array(),0,readLen));

        //7、关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
