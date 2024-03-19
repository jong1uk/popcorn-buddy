"use strict";

document.addEventListener("DOMContentLoaded", function () {
    const addButton = document.querySelector(".add_btn");

    addButton.addEventListener("click", function () {
        toggleHiddenRoom();
    });
});

// function createNewRoom() {
//     // 새로운 방을 만드는 로직을 구현합니다.
//     // 여기에 원하는 동작을 추가하세요.

//     // 숨겨진 div를 찾습니다.
//     const hiddenDiv = document.querySelector(".hidden-room");

//     // 숨겨진 div를 보이도록 변경합니다.
//     hiddenDiv.style.display = "block";
// }

function toggleHiddenRoom() {
    // hidden-room 영역을 찾습니다.
    const hiddenRoom = document.querySelector(".hidden-room");

    // hidden-room의 현재 display 상태를 가져옵니다.
    const currentDisplay = window
        .getComputedStyle(hiddenRoom)
        .getPropertyValue("display");

    // display 상태에 따라 toggle합니다.
    if (currentDisplay === "none") {
        hiddenRoom.style.display = "block"; // 보이도록 설정
    } else {
        hiddenRoom.style.display = "none"; // 숨기도록 설정
    }
}
