package top.simba1949.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author anthony
 * @version 2023/8/2 22:32
 */
public class MyHTTPServer {
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new MyHTTPServerInitializer());
			// 绑定一个端口，并同步生成一个 ChannelFuture 对象
			ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();

			// 对关闭的通道进行监听
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
