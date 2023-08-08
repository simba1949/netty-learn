package top.simba1949.rpc.provider;

import top.simba1949.rpc.provider.netty.NettyServer;

/**
 * @author anthony
 * @version 2023/8/8 21:42
 */
public class ProviderBootstrap {
	public static void main(String[] args) {
		NettyServer.startServer("127.0.0.1", 7777);
	}
}
