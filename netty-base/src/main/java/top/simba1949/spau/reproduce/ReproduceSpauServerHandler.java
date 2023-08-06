package top.simba1949.spau.reproduce;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author anthony
 * @version 2023/8/6 22:16
 */
public class ReproduceSpauServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
	/**
	 *
	 */
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		byte[] buffer = new byte[msg.readableBytes()];
		msg.readBytes(buffer);

		String message = new String(buffer, StandardCharsets.UTF_8);
		System.out.println("服务端接收到数据：" + message);
		System.out.println("服务端接收到消息量：" + (++this.count));

		// 服务器回送给客户端发送随机id
		ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "\n", StandardCharsets.UTF_8);
		ctx.writeAndFlush(byteBuf);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
