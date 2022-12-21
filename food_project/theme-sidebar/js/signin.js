$(document).ready(function(){
    $("#btn-signin".click(function(){
        var data ={
            "username": $("#email").val(),
        "password": $("#password").val()
        }
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/signin",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
          })
            .done(function( msg ) {
              alert( "Data Saved: " + msg );
            });
    }))

})