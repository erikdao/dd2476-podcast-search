import React, { useEffect } from 'react';

interface IPageTitleProps {
  title: string;
}

export function PageTitle(props: IPageTitleProps) {
  useEffect(() => {
    document.title = `${props.title} | Spotify Podcast Search`;
  }, [props.title]);
  
  return null;
}
