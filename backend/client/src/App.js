import React, { useState, useMemo } from 'react'
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import './styles/css/styles.css';
import LandingPage from './pages/LandingPage';
import EpisodePage from './pages/EpisodePage';
import { SearchResultContext } from './SearchResultContext';

const App = () => {
  const [searchResults, setSearchResults] = useState([]);
  const value = useMemo(() => ({ searchResults, setSearchResults }), [searchResults, setSearchResults]);

  return (
    <div className="App">
      <Router>
        <SearchResultContext.Provider value={value}>
          <Switch>
              <Route path="/episode" component={EpisodePage} />
              <Route component={LandingPage}/>
          </Switch>
        </SearchResultContext.Provider>
      </Router>
    </div>
  );
}

export default App;
