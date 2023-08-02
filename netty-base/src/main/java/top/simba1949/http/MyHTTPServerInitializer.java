package top.simba1949.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author anthony
 * @version 2023/8/2 22:34
 */
public class MyHTTPServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		// 得到管道
		ChannelPipeline pipeline = socketChannel.pipeline();
		// 向管道加入处理器
		// 加入 netty 提供的http编解码器：HttpServerCodec
		pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
		pipeline.addLast("MyHTTPServerHandler", new MyHTTPServerHandler());
	}
}
