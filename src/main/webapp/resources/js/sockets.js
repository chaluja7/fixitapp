var currentHost = document.location.host;

if(currentHost.indexOf('localhost') == -1 && currentHost.indexOf('127.0.0.1') == -1) {
    currentHost = currentHost + ':8000';
}

var webSocket = new WebSocket('ws://' + currentHost + document.location.pathname.replace(/[^/]*$/, '') + 'messagewebsocket');

webSocket.onerror = function(event) {
    onError(event)
};

webSocket.onopen = function(event) {
    onOpen(event)
};

webSocket.onmessage = function(event) {
    onMessage(event)
};

function onMessage(event) {
    var obj = JSON.parse(event.data);
    var chat = document.getElementById('messages');
    
    // adds message to chat window
    chat.innerHTML += '<br />['+obj.time + '] ';
    
    if(obj.type == 'CONNECTED'){
        chat.innerHTML += 'Zalogoval se uživatel ' + obj.username;
    }else if(obj.type == 'DICONNECTED'){
        chat.innerHTML += 'Odlogoval se uživatel ' + obj.username;
    }else if(obj.type == 'MESSAGE'){
        chat.innerHTML += '<b>' + obj.username + ':</b> ' + obj.text;
    }else if(obj.type == 'LOGGED_USERS'){
        chat.innerHTML += 'Zalogování jsou: ' + obj.text;
    }else{
        chat.innerHTML += 'Odlogoval se uživatel ' + obj.username;
    }

    // scrolls down
    chat.scrollTop = chat.scrollHeight;

    // clears text input with sent message
    var messageInput = document.getElementById("messageinput");
    messageInput.value = "";
}

function onOpen(event) {
}

function onError(event) {
    alert(event.data);
}

function start() {
    return false;
}

function sendMessage(){
    var text = document.getElementById("messageinput").value;
    
    if(text != "") {
        webSocket.send(text);
    }
}

$(document).ready(setInterval(onOpen(), 1000));

$(document).ready(function() {
    $("#messageinput").keyup(function(e) {
        if (e.keyCode == 13) {
            sendMessage();
        }
    });
});