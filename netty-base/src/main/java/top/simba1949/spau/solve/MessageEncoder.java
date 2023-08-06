package top.simba1949.spau.solve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author anthony
 * @version 2023/8/6 22:55
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {
	@Override
	protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
		System.out.println("MessageEncoder encode");
		out.writeInt(msg.getLen());
		out.writeBytes(msg.getContent());
	}
}
