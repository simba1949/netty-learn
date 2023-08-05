package top.simba1949.groupChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author anthony
 * @version 2023/8/5 9:00
 */
public class GroupChatServer {

	private int port;

	public GroupChatServer(int port) {
		this.port = port;
	}

	public void run() {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 向 pipeline 加入解码器
							pipeline.addLast(StringDecoder.class.getName(), new StringDecoder());
							// 向 pipeline 加入编码器
							pipeline.addLast(StringEncoder.class.getName(), new StringEncoder());
							// 添加业务处理器
							pipeline.addLast(new GroupChatServerHandler());
						}
					});

			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			// 监听关闭
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		new GroupChatServer(7777).run();
		;
	}
}
