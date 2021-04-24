import React from 'react';
import { Link } from 'react-router-dom';
import { TShow } from '../types';

interface IShowListeItemProps {
  item: TShow;
}

export function ShowListItem(props: IShowListeItemProps) {
  const { item } = props;

  return (
    <Link to={`/show/${item.id}`}>
      <li className="relative p-4 bg-gray-800 hover:bg-gray-700 cursor-pointer transition-colors duration-200 rounded">
        <div className="focus-within:ring-2 group block w-full overflow-hidden h-62">
          <img src={item.showImageUrl} alt={item.showName} className="w-full h-full block rounded-xl bg-green-100" />
        </div>
        <p className="mt-4 block text-base font-bold text-gray-100 truncate pointer-events-none">{item.showName}</p>
        <p className="mt-1 block text-sm font-medium text-gray-500 truncate pointer-events-none">{item.publisher}</p>
      </li>
    </Link>
  );
}
