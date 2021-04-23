/**
 * Base type representing the Show domain model
 */
export type TShow = {
  id: string;
  showName: string;
  showUri: string;
  showDescription?: string;
  language?: string;
  publisher?: string;
};
