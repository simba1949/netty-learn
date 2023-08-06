package top.simba1949.codec.custom;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author anthony
 * @version 2023/8/6 20:08
 */
public class CodecServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 加入自定义的编解码器
		pipeline.addLast(new MyByteToLongDecoder());
		pipeline.addLast(new MyLongToByteDecoder());
		// 加入自定义的handler，处理业务逻辑（这里只是打印客户端数据）
		pipeline.addLast(new CodecServerHandler());
	}
}