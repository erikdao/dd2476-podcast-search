import { TShow } from '../types';
import LazyLoad from 'react-lazyload';

interface IShowListeItemProps {
  item: TShow;
}

export function ShowListItem(props: IShowListeItemProps) {
  const { item } = props;

  return (
    <a href={`https://open.spotify.com/show/${item.id}`} className="block" target="_blank" rel="noreferrer">
      <li className="relative p-4 bg-gray-800 hover:bg-gray-700 cursor-pointer transition-colors duration-200 rounded">
        <div className="focus-within:ring-2 group block w-full overflow-hidden h-62">
          <LazyLoad height={150}>
            <img src={item.showImageUrl} alt={item.showName} className="w-full h-full block rounded-xl bg-green-100" />
          </LazyLoad>
        </div>
        <p className="mt-4 block text-base font-bold text-gray-100 truncate pointer-events-none">{item.showName}</p>
        <p className="mt-1 block text-sm font-medium text-gray-500 truncate pointer-events-none">{item.publisher}</p>
      </li>
    </a>
  );
}
