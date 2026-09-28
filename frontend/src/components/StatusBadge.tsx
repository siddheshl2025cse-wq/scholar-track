type Props = {
  status: string;
};

const palette: Record<string, string> = {
  ELIGIBLE: 'success',
  INELIGIBLE: 'danger',
  PENDING_VERIFICATION: 'warning',
  APPROVED: 'info',
  DISBURSEMENT_PENDING: 'purple',
  DISBURSED: 'success',
  REJECTED: 'danger',
  VERIFIED: 'success',
  REJECTED_DOCUMENT: 'danger',
  PENDING: 'warning'
};

export default function StatusBadge({ status }: Props) {
  const key = status?.toUpperCase() || 'PENDING';
  return <span className={`status-badge ${palette[key] || 'neutral'}`}>{status}</span>;
}
