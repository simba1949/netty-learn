package top.simba1949.one;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author anthony
 * @version 2023/8/6 16:12
 */
public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 当通道就绪就会触发该方法
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送一个 Student 对象到服务器
		StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("李白").build();
		ctx.writeAndFlush(student);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
