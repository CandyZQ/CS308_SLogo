# API Changes

#### Group Members
- Ryan Mecca (rm358)
- Cady Zhou (zz160)
- Ameer Syedibrahim (as877)
- Sarah Gregorich (seg47)

### Frontend Internal

### Frontend External

### Backend Internal
We kept 

### Backend External 
Backend External API has not been changed too much. We have only added a method ```runScript``` 
to the API. The change is made to read in commands from an external file and save user commands to 
an external file for future use. We did not have this method initially because the functionality 
was not required in Basic. We also agreed that the frontend should not perform file I/O, so we 
implemented the functionality in the controller. As a result, an extra method is needed to pass the 
filename read in the frontend to the backend. 
