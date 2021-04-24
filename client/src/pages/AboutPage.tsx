import { PageTitle } from '../components';

function AboutPage() {
  return (
    <>
      <PageTitle title="About" />
      <div className="min-h-screen bg-gray-900">
        <header>
          <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8">
            <h1 className="text-2xl font-bold leading-tight">About</h1>
          </div>
        </header>
      </div>
    </>  
  );
}

export default AboutPage;
