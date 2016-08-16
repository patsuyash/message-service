Use advanced rest client **/message.arc**

**OR**
use following on cmd line/bash

curl localhost:8080/message -u dom:parker

curl  -H "Content-Type: application/json" -X POST -d '{ "id":"C","name":"sss", "description":"some description"}' localhost:8080/message -u dom:parker

curl localhost:8080/message/C -u suyash:patil
