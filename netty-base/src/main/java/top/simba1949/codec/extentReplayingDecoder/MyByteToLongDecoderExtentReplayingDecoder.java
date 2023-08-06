package top.simba1949.codec.extentReplayingDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/6 20:09
 */
public class MyByteToLongDecoderExtentReplayingDecoder extends ReplayingDecoder<Long> {

	/**
	 * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
	 * @param in  the {@link ByteBuf} from which to read data
	 * @param out the {@link List} to which decoded messages should be added
	 * @throws Exception
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyByteToLongDecoder decode");
		// 这里继承 ReplayingDecoder，用户无需调用 readableBytes() 方法
		out.add(in.readLong());
	}
}