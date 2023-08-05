package top.simba1949.groupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author anthony
 * @version 2023/8/5 22:32
 */
public class GroupChatClient {

	private String host;
	private int port;

	public GroupChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}


	public void run() {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(nioEventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 向 pipeline 加入解码器
							pipeline.addLast(StringDecoder.class.getName(), new StringDecoder());
							// 向 pipeline 加入编码器
							pipeline.addLast(StringEncoder.class.getName(), new StringEncoder());
							// 添加业务处理器
							pipeline.addLast(new GroupChatClientHandler());
						}
					});
			//
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			Channel channel = channelFuture.channel();
			System.out.println("本机地址：" + channel.remoteAddress());
			// 客户端需要输入信息，创建一个扫码器，读取控制台输入信息
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				// 通过 channel，发送到服务器
				channel.writeAndFlush(msg + "\n");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new GroupChatClient("localhost", 7777).run();
	}
}