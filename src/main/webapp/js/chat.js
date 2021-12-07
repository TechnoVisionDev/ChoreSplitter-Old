var wsUrl;
if (window.location.protocol == 'http:') { wsUrl = 'ws://'; }
else { wsUrl = 'wss://'; }

var socket = new WebSocket(wsUrl + window.location.host + "/ChoreSplitter/message");
        
socket.onmessage = function(event) {
	var mySpan = document.getElementById("chat");
    mySpan.innerHTML += event.data;
};
     
socket.onerror = function(event) {
	console.log("Chat Error ", event)
} 

function sendMsg() {
	var msg = document.getElementById("msg").value;
	if (msg) {
		var name = document.querySelector('meta[name=name]').content;
		var avatar = document.querySelector('meta[name=avatar]').content;
		socket.send("<div id='chat-message'><img id='chat-avatar' src='" + avatar + "'> " +
		"<div id='msg-text'><p id='chat-name'>" + name + "</p><p id='msg-content'>" + msg + "</p></div></div>");
    }
	document.getElementById("msg").value = "";
}