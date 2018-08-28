package czy.socket.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @auther 陈郑游
 * @时间 2017/5/14 0014
 * @功能
 * @问题
 * @博客地址：http://blog.csdn.net/javawebrookie
 * @GitHub地址：https://github.com/AndyCZY
 * @GitBook地址：https://www.gitbook.com/@chenzhengyou
 */
public class EchoClient {

    public static void main(String[] args) throws Exception {
        Socket client = null;
        client = new Socket("localhost", 8888);
        BufferedReader buf = null;
        PrintStream out = null;
        BufferedReader input = null;

        input = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintStream(client.getOutputStream());
        buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;

        while (flag) {
            System.out.print("输入信息：");
            String str = input.readLine();
            out.println(str);
            if ("bye".equals(str)) {
                flag = false;
            } else {
                String echo = buf.readLine();
                System.out.println(echo);
            }
        }
        // 关闭资源
        client.close();
        buf.close();
    }
}
