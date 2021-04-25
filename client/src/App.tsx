import { Route, Routes } from 'react-router-dom';
import AboutPage from './pages/AboutPage';
import HomePage from './pages/HomePage';
import SearchResultPage from './pages/SearchResultPage';
import ShowDetailPage from './pages/ShowDetailPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="about" element={<AboutPage />} />
      <Route path="show/:showId" element={<ShowDetailPage />} />
      <Route path="search" element={<SearchResultPage />} />
    </Routes>
  );
}

export default App;
