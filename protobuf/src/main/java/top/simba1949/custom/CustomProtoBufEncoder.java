package top.simba1949.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author anthony
 * @version 2023/8/16 22:42
 */
public class CustomProtoBufEncoder extends MessageToByteEncoder<StudentPOJO.Student> {
	@Override
	protected void encode(ChannelHandlerContext ctx, StudentPOJO.Student msg, ByteBuf out) throws Exception {
		// 将对象转换成字节数组
		byte[] byteArray = msg.toByteArray();
		// 读取消息长度
		int length = byteArray.length;

		ByteBuf buffer = ctx.alloc().buffer(14 + length);

		// 先将消息长度写入，也就是消息头
		buffer.writeInt(length);
		// 写入魔数
		buffer.writeLong(123456789L);
		// 写入版本号
		buffer.writeShort((short) 5);
		// 写入内容
		buffer.writeBytes(byteArray);

		out.writeBytes(buffer);
	}
}
