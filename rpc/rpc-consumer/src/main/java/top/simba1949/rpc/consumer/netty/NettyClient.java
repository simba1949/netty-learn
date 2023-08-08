package top.simba1949.rpc.consumer.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author anthony
 * @version 2023/8/8 21:55
 */
public class NettyClient {

	private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static NettyClientHandler client;

	// 使用代理模式，获取一个代理对象
	public Object getBean(final Class<?> serviceClz, final String protocol) {
		return Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				new Class<?>[]{serviceClz},
				(proxy, method, args) -> {
					System.out.println("进入代理");

					if (null == client) {
						initClient();
					}

					// 设置要发给服务端的信息
					client.setParam(protocol + args[0]);
					return executorService.submit(client).get();
				});
	}

	private static void initClient() {
		client = new NettyClientHandler();

		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(nioEventLoopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new StringDecoder());
							pipeline.addLast(new StringEncoder());
							pipeline.addLast(client);
						}
					});

			bootstrap.connect("127.0.0.1", 7777).sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}
}
