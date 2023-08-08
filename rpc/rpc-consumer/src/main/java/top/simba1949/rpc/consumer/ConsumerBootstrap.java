package top.simba1949.rpc.consumer;

import top.simba1949.rpc.api.HelloService;
import top.simba1949.rpc.api.Protocol;
import top.simba1949.rpc.consumer.netty.NettyClient;

/**
 * @author anthony
 * @version 2023/8/8 21:29
 */
public class ConsumerBootstrap {

	public static void main(String[] args) {
		// 创建一个消费者
		NettyClient consumer = new NettyClient();
		// 创建代理对象
		HelloService helloService = (HelloService) consumer.getBean(HelloService.class, Protocol.NAME);
		// 通过代理对象代用远程服务提供者的服务
		String result = helloService.hello("World");
		System.out.println("从服务端返回的结果是：" + result);
	}
}
