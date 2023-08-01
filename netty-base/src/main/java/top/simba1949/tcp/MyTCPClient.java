package top.simba1949.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author anthony
 * @date 2023/8/1
 */
public class MyTCPClient {
    public static void main(String[] args) {
        // 客户端只需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            // 客户端使用 Bootstrap，服务器端使用 ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 加入自己的处理器
                            ch.pipeline().addLast(new MyTCPClientHandler());
                        }
                    });

            // 启动客户端，并连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7777).sync();
            // 监听关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
