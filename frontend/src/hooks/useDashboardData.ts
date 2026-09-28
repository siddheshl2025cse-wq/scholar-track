import { useEffect, useState } from 'react';
import { api } from '../services/api';
import type { Application, Scheme, Student } from '../types';

export function useDashboardData() {
  const [students, setStudents] = useState<Student[]>([]);
  const [schemes, setSchemes] = useState<Scheme[]>([]);
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const load = async () => {
      try {
        const [studentsRes, schemesRes, applicationsRes] = await Promise.all([
          api.get('/students'),
          api.get('/schemes'),
          api.get('/applications')
        ]);

        setStudents(studentsRes.data);
        setSchemes(schemesRes.data);
        setApplications(applicationsRes.data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Unable to load dashboard data.');
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  return { students, schemes, applications, loading, error };
}
