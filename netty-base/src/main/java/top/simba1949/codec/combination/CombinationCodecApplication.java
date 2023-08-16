package top.simba1949.codec.combination;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class CombinationCodecApplication {
    public static void main(String[] args) {
        CombinationCodec combinationCodec = new CombinationCodec();

        // 通道初始化器
        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(combinationCodec);
            }
        };

        // 创建嵌入式通道
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);

        // 模拟入站
        // 向嵌入式通道写一个入站数据包
        ByteBuf inboundByteBuf = Unpooled.buffer();
        inboundByteBuf.writeLong(1);
        embeddedChannel.writeInbound(inboundByteBuf);
        embeddedChannel.flush();

        // 读取入站数据
        Object readInbound = embeddedChannel.readInbound();
        System.out.println(readInbound);

        // 模拟出站
        // 向嵌入式通道写一个出站数据包
        embeddedChannel.writeOneOutbound(2);
        embeddedChannel.flush();

        // 向读取出站数据（上面出站数据包是什么，这里读取的什么）
        int readOutbound = embeddedChannel.readOutbound();
        System.out.println(readOutbound);

        // 关闭通道
        embeddedChannel.close();
    }
}