import type { Scheme } from '../types';

type Props = {
  scheme: Scheme;
  onView?: (scheme: Scheme) => void;
  onApply?: (scheme: Scheme) => void;
};

export default function ScholarshipCard({ scheme, onView, onApply }: Props) {
  return (
    <div className="scholarship-card">
      <div className="card-header-row">
        <div>
          <p className="eyebrow">{scheme.provider}</p>
          <h3>{scheme.schemeName}</h3>
        </div>
        <span className="pill">₹{scheme.scholarshipAmount.toLocaleString()}</span>
      </div>

      <p>{scheme.description}</p>

      <div className="meta-grid">
        <div><span>Minimum Marks</span><strong>{scheme.minimumMarks}%</strong></div>
        <div><span>Income Limit</span><strong>₹{scheme.incomeLimit.toLocaleString()}</strong></div>
        <div><span>Deadline</span><strong>{new Date(scheme.applicationDeadline).toLocaleDateString()}</strong></div>
      </div>

      <div className="tags-row">
        {scheme.eligibleCategories.map((cat) => (
          <span key={cat} className="tag">{cat}</span>
        ))}
      </div>

      <div className="action-row">
        <button className="secondary-btn" onClick={() => onView?.(scheme)}>View Details</button>
        <button className="primary-btn" onClick={() => onApply?.(scheme)}>Apply Now</button>
      </div>
    </div>
  );
}
