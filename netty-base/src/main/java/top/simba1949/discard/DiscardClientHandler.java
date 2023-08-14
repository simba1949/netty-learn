package top.simba1949.discard;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/14 21:39
 */
public class DiscardClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String msg = "君不见黄河之水天上来";
		ByteBuf byteBuf = Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8);
		ctx.writeAndFlush(byteBuf);
	}
}
