export interface Patient {
  identifier: string;
  createdAt: string;
  updatedAt: string;
  firstName: string;
  lastName: string;
  birthDate: string;
  gender: string;
  address: string | null;
  phoneNumber: string | null;
}
