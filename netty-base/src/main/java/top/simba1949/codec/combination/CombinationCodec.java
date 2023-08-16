package top.simba1949.codec.combination;

import io.netty.channel.CombinedChannelDuplexHandler;
import top.simba1949.codec.extentReplayingDecoder.MyByteToLongDecoderExtentReplayingDecoder;
import top.simba1949.codec.extentReplayingDecoder.MyLongToByteDecoder;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class CombinationCodec extends CombinedChannelDuplexHandler<MyByteToLongDecoderExtentReplayingDecoder, MyLongToByteDecoder> {

    public CombinationCodec() {
        super(new MyByteToLongDecoderExtentReplayingDecoder(), new MyLongToByteDecoder());
    }
}