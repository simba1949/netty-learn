package top.simba1949.codec.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author anthony
 * @version 2023/8/6 20:16
 */
public class MyLongToByteDecoder extends MessageToByteEncoder<Long> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
		System.out.println("MyLongToByteDecoder encode 被调用");
		out.writeLong(msg);
	}
}