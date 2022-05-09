var stompClient = null;
var user  = null;

function setConnected(connected) {
    $("#disconnect").prop("disabled", !connected);
    $("#name").prop("disabled", connected).val("");
    $("#password").prop("disabled", connected).val("");
    $("#sendLog").prop("disabled", connected);
    $("#sendReg").prop("disabled", connected);
    $("#send").prop("disabled", !connected);
    $("#msgText").prop("disabled", !connected).val("");
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        getHistory();
        console.log('Connected: ' + frame);
        stompClient.subscribe('/chat/messages', function (message) {
            showGreeting(JSON.parse(message.body));
        });
    });

}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    user = null;
    $('#status').text("disconnected");
}

function login() {
    var name = $(" #name ").val();
    var password = $(" #password ").val();
    var data = {
        'name': name,
        'password': password
    }

    console.log(JSON.stringify(data))
    $.ajax({
        type: "POST",
        url: "api/users/login",
        dataType: 'json',
        data: JSON.stringify(data),
        success: function (res) {
            console.log(res);
            user = res;
            console.log(user.id)
            console.log(user.name)
            console.log(user.password)
            $('#status').text(user.name);
            connect();
            $(" #name ").val("");
            $(" #password ").val("");
        },
        headers: {
            'Content-Type' : 'application/json'
        },

    });
}

function register() {
    var name = $(" #name ").val();
    var password = $(" #password ").val();
    var data = {
        'name': name,
        'password': password
    }
    console.log(JSON.stringify(data))
    $.ajax({
        type: "POST",
        url: "api/users/register",
        dataType: 'json',
        data: JSON.stringify(data),
        success: function (res) {
            console.log(res);
            $(" #name ").val("");
            $(" #password ").val("");
        },
        headers: {
            'Content-Type' : 'application/json'
        },

    });
}

function getHistory() {
    $.ajax({
        type: "GET",
        url: "api/messages",
        success: function (res) {

            console.log("History: ")
            console.log(res)
            for (var i = 0; i < res.length; i++) {
                console.log(res[i]);
                $("#greetings").append("<tr><td>" + res[i].id + ".    " + res[i].senderName +": " + res[i].content + "</td></tr>");
            }
        },
        headers: {
            'Content-Type' : 'application/json'
        },
    });
}

function sendMessage() {
    stompClient.send("/app/message", {}, JSON.stringify({
        'senderName': user.name,
        'content': $("#msgText").val()
    }));
    $("#msgText").val("");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.id + ".    " + message.senderName +": " + message.content + "</td></tr>");
}

$(function () {
    setConnected(false);
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
    $( "#sendReg" ).click(function() { register(); });
    $( "#sendLog" ).click(function() { login(); });
});
