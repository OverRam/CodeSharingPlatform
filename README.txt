The application allows you to add code via a website or via endpoints and read them.
If we want to download a code that does not exist, we will receive the HTTP 404 code. 
If the data to be added to the database is incorrect, the HTTP 400 code will be returned.
Correctly entered data will return HTTP code 200.


Addresses in the browser:
http://localhost:8889/code/new            -- adding a new code to the database. Fields time and views are optional.
http://localhost:8889/code/latest         --displaying the last 10 results without additional restrictions
http://localhost:8889/code/{id}           --displaying the code with the given id


Endpoints API:

Adding new code to the database
POST http://localhost:8889/api/code/new
Fields time and views are optional.
data format:
{
     "code": "public static void...",
     "time": 0,
     "views": 3
}

A value of 0 or negative values means no limit.

Retrieval of the last 10 codes without additional restrictions
GET http://localhost:8889/api/code/latest

data format:

[
     {
         "code": "public static void...",
         "date": "2022-12-23 17:53:15",
         "time": 0,
         "views": 0
     },
     {
         "code": "public static void...",
         "date": "2022-12-23 17:53:14",
         "time": 0,
         "views": 0
     }
]

Downloading the code with the specified ID in the UUID format

GET http://localhost:8889/api/code/{id}

ex: http://localhost:8889/api/code/ea70858d-020e-49e9-9f41-b96dad9220ed
