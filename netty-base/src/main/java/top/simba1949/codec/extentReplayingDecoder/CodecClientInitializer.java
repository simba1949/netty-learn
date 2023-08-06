package top.simba1949.codec.extentReplayingDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author anthony
 * @version 2023/8/6 20:15
 */
public class CodecClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 加入一个出站的 handler，对数据进行编码
		pipeline.addLast(new MyByteToLongDecoderExtentReplayingDecoder());
		pipeline.addLast(new MyLongToByteDecoder());

		// 加入一个自定义的 handler，处理业务
		pipeline.addLast(new CodecClientHandler());
	}
}