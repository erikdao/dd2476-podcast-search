import clsx from "clsx";
import { ICommon, TEpisodeSearchResult } from "../types";
import { WordToken } from "../types/WordToken";

interface ISearchResultItemProps extends ICommon {
  item: TEpisodeSearchResult;
  isSelected?: boolean;
}

export function SearchResultItem(props: ISearchResultItemProps) {
  const { item, isSelected } = props;

  const previewTokens = item.clips[0].wordTokens.slice(0, 25);

  return (
  <div
    className={clsx(
      "p-3 border border-gray-300 rounded-md cursor-pointer hover:border-green-500 hover:shadow-sm transition-all",
      {"border-green-500": isSelected}, isSelected ? "bg-green-50" : "bg-white"
      )}
    >
    <div className="flex space-x-2 items-start">
      <h2 className={clsx(
        "text-lg font-semibold flex-1", isSelected ? "text-green-500" : "text-gray-700"
      )}>
        {item.episodeName}
      </h2>
      <div className="flex-0 flex-shrink-0">
        <div className="text-sm text-gray-700 rounded bg-green-50 px-2 mb-2">{item.clips?.length} clips</div>
        <div className="text-sm text-gray-700 rounded bg-green-50 px-2">Score: {item.score}</div>
      </div>
    </div>
    <h3 className="text-base py-2 text-gray-700">Podcast: {item.show?.showName}</h3>
    <div className="text-sm text-gray-600 flex flex-wrap">
      ...
      {previewTokens.map((token: WordToken, index: number) => (
        <span className={clsx("mr-1", token.highlight ? "font-semibold" : "")} key={index}>{token.word}</span>
      ))}
      ...
    </div>
  </div>
  );
}
