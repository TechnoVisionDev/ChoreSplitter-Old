// Create websocket for ServerEndpoint '/message'
var wsUrl;
if (window.location.protocol == 'http:') { wsUrl = 'ws://'; }
else { wsUrl = 'wss://'; }
var socket = new WebSocket(wsUrl + window.location.host + "/ChoreSplitter/message");

/**
 * Recieves messages from ServerEndpoint and adds them frontend chatbox.
 */
socket.onmessage = function(event) {
	// Parse JSON data
	var data = JSON.parse(event.data);
	
	// Create message interface with HTML
	var mainDiv = document.createElement("div");
	mainDiv.setAttribute("id", "chat-message");
	
	var avatar = document.createElement("img");
	avatar.setAttribute("id", "chat-avatar");
	avatar.setAttribute("src", data.avatar); // Change This!
	mainDiv.appendChild(avatar);
	
	var innerDiv = document.createElement("div");
	innerDiv.setAttribute("id", "msg-text");
	mainDiv.appendChild(innerDiv);
	
	var textName = document.createElement("p");
	textName.setAttribute("id", "chat-name");
	textName.setAttribute("class", "no-margin-p");
	textName.appendChild(document.createTextNode(data.name)); // Change This!
	innerDiv.appendChild(textName);
	
	var textContent = document.createElement("p");
	textContent.setAttribute("id", "msg-content");
	textContent.setAttribute("class", "no-margin-p");
	textContent.appendChild(document.createTextNode(data.msg)); // Change This!
	innerDiv.appendChild(textContent);
	
	// Append message to chatbox
	var mySpan = document.getElementById("chat");
	mySpan.appendChild(mainDiv);
};

/**
 * Socket throws an error while receiving messages.
 */
socket.onerror = function(event) {
	console.log("Chat Error ", event)
};

/**
 * Executes when 'enter' button is clicked to send message to group chat.
 * Parses data into a JSON string and sends to server.
 */
function sendMsg() {
	var msg = document.getElementById("msg").value;
	if (msg) {
		var name = document.querySelector('meta[name=name]').content;
		var avatar = document.querySelector('meta[name=avatar]').content;
		var data = {name: name, avatar: avatar, msg: msg}
		socket.send(JSON.stringify(data));
    }
	document.getElementById("msg").value = "";
}