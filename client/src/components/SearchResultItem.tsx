export function SearchResultItem() {
  return (
  <div className="p-3 border rounded-md cursor-pointer hover:border-green-500 transition-all">
    <div className="flex space-x-2 items-start">
      <h2 className="text-lg font-semibold flex-1 text-green-600">We Are Always The Mainstream Media's Target and Not Other Groups</h2>
      <div className="flex-0 flex-shrink-0">
        <div className="text-sm text-gray-700 rounded bg-green-50 px-2 mb-2">0:45 - 1:12</div>
        <div className="text-sm text-gray-700 rounded bg-green-50 px-2">Score: 4.576</div>
      </div>
    </div>
    <h3 className="text-base py-2">Podcast: 100% Real With Lisa Cabrera</h3>
    <div className="text-sm text-gray-600">
      <span>Spotify is all the music you'll ever need. ... Millions of songs and podcasts. No credit <strong>query</strong>. GET SPOTIFY FREE </span>
    </div>
  </div>
  );
}