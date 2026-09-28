import { Navigate, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import StudentDashboardPage from './pages/StudentDashboardPage';
import ScholarshipsPage from './pages/ScholarshipsPage';
import VerifierDashboardPage from './pages/VerifierDashboardPage';
import DisbursementPage from './pages/DisbursementPage';
import ApplicationDetailPage from './pages/ApplicationDetailPage';
import ApplyPage from './pages/ApplyPage';

export default function App() {
  return (
    <div className="app-shell">
      <Sidebar />
      <main className="main-panel">
        <Routes>
          <Route path="/" element={<Navigate to="/student/dashboard" replace />} />
          <Route path="/student/dashboard" element={<StudentDashboardPage />} />
          <Route path="/scholarships" element={<ScholarshipsPage />} />
          <Route path="/apply/:schemeId" element={<ApplyPage />} />
          <Route path="/applications/:id" element={<ApplicationDetailPage />} />
          <Route path="/verifier/dashboard" element={<VerifierDashboardPage />} />
          <Route path="/verifier/disbursements" element={<DisbursementPage />} />
        </Routes>
      </main>
    </div>
  );
}
