export interface IVehicleInsurance {
  id: number;
  name?: string | null;
  age?: number | null;
  driving_experience?: number | null;
  driver_record?: number | null;
  claims?: number | null;
  car_value?: number | null;
  annual_mileage?: number | null;
  insurance_history?: number | null;
}

export interface BasePremium {
  base_premium: number;
}

export interface CarModel {
  category: string;
  make: string;
  model: string;
  year: number;
}

export type NewVehicleInsurance = Omit<IVehicleInsurance, 'id'> & { id: null };
