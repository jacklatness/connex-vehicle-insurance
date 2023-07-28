import dayjs from 'dayjs';

export interface IVehicleInsurance {
  id: number;
  name?: string | null;
  birthdate?: dayjs.Dayjs | null;
  age?: number | null;
  driving_experience?: number | null;
  driver_record?: number | null;
  claims?: number | null;
  category?: string | null;
  make?: string | null;
  model?: string | null;
  year?: number | null;
  car_value?: number | null;
  annual_mileage?: number | null;
  insurance_history?: number | null;
}

export interface BasePremium {
  base_premium: number;
}

export interface CarModel {
  Category: string;
  Make: string;
  Model: string;
  Year: number;
}

export type NewVehicleInsurance = Omit<IVehicleInsurance, 'id'> & { id: null };
