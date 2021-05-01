import { useLocation } from 'react-router';
import { PageTitle } from '../components';

function SearchResultPage() {
  const location = useLocation();

  console.log(location);
  return (
    <>
      <PageTitle title="Search results" />
      <div className="min-h-screen bg-gray-900">

      </div>
    </>
  )
}

export default SearchResultPage;
