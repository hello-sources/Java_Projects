package com.ict.test;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.junit.Test;
//import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ServerCreationFailureJobTest {

    private String charset = Charset.defaultCharset().toString();
    private static final int TIME_OUT = 1000 * 5 * 60;

    /**
     * 使用JSch来连接云主机，进行相应的操作
     */
    @Test
    public void test1() {
        JSch jsch = new JSch();
        com.jcraft.jsch.Session session  = null;
        try {
            //1.创建session
            session = JschUtil.createSession(jsch, "192.168.29.xxx", Integer.parseInt("22"), "root");
            //密码登录
            session.setPassword("root");
            //3.连接
            session.connect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //exec()可以通过outputStream拿到执行shell失败时的返回提示
            String execResult = JschUtil.exec(session, "sinfo", CharsetUtil.CHARSET_UTF_8, outputStream);
            //执行失败时execResult返回null
            if (StrUtil.isEmpty(execResult)){
                //4.打印错误日志
                System.out.println("err:{}"+ outputStream.toString("UTF-8"));
            }else {
                System.out.println("success:{}" + execResult);
            }
        } catch (Exception e) {
            //设置执行失败的响应内容
            System.out.println("发送请求异常" + e);
        } finally {
            JschUtil.close(session);
        }
        return;
    }


    @Test
    public void test2() throws JSchException, IOException {
        String host = "192.168.29.xxx";
        int port = 22;
        String username = "root";
        String password = "root";
        // 创建JSch
        JSch jSch = new JSch();
        // 获取session
        com.jcraft.jsch.Session session = jSch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        // 启动连接
        session.connect();
        ChannelExec exec = (ChannelExec)session.openChannel("exec");
        exec.setCommand("cd /home/xxx/xxxxxx/xxxxxxx && bash hostname.sh");
        exec.setInputStream(null);
        exec.setErrStream(System.err);
        exec.connect();

        // 消费所有输入流，必须在exec之后
        String inStr = consumeInputStream(exec.getInputStream());
        String errStr = consumeInputStream(exec.getErrStream());

        exec.disconnect();
        session.disconnect();

    }


    @Test
    public void test3()  {

        execCommand("sudo adduser testuser -p testuser");
        execCommand("passwd testuser");

        execCommand("useradd -p `openssl passwd -1 testuser` jinlu");
        execCommand("cd /root && python3 generate-jwt-token.py testuser 999999999");
        execCommand("cd /etc/slurm && python testjwt.sh");
        execCommand("export PATH=/szcs/software/slurm/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin && scontrol show jobs 369");
        execCommand("scontrol show jobs 369");
    }

    public void execCommand(String command) {
        Connection connection = new Connection("192.168.29.xxx");
        Session session = null;
        try {
            connection.connect();
            System.out.println("---------------------开始连接------------------------");
            boolean connectResult = connection.authenticateWithPassword("root","root");

            if(!connectResult){
                System.out.println("---------连接失败----------");
                return;
            }

            session = connection.openSession();
            // 1.创建用户
            session.execCommand(command);
            //接收目标服务器上的控制台返回结果，读取br中的内容；
            InputStream stdOut = new StreamGobbler(session.getStdout());
            getStrByInputStream(session,stdOut);

            //接收目标服务器上的控制台返回结果，读取br中的内容；
            InputStream stdErrOut = new StreamGobbler(session.getStderr());
            getStrByInputStream(session,stdErrOut);

            //当超过 TIME_OUT 中的时候的时候session自动关闭
            session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);

            //得到脚本运行成功与否的标志 ：0－成功 非0－失败
            int ret = session.getExitStatus();
            System.out.println(ret);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (session != null) {
                session.close();
            }
            connection.close();
        }
    }

    public String getStrByInputStream(Session session ,InputStream inputStream){


        System.out.println("stdOut:"+inputStream.toString());

        StringBuffer stringBuffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        int result = -1;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String str = null;
        try {
            while ((result = inputStream.read(bytes, 0, bytes.length)) != -1){
                byteArrayOutputStream.write(bytes,0,result);
            }
            str = new String(byteArrayOutputStream.toByteArray(),charset);

            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("获取linux的标准输出的结果-----" + str);

        return str;
    }

    /**
     *   消费inputstream，并返回
     */
    public static String consumeInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s ;
        StringBuilder sb = new StringBuilder();
        while((s=br.readLine())!=null){
            System.out.println(s);
            sb.append(s);
        }
        return sb.toString();
    }

}