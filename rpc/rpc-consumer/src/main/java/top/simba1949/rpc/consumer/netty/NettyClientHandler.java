package top.simba1949.rpc.consumer.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author anthony
 * @version 2023/8/8 21:45
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

	private ChannelHandlerContext context; // 客户端上下文
	private String result; // 返回的结果
	private String param; // 客户端调用方法，传入的参数

	/**
	 * 与服务器连接创建后，被调用
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("NettyClientHandler channelActive");
		context = ctx;
	}

	/**
	 * 收到服务器的数据后，被调用
	 *
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("NettyClientHandler channelRead");
		result = msg.toString();
		// 唤醒等待的线程
		notify();
	}

	/**
	 * 被代理对象调用，发送数据到服务提供者————>发送后交出CPU，等待结果————>等待被唤醒（channelRead唤醒）————>返回结果
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized String call() throws Exception {
		System.out.println("NettyClientHandler call");

		context.writeAndFlush(param);

		wait();

		System.out.println("NettyClientHandler call 2");
		// 等待 channelRead 方法获取到服务器结果后被唤醒，将结果返回
		return result;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * 设置入参
	 *
	 * @param param
	 */
	public void setParam(String param) {
		System.out.println("NettyClientHandler setParam");
		this.param = param;
	}
}
