package demo.charset;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class CharsetDemo {
    public static void main(String[] args) throws CharacterCodingException {
        //1、获取Charset对象
        Charset charset = Charset.forName("UTF-8");

        //2、获取编码器对象
        CharsetEncoder charsetEncoder = charset.newEncoder();

        //3、创建缓冲区
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("内容");
        charBuffer.flip();

        //4、编码，用编码器对象
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        System.out.println("编码后的结果：");
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }

        //5、获取解码器对象
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();

        //6、解码，用解码器对象
        CharBuffer decodedBuffer = charsetDecoder.decode(byteBuffer);
        System.out.println("解码后的结果：");
        System.out.println(decodedBuffer.toString());

        //7、使用GBK解码#############解码是乱码的
        Charset gbkCharSet = Charset.forName("GBK");
        byteBuffer.flip();
        CharBuffer decode = gbkCharSet.decode(byteBuffer);
        System.out.println(decode.toString());

    }

    /**
     * 打印系统支持的编码
     */
    @Test
    public void testAvailableCharsets() {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue().toString());
        }
    }
}
