package top.simba1949.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author anthony
 * @version 2023/8/5 22:16
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
	// 定义一个 channel 组，管理所有的 channel
	// GlobalEventExecutor.INSTANCE 是全局事件执行器，单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * handlerAdded 方法表示连接建立，一旦连接，第一个被执行
	 * 将当前的 Channel 加入到 channelGroup 组中
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		// 将该客户端加入聊天的信息推送给其他在线客户端
		// channelGroup.writeAndFlush 底层会逐一遍历发送消息
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天\n");
		channelGroup.add(channel);
	}

	/**
	 * handlerRemoved 表示 Channel 连线
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
	}

	/**
	 * 表示 Channel 处于活跃状态，上线提示
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " 上线了！");
	}

	/**
	 * 表示 Channel 处于离线状态，下线提示
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " 离线了！");
	}

	/**
	 * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *            belongs to
	 * @param msg the message to handle
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// 获取到当前 channel
		Channel currentChannel = ctx.channel();

		// 这里遍历 channelGroup ，根据不同的情况，会送不同的消息
		channelGroup.forEach(channel -> {
			if (channel != currentChannel) {
				// 如果遍历的 Channel 不是当前的 Channel，则进行转发消息
				channel.writeAndFlush("[客户]" + currentChannel.remoteAddress() + "发送了消息：" + msg + "\n");
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
