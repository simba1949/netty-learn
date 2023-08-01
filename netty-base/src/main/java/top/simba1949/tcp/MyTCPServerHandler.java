package top.simba1949.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.nio.charset.StandardCharsets;

/**
 * @author anthony
 * @date 2023/8/1
 */
public class MyTCPServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当当前通道已从对等方读取消息时调用。
     *
     * @param ctx ChannelHandlerContext 上线文对象，含有管道 Pipeline，通道 Channel(通道中包含客户端IP地址)
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx =" + ctx);

        // 获取通道
        Channel channel = ctx.channel();
        // 获取管道，pipeline 本质上是一个双向链接，出站入站
        ChannelPipeline pipeline = ctx.pipeline();

        // 将 msg 转换成 Netty 提供的 ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + byteBuf.toString(StandardCharsets.UTF_8));
        System.out.println("客户端地址：" + channel.remoteAddress());
    }

    /**
     * 当当前读取操作读取的最后一条消息已被channelRead（ChannelHandlerContext， Object）使用时调用。
     * 如果ChannelOption.AUTO_READ关闭，则在调用ChannelHandlerContext.read（）之前不会再尝试从当前Channel读取入站数据。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 一般来讲，对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常，一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
