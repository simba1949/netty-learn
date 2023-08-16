package top.simba1949.builtIn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author anthony
 * @version 2023/8/16 22:08
 */
public class ProtobufServer {
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(new ProtobufVarint32FrameDecoder())
									.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()))
									.addLast(new ChannelInboundHandlerAdapter() {
										@Override
										public void channelActive(ChannelHandlerContext ctx) throws Exception {
											System.out.println("客户端连接成功！");
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

			ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 7777);
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
