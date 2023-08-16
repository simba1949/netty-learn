package top.simba1949.json;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class JsonServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8)) // 添加Netty内置的字符串解码器
                                    .addLast(new JsonMsgDecoder()) // 添加自定义的JSON解码器
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        // 这里添加一个入站处理器，用于打印日志校验数据是否解码正确
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("客户端连接成功！");
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            JsonMsg jsonMsg = (JsonMsg) msg;
                                            System.out.println("收到一个JSON数据包=" + jsonMsg.toString());
                                        }
                                    });
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}