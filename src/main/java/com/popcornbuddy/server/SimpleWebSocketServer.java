package main.java.com.popcornbuddy.server;


import static main.java.com.popcornbuddy.utils.JwtUtil.generateToken;

import java.net.InetSocketAddress;

import java.net.URLEncoder;
import main.java.com.popcornbuddy.dao.UserDao;
import main.java.com.popcornbuddy.domain.User;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;
import main.java.com.popcornbuddy.api.ApiExplorer;

public class SimpleWebSocketServer extends WebSocketServer{
	UserDao userDao;
	ApiExplorer api = new ApiExplorer();

	public static void main(String[] args) {
		String host = "127.0.0.1";		// localhost
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

		return user!=null && user.getPwd().equals(pwd);
//        return "asdf".equals(id) && "1234".equals(pwd);
	}
	void login(String id, String pass, WebSocket conn) {
		if (!loginCheck(id, pass)) {
			String msg = "id 또는 pwd가 일치하지 않습니다.";
			JSONObject ackObj = new JSONObject();
			ackObj.put("result", msg);

			conn.send(ackObj.toString()); // 클라이언트에게 return
		}

	}
	private void checkCredentials(String id, String pwd) throws Exception {
		// 여기에 데이터베이스에서 ID와 비밀번호를 검증하는 코드를 작성합니다.
		User user = userDao.selectUser(id);
		if(user.getId().equals(id) && user.getPwd().equals(pwd)) {
			String token = generateToken(id);
		}
	}
	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("Message from client: " + message);

		// json문자열 -> json객체
		JSONObject msgObj = new JSONObject(message);
		String cmd = msgObj.getString("cmd");		// 패킷 종류를 구분할 수 있는 명령어를 제일 먼저 추출


		if(cmd.equals("login")) {
			String id = msgObj.getString("id");
			String pass = msgObj.getString("pass");
			System.out.printf("로그인 id: %s   pass: %s\n", id, pass);

			/* DB에서 해당 id, pass가 있는 지 확인하는 작업 추가
			 * */
			login(id, pass, conn);

			JSONObject ackObj = new JSONObject();
			ackObj.put("cmd", "login");
			ackObj.put("result", "ok");
			conn.send(ackObj.toString());   // json문자열로 변환되어서 클라이언트한테 전송됨
		} else if(cmd.equals("signup")){
			String name = msgObj.getString("name");
			String email = msgObj.getString("email");
			String pass = msgObj.getString("pass");
			System.out.printf("회원가입 닉네임: %s, 이메일: %s   pass: %s\n", name , email, pass);

			/* DB에서 해당 id, pass가 있는 지 확인하는 작업 추가
			 * */



			JSONObject ackObj = new JSONObject();
			ackObj.put("cmd", "login");
			ackObj.put("result", "ok");
			conn.send(ackObj.toString());
		} else if(cmd.equals("allchat")) {
			String id = msgObj.getString("id");
			String msg = msgObj.getString("msg");
			System.out.printf("채팅 id: %s   msg: %s\n", id, msg);

			// 클라이언트한테 응답 전송
			JSONObject ackObj = new JSONObject();
			ackObj.put("cmd", "allchat");
			ackObj.put("result", "ok");
			conn.send(ackObj.toString());

			// 전체 접속자한테 브로드캐스팅
			for(WebSocket con : this.getConnections()) {
				if(conn != con)			// 나를 제외한 모든 접속자한테 전송
					con.send(message);
			}
		}else if(cmd.equals("movieSearch")){
			String movieTitle = msgObj.getString("movieTitle");
			System.out.printf("영화 제목: %s", movieTitle);
			try {
				api.movieSearch();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			JSONObject ackObj = new JSONObject();
			ackObj.put("cmd", "movieSearch");
			ackObj.put("result", "ok");
			conn.send(ackObj.toString());
		}
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		String hostIp = conn.getRemoteSocketAddress().getAddress().getHostAddress().toString();
		System.out.println(hostIp + " connected");

		JSONObject ackObj = new JSONObject();
		ackObj.put("cmd", "connect");
		ackObj.put("result", "Welcome to the Server!");
		conn.send(ackObj.toString());   // 클라이언트한테 메시지 보내기
	}

	@Override
	public void onStart() {
		System.out.println("Server started successfully!!!");
	}

}