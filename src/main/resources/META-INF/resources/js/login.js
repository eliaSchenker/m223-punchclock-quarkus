$("#loginForm").submit(function(e) {
    e.preventDefault();
    let data = serializeObject($(this));
    console.log(data);
    makeRequest("POST", "auth/login", data, "text", function(result) {
        localStorage.setItem("token", result);
        location.href = "index.html";
    }, function(error) {
        $("#error").text("Invalid credentials");
    });
});