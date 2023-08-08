package top.simba1949.rpc.provider.api.impl;

import top.simba1949.rpc.api.HelloService;

/**
 * @author anthony
 * @version 2023/8/8 21:25
 */
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String msg) {
		System.out.println("接收到客户端消息" + msg);

		if (null != msg) {
			return "你好，客户端！我已经收到你的消息(" + msg + ")";
		} else {
			return "你好，客户端！我已经收到你的消息";
		}
	}
}
