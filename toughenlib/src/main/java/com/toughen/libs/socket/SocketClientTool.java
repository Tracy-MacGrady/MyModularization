package com.toughen.libs.socket;

import com.toughen.libs.libtools.FastJsonUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author 麦迪
 * @socket客户端
 */
public class SocketClientTool {
    private static Socket socket;
    private static InetSocketAddress address;// Tcp
    private static boolean is_connected;
    private static DatagramSocket udpSocket;// Udp
    private static SocketCallBack sockerCallBack;
    private static Class type;

    /**
     * 建立socket的链接
     *
     * @param host     主机
     * @param port     端口号
     * @param callback 回掉接口
     * @param ty       服务端的数据 需要转换类型
     * @return
     */
    public static void socketClient(String host, int port,
                                    SocketCallBack callback, Class<?> ty) {
        sockerCallBack = callback;
        type = ty;
        socket = new Socket();
        address = new InetSocketAddress(host, port);
        new Thread(new ConnectToServer()).start();
    }

    /**
     * 自动链接到服务端
     *
     * @param callback
     * @param ty
     */
    public static void socketClient(SocketCallBack callback, Class<?> ty) {
        try {
            udpSocket = new DatagramSocket();
            new Thread(new ConnectToServerByUdp()).start();
            new Thread(new UDP_ReciverData(callback, ty)).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author 麦迪
     * @通过UDP协议向服务端发送数据（目的是为了获取服务端的ip地址等信息）
     */
    static class ConnectToServerByUdp implements Runnable {
        @Override
        public void run() {
            try {
                while (udpSocket != null && !udpSocket.isClosed()) {
                    byte[] data = {(byte) 0xff, (byte) 0x00, (byte) 0x00};
                    DatagramPacket packet = new DatagramPacket(data,
                            data.length,
                            InetAddress.getByName("255.255.255.255"), 44444);
                    udpSocket.send(packet);
                    Thread.sleep(1000);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author 麦迪
     * @接收从服务端传递回来的ip地址的消息，并发送tcp链接
     */
    static class UDP_ReciverData implements Runnable {
        private SocketCallBack cal;
        private Class type;

        public UDP_ReciverData(SocketCallBack callback, Class ty) {
            this.cal = callback;
            this.type = ty;
        }

        @Override
        public void run() {
            try {
                byte[] bytes = new byte[25];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                udpSocket.receive(packet);
                String[] ip_port = new String(bytes, 0, bytes.length).trim()
                        .split(":");
                socketClient(ip_port[0], Integer.parseInt(ip_port[1]), cal,
                        type);
                closeUDP_Socket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author 麦迪
     * @链接到服务端
     */
    static class ConnectToServer implements Runnable {
        @Override
        public void run() {
            try {
                socket.connect(address, 3000);
                socket.setKeepAlive(true);
                socket.setSoTimeout(5000);
                new Thread(new ReceiverServerData()).start();// 开启接收服务端数据的线程
                new Thread(new SendTestIS_Connect()).start();// 开启发送心跳包的线程
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @author 麦迪
     * @发送心跳包的线程
     */
    static class SendTestIS_Connect implements Runnable {
        @Override
        public void run() {
            try {
                if (socket != null) {
                    while (socket.isConnected()) {
                        sendMessage("r");
                        Thread.sleep(5000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author 麦迪
     * @监听服务端的消息
     */
    static class ReceiverServerData implements Runnable {

        @Override
        public void run() {
            try {
                while (socket.isConnected()) {
                    try {
                        Object o = getMessage(type);
                        if (o != null) {
                            sockerCallBack.ReceiverServerData_callBack(o);
                        }
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param obj
     * @发送数据到服务端
     */
    public static void sendMessage(Object obj) {
        try {
            if (socket != null) {
                if (socket.isConnected()) {
                    String printval = obj.toString();
                    if (!obj.equals("r")) {
                        printval = FastJsonUtil.Object2JsonString(obj);
                    }
                    OutputStreamWriter writer = new OutputStreamWriter(
                            socket.getOutputStream(), "UTF-8");
                    BufferedWriter bufferWriter = new BufferedWriter(writer);
                    bufferWriter.write(printval);
                    bufferWriter.newLine();
                    bufferWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 获取到服务端传回的数据并转换成特定的类型的object
     * @throws IOException
     */
    public static <T> T getMessage(Class<T> tClass) throws IOException {
        T returnObj = null;
        InputStream inputstream = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String val = reader.readLine();
            if (val.equals("r")) {
                return null;
            }
            returnObj = FastJsonUtil.JsonStr2Object(val, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return returnObj;
    }

    /**
     * @关闭socket链接
     */
    public static void closeSeocket() {
        closeTCP_Socket();
        closeUDP_Socket();
    }

    /**
     * 关闭tcp_socke连接
     */
    private static void closeTCP_Socket() {
        try {
            if (socket != null) {
                if (socket.isConnected()) {
                    socket.close();
                    socket = null;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 关闭udp_socke连接
     */
    private static void closeUDP_Socket() {
        if (udpSocket != null) {
            if (!udpSocket.isClosed()) {
                udpSocket.close();
                udpSocket = null;
            }
        }
    }
}
