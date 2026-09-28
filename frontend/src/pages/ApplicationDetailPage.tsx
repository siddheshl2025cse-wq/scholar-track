import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ErrorMessage from '../components/ErrorMessage';
import LoadingSpinner from '../components/LoadingSpinner';
import { api } from '../services/api';
import type { Application } from '../types';

export default function ApplicationDetailPage() {
  const { id } = useParams();
  const [application, setApplication] = useState<Application | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get(`/applications/${id}`);
        setApplication(res.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load application.');
      } finally {
        setLoading(false);
      }
    };

    if (id) load();
  }, [id]);

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;
  if (!application) return <ErrorMessage message="Application not found." />;

  return (
    <div className="page-wrap">
      <div className="panel">
        <h2>Application Status</h2>
        <div className="progress-tracker">
          {['Submitted', 'Eligibility Checked', 'Document Verification', 'Approved', 'Disbursement Pending', 'Scholarship Disbursed'].map((step, index) => (
            <div key={step} className={`tracker-step ${index <= 4 ? 'active' : ''}`}>
              <span>{step}</span>
            </div>
          ))}
        </div>

        <div className="detail-grid">
          <div><strong>Application Number</strong><p>{application.applicationNumber}</p></div>
          <div><strong>Scheme</strong><p>{application.schemeName}</p></div>
          <div><strong>Student</strong><p>{application.studentName}</p></div>
          <div><strong>Application Date</strong><p>{new Date(application.applicationDate).toLocaleDateString()}</p></div>
          <div><strong>Eligibility</strong><p>{application.eligibilityStatus}</p></div>
          <div><strong>Verification</strong><p>{application.applicationStatus}</p></div>
          <div><strong>Remarks</strong><p>{application.remarks || 'No remarks'}</p></div>
        </div>
      </div>
    </div>
  );
}
