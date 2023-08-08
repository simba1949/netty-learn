package top.simba1949.rpc.provider.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author anthony
 * @version 2023/8/8 21:32
 */
public class NettyServer {
	public static void startServer(String hostname, int port) {
		startServer0(hostname, port);
	}

	private static void startServer0(String hostname, int port) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 添加编解码器
							pipeline.addLast(new StringDecoder());
							pipeline.addLast(new StringEncoder());
							// 添加业务处理器
							pipeline.addLast(new NettyServerHandler());
						}
					});

			ChannelFuture channelFuture = serverBootstrap.bind(hostname, port).sync();
			System.out.println("服务提供方开始提供服务");
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
