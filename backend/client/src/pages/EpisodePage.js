import React, { useState } from 'react'

const EpisodePage = ({history, location}) => {
    console.log('location = ', location.state)
    const episode = location.state !== undefined ? location.state : {};
    const { episodeName, episodeDescription, clips } = episode;
    const logProps = e => {
        // console.log(title);
        // console.log(description);
    };

  return (
    <div className="episode-container">
        <div className="episode-header">
            <h3>EPISODE</h3>
            <h1>{episodeName}</h1>
        </div>
        <div>
            <h2>EPISODE DESCRIPTION</h2>
            <p>{episodeDescription}</p>
        </div>
        <div>
            {clips.map((clip, idx) => {
                return (
                    <div style={{border: "2px solid red"}} key={idx}>
                        <p>Start time: {clip.startTime}</p>
                        <p>Start time: {clip.endTime}</p>
                        <h3>Transcript</h3>
                        <p>{clip.transcriptExcerpt}</p>
                    </div>
                )
            })}
        </div>
    </div>
    
  );
}

export default EpisodePage;
