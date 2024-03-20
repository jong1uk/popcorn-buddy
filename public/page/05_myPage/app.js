const SERVER_IP = "127.0.0.1";
const SERVER_PORT = 9000;
const server_address = `ws://${SERVER_IP}:${SERVER_PORT}`; // ws://127.0.0.1:9000

const socket = new WebSocket(server_address);

socket.opopen = function (e) {
    const log_msg = "[open] 연결이 설정되었습니다.";

    displayMessage("#messages", log_msg);
};

socket.onclose = function (e) {
    let log_msg = "";
    if (e.wasClean)
        log_msg = `[close] 연결이 정상적으로 종료되었습니다. 코드=${e.code}, 원인=${e.reason}`;
    else
        log_msg = `[close] 연결이 비정상적으로 종료되었습니다.. 코드=${e.code}, 원인=${e.reason}`;

    displayMessage("#messages", log_msg);
};

socket.onerror = function (error) {
    const log_msg = `[error] ${error.message}`;

    displayMessage("#messages", log_msg);
};

socket.onmessage = function (e) {
    const log_msg = `[message] 서버로부터 데이터 수신 : ${e.data}`;

    displayMessage("#messages", log_msg);

    displayPacketMessage("#messages", e.data);
};

const sendMessage = function (message) {
    socket.send(message); // 서버로 전송

    const log_msg = `클라이언트 => 서버 ${message}`;
    displayMessage("#messages", log_msg);
};

// 이벤트 로그 출력
const displayMessage = function ($parentSelector, log_msg, kind_log = 0) {
    if (kind_log === 0 || kind_log === 2) console.log(log_msg);
    if (kind_log === 1 || kind_log === 2) {
        // 이 요소 아래에 메시지 요소를 추가
        const parentElem = document.querySelector($parentSelector);

        const childElem = document.createElement("div");
        childElem.textContent = log_msg;
        parentElem.appendChild(childElem);
    }
};

// 통신 패킷 출력
const displayPacketMessage = function ($parentSelector, message) {
    // 이 요소 아래에 메시지 요소를 추가
    const parentElem = document.querySelector($parentSelector);

    // json문자열 -> js 객체로 변환
    const msgObj = JSON.parse(message);

    let msg = "";
    switch (msgObj.cmd) {
        case "connect":
            msg = msgObj.result;
            break;
        case "login":
            msg = msgObj.result === "ok" ? "로그인 성공" : "로그인 실패";
            break;
        case "allchat":
            if ("result" in msgObj)
                msg =
                    msgObj.result === "ok"
                        ? "채팅 전송 성공"
                        : "채팅 전송 실패";
            // else if ('id' in msgObj)
            //  msg = `${msgObj.id} => ${msgObj.msg}`;
            msg = `id아직  ${msgObj.msg}`;
            break;
        case "movieSearch":
            if ("result" in msgObj)
                msg =
                    msgObj.result === "ok"
                        ? "movie title전송 성공"
                        : "movietitle 전송 실패";
            else if ("movieTitle" in msgObj) msg = `${msgObj.movieSearch}`;
            break;
    }
    const childElem = document.createElement("div");
    childElem.classList.add("chatmsg");
    childElem.textContent = msg;
    parentElem.appendChild(childElem);

    // 새로운 메시지를 맨 위에 추가
    // parentElem.insertBefore(childElem, parentElem.firstChild);
    // parentElem.insertBefore(childElem, parentElem.firstChild);
};
// 로그인 정보 저장
function saveLoginInfo(token) {
    sessionStorage.setItem("authToken", token);
}

// 로그인 정보 확인
function isLoggedIn() {
    return sessionStorage.getItem("authToken") !== null;
}

// 로그아웃
function logout() {
    sessionStorage.removeItem("authToken");
}

// 세션 만료 처리
function checkSessionTimeout() {
    var lastActivityTime = sessionStorage.getItem("lastActivityTime");
    var currentTime = new Date().getTime();
    var sessionTimeout = 30 * 60 * 1000; // 30분

    if (currentTime - lastActivityTime > sessionTimeout) {
        logout(); // 세션 만료되면 로그아웃
    } else {
        sessionStorage.setItem("lastActivityTime", currentTime); // 세션 유지될 경우 마지막 활동 시간 갱신
    }
}

// 페이지 로드 시 세션 체크
window.onload = function () {
    if (isLoggedIn()) {
        // 사용자가 로그인한 경우
        checkSessionTimeout();
    } else {
        // 사용자가 로그인하지 않은 경우
        // 로그인 페이지로 이동하거나 다른 작업을 수행
    }
};
