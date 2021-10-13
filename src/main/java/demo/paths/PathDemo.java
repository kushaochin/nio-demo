package demo.paths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
    public static void main(String[] args) {
        //创建绝对路径
        Path absPath = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\test.txt");

        //创建相对路径
        Path relPath = Paths.get("C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\","test.txt");

        //Path.normalize()
        String strPath = "C:\\Users\\kusha\\Desktop\\NIO\\nio-demo\\src\\main\\resources\\..\\test.txt";
        Path path = Paths.get(strPath);
        System.out.println("原始路径："+path);

        Path path1 = path.normalize();
        System.out.println("标准化后的路径："+path1);



    }
}
