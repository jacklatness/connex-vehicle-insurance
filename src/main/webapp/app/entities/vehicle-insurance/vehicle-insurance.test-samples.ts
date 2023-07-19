import { IVehicleInsurance, NewVehicleInsurance } from './vehicle-insurance.model';

export const sampleWithRequiredData: IVehicleInsurance = {
  id: 28426,
  name: 'green Dynamic',
  age: 14343,
  driving_experience: 1486,
  driver_record: 8492,
  claims: 5510,
  car_value: 19280,
  annual_mileage: 19481,
  insurance_history: 5530,
};

export const sampleWithPartialData: IVehicleInsurance = {
  id: 14548,
  name: 'Maserati',
  age: 13217,
  driving_experience: 23113,
  driver_record: 24588,
  claims: 2561,
  car_value: 233,
  annual_mileage: 769,
  insurance_history: 30565,
};

export const sampleWithFullData: IVehicleInsurance = {
  id: 12460,
  name: 'Southeast Northwest eos',
  age: 26370,
  driving_experience: 22668,
  driver_record: 16355,
  claims: 21416,
  car_value: 3292,
  annual_mileage: 15212,
  insurance_history: 32296,
};

export const sampleWithNewData: NewVehicleInsurance = {
  name: 'deploy Northwest',
  age: 753,
  driving_experience: 28637,
  driver_record: 24896,
  claims: 22738,
  car_value: 12415,
  annual_mileage: 24739,
  insurance_history: 20550,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
