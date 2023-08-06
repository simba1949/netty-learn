package top.simba1949.spau.solve;

/**
 * @author anthony
 * @version 2023/8/6 22:49
 */
public class MessageProtocol {
	// 长度
	private int len;
	// 内容
	private byte[] content;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}