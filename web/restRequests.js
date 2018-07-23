//Rest Server URL
const SERVER_URL = "http://localhost:9999/";

/**
 * Creates a XMLHttpResquest and returns it
 */
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

/**
 * On load function. This code executes when the HTML page is opened 
 */
window.onload = function () {
  var req = createRequest();
  //Rest server GET method request
  req.open("GET", SERVER_URL, true);
  req.send();
  req.onreadystatechange = function () {
    if (req.readyState != 4) return;
    if (req.status != 200) {
      return;
    }
    var resp = req.responseText;
    generateDiv(resp, true);
  }
}

/**
 * Creates a POST request to the Rest Server
 */
function XMLPostRequest() {
  //Get input text
  var text = document.getElementById("input").value;
  if (text === '') {
    alert('Empty message!');
  } else {
    generateDiv(text, false);
    document.getElementById("input").value = '';
    var req = createRequest();
    req.open("POST", SERVER_URL + 'conversation', true);
    req.setRequestHeader("Content-Type",
      "text/plain");
    var str = String(text);
    req.send(str);
    req.onreadystatechange = function () {
      if (req.readyState != 4) return;
      if (req.status != 200) {
        return;
      }
      var resp = req.responseText;
      generateDiv(resp, true);
    }
  }
}

/**
 * Creates the chat bubble in web page
 * @param {String} resp 
 * @param {Boolean} isBot 
 */
function generateDiv(resp, isBot) {
  var iDiv = document.createElement('div');
  var para = document.createElement("p");
  para.className = 'chat-message';
  var node = document.createTextNode(resp);
  var element = document.getElementById("logs");
  if (isBot == true) {
    iDiv.className = 'chat bot';
    iDiv.id = 'bot';
  } else {
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
