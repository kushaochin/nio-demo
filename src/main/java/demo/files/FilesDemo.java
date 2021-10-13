package demo.files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo {
    @Test
    public void testCreateDir() {
        //创建文件夹
        Path path = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\testDir");
        try {
            Path directories = Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopy(){
        //拷贝文件
        Path path1 = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");
        Path path2 = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\testDir\\test_copy.txt");
        try {
            Path copy = Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMove(){

        //移动文件、重命名
        Path srcPath = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\testDir\\test_copy.txt");
        Path destPath = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\testDir\\test_copy_move.txt");
        try {
            Path move = Files.move(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        Path path2 = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\testDir\\test_copy.txt");
        try {
            Files.delete(path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找一个名为test.txt的文件
     */
    @Test
    public void testWalkFileTree(){
        Path rootPath = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\");
        String fileToFind = "test.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);
                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
