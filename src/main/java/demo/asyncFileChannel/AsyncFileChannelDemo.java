package demo.asyncFileChannel;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * 两种读写数据的方式例子
 */
public class AsyncFileChannelDemo {

    @Test
    public void testWriteAsCompletionHandler() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //write
        buffer.put("通过CompletionHandler方式写的数据".getBytes());
        buffer.flip();
        fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result:"+result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });



    }
    /**
     * 通过Future方式写入数据
     * @throws IOException
     */
    @Test
    public void testWriteAsFuture() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //write
        buffer.put("通过future方式写的数据".getBytes());
        buffer.flip();
        Future<Integer> future = fileChannel.write(buffer, 0);

        while (!future.isDone()){}
        buffer.clear();
        System.out.println("write over");
    }

    /**
     * 通过Future方式读取数据
     * @throws IOException
     */
    @Test
    public void testReadAsFuture() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //调用channel的read()得到Future
        Future<Integer> future = fileChannel.read(buffer, 0);

        //判断是否完成isDone，返回true
        while (!future.isDone()) {
        }
        //读取数据到buffer里面
        buffer.flip();
//        while (buffer.remaining()>0){
//            System.out.println(buffer.get());
//        }

        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    /**
     * 通过CompletionHandler方式读取数据
     * @throws IOException
     */
    @Test
    public void testReadAsCompletionHandler() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //调用channel的read()得到Future
        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result:"+result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }
}
