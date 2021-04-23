import React from 'react';
import { PageTitle } from '../components';

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
                <div className="border-4 border-dashed border-gray-600 rounded-lg h-96"></div>
              </div>
            </div>
          </main>
        </div>
    </div>
    </>
  );
}

export default HomePage;
