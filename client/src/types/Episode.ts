import { ESearchType } from "./common";
import { TShow } from "./Show";
import { WordToken } from "./WordToken";

/**
 * Base type representing the Episode domain model
 */
export type TEpisode = {
  id: string;
  episodeName: string;
  episodeUri: string;
  episodeDescription?: string;
  duration: number;
  show?: TShow;
  transcript?: string;
}

export type TEpisodeSearchBody = {
  query: string | null;
  type: ESearchType;
  clipLength: number;
}

export type TEpisodeClip = {
  order: number;
  startTime: number;
  endTime: number;
  wordTokens: WordToken[];
}

export type TEpisodeSearchResult = {
  id: string;
  score?: number;
  episodeName: string;
  episodeUri: string;
  episodeDescription: string;
  duration: number;
  show?: TShow;
  clips?: TEpisodeClip[];
}
