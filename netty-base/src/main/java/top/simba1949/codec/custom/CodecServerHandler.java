package top.simba1949.codec.custom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author anthony
 * @version 2023/8/6 20:12
 */
public class CodecServerHandler extends SimpleChannelInboundHandler<Long> {

	/**
	 * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *            belongs to
	 * @param msg the message to handle
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("CodecServerHandler channelRead0");
		System.out.println("从客户端：" + ctx.channel().remoteAddress() + "读取到long值是：" + msg);
		// 读到消息是，给客户端发送 long 类型数据
		ctx.writeAndFlush(98765L);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}
}