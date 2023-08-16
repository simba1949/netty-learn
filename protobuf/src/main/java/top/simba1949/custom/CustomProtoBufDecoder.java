package top.simba1949.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/16 22:43
 */
public class CustomProtoBufDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 标记一下当前的读指针 readIndex 的位置
		in.markReaderIndex();
		// 判断包头的长度
		if (in.readableBytes() < 4) {
			return;
		}

		// 读取传送过来的消息的长度。
		int length = in.readUnsignedShort();

		// 长度如果小于0
		if (length < 0) {
			// 非法数据，关闭连接
			ctx.close();
		}

		// 读到的消息体长度如果小于传送过来的消息长度
		if (length > in.readableBytes()) {
			// 重置读取位置
			in.resetReaderIndex();
			return;
		}

		short readLength = in.readShort();
		System.out.println("读取对象长度" + readLength);
		long readMagicNumber = in.readLong();
		System.out.println("读取魔数" + readMagicNumber);
		short readVersion = in.readShort();
		System.out.println("读取版本号" + readVersion);

		byte[] array = new byte[readLength];
		in.readBytes(array);
		// 字节转成对象
		StudentPOJO.Student student = StudentPOJO.Student.parseFrom(array);

		if (student != null) {
			// 获取业务消息头
			out.add(student);
		}
	}
}