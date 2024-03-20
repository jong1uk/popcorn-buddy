package main.java.com.popcornbuddy.server;



import java.net.InetSocketAddress;

import java.net.URLEncoder;
import main.java.com.popcornbuddy.controller.LogInController;
import main.java.com.popcornbuddy.controller.signUpController;
import main.java.com.popcornbuddy.dao.DBConnection;
import main.java.com.popcornbuddy.dao.UserDao;
import main.java.com.popcornbuddy.domain.User;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;
import main.java.com.popcornbuddy.api.ApiExplorer;

public class SimpleWebSocketServer extends WebSocketServer {
    LogInController lc = new LogInController();

    UserDao userDao;
    ApiExplorer api = new ApiExplorer();
    String id = "";
    String name = "";
    String email = "";
    String pwd = "";
    String pwdCheck = "";
    String msg = "";
    JSONObject ackObj = null;


    public static void main(String[] args) {
        String host = "127.0.0.1";        // localhost
        final int PORT = 9000;

        WebSocketServer server = new SimpleWebSocketServer(new InetSocketAddress(host, PORT));
        server.run();
    }

    public SimpleWebSocketServer(InetSocketAddress inetAddr) {
        super(inetAddr);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has diconnected");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println(ex.getMessage());
        //ex.getStackTrace();
    }

    private boolean loginCheck(String id, String pwd) {
        User user = null;

        try {
            user = userDao.selectUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return user != null && user.getPwd().equals(pwd);
    }

//    void login(String id, String pass, WebSocket conn) {
//        if (!loginCheck(id, pass)) {
//            String msg = "id 또는 pwd가 일치하지 않습니다.";
//            JSONObject ackObj = new JSONObject();
//            ackObj.put("result", msg);
//
//            conn.send(ackObj.toString()); // 클라이언트에게 return
//        }
//    }

    private void checkCredentials(String id, String pwd) throws Exception {
        // 여기에 데이터베이스에서 ID와 비밀번호를 검증하는 코드를 작성합니다.
        User user = userDao.selectUser(id);
        if (user.getId().equals(id) && user.getPwd().equals(pwd)) {
//            String token = generateToken(id);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        System.out.println("Message from client: " + message);


        // json문자열 -> json객체
        JSONObject msgObj = new JSONObject(message);
        String cmd = msgObj.getString("cmd");        // 패킷 종류를 구분할 수 있는 명령어를 제일 먼저 추출

        switch (cmd) {
            case "login":
                id = msgObj.getString("id");
                pwd = msgObj.getString("pwd");
                System.out.printf("로그인 id: %s   pwd: %s\n", id, pwd);

                /* DB에서 해당 id, pass가 있는 지 확인하는 작업 추가
                 * */

                try {
                    if(lc.authenticateUser(id,pwd)){
                        ackObj = new JSONObject();
                        ackObj.put("cmd", "login");
                        ackObj.put("result", "ok");
                        ackObj.put("id", id);
                        conn.send(ackObj.toString());
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

//                login(id, pwd, conn);

            case "signup":
                id = msgObj.getString("id");
                name = msgObj.getString("name");
                email = msgObj.getString("email");
                pwd = msgObj.getString("pwd");
                pwdCheck = msgObj.getString("pwdCheck");

                signUpController.signUp(id, name, email, pwd, pwdCheck);

                    ackObj = new JSONObject();
                    ackObj.put("cmd", "signup");
                    ackObj.put("result", "ok");
                    conn.send(ackObj.toString());
                }
        }

        @Override
        public void onOpen (WebSocket conn, ClientHandshake handshake){
            String hostIp = conn.getRemoteSocketAddress().getAddress().getHostAddress()
                .toString();
            System.out.println(hostIp + " connected");

            ackObj = new JSONObject();
            ackObj.put("cmd", "connect");
            ackObj.put("result", "Welcome to the Server!");
            conn.send(ackObj.toString());   // 클라이언트한테 메시지 보내기
        }

        @Override
        public void onStart () {
            System.out.println("Server started successfully!!!");
        }


}