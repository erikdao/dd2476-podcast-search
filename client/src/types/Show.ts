import { TEpisode } from "./Episode";

/**
 * Base type representing the Show domain model
 */
export type TShow = {
  id: string;
  showName: string;
  showUri: string;
  showDescription?: string;
  showImageUrl?: string;
  language?: string;
  publisher?: string;
};

export type TShowDetail = TShow & {
  episodes: TEpisode[];
}
