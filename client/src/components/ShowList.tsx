import React from 'react';

export function ShowList() {
  const data = Array.from({length: 12}, () => Math.floor(Math.random() * 12));
  return (
    <ul className="grid grid-cols-2 gap-x-4 gap-y-8 sm:grid-cols-4 sm:gap-x-6 lg:grid-cols-6 xl:gap-x-8">
      {data.map((d: number) => (
        <li key={d} className="relative p-4 bg-gray-800 hover:bg-gray-700 cursor-pointer transition-colors duration-200 rounded">
          <div className="focus-within:ring-2 group block w-full overflow-hidden h-40">
            <img src="https://i.scdn.co/image/ab6765630000ba8a5626269a1000e3e699ca899b" alt="" className="w-full rounded-xl" />
          </div>
          <p className="mt-4 block text-xl font-bold text-gray-100 truncate pointer-events-none">Podcast {d} title</p>
          <p className="mt-1 block text-md font-medium text-gray-500 pointer-events-none">Publisher</p>
        </li>
      ))}
    </ul>    
  );
}
