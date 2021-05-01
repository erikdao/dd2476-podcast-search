import { ICommon, TEpisodeClip, TEpisodeSearchResult } from "../types";
import { ShowImagePlaceHolder } from "./ShowImagePlaceholder";
import { EpisodeClip } from "./EpisodeClip";
import { format, addSeconds } from "date-fns";

interface ISearchItemDetailProps extends ICommon {
  item: TEpisodeSearchResult;
  query: string | null;
}

function getReadableTime(time: number): string {
  let now = addSeconds(new Date(0), time);
  return format(now, "mm:ss");
} 

export function SearchItemDetail(props: ISearchItemDetailProps) {
  const { item, query } = props;
  return (
    <>
      {/* Meta data */}
      <div className="flex items-start space-x-4">
        <div className="w-40 h-40 flex-0 flex-shrink-0">
          {item.show && !item.show.showImageUrl ? (
            <ShowImagePlaceHolder className="bg-gray-900 block w-full h-full rounded-md border-transparent shadow-xl" />
          ) : (
            <img src={`${item.show?.showImageUrl}`} alt="" className="h-full w-full border-transparent rounded-md shadow-xl" />
          )}
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
        <p className="text-gray-600 text-sm overflow-ellipsis">{item.episodeDescription}</p>
      </div>
      {/* Clip information */}
      <div className="mt-8">
        <div className="flex content-between items-center mb-4">
          <div className="flex flex-1 items-start">
            <span className="font-bold text-gray-800">{item.clips.length} clip(s) contain keyword: <span className="px-2 py-1 rounded truncate">{query}</span></span>
          </div>
        </div>
        <div className="flex flex-wrap pb-4 text-gray-700 space-x-1 space-y-4 items-start">
          {item.clips && item.clips.map((clip: TEpisodeClip, index: number) => (
            <div className="rounded-md shadow-md">
              <div className="p-2 text-sm font-semibold text-white bg-gray-700 rounded-t-md">
                <span className="mr-4">Clip {index + 1}: </span>
                <span>{getReadableTime(clip.startTime)} - {getReadableTime(clip.endTime)}</span>
              </div>
              <div className="p-2">
                <EpisodeClip {...clip} key={index} />
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  )
}
