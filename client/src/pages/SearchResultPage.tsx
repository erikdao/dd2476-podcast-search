import React, { useEffect, useState, Fragment } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';

import { LoadingIndicator, PageTitle, QueryTypeSelect, SearchBox, SearchResultItem } from '../components';
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

  const array = Array.from(Array(15).keys());
  console.log(array);
  const handleSearchSubmit = (q: string): void => {
    setQuery(q);
  }

  useEffect(() => {
    console.log('query', query);
  }, [query]);

  return (
    <>
      <PageTitle title={`Search results for ${query}`} />
      <div className="min-h-screen bg-white flex flex-col overflow-x-hidden overflow-y-auto">
        <header className="w-full p-4 flex border-b flex-0 bg-white fixed">
          {/* Logo */}
          <Link to="/" className="block w-1/4 flex items-center content-center">
            <SpotifyLogo className="w-8 h-8 text-spotify-green" />
            <h1 className="text-lg sm:text-xl md:text-2xl lg:text-2xl ml-2 font-bold leading-tight text-spotify-green">Podcast Search</h1>
          </Link>
          <div className="w-3/4 flex items-center content-center">
            <SearchBox query={query || ""} inputClassName="ring ring-1 ring-gray-300" onSubmit={handleSearchSubmit} />
            <div className="ml-4 w-1/4">
              <QueryTypeSelect value={type} onChange={setType} />
            </div>
          </div>
        </header>

        <div className="w-full p-2 bg-white flex flex-1 overflow-hidden flex-grow-0 mt-14">
          <main className="flex-0 overflow-y-auto focus:outline-none w-1/2">
            {/* Start main area*/}
            <ul className="inset-0 py-6 px-4 sm:px-6 lg:px-8 space-y-3">
              {/* Search result item */}
              {array.map((n: number) => (
                <li key={n}>
                  <SearchResultItem />
                </li>
              ))}
            </ul>
            {/* End main area */}
          </main>
          <aside className="relative xl:order-last xl:flex xl:flex-col flex-1 flex-shrink-0">
            {/* Start secondary column (hidden on smaller screens) */}
            <div className="inset-0 py-6 px-4 sm:px-6 lg:px-8">
              <div className="border border-gray-200 border-dashed rounded-lg" />
            </div>
            {/* End secondary column */}
          </aside>
        </div>
      </div>
    </>
  )
}

export default SearchResultPage;
