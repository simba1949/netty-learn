package top.simba1949.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author anthony
 * @version 2023/8/14 21:33
 */
public class DiscardClient {
	public static void main(String[] args) {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(nioEventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new DiscardClientHandler());
						}
					});

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7777).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}
}
