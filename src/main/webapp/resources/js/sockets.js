var webSocket =
    new WebSocket('ws://' + document.location.host + document.location.pathname.replace(/[^/]*$/, '') + 'websocket');

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
    
    document.getElementById('messages').innerHTML
        += '<br />' + obj.username + ': ' + obj.text ;

    var element = document.getElementById("messages");
    element.scrollTop = element.scrollHeight;
}

function onOpen(event) {
}

function onError(event) {
    alert(event.data);
}

function start() {
    return false;
}

function waitForSocketConnection(socket, callback){
    setTimeout(
        function(){
            if (socket.readyState === 1) {
                if(callback !== undefined){
                    callback();
                }
                return;
            } else {
                waitForSocketConnection(socket,callback);
            }
        }
        , 5);
};

function sendMessage(){
    var text = document.getElementById("messageinput").value;

    webSocket.send(text);

}

$(document).ready(setInterval(onOpen(), 1000));