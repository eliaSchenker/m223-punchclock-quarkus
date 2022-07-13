
//Ändern zu localhost, falls in Entwicklung
const API_URL = "http://localhost:8080/";

/**
 * @param method    Die Request-Methode (e.g. GET, POST, PUT, ...)
 * @param url       Die Sub-URL der API (e.g. login.php)
 * @param data      Daten, welche in der Request mitgeschickt werden sollten.
 * @param onSuccess Methode, welche bei erfolgreicher Ausführung aufgerufen wird.
 * @param onFail    Methode, welche bei nicht erfolgreicher Ausführung aufgerufen wird.
 */
function makeRequest(method, url, data, onSuccess, onFail) {
   $.ajax({
     method:method,
     url: API_URL + url,
     data: data
   }).done(function(data) {
     if(onSuccess != undefined) {
      onSuccess(data);
     }
   }).fail(function(data) {
     if(data.status == 403) {
       location.href = "login.html";
     }else {
       if(onFail != undefined) {
        onFail(JSON.parse(data.responseText), data.status);
       }
     }
   });
}