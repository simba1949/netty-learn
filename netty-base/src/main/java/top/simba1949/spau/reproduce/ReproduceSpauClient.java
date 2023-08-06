package top.simba1949.spau.reproduce;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author anthony
 * @version 2023/8/6 22:07
 */
public class ReproduceSpauClient {
	public static void main(String[] args) {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(nioEventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ReproduceSpauClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect("localhost", 7777).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}
}