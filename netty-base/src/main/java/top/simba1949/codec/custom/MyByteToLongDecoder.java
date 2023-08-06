package top.simba1949.codec.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/6 20:09
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

	/**
	 * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
	 * @param in  the {@link ByteBuf} from which to read data
	 * @param out the {@link List} to which decoded messages should be added
	 * @throws Exception
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyByteToLongDecoder decode");

		// long 类型是8个字节
		if (in.readableBytes() >= 8) {
			out.add(in.readLong());
		}
	}
}