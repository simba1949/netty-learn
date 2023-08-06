package top.simba1949.spau.reproduce;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/6 22:11
 */
public class ReproduceSpauClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 使用客户端发送10条数据
		for (int i = 0; i < 1000; i++) {
			ByteBuf byteBuf = Unpooled.copiedBuffer("hello,server " + i, StandardCharsets.UTF_8);
			ctx.writeAndFlush(byteBuf);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		byte[] buffer = new byte[msg.readableBytes()];
		msg.readBytes(buffer);

		String message = new String(buffer, StandardCharsets.UTF_8);
		System.out.println("客户端接收到消息是：" + message);
		System.out.println("客户端接收到消息数量量：" + (++this.count));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
