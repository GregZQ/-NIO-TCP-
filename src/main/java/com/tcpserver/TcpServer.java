package com.tcpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/*
*   TCP服务端
* */
public class TcpServer{
  private static final int BUFSIZE = 256;

  //端口号
  private static final Integer PORT=8888;
  //IP地址
  private static final String HOST="192.168.1.120";//127.0.0.1
  public static void main(String[] args) throws IOException {
    Selector selector = Selector.open();
    ServerSocketChannel listnChannel = ServerSocketChannel.open();
    listnChannel.socket().bind(new InetSocketAddress(HOST, PORT));
    listnChannel.configureBlocking(false);
    listnChannel.register(selector, SelectionKey.OP_ACCEPT);
    TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);
    System.out.println("服务器已启动。。。。");
    while (true){

      selector.select();//这地方不是阻塞，是不断轮询

      Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

      while (keyIter.hasNext()){
        SelectionKey key = keyIter.next(); 
        if (key.isAcceptable()){
          protocol.handleAccept(key);
        }
        if (key.isReadable()){
          protocol.handleRead(key);
        }
        if (key.isValid() && key.isWritable()) {
          protocol.handleWrite(key);
        }
        keyIter.remove();
      }
    }
  }
}