package top.simba1949.json;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author anthony
 * @version 2023/8/16
 */
public class JsonMsgEncoder extends MessageToMessageEncoder<JsonMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, JsonMsg msg, List<Object> out) throws Exception {
        String json = JSONObject.toJSONString(msg);
        System.out.println("将数据进行JSON编码=" + json);
        out.add(json);
    }
}