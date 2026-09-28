export type Student = {
  id: number;
  studentName: string;
  email: string;
  phone: string;
  dateOfBirth: string;
  gender: string;
  category: string;
  annualIncome: number;
  marksPercentage: number;
  collegeName: string;
  course: string;
  yearOfStudy: number;
};

export type Scheme = {
  id: number;
  schemeName: string;
  description: string;
  provider: string;
  incomeLimit: number;
  minimumMarks: number;
  eligibleCategories: string[];
  requiredDocuments: string[];
  applicationDeadline: string;
  scholarshipAmount: number;
  active: boolean;
};

export type Application = {
  id: number;
  applicationNumber: string;
  studentId: number;
  studentName: string;
  schemeId: number;
  schemeName: string;
  applicationDate: string;
  eligibilityStatus: 'ELIGIBLE' | 'INELIGIBLE';
  applicationStatus: string;
  submitted: boolean;
  remarks: string;
};

export type Verification = {
  id: number;
  applicationId: number;
  verifierName: string;
  verificationStatus: string;
  remarks: string;
  verifiedAt: string;
  documentStatus: string;
};

export type Disbursement = {
  id: number;
  applicationId: number;
  amount: number;
  disbursementStatus: string;
  transactionReference: string;
  disbursementDate: string;
  remarks: string;
};

export type ApiError = {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  details?: Record<string, string>;
};
