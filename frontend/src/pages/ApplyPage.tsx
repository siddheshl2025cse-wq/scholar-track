import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../services/api';

const initialForm = {
  studentName: '',
  email: '',
  phone: '',
  category: 'General',
  annualIncome: '',
  marksPercentage: '',
  collegeName: '',
  course: '',
  yearOfStudy: '1'
};

export default function ApplyPage() {
  const { schemeId } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(initialForm);
  const [eligibility, setEligibility] = useState<'eligible' | 'ineligible' | null>(null);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (field: string, value: string) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const runEligibilityCheck = () => {
    const annualIncome = Number(form.annualIncome);
    const marks = Number(form.marksPercentage);

    if (!form.studentName || !form.email || !form.phone || !form.collegeName || !form.course) {
      setEligibility(null);
      setMessage('Please complete all student details.');
      return;
    }

    const isEligible = annualIncome <= 500000 && marks >= 60 && ['General', 'OBC', 'SC', 'ST'].includes(form.category);
    setEligibility(isEligible ? 'eligible' : 'ineligible');
    setMessage(isEligible ? '✓ Eligible for scholarship' : '✕ Not eligible. Check income, marks and category rules.');
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setError('');
    setSubmitting(true);

    try {
      if (!schemeId) {
        throw new Error('Scheme not found.');
      }

      const payload = {
        studentId: 1,
        schemeId: Number(schemeId)
      };

      await api.post('/applications', payload);
      navigate('/student/dashboard');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unable to submit application.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="page-wrap">
      <div className="panel form-panel">
        <h2>Scholarship Application</h2>
        <form onSubmit={handleSubmit} className="form-grid">
          <div className="field"><label>Student Name</label><input value={form.studentName} onChange={(e) => handleChange('studentName', e.target.value)} /></div>
          <div className="field"><label>Email</label><input type="email" value={form.email} onChange={(e) => handleChange('email', e.target.value)} /></div>
          <div className="field"><label>Phone</label><input value={form.phone} onChange={(e) => handleChange('phone', e.target.value)} /></div>
          <div className="field"><label>Category</label><select value={form.category} onChange={(e) => handleChange('category', e.target.value)}><option>General</option><option>OBC</option><option>SC</option><option>ST</option></select></div>
          <div className="field"><label>Annual Income</label><input type="number" value={form.annualIncome} onChange={(e) => handleChange('annualIncome', e.target.value)} /></div>
          <div className="field"><label>Marks Percentage</label><input type="number" value={form.marksPercentage} onChange={(e) => handleChange('marksPercentage', e.target.value)} /></div>
          <div className="field"><label>College</label><input value={form.collegeName} onChange={(e) => handleChange('collegeName', e.target.value)} /></div>
          <div className="field"><label>Course</label><input value={form.course} onChange={(e) => handleChange('course', e.target.value)} /></div>
          <div className="field"><label>Year of Study</label><input type="number" value={form.yearOfStudy} onChange={(e) => handleChange('yearOfStudy', e.target.value)} /></div>

          <div className="form-actions full-width">
            <button type="button" className="secondary-btn" onClick={runEligibilityCheck}>Check Eligibility</button>
            {eligibility && <div className={eligibility === 'eligible' ? 'success-note' : 'error-note'}>{message}</div>}
          </div>

          {error && <div className="error-note full-width">{error}</div>}

          <div className="form-actions full-width">
            <button type="submit" className="primary-btn" disabled={submitting || eligibility !== 'eligible'}>
              {submitting ? 'Submitting...' : 'Submit Application'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
