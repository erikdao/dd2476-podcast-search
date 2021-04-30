import React, { useEffect, useState, Fragment } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import EpisodeApiService from '../api/episode';

import { LoadingIndicator, PageTitle, QueryTypeSelect, SearchBox, SearchResultItem } from '../components';
import { SpotifyLogo } from '../components/SpotifyLogo';
import { ESearchType, TEpisodeSearchBody } from '../types';

function getQueryFromUrl(url: string): string | null {
  const urlParams = new URLSearchParams(url);
  return urlParams.get('query');
}

function SearchResultPage() {
  const location = useLocation();
  const [searching, setSearching] = useState(false);
  const [query, setQuery] = useState(getQueryFromUrl(location.search))
  const [type, setType] = useState<ESearchType>(ESearchType.PHRASE);

  const array = Array.from(Array(15).keys());
  console.log(array);
  const handleSearchSubmit = (q: string): void => {
    setQuery(q);
  }

  const searchEpisodes = async (): Promise<void> => {
    setSearching(true);
    try {
      const requestBody: TEpisodeSearchBody = { query, type };
      const response = await EpisodeApiService.search(requestBody);
      console.log(response);
    } catch (error) {
      console.log(error);
    } finally {
      setSearching(false);
    }
  }

  useEffect(() => {
    (async function useEffectSearchEpisodes() {
      if (query) {
        await searchEpisodes();
      }
    })();
  }, [query]);

  return (
    <>
      <PageTitle title={`Search results for ${query}`} />
      <div className="min-h-screen bg-white flex flex-col overflow-hidden">
        <header className="w-full p-4 flex border-b flex-0 fixed">
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

        <div className="w-full h-full fixed flex flex-1 overflow-hidden flex-grow-0" style={{ marginTop: 75}}>
          <main className="flex-0 overflow-x-hidden overflow-y-auto focus:outline-none w-1/2">
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
          <aside className="w-full flex-1 flex-shrink-0 border-l p-6 overflow-x-hidden overflow-y-auto">
            {/* Meta data */}
            <div className="flex items-start space-x-4">
              <div className="w-40 h-40 flex-0 flex-shrink-0">
                <img src="https://i.scdn.co/image/afd13fc941059b20cb536fb96d23874eea88d521" alt="" className="h-full w-full border-transparent rounded-md shadow-xl" />
              </div>
              <div>
                <p className="text-sm uppercase font-semibold text-gray-700 mb-3">Podcast episode</p>
                <h3 className="text-3xl font-bold leading-none">WS Alive and Well Even In Isolation</h3>
                <p className="mt-4 text-gray-600">Duration: 16 mins</p>
                <p className="text-gray-600">In podcast: <a href="https://open.spotify.com/episode/60U0vD190ZoY12mKLfYsM8" target="_blank" rel="noreferrer" className="font-semibold">100% Real With Lisa Cabrera</a></p>
              </div>
            </div>
            {/* Description */}
            <div className="mt-8">
              <p className="text-gray-800 font-semibold mb-2">Episode description</p>
              <p className="text-gray-600 text-sm">Mainstream Media always get black willing attackers to speak against the black community.  Asians are not getting any anti-Asian commentators denying their racism.  Stacey Dash showed us how money will make you sell out against your own.</p>
            </div>
            {/* Clip information */}
            <div className="mt-8">
              <div className="flex content-between items-center mb-4">
                <div className="flex flex-1 items-start">
                  <span className="font-bold text-gray-800">Clip contains keyword: <span className="px-2 py-1 rounded truncate">new technologies</span></span>
                </div>
                <div className="flex items-end flex-shrink-0">
                  <span className="px-2 py-1 rounded bg-green-100">0:45 - 1:12</span>
                </div>
              </div>
              <div className="flex flex-wrap pb-4 text-gray-700 space-x-1 items-start">
                <span>Why</span> <span>do</span> <span className="px-2 rounded bg-spotify-green text-gray-50">Germans</span> <span className="px-2 rounded bg-spotify-green text-gray-50">loves</span> <span className="px-2 rounded bg-spotify-green text-gray-50">American</span> <span>is</span> <span>a</span>
              </div>
            </div>
          </aside>
        </div>
      </div>
    </>
  )
}

export default SearchResultPage;
