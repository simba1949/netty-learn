package top.simba1949.spau.solve;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author anthony
 * @version 2023/8/6 22:13
 */
public class SolveSpauServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 加入自定义的编解码器
		pipeline.addLast(new MessageEncoder());
		pipeline.addLast(new MessageDecoder());

		pipeline.addLast(new SolveSpauServerHandler());
	}
}