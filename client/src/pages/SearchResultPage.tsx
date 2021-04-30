import React, { useEffect, useState, Fragment } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';

import { LoadingIndicator, PageTitle, QueryTypeSelect, SearchBox } from '../components';
import { SpotifyLogo } from '../components/SpotifyLogo';
import { ESearchType } from '../types';

function getQueryFromUrl(url: string): string | null {
  const urlParams = new URLSearchParams(url);
  return urlParams.get('query');
}

function SearchResultPage() {
  const location = useLocation();
  const [query, setQuery] = useState(getQueryFromUrl(location.search))
  const [type, setType] = useState<ESearchType>(ESearchType.PHRASE);

  useEffect(() => {
    console.log('query', query);
  }, [query]);

  return (
    <>
      <PageTitle title={`Search results for ${query}`} />
      <div className="min-h-screen bg-white">
        <header className="w-full p-4 flex border-b">
          {/* Logo */}
          <Link to="/" className="block w-1/4 flex items-start content-center">
            <SpotifyLogo className="w-8 h-8 text-spotify-green" />
            <h1 className="text-lg sm:text-xl md:text-2xl lg:text-2xl ml-2 font-bold leading-tight text-spotify-green">Podcast Search</h1>
          </Link>
          <div className="w-3/4 flex items-center content-center">
            <SearchBox query={query || ""} inputClassName="ring ring-1 ring-gray-300" />
            <div className="ml-4 w-1/4">
              <QueryTypeSelect value={type} onChange={setType} />
            </div>
          </div>
        </header>
      </div>
    </>
  )
}

export default SearchResultPage;
