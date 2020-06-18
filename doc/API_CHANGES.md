# API Changes

See ```src/APIs```. 

BackEndExternalAPI remains similar to what it was. Two additional methods ```getUserVars()``` and ```getFunctions()``` are added to send frontend the information. The frontend can then put these info on the UI. There is also one additional ```runScript()``` method added to allow scripting, which is a feature of Complete. 

ControllerInternalAPI is an API I wrote when the size of the controller grows bulkier. Classes in ```/operations``` use this API to change states of turtles/perform calculations when they need to.  