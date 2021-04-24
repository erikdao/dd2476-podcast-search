import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ShowApiService from '../api/show';
import { PageTitle } from '../components';
import { TShowDetail } from '../types';

function ShowDetailPage() {
  const { showId } = useParams();

  const [show, setShow] = useState<TShowDetail>();

  const loadShow = async (): Promise<void> => {
    try {
      const response = await ShowApiService.getById(showId);
      const { data } = response;
      setShow(data);
    } catch (error) {
      console.log("Error while loading show detail", error);
    }
  }

  useEffect(() => {
    (async function useEffectLoadShow() {
      await loadShow();
    })();
  }, []);

  return (
    <>
      <PageTitle title={(show && show.showName) || ""} />
      <div className="min-h-screen bg-gray-900">

      </div>
    </>
  );
}

export default ShowDetailPage;
