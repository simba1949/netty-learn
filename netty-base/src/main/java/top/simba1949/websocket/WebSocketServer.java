package top.simba1949.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author anthony
 * @version 2023/8/6 14:52
 */
public class WebSocketServer {
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
							ChannelPipeline pipeline = ch.pipeline();
							// 因为是基于 HTTP 协议，需要使用 HTTP 的编解码器
							pipeline.addLast(new HttpServerCodec());
							// 是以块方式写，添加 ChunkedWriteHandler 处理器
							pipeline.addLast(new ChunkedWriteHandler());
							// HTTP 数据在传输过程中是分段的，HttpObjectAggregator 就是将多段聚合，
							// 这就是为什么，当浏览器发送大量数据时，就会发出多次请求
							pipeline.addLast(new HttpObjectAggregator(8 * 1024));
							/* 说明：
							 * 1.对应 websocket 它的数据是以【帧（frame）】形式传递
							 * 2.可以看到 WebSocketFrame 下面有六个子类
							 * 3.浏览器请求时：ws://localhost:7777/hello 表示请求的 url
							 * 4.WebSocketServerProtocolHandler 核心功能是将 HTTP 协议升级为 WS 协议，保持长连接
							 * 5.是通过一个状态码【101】
							 */
							pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
							// 自定义 handler，用于处理业务
							pipeline.addLast(new WebSocketServerHandler());
						}
					});

			ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}