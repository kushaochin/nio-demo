package demo.fileLock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockDemo {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap("hello FileLock ".getBytes());
        String filePath = "C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt";
        Path path = Paths.get(filePath);
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
        if (fileChannel.size()!=0) {
            fileChannel.position(fileChannel.size() - 1);
        }
        //加锁
        FileLock lock = fileChannel.lock();
        System.out.println("是否共享锁："+lock.isShared());

        //写入文件
        fileChannel.write(buffer);
        fileChannel.close();

        //读文件
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println("读取的内容：");
        bufferedReader.lines().forEach((line)->{
            System.out.println("---"+line);
        });
        fileReader.close();
        bufferedReader.close();
    }
}
