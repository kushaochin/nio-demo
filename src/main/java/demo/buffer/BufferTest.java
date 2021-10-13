package demo.buffer;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferTest {
    @Test
    public void buffer01(){
        
    }
    
    @Test
    public void buffer02(){
        //创建buffer
        IntBuffer buffer = IntBuffer.allocate(8);
        
        //往buffer放数据
        for (int i = 0; i < buffer.capacity(); i++) {
            int j = i*i;
            buffer.put(j);
        }

        //充值缓冲区
        buffer.flip();

        //获取
        while (buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value + "");

        }
    }

    /**
     * 缓冲区分片
     */
    @Test
    public void testSlice(){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        //改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i,b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining()>0){
            System.out.println(buffer.get());
        }
    }

    /**
     * 只读缓冲区
     */
    @Test
    public void testReadOnly(){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        //创建只读缓冲区
        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();

        //修改原缓冲区内容
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= b;
            buffer.put(i,b);
        }

        readonlyBuffer.position(0);
        readonlyBuffer.limit(buffer.capacity());

        //读取只读缓冲区（内容与原缓冲区一致）
        while (readonlyBuffer.remaining()>0){
            System.out.println(readonlyBuffer.get());
        }
    }

    /**
     * 直接缓冲区
     */
    @Test
    public void testDirect() throws Exception {
        String infile = "C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt";
        FileInputStream finStream = new FileInputStream(infile);
        FileChannel finChannel = finStream.getChannel();

        String outfile = "C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test_copy.txt";
        FileOutputStream foutStream = new FileOutputStream(outfile);
        FileChannel foutChannel = foutStream.getChannel();

        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        //复制文件
        while (true){
            buffer.clear();

            int r = finChannel.read(buffer);
            if (r == -1){
                break;
            }

            buffer.flip();
            foutChannel.write(buffer);
        }
    }

    /**
     * 内存映射文件I/O
     */
    @Test
    public void testRamMapping() throws Exception {
        RandomAccessFile raFile = new RandomAccessFile("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt","rw");
        FileChannel fileChannel = raFile.getChannel();
        MappedByteBuffer mbBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        mbBuffer.put(0,(byte)0);
        mbBuffer.put(1023,(byte)1023);
        raFile.close();
    }
}
