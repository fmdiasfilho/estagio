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

  window.onload = function(event){
        var req = createRequest(); // defined above
        // Create the callback:
        req.open("GET", GET_URL, true);
        req.send();
        req.onreadystatechange = function() {
          if (req.readyState != 4) return; // Not there yet
          if (req.status != 200) {
            // Handle request failure here...
            return;
          }
          // Request successful, read the response
          var resp = req.responseText;
          document.getElementById("output").innerHTML = resp;
          event.preventDefault();
        }  // ... and use it as needed by your app.
  }

  function XMLHttpPostRequest(){
    var text = document.getElementById("input").value;
    var req = createRequest(); // defined above
    // Create the callback:
    req.open("POST",POST_URL, true);
    req.setRequestHeader("Content-Type",
                     "application/json");
    req.send(JSON.parse(text));
    req.onreadystatechange = function() {
      if (req.readyState != 4) return; // Not there yet
      if (req.status != 200) {
        // Handle request failure here...
        return;
      }
      // Request successful, read the response
      var resp = req.responseText;
      // ... and use it as needed by your app.
    }
  }


/*function getFirstMessage() {
    xhttp.open("GET", "http://localhost:9999/", true);
    xhttp.send();
        if (xhttp.status == 200) {
        document.getElementById("output").innerHTML = xhttp.response;
        }
  }
  
function postQuestion(){
    let text = document.getElementById("input").value;
    xhttp.open("POST", "http://localhost:9999/conversation", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.parse(text));
    if (xhttp.status == 200) {
        alert(xhttp.responseText);
        }
   
}
*/

