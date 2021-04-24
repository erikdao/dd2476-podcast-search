import React from 'react';
import { TShow } from '../types';

interface IShowListeItemProps {
  item: TShow;
}

export function ShowListItem(props: IShowListeItemProps) {
  const { item } = props;

  return (
    <li className="relative p-4 bg-gray-800 hover:bg-gray-700 cursor-pointer transition-colors duration-200 rounded">
      <div className="focus-within:ring-2 group block w-full overflow-hidden h-62">
        <img src="https://i.scdn.co/image/ab6765630000ba8a5626269a1000e3e699ca899b" alt="" className="w-full h-auto rounded-xl" />
      </div>
      <p className="mt-4 block text-base font-bold text-gray-100 truncate pointer-events-none">{item.showName}</p>
      <p className="mt-1 block text-md font-medium text-gray-500 pointer-events-none">{item.publisher}</p>
    </li>
  )
}
