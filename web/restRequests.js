const GET_URL = "http://localhost:9999/";
const POST_URL = "http://localhost:9999/conversation"; 

function createRequest() {
    var result = null;
    if (window.XMLHttpRequest) {
      // FireFox, Safari, etc.
      result = new XMLHttpRequest();
      if (typeof result.overrideMimeType != 'undefined') {
        result.overrideMimeType('text/xml'); // Or anything else
      }
    }
    else if (window.ActiveXObject) {
      // MSIE
      result = new ActiveXObject("Microsoft.XMLHTTP");
    } 
    else {
      // No known mechanism -- consider aborting the application
    }
    return result;
  }

  window.onload = function(){
        var req = createRequest(); 
        req.open("GET", GET_URL, true);
        req.send();
        req.onreadystatechange = function() {
          if (req.readyState != 4) return;
          if (req.status != 200) {
            return;
          }
          var resp = req.responseText;
         generateDiv(resp,true);
        }
  }

  function XMLPostRequest(){
    var text = document.getElementById("input").value;
    generateDiv(text, false);
    document.getElementById("input").value ='';
    var req = createRequest();
    req.open("POST",POST_URL, true);
    req.setRequestHeader("Content-Type",
                 "text/plain");
    var str = String(text);
    req.send(str);
    req.onreadystatechange = function() {
    if (req.readyState != 4) return;
    if (req.status != 200) {
    return;
    }
    var resp = req.responseText;
    //document.getElementById("response").innerHTML = resp;
    generateDiv(resp, true);
  }
}

function generateDiv(resp, isBot){
  var iDiv = document.createElement('div');
  var para = document.createElement("p");
  para.className = 'chat-message';
  var node = document.createTextNode(resp);
  var element = document.getElementById("logs");
  if(isBot == true){
    iDiv.className = 'chat bot';
    iDiv.id = 'bot';
  }else{
    iDiv.className = 'chat self';
    iDiv.id = 'self';
    var order = document.createElement('div');
    order.className = 'blank';
    iDiv.appendChild(order);
  }
 
  para.appendChild(node);
  iDiv.appendChild(para);
  element.appendChild(iDiv);
}
