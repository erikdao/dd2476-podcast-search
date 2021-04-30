import React, { useState } from 'react';

import { SearchIcon } from '@heroicons/react/outline';
import clsx from 'clsx';

interface ISearchBoxProps {
  query?: string;
  isCenter?: boolean;  // FIX ME
  inputClassName?: string;
  onSubmit?: (params?: any) => void;
}

export function SearchBox(props: ISearchBoxProps) {
  const [query, setQuery] = useState<string>(props.query || "");

  const handleQueryChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { target: { value }} = e;
    setQuery(value);
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      if (props.onSubmit && typeof props.onSubmit === 'function') {
        props.onSubmit(query);
      }
    }
  }

  return (
    <div className={clsx("relative w-3/5 rounded-3xl flex items-center content-center", {"mx-auto": props.isCenter})}>
      <input
        type="text"
        name="query"
        id="query"
        className={clsx("focus:ring-2 focus:outline-none focus:ring-green-500 block pl-3 pr-10 py-2 rounded-3xl text-gray-600 w-full", props.inputClassName)}
        placeholder="Search for podcast by names, transcripts..."
        value={query}
        onChange={handleQueryChanged}
        onKeyPress={handleKeyDown}
        />
      <div className="absolute inset-y-0 right-0 py-2 pr-3 flex items-center cursor-pointer">
        <SearchIcon className="h-5 w-5 text-gray-700" aria-hidden="true" />
      </div>
    </div>
  );
}
