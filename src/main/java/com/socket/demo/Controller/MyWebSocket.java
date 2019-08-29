package com.socket.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.demo.Service.Convetutil;
import com.socket.demo.po.Constant;
import com.socket.demo.po.Message;
import com.socket.demo.po.SocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{id}/{name}")
@Component
public class MyWebSocket {
    //    @Autowired
//    private ConfigBeanProp configBeanProp;
    //存放数据库消息字段
    private Message dbmessage = new Message();
    //消息队列
    private ConcurrentLinkedQueue<String> linkedList = new ConcurrentLinkedQueue<>();
    private String name;
    private String id;
    //logback对象
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    //存放私发离线消息
    private static Map<String, ConcurrentLinkedQueue<String>> offmsgdl = new Hashtable<String, ConcurrentLinkedQueue<String>>();

    //    // 存放群发离线消息
//    private static ConcurrentLinkedQueue<String> offqfmsg=new ConcurrentLinkedQueue<String>();
    public static Map<String, ArrayList> getMap() {
        return map;
    }

    public static void setMap(Map<String, ArrayList> map) {
        MyWebSocket.map = map;
    }

    // 放在线用户信息
    private static Map<String, ArrayList> map = new Hashtable<String, ArrayList>();

    public static Map<String, ArrayList> getOffmap() {
        return offmap;
    }

    public static void setOffmap(Map<String, ArrayList> offmap) {
        MyWebSocket.offmap = offmap;
    }

    // 放离线用户信息
    private static Map<String, ArrayList> offmap = new Hashtable<String, ArrayList>();
    // 用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private ArrayList arrayList = new ArrayList();

