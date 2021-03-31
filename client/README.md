# Front end

## How to run?
Make sure that you have node and npm installed.

In the client directory, run:

### `npm install`
to install packages
and then
### `npm start`
to run the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

## Structure
* Components
    * Header.js - Component for the Spotify banner
    * ProgressBar.js - Loading animation to be displayed when fetching data.
    * SearchBar.js - Where the query is entered. Sends query to parent (App.js) on submit via props.
    * SearchResultItem.js - Component for displaying one search result.
* styles/css
    * style.css - Contains all styles for the entire UI.
* App.js - Driver for the front end. This is where the fetching and displaying results is done. 