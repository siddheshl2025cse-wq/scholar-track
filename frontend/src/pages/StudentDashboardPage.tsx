import { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import ApplicationTable from '../components/ApplicationTable';
import ErrorMessage from '../components/ErrorMessage';
import LoadingSpinner from '../components/LoadingSpinner';
import StatCard from '../components/StatCard';
import { api } from '../services/api';
import type { Application } from '../types';

export default function StudentDashboardPage() {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/applications');
        setApplications(res.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load applications.');
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  const stats = useMemo(() => {
    const total = applications.length;
    const eligible = applications.filter((a) => a.eligibilityStatus === 'ELIGIBLE').length;
    const pendingVerification = applications.filter((a) => a.applicationStatus === 'PENDING_VERIFICATION').length;
    const approved = applications.filter((a) => a.applicationStatus === 'APPROVED' || a.applicationStatus === 'DISBURSEMENT_PENDING' || a.applicationStatus === 'DISBURSED').length;
    const disbursedAmount = applications.filter((a) => a.applicationStatus === 'DISBURSED').length * 25000;

    return { total, eligible, pendingVerification, approved, disbursedAmount };
  }, [applications]);

  if (loading) return <LoadingSpinner />;
  if (error) return <ErrorMessage message={error} />;

  return (
    <div className="page-wrap">
      <div className="stats-grid">
        <StatCard label="Total Applications" value={stats.total} tone="blue" />
        <StatCard label="Eligible Applications" value={stats.eligible} tone="green" />
        <StatCard label="Pending Verification" value={stats.pendingVerification} tone="amber" />
        <StatCard label="Approved Applications" value={stats.approved} tone="purple" />
        <StatCard label="Disbursed Amount" value={`₹${stats.disbursedAmount.toLocaleString()}`} tone="green" />
      </div>

      <div className="panel">
        <div className="panel-header-row">
          <h2>Recent Applications</h2>
          <Link to="/scholarships" className="primary-btn">Browse Schemes</Link>
        </div>
        <ApplicationTable applications={applications.slice(0, 5)} />
      </div>
    </div>
  );
}
