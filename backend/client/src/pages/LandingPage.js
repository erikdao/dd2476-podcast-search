import React, { useState, useContext } from 'react'
import Searchbar from '../components/Searchbar';
import Header from '../components/Header'
import SearchResultItem from '../components/SearchResultItem';
import ProgressBar from '../components/ProgressBar';
import { SearchResultContext } from '../SearchResultContext';
import { mockdata } from "../mockdata";

const LandingPage = () => {

//   const [searchResults, setSearchResults] = useState([]);
  const { searchResults, setSearchResults } = useContext(SearchResultContext);
  const [isLoading, setIsLoading] = useState(false);

  const onSubmit = ({query, querytype}) => {
    // query is the string typed in the search bar.
    // TODO: fetch the right enpoint 
    // console.log(searchOptions)
    // const {query, querytype} = searchOptions;
    console.log(query);
    console.log(querytype)
    setIsLoading(true);
    fetch('https://jsonplaceholder.typicode.com/users')
      .then(response => response.json())
      .then(data => {
        // setSearchResults(data);
        setSearchResults(mockdata);
        setIsLoading(false);
      })
      .catch(err => {
        console.error('Error in fetch app.js');
        console.error(err);
        setIsLoading(false);
      });
  };

  return (
    <div className="container">
      <Header/>
      <Searchbar onSubmit={onSubmit}/>
      <div className="search-result-container">
        {isLoading ? <ProgressBar/> :
        searchResults.map((searchRes, idx) => <SearchResultItem key={`${idx}`} episode={searchRes}/>)}
      </div>
    </div>
  );
}

export default LandingPage;
