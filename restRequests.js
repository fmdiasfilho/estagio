function getFirstMessage() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:9999/chat/", true);
    xhttp.send();
    document.getElementById("output").innerHTML = xhttp.responseText;
}

function getResponse(responsetext){
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:999/chat/log", true);
}