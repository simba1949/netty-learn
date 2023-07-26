package top.simba1949.start;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author anthony
 * @date 2023/7/10
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Throwable {
        // 设置端口值，如果端口值参数的格式不正确，则抛出一个异常
        int port = 8888;
        new EchoServer(port).start();
    }

    /**
     * 启动服务
     * 1.创建一个 ServerBootstrap 的实例以引导和绑定服务器；
     * 2.创建并分配一个 NioEventLoopGroup 实例以进行事件的处理，如接受新连接以及读/写数据；
     * 3.指定服务器绑定的本地的 InetSocketAddress；
     * 4.使用一个 EchoServerHandler 的实例初始化每一个新的 Channel；
     * 5.调用 ServerBootstrap.bind()方法以绑定服务器。
     * @throws Throwable
     */
    public void start() throws Throwable {
        final EchoServerChannelHandler echoServerChannelHandler = new EchoServerChannelHandler();

        // 创建 EventLoopGroup，指定NioEventLoopGroup来接受和处理新的连接，即使用NIO传输
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            // 创建 ServerBootstrap，引导服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class) // 指定所使用的 NIO 传输 Channel
                    .localAddress(new InetSocketAddress(port)) // 使用指定的端口设置套接字地址
                    .childHandler(
                            // 这里使用了一个特殊的类——ChannelInitializer。这是关键。当一个新的连接被接受时，一个新的子 Channel 将会被创建，
                            // 而 ChannelInitializer 将会把一个你的 EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中。
                            // 这个 ChannelHandler 将会收到有关入站消息的通知。
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    // 添加一个 EchoChannelHandler 到子 Channel 的 ChannelPipeline
                                    ch.pipeline().addLast(echoServerChannelHandler);
                                }
                            });
            // 异步地绑定服务器，调用 sync() 方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // 获取 Channel 的 CloseFuture，并阻塞当前线程直到它完成
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup，释放所有的资源
            nioEventLoopGroup.shutdownGracefully().sync();
        }
    }
}
