import { ICommon } from "../types";
import { WordToken } from "../types/WordToken";

interface IEpisodeClipProps extends ICommon {
  order: number;
  startTime: number;
  endTime: number;
  wordTokens: WordToken[];
}

function WordTokenEl(props: WordToken) {
  if (props.highlight) {
    return <span className="bg-spotify-green py-1 px-2 text-gray-50 rounded mr-1">{props.word}</span>
  }
  return <span className="text-gray-600 mr-1">{props.word}</span>;
}

export function EpisodeClip(props: IEpisodeClipProps) {
  const { wordTokens } = props;
  return (
    <div className="flex flex-wrap">
      {wordTokens.map((token: WordToken, index: number) => (
        <WordTokenEl {...token} key={index} />
      ))}
    </div>
  )
}
