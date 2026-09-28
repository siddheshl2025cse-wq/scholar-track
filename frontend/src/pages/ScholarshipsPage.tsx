import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import ErrorMessage from '../components/ErrorMessage';
import LoadingSpinner from '../components/LoadingSpinner';
import ScholarshipCard from '../components/ScholarshipCard';
import { api } from '../services/api';
import type { Scheme } from '../types';

export default function ScholarshipsPage() {
  const navigate = useNavigate();
  const [schemes, setSchemes] = useState<Scheme[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/schemes');
        setSchemes(res.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load scholarship schemes.');
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;

  return (
    <div className="page-wrap">
      <div className="panel">
        <div className="panel-header-row">
          <h2>Available Scholarships</h2>
          <Link to="/student/dashboard" className="secondary-btn">Back to Dashboard</Link>
        </div>
        <div className="cards-grid">
          {schemes.map((scheme) => (
            <ScholarshipCard
              key={scheme.id}
              scheme={scheme}
              onView={() => navigate(`/applications/${scheme.id}`)}
              onApply={() => navigate(`/apply/${scheme.id}`)}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
