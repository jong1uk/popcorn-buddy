"use strict";

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.querySelector(".modal");
    const modalOpen = document.querySelector(".open_btn");
    const modalClose = document.querySelector(".close_btn");

    modalOpen.addEventListener("click", function () {
        modal.classList.add("on");
    });

    modalClose.addEventListener("click", function () {
        modal.classList.remove("on");
    });

    window.addEventListener("click", (e) => {
        e.target === modal ? modal.classList.remove("on") : false;
    });
});
