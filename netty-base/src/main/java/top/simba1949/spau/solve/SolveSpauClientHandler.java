package top.simba1949.spau.solve;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/6 22:11
 */
public class SolveSpauClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 向客户端发送十条数据
		for (int i = 0; i < 10; i++) {
			String msg = "君不见黄河之水天上来";
			byte[] content = msg.getBytes(StandardCharsets.UTF_8);
			int length = content.length;
			// 创建协议包对象
			MessageProtocol messageProtocol = new MessageProtocol();
			messageProtocol.setLen(length);
			messageProtocol.setContent(content);
			// 将协议包对象写到通道中
			ctx.writeAndFlush(messageProtocol);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		System.out.println("客户端接收到服务端数据如下：");
		System.out.println("客户端接收到服务端数据len=" + msg.getLen());
		System.out.println("客户端接收到服务端数据content=" + new String(msg.getContent(), StandardCharsets.UTF_8));
		System.out.println("客户端接收到服务端次数：" + (++count));
		System.out.println("---------------------");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
