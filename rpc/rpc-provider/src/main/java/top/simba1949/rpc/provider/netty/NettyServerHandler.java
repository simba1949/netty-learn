package top.simba1949.rpc.provider.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.simba1949.rpc.api.Protocol;
import top.simba1949.rpc.provider.api.impl.HelloServiceImpl;

/**
 * @author anthony
 * @version 2023/8/8 21:36
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 获取客户端发送的消息，并调用服务
		System.out.println("msg=" + msg);

		// 客户端在调用服务器的 api 时，需要定义一个协议
		// 比如我们要求，每次发消息必须以某个字符串开头 simba#
		if (msg.toString().startsWith(Protocol.NAME)) {
			String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
			ctx.writeAndFlush(result);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
