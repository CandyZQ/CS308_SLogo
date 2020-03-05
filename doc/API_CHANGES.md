# API Changes

#### Group Members
- Ryan Mecca (rm358)
- Cady Zhou (zz160)
- Ameer Syedibrahim (as877)
- Sarah Gregorich (seg47)

### Frontend Internal

### Frontend External
The Frontend External API has been slightly changed to reflect a better communication scheme with the Backend External. Upon completing the front end functionality for allowing the user to select a language from the dropdown menu, it is then necessary to send this information to the backend for parsing. Thus, we have the getLanguage() method which is passed to the Parser in controller. The exceptionHandling method is also part of the External API which is called in the controller class.

### Backend Internal
We kept all 

### Backend External g
Backend External API has not been changed too much. We have only added a method ```runScript``` to the API. The change is made to read in commands from an external file and save user commands to an external file for future use. We did not have this method initially because the functionality was not required in Basic. We also agreed that the frontend should not perform file I/O, so we implemented the functionality in the controller. As a result, an extra method is needed to pass the filename read in the frontend to the backend. We also added ```getUserVars``` and ```getFunctions``` methods 
