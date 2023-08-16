package top.simba1949.json;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class JsonClient {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldPrepender(4)) // 添加等长编码器
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8)) // 添加Netty内置的字符串编码器
                                    .addLast(new JsonMsgEncoder()) // 添加自定义的JSON编码器
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        // 这里添加一个入站处理器，用于客户端发送数据
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            Channel channel = ctx.channel();
                                            // 发送 Json 字符串对象
                                            for (int i = 0; i < 1000; i++) {
                                                JsonMsg jsonMsg = new JsonMsg();
                                                jsonMsg.setId(i);
                                                jsonMsg.setContent(i + "->" + i);
                                                channel.writeAndFlush(jsonMsg);
                                            }
                                        }
                                    });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7777).sync();

            // 添加监听器
            channelFuture.addListener((ChannelFuture futureListener) -> {
                if (futureListener.isSuccess()) {
                    System.out.println("连接服务端成功!");
                } else {
                    System.out.println("连接服务端失败!");
                }
            });

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}