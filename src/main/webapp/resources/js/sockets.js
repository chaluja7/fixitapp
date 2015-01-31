var webSocket =
    new WebSocket('ws://' + document.location.host + '/fixapp/websocket');
//new WebSocket('ws://' + document.location.host + document.location.pathname.replace(/[^/]*$/, '') + 'websocket');

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
    document.getElementById('messages').innerHTML
        += '<br />' + event.data;
}

function onOpen(event) {
    /*document.getElementById('messages').innerHTML
        = 'Connection established';*/
}

function onError(event) {
    alert(event.data);
}

function start() {
    //waitForSocketConnection(webSocket, function() {
    //    webSocket.send('hello');
    //});

    //webSocket.send('hello');
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

function foo(){
    var text = document.getElementById("messageinput").value;

    webSocket.send(text);

}

$(document).ready(setInterval(onOpen(), 1000));