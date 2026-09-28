import { NavLink } from 'react-router-dom';

const items = [
  { to: '/student/dashboard', label: 'Student Dashboard' },
  { to: '/scholarships', label: 'Scholarships' },
  { to: '/verifier/dashboard', label: 'Verifier Dashboard' },
  { to: '/verifier/disbursements', label: 'Disbursements' }
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <div className="brand-mark">S</div>
        <div>
          <strong>ScholarTrack</strong>
          <small>Scholarship Office</small>
        </div>
      </div>

      <nav className="nav-menu">
        {items.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) => (isActive ? 'nav-item active' : 'nav-item')}
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
