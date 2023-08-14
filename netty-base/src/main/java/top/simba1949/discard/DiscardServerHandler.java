package top.simba1949.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/14 21:31
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf) msg;
		try {
			while (byteBuf.isReadable()) {
				byte[] buffer = new byte[byteBuf.readableBytes()];
				byteBuf.readBytes(buffer);
				System.out.println(new String(buffer, StandardCharsets.UTF_8));
			}
			// 换行
			System.out.println();
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
}
