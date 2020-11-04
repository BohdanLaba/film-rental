####Prerequisites:
1. prepare Mongo Db:
 - have it locally set up;
 - run  {path to mongo binaries}/mongorestore --db=filmretaldb filmrentaldb.json --drop

####Execute:
1. launch  ./gradlew bootRun
2. Use http://localhost:8080/swagger-ui as reference point for API

Create your own user, or use given John Doe

Notes on implementation:
1) see ParserFacede class for second part of the task
2) further improvements, that should be done:
- divide FilmService to a FilmService, that will serve simple crud operations & 
FilmRentService, that will provide 'rent' & 'return' methods 
- add validation - e.g. 'minus' days in (FilmRentRequest)
- add basic authorization & divide methods as public (e.g. get all films) & private(e.g. create customer)
- move bonus points from constants to application.yaml
- add tests
- add 'quantity' parameter to film