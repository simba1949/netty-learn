package top.simba1949.one;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author anthony
 * @version 2023/8/6 16:11
 */
public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		StudentPOJO.Student student = (StudentPOJO.Student) msg;
		System.out.println("客户端发送的数据是：" + student.toString());
		System.out.println("客户端发送的数据[id]是：" + student.getId());
		System.out.println("客户端发送的数据[name]是：" + student.getName());
	}
}