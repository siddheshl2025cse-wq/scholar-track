import type { Application } from '../types';
import StatusBadge from './StatusBadge';

type Props = {
  applications: Application[];
};

export default function ApplicationTable({ applications }: Props) {
  if (!applications.length) {
    return <div className="empty-state">No applications found.</div>;
  }

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>Application No.</th>
            <th>Student</th>
            <th>Scheme</th>
            <th>Eligibility</th>
            <th>Status</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((app) => (
            <tr key={app.id}>
              <td>{app.applicationNumber}</td>
              <td>{app.studentName}</td>
              <td>{app.schemeName}</td>
              <td><StatusBadge status={app.eligibilityStatus} /></td>
              <td><StatusBadge status={app.applicationStatus} /></td>
              <td>{new Date(app.applicationDate).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
