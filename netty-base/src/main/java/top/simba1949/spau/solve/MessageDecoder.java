package top.simba1949.spau.solve;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/6 22:57
 */
public class MessageDecoder extends ReplayingDecoder<MessageProtocol> {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MessageDecoder decode");
		// 需要将得到的二进制字节码转换成 MessageProtocol
		int length = in.readInt();
		byte[] content = new byte[length];
		in.readBytes(content);

		// 封装成 MessageProtocol
		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setLen(length);
		messageProtocol.setContent(content);

		out.add(messageProtocol);
	}
}
