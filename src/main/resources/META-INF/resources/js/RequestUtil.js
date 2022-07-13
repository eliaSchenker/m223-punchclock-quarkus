
//Ändern zu localhost, falls in Entwicklung
const API_URL = "http://localhost:8080/";

let login_token;

/**
 * @param method    Die Request-Methode (e.g. GET, POST, PUT, ...)
 * @param url       Die Sub-URL der API (e.g. login.php)
 * @param data      Daten, welche in der Request mitgeschickt werden sollten.
 * @param onSuccess Methode, welche bei erfolgreicher Ausführung aufgerufen wird.
 * @param onFail    Methode, welche bei nicht erfolgreicher Ausführung aufgerufen wird.
 */
function makeRequest(method, url, data, dataType, onSuccess, onFail) {
  if(login_token == undefined && localStorage.getItem("token") != undefined) {
    login_token = localStorage.getItem("token");
  }
   $.ajax({
     method:method,
     url: API_URL + url,
     data: JSON.stringify(data),
     dataType: dataType,
     processData: false,
     contentType: "application/json",
     beforeSend: function(request) {
      request.setRequestHeader("Authorization", "Bearer" + login_token);
     },
   }).done(function(data) {
     if(onSuccess != undefined) {
      onSuccess(data);
     }
   }).fail(function(data) {
     if(data.status == 401 && !location.href.includes("login.html")) {
       location.href = "login.html";
     }else {
       if(onFail != undefined) {
        onFail(dataType == "json" ? JSON.parse(data.responseText) : data.responseText, data.status);
       }
     }
   });
}

/**
 * Serializes a form to an object using the existing jquery method serializeObject
 * @author Elia Schenker
 * @param {*} e Form Event Data
 * @returns Object containing the data
 */
function serializeObject(e) {
  let data = e.serializeArray();
  let result = {};
  data.forEach((element) => {
    result[element.name] = element.value;
  });
  return result;
}