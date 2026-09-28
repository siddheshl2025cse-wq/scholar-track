import { useEffect, useState } from 'react';
import ErrorMessage from '../components/ErrorMessage';
import LoadingSpinner from '../components/LoadingSpinner';
import StatusBadge from '../components/StatusBadge';
import { api } from '../services/api';
import type { Application } from '../types';

export default function DisbursementPage() {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/applications');
        setApplications(res.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load disbursement list.');
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
        <h2>Disbursement Queue</h2>
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>Application No.</th>
                <th>Student</th>
                <th>Scholarship</th>
                <th>Amount</th>
                <th>Verification</th>
                <th>Status</th>
                <th>Transaction</th>
              </tr>
            </thead>
            <tbody>
              {applications.map((item) => (
                <tr key={item.id}>
                  <td>{item.applicationNumber}</td>
                  <td>{item.studentName}</td>
                  <td>{item.schemeName}</td>
                  <td>₹25000</td>
                  <td><StatusBadge status="APPROVED" /></td>
                  <td><StatusBadge status={item.applicationStatus} /></td>
                  <td>ST-2026-{String(item.id).padStart(6, '0')}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
