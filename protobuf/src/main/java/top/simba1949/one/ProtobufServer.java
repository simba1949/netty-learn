package top.simba1949.one;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author anthony
 * @version 2023/8/6 16:11
 */
public class ProtobufServer {
	public static void main(String[] args) {
		// 创建两个线程池组 bossGroup 和 workerGroup（备注：NioEventLoopGroup 本质是线程池）
		// bossGroup 用来处理连接请求
		// workerGroup 用来处理客户端的业务
		// new NioEventLoopGroup() 如果没有指定线程数，默认是（实际CPU核数 * 2）
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();


		try {
			// 创建服务器端的启动对象，并配置参数
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossGroup, workerGroup) // 第一个参数bossGroup，第二个参数配置workerGroup
					.channel(NioServerSocketChannel.class) // 选择通道，使用 NioServerSocketChannel 作为服务器的通道实现
					.option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
					.childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 指定对哪种对象进行解码
							pipeline.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
							pipeline.addLast(new ProtobufServerHandler());
						}
					});

			// 绑定一个端口，并同步生成一个 ChannelFuture 对象
			ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();

			// 对关闭的通道进行监听
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 关闭线程池
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}