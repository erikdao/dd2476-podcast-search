import clsx from "clsx";
import { ICommon, TEpisodeSearchResult } from "../types";
import { WordToken } from "../types/WordToken";

interface ISearchItemDetailProps extends ICommon {
  item: TEpisodeSearchResult;
  query: string | null;
}

function getReadableTime(time: number) {
  if (time < 60.0) {
    return `0:${Math.floor(time)}`;  
  } else if (time == 60.0) {
    return `1:00`;
  } else {
    const minute = Math.floor(time / 60.0);
    const second = time - minute * 60;
    return `${minute}:${Math.round(second)}`;
  }
}

function getClipTimes(item: TEpisodeSearchResult) {
  if (!item.clips || (item.clips && !item.clips.length)) {
    return { clipStart: null, clipEnd: null};
  }
  const clips: WordToken[] = item.clips;
  const firstToken = clips[0];
  const lastToken = clips.slice(-1)[0];
  return {
    clipStart: getReadableTime(firstToken.startTime),
    clipEnd: getReadableTime(lastToken.endTime)
  }
}

export function SearchItemDetail(props: ISearchItemDetailProps) {
  const { item, query } = props;

  const { clipStart, clipEnd } = getClipTimes(item);

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
            <span className="px-2 py-1 rounded bg-green-100">{clipStart} - {clipEnd}</span>
          </div>
        </div>
        <div className="flex flex-wrap pb-4 text-gray-700 space-x-1 items-start">
          {item.clips && item.clips.length > 0 && item.clips.map((wordToken: WordToken, index: number) => (
            <span key={index} className={clsx(
              wordToken.highlight ? "px-2 rounded bg-spotify-green text-gray-50" : ""
            )}>{wordToken.word}</span>
          ))}
        </div>
      </div>
    </>
  )
}
