window.onload = function () {
    if (document.getElementById("linkTipuj").getAttribute("disabled") === "disabled") {
        document.getElementById("linkTipuj").href = "#";
        let button = document.getElementById("tipujButton");
        button.setAttribute("disabled", "disabled");
        button.classList.remove("hover:bg-green-800");
        button.classList.remove("focus:bg-green-800");
        button.classList.remove("active:bg-green-800");
    }

}