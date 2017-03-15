<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>spring boot webSocket</title>
</head>
<body>
<div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
    </div>
    </br>
    <div id="conversationDiv">
        <label style="font-size: 14px;">输入你的名字: </label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">发送</button>
        <p id="response"></p>
    </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/sockjs.min.js"></script>
<script type="text/javascript" src="/js/stomp.min.js"></script>
<script>
    var stompClient = null;

    function setConnected(connected) {
        $("#connect").attr("disabled", connected);
        $("#disconnect").attr("disabled", !connected);
        $("#conversationDiv").attr("disabled", connected);
        var visibility = connected ? 'visible' : 'hidden';
        $("#conversationDiv").attr("style", visibility);
        $("#response").text("");
    }

    function connect(){
        var socket = new SockJS('/socket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame){
            setConnected(true);
            stompClient.subscribe('/topic/greetings', function (serverMessage){
                showGreeting(JSON.parse(serverMessage.body).content);
            });

            stompClient.subscribe('/app/macro',function(greeting){
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        alert("Disconnected");
    }

    function sendName() {
        var name = $('#name').val();
        stompClient.send("/app/socket", {}, JSON.stringify({ 'name': name }));//5
    }

    function showResponse(message) {
        var response = $("#response");
        response.html(message);
    }

    function showGreeting(message) {
        $("#response").text(message);
    }
</script>
</body>
</html>