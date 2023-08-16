package top.simba1949.json;


import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class JsonMsgDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        JsonMsg jsonMsg = JSONObject.parseObject(msg, JsonMsg.class);
        System.out.println("解码器解析数据是：" + jsonMsg.toString());
        out.add(jsonMsg);
    }
}