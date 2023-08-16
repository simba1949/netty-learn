package top.simba1949.custom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author anthony
 * @version 2023/8/16 22:42
 */
public class CustomProtoBufClient {
	public static void main(String[] args) {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(nioEventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(new CustomProtoBufEncoder())
									.addLast(new ChannelInboundHandlerAdapter() {
										@Override
										public void channelActive(ChannelHandlerContext ctx) throws Exception {
											// 用于发送客户端数据到服务器端
											// 发送一个 Student 对象到服务器
											StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("李白").build();
											ctx.writeAndFlush(student);
										}
									});
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
