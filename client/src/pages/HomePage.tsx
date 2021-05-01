import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import ShowApiService from '../api/show';
import { LoadingIndicator, PageTitle, SearchBox, ShowList } from '../components';
import { SpotifyLogo } from '../components/SpotifyLogo';
import { TShow } from '../types';

function HomePage() {
  const navigate = useNavigate();

  const [shows, setShows] = useState<TShow[]>();
  const [loading, setLoading] = useState(true);

  const loadShows = async () => {
    try {
      const response = await ShowApiService.getAll();
      const { data } = response;
      setShows(data);
    } catch (error) {
      console.log("Error while fetching shows list");
    } finally {
      setLoading(false);
    }
  }

  /**
   * Handler for when user submits search keyword (i.e., by pressing Enter)
   * @param query search query
   * @returns 
   */
  const handleSearchSubmit = (query: string) => {
    if (!query) {
      return;
    }
    navigate(`/search?query=${query}`);
  }

  useEffect(() => {
    (async function useEffectLoadShows() {
      await loadShows();
    })();
  }, []);

  return (
    <>
      <PageTitle title="Home" />
      <div className="min-h-screen bg-gray-900">
          <header>
            <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8 flex flex-col items-end">
              <div className="p-2">
                <button type="button" className="focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-spotify-green text-gray-50 uppercase text-sm py-2 px-4 font-bold rounded-3xl">
                  Login with Spotify
                </button>
              </div>
            </div>
          </header>
          <main>
            <div className="max-w-7xl mx-auto sm:px-6 lg:px-8">
              <div className="px-0 py-8">
                <div className="w-3/5 mx-auto flex items-center content-center mb-4">
                  <SpotifyLogo />
                  <h1 className="text-3xl sm:text-4xl md:text-7xl lg:text-7xl font-bold leading-tight text-gray-50">Podcast Search</h1>
                </div>
                <SearchBox onSubmit={handleSearchSubmit} />
              </div>
              <div className="px-4 py-8 sm:px-0">
                <div className="flex">
                  <h2 className="text-lg font-medium text-gray-300 text-center w-full mb-6">Here are things you might be interested in</h2>
                </div>
                {loading ? (
                  <div className="flex w-full py-10">
                    <LoadingIndicator className="text-white h-7 w-8 mx-auto" />
                  </div>
                ) : (
                  <ShowList items={shows} />
                )}
              </div>
            </div>
          </main>
    </div>
    </>
  );
}

export default HomePage;
