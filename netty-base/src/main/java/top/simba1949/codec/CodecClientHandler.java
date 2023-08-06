package top.simba1949.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author anthony
 * @version 2023/8/6 20:18
 */
public class CodecClientHandler extends SimpleChannelInboundHandler<Long> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("CodecClientHandler channelActive");
		ctx.writeAndFlush(123456L);
		// ctx.writeAndFlush，encode 的时候，会判断当前 msg 是不是处理的类型，如果是 encode 处理，如果不是就跳过 encode
		// 因此在编写 encode 的时候需要注意传输的类型
		// ctx.writeAndFlush(Unpooled.copiedBuffer("ABCDEFGHIGKLMNOPQRSTUVWXYZ".getBytes(StandardCharsets.UTF_8)));
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("CodecClientHandler channelRead0");
		System.out.println("读取的服务端发送的数据：" + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}
}