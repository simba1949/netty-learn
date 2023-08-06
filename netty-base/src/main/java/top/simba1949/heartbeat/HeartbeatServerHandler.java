package top.simba1949.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author anthony
 * @version 2023/8/6 14:37
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			// 如果是 IdleStateEvent 事件，向下转型
			IdleStateEvent event = (IdleStateEvent) evt;
			//
			String eventType = null;
			switch (event.state()) {
				case READER_IDLE:
					eventType = "读空闲";
					break;
				case WRITER_IDLE:
					eventType = "写空闲";
					break;
				case ALL_IDLE:
					eventType = "读写空闲";
					break;
			}

			System.out.println(ctx.channel().remoteAddress() + "超时时间" + eventType);
			System.out.println("服务器根据不同的事件进行处理");
			// 如果关闭该通道，则只会有一次空闲
			ctx.close();
		}
	}
}