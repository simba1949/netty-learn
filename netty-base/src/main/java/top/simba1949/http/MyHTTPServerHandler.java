package top.simba1949.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * HttpObject 表示客户端和服务器端相互通信的数据被封装成 HttpObject
 *
 * @author anthony
 * @version 2023/8/2 22:33
 */
public class MyHTTPServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	/**
	 * 读取客户端数据
	 *
	 * @param channelHandlerContext
	 * @param httpObject
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
		// 判断请求是否是 HttpRequest
		if (httpObject instanceof HttpRequest) {
			System.out.println("httpObject 类型是：" + httpObject.getClass());
			System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());

			HttpRequest httpRequest = (HttpRequest) httpObject;
			String uri = httpRequest.uri();
			if ("/favicon.ico".equals(uri)) {
				System.out.println("请求了 favicon.ico 资源，不做处理");
				return;
			}

			// 恢复消息给浏览器
			ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,我是服务器", StandardCharsets.UTF_8);
			// 构建 http 响应
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
			response.headers()
					.set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8")
					.set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

			// 返回
			channelHandlerContext.writeAndFlush(response);
		}
	}
}
