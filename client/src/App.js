import React, { useState } from 'react'
import Searchbar from './components/Searchbar';
import Header from './components/Header'
import './styles/css/styles.css';
import SearchResultItem from './components/SearchResultItem';
import ProgressBar from './components/ProgressBar';

const App = () => {

  const [searchResults, setSearchResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const onSubmit = query => {
    // query is the string typed in the search bar.
    // TODO: fetch the right enpoint 
    setIsLoading(true);
    fetch('https://jsonplaceholder.typicode.com/users')
      .then(response => response.json())
      .then(data => {
        setSearchResults(data);
        setIsLoading(false);
      })
      .catch(err => {
        console.error('Error in fetch app.js');
        console.error(err);
        setIsLoading(false);
      });
  };

  return (
    <div className="App">
      <div className="container">
        <Header/>
        <Searchbar onSubmit={onSubmit}/>
        <div className="search-result-container">
          {isLoading ? <ProgressBar/> :
          searchResults.map((searchRes, idx) => <SearchResultItem key={`${idx}`} title={searchRes.name} description={searchRes.company.catchPhrase}/>)}
        </div>
      </div>
    </div>
  );
}

export default App;
