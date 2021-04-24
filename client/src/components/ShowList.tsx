import React from 'react';
import { TShow } from '../types';
import { ShowListItem } from './ShowListItem';

interface IShowListProps {
  items?: TShow[];
}

export function ShowList(props: IShowListProps) {
  const { items } = props;

  return (
    <ul className="grid grid-cols-2 gap-x-4 gap-y-8 sm:grid-cols-4 sm:gap-x-6 lg:grid-cols-6 xl:gap-x-8">
      {(items || []).map((show: TShow) => (
        <ShowListItem key={show.id} item={show} /> 
      ))}
    </ul>    
  );
}
