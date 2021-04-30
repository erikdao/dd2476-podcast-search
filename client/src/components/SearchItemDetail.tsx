import { ICommon, TEpisodeSearchResult } from "../types";

interface ISearchItemDetailProps extends ICommon {
  item: TEpisodeSearchResult;
  query: string | null;
}

export function SearchItemDetail(props: ISearchItemDetailProps) {
  const { item, query } = props;
  console.log(item.id);
  return (
    <>
      {/* Meta data */}
      <div className="flex items-start space-x-4">
        <div className="w-40 h-40 flex-0 flex-shrink-0">
          <img src={`${item.show?.showImageUrl}`} alt="" className="h-full w-full border-transparent rounded-md shadow-xl" />
        </div>
        <div>
          <p className="text-sm uppercase font-semibold text-gray-700 mb-3">Podcast episode</p>
          <h3 className="text-3xl font-bold leading-none">{item.episodeName}</h3>
          <p className="mt-4 text-gray-600">Duration: {Math.round(item.duration)} mins</p>
          <p className="text-gray-600">In podcast: <a href={`https://open.spotify.com/show/${item.show?.id}`} target="_blank" rel="noreferrer" className="font-semibold">{item.show?.showName}</a></p>
        </div>
      </div>
      {/* Description */}
      <div className="mt-8">
        <p className="text-gray-800 font-semibold mb-2">Episode description</p>
        <p className="text-gray-600 text-sm">{item.episodeDescription}</p>
      </div>
      {/* Clip information */}
      <div className="mt-8">
        <div className="flex content-between items-center mb-4">
          <div className="flex flex-1 items-start">
            <span className="font-bold text-gray-800">Clip contains keyword: <span className="px-2 py-1 rounded truncate">{query}</span></span>
          </div>
          <div className="flex items-end flex-shrink-0">
            <span className="px-2 py-1 rounded bg-green-100">0:45 - 1:12</span>
          </div>
        </div>
        <div className="flex flex-wrap pb-4 text-gray-700 space-x-1 items-start">
          <span>Why</span> <span>do</span> <span className="px-2 rounded bg-spotify-green text-gray-50">Germans</span> <span className="px-2 rounded bg-spotify-green text-gray-50">loves</span> <span className="px-2 rounded bg-spotify-green text-gray-50">American</span> <span>is</span> <span>a</span>
        </div>
      </div>
    </>
  )
}
