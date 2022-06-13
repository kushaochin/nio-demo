https://blog.csdn.net/weixin_47872288/article/details/120342049

# 第一张 概述
## Java NIO(New IO 和None Blocking IO)
- 阻塞IO
- 非阻塞IO（NIO）
- NIO概述
	- Channels
		双向的，既可以用来进行读操作，又可以用来进行写操作
		主要实现有如下：FileChannel（IO）. DatagramChannel（UDP ）. SocketChannel （TCP中Server ）和 ServerSocketChannel（TCP中Client）
	- Buffers
		实现有：ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer,IntBuffer, LongBuffer, ShortBuffer，分别对应基本数据类型：byte. char. double. float. int. long. short
	- Selectors
	- ...Pipe...FileLock
# 第二章 Channel
- Channel概述
- Channel实现
	- FileChannel 从文件中读取数据
	- DatagramChannel 能通过UDP读取网络中的数据
	- SocketChannel 能通过TCP读取网络中的数据
	- ServerSocketChannel 可以监听新进来的TCP连接,像Web服务器那样，对每一个新进来的来连接都会创建一个SocketChannel
- FileChannel
- Socket通道
	- ServerSocketChannel
	- SocketChannel
	- DatagramChannel
- Scatter/Gather
# 第三章 Buffer
## 3.1 Buffer简介
## 3.2 Buffer的基本用法
## 3.3 Buffer的capacity. position. limit
	读写模式中有些区别
## 3.4 Buffer的类型
- ByteBuffer
- MappedByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer
## 3.5 Buffer分配和写数据
- Buffer分配和写数据
    CharBuffer buffer = CharBuffer.allocate(1024);//分配一个可存储1024个字符的CharBuffer
- 向Buffer中写数据
    1. 从Channel写到Buffer
        int bytesRead = inChannel.read(buf);
    2. 通过Buffer的put()方法写到Buffer
        buf.put(127);
- flip()方法
    将Buffer从写模式切换到读模式，position置为0，limit置为position的值
## 3.6 从Buffer中读取数据
- 从Buffer中读取数据有两种方式
    1. 从Buffer中读取数据到Channel
        channel.write(buffer);
    2. 使用get()方法从Buffer中读取数据
        int value = buffer.get();
## 3.7 Buffer几个方法
- rewind()
    将position置为0，limit保持不变
- clear()
    position置为0，limit置为capacity的值
- compact()
    将所有未读数据拷贝到Buffer起始处，position置为最后一个未读元素正后面，limit置为capacity。就是清除了已读取过的数据
- mark()与reset()
    ？？？？？？？？？？？？？？？？
## 3.8 缓冲区操作
1. 缓冲区分片
2. 只读缓冲区
3. 直接缓冲区
    为了加快IO速度，jvm尽最大努力直接执行IO操作，避免使用中间缓冲区
4. 内存映射文件I/O
    一种读写文件的方法，比常规流. 通道快的多。
    把实际读取或写入的部分映射到内存中
	
# 第四章 Selector
## 4.1 Selector简介
1. Selector和Channel关系
    Selector一般成为选择器，也可以翻译为多路复用器。核心组件，用于检查一个或多个NIO Channel的状态是否处于可读. 可写。
2. 可选择通道（SelectableChannel）
    继承SelectableChannel的类是可选择通道，可以被Selector服用的。
3. Channel注册到Selector
    Channel.register注册到Selector
4. 选择键（SelectionKey）
    这个概念很重要，但是还没太理解。。。。。。
## 4.2 Selector的使用方法
1. Selector的创建
    Selector selector = Selector.open();
2. 注册Channel到Selector
    注意：
    1. Channel必须处于非阻塞模式下
    2. 通道并没有一定要支持所有的四种操作，validOps()获取通道支持的操作集合
3. 轮询查询就绪操作
    select()方法查询已经就绪的通道操作
4. 停止选择的方法
    wakeup():让处于阻塞状态的select()方法立刻返回
    close():关闭Selector
## 4.3 示例代码
1. 服务端代码
2. 客户端代码
3. NIO编程步骤总结
    第一步：创建ServerSocketChannel通道，绑定监听端口
    第二步：设置通道是非阻塞模式
    第三步：创建Selector选择器
    第四步：把Channel注册到Selector选择器上，监听连接事件
    第五步：调用Selector的select()（循环调用），检测通道的就绪状况
    第六步：调用selectKeys()获取就绪的Channel集合
    第七步：遍历就绪Channel集合，判断就绪集合事件类型，实现具体的业务操作
    第八步：根据业务，是否需要再次注册监听事件，重复执行

# 第五章 Pipe和FileLock
## 5.1 Pipe
Java NIO管道是两个线程之间的单向数据连接。Pipi又一个source通道和一个sink通道，从source通道读取数据，写入到siink通道。
1. 创建管道
    Pipi pipe = Pipe.open();
2. 写入管道
3. 从管道读取数据
4. 示例
## 5.2 FileLock
1. FileLock简介
    文件锁是进程级别的
    可以解决多个进程并发访问. 修改同一个文件的问题
    但不能解决多线程并发访问. 修改同一个文件的问题
    同一个进程内多个线程，可以同时访问. 修改同一个问题件
    加锁后，调用release()或者关闭FileChannel或者退出当前jvm，才会释放锁
2. 文件锁分类
    - 排他锁
        加锁后，当前进程可进行读写，其他进程不可以读写，直到当前进程释放锁
    - 共享锁
        枷锁后，其他进程只可以读，不能写
3. 使用示例
4. 获取文件锁的方法
    lock()
    lock(long position,long size,boolean shared)
    tryLock()
    tryLock(long position,long size,boolean shared)
5. lock与tryLock的区别
    lock()是阻塞式的，会一直阻塞当前线程，知道获取到文件锁
    tryLock()是非阻塞式的，尝试获取文件锁，否则返回null，不会阻塞当前线程
    
6. FileLock两个方法
    boolean isShared()	//此文件锁是否是共享锁
    boolean isValid() //此文件锁是否还有效
7. 完整例子
# 第六章 其他
## 6.1 Path
1. Path简介
2. 创建Paht实例
3. 创建绝对路径
4. 创建相对路径
5. Path.normalize()
    就是规范化路径
## 6.2 Files
1. Files.createDirectory()
2. Files.copy()
3. Files.move()
4. Files.delete()
5. Files.walkFileTree()
    递归遍历目录树功能
## 6.3 AsynchronousFileChannel
异步写入数据到文件中
1. 创建
2. 通过Future读取数据
3. 通过CompletionHandler读取数据
4. 通过Future写数据
5. 通过CompletionHandler写数据
## 6.4 字符集（Charset）
- Charset 常用静态方法
```
public static Charset forName(String charsetName)//通过编码类型获得 Charset 对象
public static SortedMap<String,Charset> availableCharsets()//获得系统支持的所有编码方式
public static Charset defaultCharset()//获得虚拟机默认的编码方式
public static boolean isSupported(String charsetName)//判断是否支持该编码类型
```
- Charset 常用普通方法
```
public final String name()//获得 Charset 对象的编码类型(String)
public abstract CharsetEncoder newEncoder()//获得编码器对象
public abstract CharsetDecoder newDecoder()//获得解码器对象
```
# 第七章 综合案例

