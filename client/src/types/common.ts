export interface IClassName {
  className?: string;
}

export interface ICommon extends IClassName {}

export enum ESearchType {
  PHRASE = 'phrase',
  MULTIWORD = 'multiword'
}
