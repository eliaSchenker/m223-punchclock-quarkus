$("#registerForm").submit(function(e) {
    e.preventDefault();
    console.log(typeof this);
    //let data = serializeObject(this);
    /*makeRequest("POST", "auth/register", data, function(result) {
        location.href = "login.html";
    }, function(error) {
        $("#error").text(error);
    });*/
});