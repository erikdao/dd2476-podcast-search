import React, { useState } from 'react';

import { SearchIcon } from '@heroicons/react/outline';

export function SearchBox() {
  const [query, setQuery] = useState<string>("");

  const handleQueryChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { target: { value }} = e;
    setQuery(value);
  }

  return (
    <div className="w-fulli px-8">
      <div className="relative w-3/5 mx-auto rounded-3xl flex items-center content-center">
        <input
          type="text"
          name="query"
          id="query"
          className="focus:ring-2 focus:outline-none focus:ring-green-500 block pl-3 pr-10 py-2 rounded-3xl text-gray-600 w-full"
          placeholder="Search for podcast by names, transcripts..."
          value={query}
          onChange={handleQueryChanged}
         />
        <div className="absolute inset-y-0 right-0 py-2 pr-3 flex items-center cursor-pointer">
          <SearchIcon className="h-5 w-5 text-gray-700" aria-hidden="true" />
        </div>
      </div>
    </div>
  );
}
