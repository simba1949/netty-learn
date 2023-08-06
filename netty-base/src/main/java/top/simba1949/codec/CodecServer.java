package top.simba1949.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author anthony
 * @version 2023/8/6 20:04
 */
public class CodecServer {
	public static void main(String[] args) {
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boosGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new CodecServerInitializer());

			ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			boosGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}