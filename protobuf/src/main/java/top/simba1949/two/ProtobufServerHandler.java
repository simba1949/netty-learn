package top.simba1949.two;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author anthony
 * @version 2023/8/6 16:11
 */
public class ProtobufServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
		MyDataInfo.MyMessage.DataType dataType = msg.getDataType();

		switch (dataType) {
			case StudentType: {
				MyDataInfo.Student student = msg.getStudent();
				System.out.println("学生信息" + student);
				break;
			}
			case WorkerType: {
				MyDataInfo.Worker worker = msg.getWorker();
				System.out.println("工作者信息" + worker);
				break;
			}
			default: {
				System.out.println("未知的传输类型");
			}
		}
	}
}