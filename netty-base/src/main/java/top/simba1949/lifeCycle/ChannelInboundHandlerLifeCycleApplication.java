package top.simba1949.lifeCycle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class ChannelInboundHandlerLifeCycleApplication {

    public static void main(String[] args) {
        ChannelInboundHandlerLifeCycleHandler handler = new ChannelInboundHandlerLifeCycleHandler();
        // 通道初始化器
        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(handler);
            }
        };

        // 创建嵌入式通道
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);

        // 模拟入站，向嵌入式通道写一个入站数据包
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        embeddedChannel.writeInbound(buffer);
        embeddedChannel.flush();
        // 关闭通道
        embeddedChannel.close();
    }
}
