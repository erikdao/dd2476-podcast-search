import React from 'react';
import { PageTitle, ShowList } from '../components';

function HomePage() {
  return (
    <>
      <PageTitle title="Home" />
      <div className="min-h-screen bg-gray-900">
        <div className="py-10">
          <header>
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <h1 className="text-xl font-bold leading-tight text-gray-50">
                Spotify Podcast Search
              </h1>
            </div>
          </header>
          <main>
            <div className="max-w-7xl mx-auto sm:px-6 lg:px-8">
              <div className="px-4 py-8 sm:px-0">
                <div className="flex">
                  <h2 className="text-xl font-semibold text-gray-200 text-center w-full mb-6">Here are something you might be interested in</h2>
                </div>
                <ShowList />
              </div>
            </div>
          </main>
        </div>
    </div>
    </>
  );
}

export default HomePage;
