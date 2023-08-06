package top.simba1949.spau.solve;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author anthony
 * @version 2023/8/6 22:16
 */
public class SolveSpauServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		byte[] content = msg.getContent();
		int len = msg.getLen();
		System.out.println("服务端接收到消息内容如下：");
		System.out.println("长度：" + len);
		System.out.println("内容：" + new String(content, StandardCharsets.UTF_8));
		System.out.println("服务端接收到消息包数量是：" + (++count));
		System.out.println("-----------------------");

		// 回复消息
		byte[] responseContent = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setLen(responseContent.length);
		messageProtocol.setContent(responseContent);
		ctx.writeAndFlush(messageProtocol);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}