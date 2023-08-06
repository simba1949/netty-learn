package top.simba1949.longConnection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * TextWebSocketFrame 表示一个文本帧（frame）
 *
 * @author anthony
 * @version 2023/8/6 15:17
 */
public class LongConnectionServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		System.out.println("服务器收到消息：" + msg.text());
		// 回复消息
		String responseMsg = "服务器时间：" + LocalDateTime.now() + "收到的消息是：" + msg.text();
		ctx.channel().writeAndFlush(new TextWebSocketFrame(responseMsg));
	}

	/**
	 * 当客户端连接后，触发方法
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// id 表示唯一的值 LongText 是唯一的，ShortText 不是唯一的
		System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
		System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
		System.out.println("handlerRemoved 被调用" + ctx.channel().id().asShortText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常信息：" + cause.getMessage());
		ctx.close();
	}
}