# mobile application web service

A web service for the app 'romstatus'. It's purpose is to send status updates about 
rooms. 

## How it works

Using spring the spring framework to set up server. The 'GET' method, 'getObject()', 
calls another method that returns an xml file that is converted to a java object. 
Then, the object is returned as JSON.

### Converting an xml file to a java object

In the package 'XmlConverter', there is a class named 'RoomList'. The class is 
annotaded as an xml root element, and the list containing the class 'Room' is 
annotaded as an xml element. The class 'XmlToObject' has a method that converts the 
xml file to a 'RoomList' class.

### Sending the list of "Room"s to client

When requested, 'getObject' creates and returns a new instance of 'Rooms', passing 
the list as the parameter