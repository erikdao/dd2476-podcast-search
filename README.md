# Group 18 - Spotify Podcast Search

Group project for DD2476 - Search Engines and Information Retrieval @ KTH Royal Institute of Technology

Contributors:
- Caroline Larsen
- Cuong Duc Dao
- Kalle Petterson
- William Lindblom
- Xitao Mo

## Prerequisite
The Spotify data used in the application is stored on Google cloud while the application is run locally and using ssh port forwarding for data retrieval.</br>
**If you are a TA please contact cuongdd@kth.se in order to grant access to the data.** 
<br/><br/>
# Getting started
## Step 1. Connect to the remote service in shell
Please open three independent terminal windows and run the following commands in order to forward the ports 5432, 9200 and 5601 to the Postgres database, ElasticSearch and Kibana services respectively.</br>
Run:
```
ssh -L 5432:localhost:5432 cuong@podcast-server
ssh -L 9200:localhost:9200 cuong@podcast-server
ssh -L 5601:localhost:5601 cuong@podcast-server
```
The application is using Java with gradle as a build tool on the backend and Node.js on the frontend.

## Step 2. Install and run the application
The backend and the frontend runs as two standalone applications. 
### Backend
From the root directory of the project run: 
```
cd backend && ./run.sh
```
### Frontend
From the root directory of the project run: 
```
cd client/ && npm install && npm start
```
The frontend should open a web browser window and show the user interface, if thats not the case, fire up your favourite browser and navigate to http://localhost:3000/

## Project structure

_Note: The structure is pretty much a ongoing work, subjected to changes as the project progresses._

- [`data`](./data): this directory contain all the data needed for this project. Due to the huge size of dataset we have for this project, only a subset of data is pushed to GitHub. For more details on how to structure the dataset on your machine, see the instructions inside this directory.
- [`docs`](./docs): contains necessary information to setup, install and run the search engine, backend, frontend, etc. for this project
- [`analysis`](./analysis): contains exploratory analysis of the dataset.

