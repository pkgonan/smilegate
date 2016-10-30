function doInsert() {
    $.ajax("http://localhost:8080/api/insertRankingData", {
        type: "POST",
        data: $("#insertData").serialize(),
        success: function(response) {
        }
    })
}

function doResister() {
    $.ajax("http://localhost:8080/api/registerFriend", {
        type: "POST",
        data: $("#registerFriend").serialize(),
        success: function(response) {
        }
    })
}

function doDelete() {
    $.ajax("http://localhost:8080/api/deleteFriend", {
        type: "POST",
        data: $("#deleteFriend").serialize(),
        success: function(response) {
        }
    })
}

function doPrintAllRanking() {
    $.ajax("http://localhost:8080/api/getAllRanking", {
        type: "POST",
        data: $("").serialize(),
        success: function(response) {
            $('#stage').html(response);
        }
    })
}

function doPrintMyRanking() {
    $.ajax("http://localhost:8080/api/getMyRanking", {
        type: "POST",
        data: $("#myRankingId").serialize(),
        success: function(response) {
            $('#stage').html(response);
        }
    })
}

function doPrintFriendRanking() {
    $.ajax("http://localhost:8080/api/getFriendRanking", {
        type: "POST",
        data: $("#friendRankingId").serialize(),
        success: function(response) {
            $('#stage').html(response);
        }
    })
}

function doPrintFriendList() {
    $.ajax("http://localhost:8080/api/getFriendList", {
        type: "POST",
        data: $("#friendListId").serialize(),
        success: function(response) {
            $('#stage').html(response);
        }
    })
}