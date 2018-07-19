# Weather Chatbot

## What is it?

A simple weather chat bot that give the Lisbon weather to a user.

## Architecture 

![image1](https://github.com/fmdiasfilho/estagio/blob/master/docs/images/1.jpg?raw=true)
 
The basic architecture is composed by two components:

-	**Web Browser:** This component uses a HTML (web page) and a JavaScript file. The HTML is a simple GUI and the JavaScript is used to manage the HTML’s elements functionalities.

-	**Chatbot:** Components is represented by five sub components (explained above).
 ![image2](https://github.com/fmdiasfilho/estagio/blob/master/docs/images/2.jpg?raw=true)

	The **Chatbot** components are:

-	**Rest Server:** This component is a Java Rest Server that can be called as the Brain of the operation. Besides being a Rest server and handle the http requests, this component’s role is insert the updated database information, into the chat-bot “knowledge”, to create the most accurate response.

- **Brain:** This component is used to update the weather information from the database to the Chatbot dialog nodes.

-	**IBM Watson:** The server used API. The requests are made by HTTP and everything can be changed online (service workspace) or by consuming the API. This API was chosen because of his powerful computing, because it is very easy to use it and because it is almost full customizable by using the API. The chat bot is represented by dialog nodes that recognize some specific expressions.

-	**MongoDB:** This component represents the weather information database. All the weather data from the requests to the weather API are saved into this database. It is saved the current, yesterday and today until the current weather information.

-	**Apixu Server:** The server that has the weather information. This server’s API was chosen because it is free and has a simple response.

## Workflow
### There are three workflows:
#### Weather information update:
![image3](https://github.com/fmdiasfilho/estagio/blob/master/docs/images/3.jpg?raw=true)

The steps are:

**1.**	There is initialized threads to create http requests to the weather server. It will be updated the current time, yesterday and today until the current time weather.

**2.**	The response comes in JSON format. When we got it, we transform into an Object using GSON and then it will be saved into the MongoDB database
Only the database update is executed by threads. The chatbot’s dialog update is manual.

#### Update the chatbot dialog nodes: 
When we prefer, we manually update the dialog nodes with the current database weather information. 

 ![image4](https://github.com/fmdiasfilho/estagio/blob/master/docs/images/4.jpg?raw=true)

The steps of this workflow are:

**1.**	Access the MongoDB database and get the information that we are updating (the update is separated by the current node, yesterday and today nodes).

**2.**	It is used the IBM Watson API to get the dialog node that we want to change.

**3.**	We insert the new information and send it to the server with this information update.

### Handle the user HTTP requests:
![image5](https://github.com/fmdiasfilho/estagio/blob/master/docs/images/5.jpg?raw=true)

When the user wants to know the weather, what happen is:

**1.**	The user inserts a weather message into the GUI.

**2.**	The message is inserted into the web page as a chat bubble (DOM element).

**3.**	This message is sent to the Rest Server by using JavaScript XMLHttpRequest function. This function will POST the user message into the HTTP request body.

**4.**	The Rest Server receives the request and extract the message from it.

**5.**	The Brain creates an HTTP request to the chatbot server (IBM Watson), inserting the message into the request body.

**6.**	The response comes in JSON format and it is parsed to a Java Object (using GSON). The JSON file has all the possible responses order by answer confidence.

**7.**	To send as a response to the web page, the most confident answer is sent as the Rest Server response to the JavaScript request.

**8.**	The JavaScript receives this message and creates a chat bubble (DOM element) with the response received.

## Documentation

All the developed code is commented, so for a good explanation about all the implemented functions, read the code’s comments.

## What is missing?

There are some improvements that can be made to this project:

**1.**	Change the IBM Watson’s API usage from Java to JavaScript. By doing this improvement, the Java Rest server is not more necessary. (Now, the Rest Server is working as a proxy between the web browser and the Watson’s service).

**2.**	Transform the dialog nodes manual update into automatic.

**3.**	Add forecast by using Machine Learning prediction algorithms.

**4.**	Add more cities and countries to the chatbot’s knowledge.

## References:

### Java Dependencies Documentations
- [IBM Watson Assistant](https://www.ibm.com/watson/developercloud/conversation/api/v1/java.html?java)
- [MongoDB](http://mongodb.github.io/mongo-java-driver/3.7/driver/getting-started/quick-start/)
- [GSON](https://github.com/google/gson/blob/master/UserGuide.md)
- [Apixu](https://www.apixu.com/api-explorer.aspx)
- [Jersey](https://jersey.github.io/documentation/latest/index.html)

---------
# Estágio curricular 2017/2018