    //连接服务器成功调用此方法
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("id") String id) {
        String totalmsg = new String();
        this.session = session;
        this.name = name;
        this.id = id;
        arrayList.add(name);
        arrayList.add(session);
        arrayList.add(session.getId());

        //判断登入用户是否在离线表中
        if (offmap.get(id) != null) {
            offmap.remove(id);
        }
        //将用户加入在线表
        map.put(id, arrayList);
        //     Session rfroms=(Session) map.get(id).get(1);


        if (offmsgdl.get(id) != null) {//如果该用户存在离线消息

            for (String msggg : offmsgdl.get(id)) {
                totalmsg += msggg + "<br/>";
                offmsgdl.get(id).poll();
            }

            session.getAsyncRemote().sendText(totalmsg);

        }

        webSocketSet.add(this);// 加入set中
        broadcast("恭喜" + name + "成功连接服务器" + ")-->当前在线人数为：" + webSocketSet.size() + Constant.getSystxt());
    }

    //连接关闭调用此方法
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        map.remove(this.id);// 从map中删除
        offmap.put(this.id, arrayList);// 加入离线表
        broadcast(name + "退出聊天服务器" + ")-->当前在线人数为：" + webSocketSet.size() + Constant.getSystxt());
    }

    //接收到客户端消息调用此方法
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("name") String nickname) {
        // 客户端传过来json数据，这里使用jackson转换为SocketMsg对象，
        // 通过socketMsg的type的值判断是私聊还是群聊消息
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            // 私聊
            if (socketMsg.getType() == 1) {
                socketMsg.setFromUser(this.id);
                // 发送者
                Session fromSession = null;
                if (map.get(socketMsg.getFromUser()).get(1) != null) {
                    //获取发送者session
                    fromSession = (Session) map.get(socketMsg.getFromUser()).get(1);
                }
                // 接收者
                Session toSession;
                // 如果接收者在线
                if (map.get(socketMsg.getToUser()) != null) {
                    toSession = (Session) map.get(socketMsg.getToUser()).get(1);
                    // 如果发送者的频道和接收者的频道一样
                    if (toSession.getId() == fromSession.getId()) {
                        return;
                    }
                } else if (offmap.get(socketMsg.getToUser()) != null) { //接受者不在线
                    touseroff(nickname, socketMsg, fromSession);
                    return;
                } else {// 不存在接受者
                    toSession = null;
                }

                if (toSession != null) {
                    sochat(nickname, socketMsg, fromSession, toSession);
                } else {
                    // 发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：此用户不存在" + Constant.getSystxt());
                }
            } else {
                // 群发消息
                broadcast(nickname + ": " + socketMsg.getMsg() + Constant.getQftxt());
                if (!offmap.isEmpty()) {
                    //遍历离线用户
                    for (Map.Entry<String, ArrayList> entry : offmap.entrySet()) {
                        linkedList.offer("离线群发消息---" + nickname + ": " + socketMsg.getMsg() + Constant.getLxtt());
                        offmsgdl.put(entry.getKey(), linkedList);

                    }
                }
                // 群发消息处理
                schat(nickname, socketMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("logback info");
            logger.error("logback erro");
            logger.debug("logback debug");
        }


//        //群发消息
//        broadcast(nickname+": "+message);

    }


    //发生错误
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //聊天室群发
    public void broadcast(String message) {
//               for(Map.Entry<String,ArrayList>entry:map.entrySet()){
//
//               }
        for (MyWebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }

    }

    private void touseroff(@PathParam("name") String nickname, SocketMsg socketMsg, Session fromSession) throws IllegalAccessException {
        //存放离线消息并加上离线消息字符串
        String offmsg = "离线私聊消息---[" + nickname + "悄悄对你说：" + socketMsg.getMsg() + "]" + Constant.getLxtt();
        linkedList.offer(offmsg);
        offmsgdl.put(socketMsg.getToUser(), linkedList);
        //发送消息给发送者
        fromSession.getAsyncRemote().sendText("对方已离线，发出离线消息---" + "你悄悄对" + offmap.get(socketMsg.getToUser()).get(0) + "说：" + socketMsg.getMsg() + Constant.getSltxt());
        dbmessage.setMsg("私聊---[" + nickname + "悄悄对" + offmap.get(socketMsg.getToUser()).get(0) + "说：" + socketMsg.getMsg() + "]");
        sdb(socketMsg, offmap);
        return;
    }

    private void sdb(SocketMsg socketMsg, Map<String, ArrayList> offmap) throws IllegalAccessException {
        //存放数据库消息表数据
        dbmessage.setFromid(this.id);
        if (map.get(socketMsg.getFromUser()).get(0) != null) {
            //存放发送者ID
            dbmessage.setFromname((String) map.get(socketMsg.getFromUser()).get(0));
        } else {
            return;
        }
        dbmessage.setToid(socketMsg.getToUser());
        //存放接受者ID
        if (offmap.get(socketMsg.getToUser()).get(0) != null) {
            dbmessage.setToname((String) offmap.get(socketMsg.getToUser()).get(0));
        } else {
            return;
        }
        dbmessage.setSendtype("私聊");
        //存放发送时间
        dbmessage.setSendtime(new Timestamp(System.currentTimeMillis()));
        Convetutil.add(dbmessage);
        return;
    }


    private void sochat(@PathParam("name") String nickname, SocketMsg socketMsg, Session fromSession, Session toSession) throws IllegalAccessException {
        String fromtext = "[" + "你悄悄对" + map.get(socketMsg.getToUser()).get(0) + "说：" + socketMsg.getMsg() + "]" + Constant.getSltxt();
        String totext = "[" + nickname + "悄悄对你说：" + socketMsg.getMsg() + "]" + Constant.getSltxt();
        //存放数据库消息数据
        String dbfromtext = "私聊---[" + nickname + "悄悄对" + map.get(socketMsg.getToUser()).get(0) + "说：" + socketMsg.getMsg() + "]";

        // 发送给接受者.
        fromSession.getAsyncRemote().sendText(fromtext);

        // 发送给发送者.
        toSession.getAsyncRemote().sendText(totext);

        dbmessage.setMsg(dbfromtext);
        sdb(socketMsg, map);
        return;
    }

    private void schat(@PathParam("name") String nickname, SocketMsg socketMsg) throws IllegalAccessException {
        dbmessage.setMsg("群发---" + nickname + ": " + socketMsg.getMsg());
        dbmessage.setFromid(this.id);
        //给消息对象发送者赋值ID
        socketMsg.setFromUser(this.id);
        if (map.get(socketMsg.getFromUser()).get(0) != null) {
            //给数据库数据赋值发送者用户名
            dbmessage.setFromname((String) map.get(socketMsg.getFromUser()).get(0));
        } else {
            return;
        }
        dbmessage.setSendtype("群发");
        dbmessage.setToid("无");
        //给数据库数据赋值接受者用户名
        dbmessage.setToname("无");
        dbmessage.setSendtime(new Timestamp(System.currentTimeMillis()));
        //添加数据到数据库中
        Convetutil.add(dbmessage);
        return;
    }

}
