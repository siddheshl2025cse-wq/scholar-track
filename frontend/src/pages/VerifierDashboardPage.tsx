import { useEffect, useState } from 'react';
import { api } from '../services/api';
import type { Application } from '../types';
import StatusBadge from '../components/StatusBadge';
import LoadingSpinner from '../components/LoadingSpinner';
import ErrorMessage from '../components/ErrorMessage';

export default function VerifierDashboardPage() {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/applications');
        setApplications(res.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load verifier data.');
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
      <div className="stats-grid">
        <div className="stat-card blue"><span>Pending Applications</span><strong>{applications.filter((a) => a.applicationStatus === 'PENDING_VERIFICATION').length}</strong></div>
        <div className="stat-card green"><span>Approved Applications</span><strong>{applications.filter((a) => a.applicationStatus === 'APPROVED').length}</strong></div>
        <div className="stat-card amber"><span>Rejected Applications</span><strong>{applications.filter((a) => a.applicationStatus === 'REJECTED').length}</strong></div>
        <div className="stat-card purple"><span>Pending Disbursement</span><strong>{applications.filter((a) => a.applicationStatus === 'DISBURSEMENT_PENDING').length}</strong></div>
      </div>

      <div className="panel">
        <h2>Applications to Review</h2>
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>Application No.</th>
                <th>Student</th>
                <th>Scheme</th>
                <th>Income</th>
                <th>Marks</th>
                <th>Eligibility</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {applications.map((app) => (
                <tr key={app.id}>
                  <td>{app.applicationNumber}</td>
                  <td>{app.studentName}</td>
                  <td>{app.schemeName}</td>
                  <td>₹250000</td>
                  <td>88%</td>
                  <td><StatusBadge status={app.eligibilityStatus} /></td>
                  <td><StatusBadge status={app.applicationStatus} /></td>
                  <td><button className="primary-btn small">Review</button></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
