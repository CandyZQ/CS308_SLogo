# Refactoring Discussion 
#### Group Members
- Ryan Mecca (rm358)
- Cady Zhou (zz160)
- Ameer Syedibrahim (as877)
- Sarah Gregorich (seg47)

#### Most important issues
- Longest Method refactoring: The longest method was in SubSceneLeft, a class in the frontend. We looked over the code but could not think of a good way to reduce the size except taking out part of the code and make another private method for it. Since the length of this method is acceptable, we kept it untouched for now. 
- Duplication refactoring: We didn't have duplicated code issue in our code.
- Checklist Refactoring: We made some changes based on the warnings given by the design checkup so that the code fulfills the checklist. We removed a lot of magical numbers that was originally in the code during rapid development. There was also the problem that we modified exception type after catching it as pointed out by the design check tool. We also tried to fix those problems. 
- Others: The frontend was trying to refactor the code so that it is in a better structure. Backend was mainly focusing on resolve complicated design logic and "hide" some public classes/methods from the rest of the project.   