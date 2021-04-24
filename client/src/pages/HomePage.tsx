import React, { useEffect, useState } from 'react';
import ShowApiService from '../api/show';
import { PageTitle, SearchBox, ShowList } from '../components';
import { SpotifyLogo } from '../components/SpotifyLogo';
import { TShow } from '../types';

function HomePage() {
  const [shows, setShows] = useState<TShow[]>();

  const loadShows = async () => {
    try {
      const response = await ShowApiService.getAll();
      const { data } = response;
      setShows(data);
    } catch (error) {
      console.log("Error while fetching shows list");
    }
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
        <div className="py-10">
          {/* <header>
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <h1 className="text-xl font-bold leading-tight text-gray-50">
                Spotify Podcast Search
              </h1>
            </div>
          </header> */}
          <main>
            <div className="max-w-7xl mx-auto sm:px-6 lg:px-8">
              <div className="px-0 py-8">
                <div className="w-3/5 mx-auto flex items-center content-center mb-4">
                  <SpotifyLogo />
                  <h1 className="text-7xl font-bold leading-tight text-gray-50">Podcast Search</h1>
                </div>
                <SearchBox />
              </div>
              <div className="px-4 py-8 sm:px-0">
                <div className="flex">
                  <h2 className="text-lg font-medium text-gray-300 text-center w-full mb-6">Here are things you might be interested in</h2>
                </div>
                <ShowList items={shows?.slice(0, 12)} />
              </div>
            </div>
          </main>
        </div>
    </div>
    </>
  );
}

export default HomePage;
