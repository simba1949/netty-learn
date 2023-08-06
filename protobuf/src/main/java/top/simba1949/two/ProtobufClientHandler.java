package top.simba1949.two;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

/**
 * @author anthony
 * @version 2023/8/6 16:12
 */
public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 当通道就绪就会触发该方法
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送一个 Student 对象到服务器
		int random = new Random().nextInt(3);
		MyDataInfo.MyMessage myMessage = null;

		if (random % 2 == 0) {
			myMessage = MyDataInfo.MyMessage.newBuilder()
					.setDataType(MyDataInfo.MyMessage.DataType.StudentType)
					.setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("杜甫").build())
					.build();
		} else {
			myMessage = MyDataInfo.MyMessage.newBuilder()
					.setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
					.setWorker(MyDataInfo.Worker.newBuilder().setName("高适").setAge(18).build())
					.build();
		}

		ctx.channel().writeAndFlush(myMessage);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
