package com.toughen.libs.socket;


import com.toughen.libs.libtools.FastJsonUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author 李健健
 * @socket服务端
 */
public class SocketServerTool {
    private static ArrayList<ClientInfo> client_list = new ArrayList<ClientInfo>();
    private static ServerSocket serverSocket;
    private static DatagramSocket udpSocket;
    private static String m_ip;// 服务端ip
    private static boolean isServerStart = false;
    private static boolean canGetAddress_UDP = false;

    /**
     * @author 李健健
     * @连接的客户端的信息类
     */
    private static class ClientInfo {
        private int id;
        private Socket socket;
        private String ip;
        private String name = "a";

        public ClientInfo(int id, Socket soc, InetAddress inetAddress) {
            super();
            this.id = id;
            this.socket = soc;
            this.ip = inetAddress.getHostName();
        }

        public Socket getSocket() {
            return this.socket;
        }

        public int getId() {
            return id;
        }

        public String getIp() {
            return ip;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 设置可以UDP链接
     */
    public static void setCanGetAddr_UDP() {
        canGetAddress_UDP = true;
    }

    /**
     * 设置服务端的ip
     *
     * @param ip
     */
    public static void setIp(String ip) {
        m_ip = ip;
    }

    /**
     * 开启服务端socket
     */
    public static void startServerSocket() {
        stopServerSocket();
        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(null);
            isServerStart = true;
            new Thread(new ClientAccept()).start();
            if (canGetAddress_UDP) {
                if (udpSocket == null) {
                    udpSocket = new DatagramSocket(null);
                    udpSocket.setReuseAddress(true);
                    udpSocket.bind(new InetSocketAddress(44444));
                    new Thread(new UDPReceiverData()).start();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭服务端的socket
     */
    public static void stopServerSocket() {
        try {
            if (isServerStart) {
                closeAllClient();
                if (serverSocket != null) {
                    serverSocket.close();
                    serverSocket = null;
                }
                if (udpSocket != null) {
                    if (!udpSocket.isClosed()) {
                        udpSocket.close();
                        udpSocket = null;
                    }
                }
                isServerStart = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TCP 关闭所有的连接的客户端
     */
    private static void closeAllClient() {
        try {
            if (client_list != null && client_list.size() > 0) {
                for (ClientInfo client : client_list) {
                    if (!client.getSocket().isClosed()) {
                        client.getSocket().close();
                    }
                }
            }
            client_list.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TCP 移除某一连接的客户端
     */
    private static void removeClient(int id) {
        try {
            if (client_list != null && client_list.size() > 0) {
                for (int i = 0; i < client_list.size(); i++) {
                    if (id == client_list.get(i).getId()) {
                        if (client_list.get(i) != null) {
                            if (client_list.get(i).getSocket().isClosed()) {
                                client_list.get(i).getSocket().close();
                            }
                            client_list.remove(i);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 获取随机数 作为
     */
    static int getId() {
        Random random = new Random();
        int id = Math.abs(random.nextInt()) % 999;
        return id;
    }

    ;

    /**
     * TCP
     *
     * @author 李健健
     * @获取连接的客户端
     */
    static class ClientAccept implements Runnable {
        @Override
        public void run() {
            try {
                if (isServerStart) {
                    if (serverSocket != null) {
                        while (true) {
                            Socket socket = serverSocket.accept();
                            ClientInfo clientInfo = new ClientInfo(getId(),
                                    socket, socket.getInetAddress());
                            clientInfo.getSocket().setSoTimeout(5000);
                            client_list.add(clientInfo);
                            new Thread(new ReceiverClientData(
                                    clientInfo.getId())).start();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * UDP
     *
     * @author 李健健
     * @接收来自客户端的数据并将数据返回给客户端
     */
    static class UDPReceiverData implements Runnable {
        @Override
        public void run() {
            try {
                byte[] bytes = new byte[3];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                while (udpSocket != null && !udpSocket.isClosed()) {
                    udpSocket.receive(packet);
                    if ((bytes[0] & 0xff) == 0xff && (bytes[1] & 0xff) == 0x00
                            && (bytes[2] & 0xff) == 0x00) {
                        String address = m_ip + ":"
                                + serverSocket.getLocalPort();
                        byte[] data = address.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(data,
                                data.length, packet.getAddress(),
                                packet.getPort());
                        udpSocket.send(sendPacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TCP
     *
     * @author 李健健
     * @监听从客户端传来的数据
     */
    static class ReceiverClientData implements Runnable {
        private Socket socket;
        private int id;

        public ReceiverClientData(int id) {
            super();
            this.id = id;
            this.socket = getSocketById(id);
        }

        @Override
        public void run() {
            while (socket != null && !socket.isClosed()) {
                try {
                    if (!socket.isClosed()) {
                        DataInputStream input = new DataInputStream(
                                socket.getInputStream());
                        StringBuffer stringBuf = new StringBuffer();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream(),
                                        "UTF-8"));
                        String value;
                        value = reader.readLine();
                        if (value != null) {
                            if (value.contains("flag:login")) {
                                for (ClientInfo cli : client_list) {
                                    if (socket.getInetAddress().toString()
                                            .equals(cli.ip)) {
                                        cli.name = value.split("=")[1];
                                    }
                                }
                                sendDate("flag=" + value.split("=")[1],
                                        FastJsonUtil.Object2JsonString(client_list));
                            } else {
                                sendDate(
                                        value.getClass()
                                                .getDeclaredField("name")
                                                .get(value).toString(), value);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            removeClient(id);
        }
    }

    /**
     * @author 李健健
     * @发送数据到客户端
     */
    static class sendDataToClient implements Runnable {
        private Socket socket;
        private String msg_toClient = "";

        public sendDataToClient(Socket socket, String s) {
            super();
            this.socket = socket;
            this.msg_toClient = s;
        }

        @Override
        public void run() {
            try {
                if (!socket.isClosed()) {
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(),
                                    "UTF-8"));
                    writer.write(msg_toClient);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param id
     * @return 根据id获取到socket实例
     */
    static Socket getSocketById(int id) {
        Socket client = null;
        if (client_list != null && client_list.size() > 0) {
            for (ClientInfo cl : client_list) {
                if (cl.getId() == id) {
                    client = cl.getSocket();
                }
            }
        }
        return client;
    }

    public static void sendDate(String name, String s) {
        Socket socket = null;
        for (ClientInfo clientinfo : client_list) {
            if (clientinfo.name.equals(name.split("="))) {
                socket = clientinfo.getSocket();
            } else if (clientinfo.getName().equals(name)) {
                socket = clientinfo.getSocket();
            }
            if (socket != null) {
                new Thread(new sendDataToClient(socket, s)).start();
            }
        }
    }
}
