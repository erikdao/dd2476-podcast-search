import React, { useEffect, useState, Fragment } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import EpisodeApiService from '../api/episode';

import { LoadingIndicator, PageTitle, QueryTypeSelect, SearchBox, SearchResultItem } from '../components';
import { SearchItemDetail } from '../components/SearchItemDetail';
import { SpotifyLogo } from '../components/SpotifyLogo';
import { ESearchType, TEpisodeSearchBody, TEpisodeSearchResult } from '../types';

function getQueryFromUrl(url: string): string | null {
  const urlParams = new URLSearchParams(url);
  return urlParams.get('query');
}

function SearchResultPage() {
  const location = useLocation();
  const [searching, setSearching] = useState(false);
  const [query, setQuery] = useState(getQueryFromUrl(location.search))
  const [type, setType] = useState<ESearchType>(ESearchType.PHRASE);
  const [episodes, setEpisodes] = useState<TEpisodeSearchResult[]>([]);
  const [selectedEpisode, setSelectedEpisode] = useState<TEpisodeSearchResult>();

  const clipLength = 60;
  // Pagination param
  const [from, setFrom] = useState(30);
  const [size, setSize] = useState(15);

  const handleSearchSubmit = (q: string): void => {
    setQuery(q);
  }

  const searchEpisodes = async (): Promise<void> => {
    setSearching(true);
    setEpisodes([]);
    setSelectedEpisode(undefined);
    try {
      const requestBody: TEpisodeSearchBody = { query, type, clipLength };
      const response = await EpisodeApiService.search(requestBody, { from, size });
      let data: TEpisodeSearchResult[] = response.data;
      data = data.filter((d: TEpisodeSearchResult) => d.clips && d.clips.length > 0 && d.clips[0].wordTokens);
      setEpisodes(data);
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
      <div className="h-screen bg-white flex flex-col overflow-hidden">
        <header className="w-full p-4 flex border-b flex-0 flex-shrink-0 h-20">
          {/* Logo */}
          <Link to="/" className="w-1/4 flex items-center content-center">
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

        <div className="w-full flex flex-1 overflow-hidden flex-shrink-0">
          <main className="flex-0 overflow-x-hidden overflow-y-auto focus:outline-none w-1/2 bg-gray-50">
            {/* Start main area*/}
            <ul className="inset-0 py-6 px-4 sm:px-6 lg:px-8 space-y-3">
              {/* Search result item */}
              { searching && <LoadingIndicator className="mx-auto h-6 w-6 text-green-500" /> }
              { !searching && episodes.map((episode: TEpisodeSearchResult, index: number) => (
                  <li key={index} onClick={() => setSelectedEpisode(episode)}>
                    <SearchResultItem item={episode} isSelected={selectedEpisode?.id === episode.id}/>
                  </li>
              ))}
            </ul>
            {/* End main area */}
          </main>
          <aside className="w-full flex-1 flex-shrink-0 border-l p-6 overflow-x-hidden overflow-y-auto">
            {!selectedEpisode && (
              <div>
                <p className="text-center text-gray-600">No episode is selected</p>
              </div>
            )}
            {selectedEpisode && <SearchItemDetail item={selectedEpisode} query={query} /> }
          </aside>
        </div>
      </div>
    </>
  )
}

export default SearchResultPage;
