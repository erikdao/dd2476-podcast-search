import React from 'react';
import { ShowListItem } from './ShowListItem';

export function ShowList() {
  const data = Array.from({length: 12}, () => Math.floor(Math.random() * 12));
  return (
    <ul className="grid grid-cols-2 gap-x-4 gap-y-8 sm:grid-cols-4 sm:gap-x-6 lg:grid-cols-6 xl:gap-x-8">
      {data.map((d: number) => (
        <ShowListItem key={d} /> 
      ))}
    </ul>    
  );
}
