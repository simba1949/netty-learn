package top.simba1949.custom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author anthony
 * @version 2023/8/16 22:42
 */
public class CustomProtoBufServer {
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(new CustomProtoBufDecoder())
									.addLast(new ChannelInboundHandlerAdapter() {
										@Override
										public void channelActive(ChannelHandlerContext ctx) throws Exception {
											System.out.println("客户端连接成功");
										}

										@Override
										public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
											// 用于读取客户端数据
											StudentPOJO.Student student = (StudentPOJO.Student) msg;
											System.out.println("客户端发送的数据是：" + student.toString());
											System.out.println("客户端发送的数据[id]是：" + student.getId());
											System.out.println("客户端发送的数据[name]是：" + student.getName());
										}
									});
						}
					});
			ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
