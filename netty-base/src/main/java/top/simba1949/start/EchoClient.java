package top.simba1949.start;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 1.为初始化客户端，创建了一个 Bootstrap 实例；
 * 2.为进行事件处理分配了一个 NioEventLoopGroup 实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
 * 3.为服务器连接创建了一个 InetSocketAddress 实例；
 * 4.当连接被建立时，一个 EchoClientChannelHandler 实例会被安装到（该 Channel 的 ） ChannelPipeline 中；
 * 5.在一切都设置完成后，调用 Bootstrap.connect()方法连接到远程节点；
 *
 * @author anthony
 * @date 2023/7/11
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;

        new EchoClient(host, port).start();
    }

    /**
     * 客户端引导服务器
     */
    public void start() {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup) // 指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
                    .channel(NioSocketChannel.class) // 适用于 NIO 传输的 Channel 类型
                    .remoteAddress(new InetSocketAddress(host, port)) // 设置服务器的 InetSocketAddress
                    .handler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    // 在创建Channel时，向 ChannelPipeline 中添加一个 EchoClientChannelHandler 实例
                                    ch.pipeline().addLast(new EchoClientChannelHandler());
                                }
                            });
            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture channelFuture = bootstrap.connect().sync();
            // 阻塞，直到 Channel 关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭线程池并且释放所有的资源
                nioEventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
