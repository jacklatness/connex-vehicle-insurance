import dayjs from 'dayjs/esm';

import { IVehicleInsurance, NewVehicleInsurance } from './vehicle-insurance.model';

export const sampleWithRequiredData: IVehicleInsurance = {
  id: 9486,
  name: 'voluptates forenenst',
  birthdate: dayjs('2023-07-18'),
  age: 18537,
  driving_experience: 12330,
  driver_record: 16484,
  claims: 24413,
  car_value: 21903,
  annual_mileage: 32737,
  insurance_history: 32409,
};

export const sampleWithPartialData: IVehicleInsurance = {
  id: 704,
  name: 'doubtfully Northeast',
  birthdate: dayjs('2023-07-19'),
  age: 22748,
  driving_experience: 18269,
  driver_record: 10943,
  claims: 26153,
  car_category: 'Southwest ivory Money',
  car_make: 'fragrant',
  car_model: 'methodical snowplow Northwest',
  car_year: 13704,
  car_value: 23092,
  annual_mileage: 18323,
  insurance_history: 31781,
};

export const sampleWithFullData: IVehicleInsurance = {
  id: 4455,
  name: 'teal',
  birthdate: dayjs('2023-07-18'),
  age: 14830,
  driving_experience: 6649,
  driver_record: 940,
  claims: 21727,
  car_category: 'Secured',
  car_make: 'World Hatchback whose',
  car_model: 'enim deploy',
  car_year: 1122,
  car_value: 29781,
  annual_mileage: 17651,
  insurance_history: 31270,
};

export const sampleWithNewData: NewVehicleInsurance = {
  name: 'blue where Hybrid',
  birthdate: dayjs('2023-07-18'),
  age: 12989,
  driving_experience: 21621,
  driver_record: 6193,
  claims: 2975,
  car_value: 3859,
  annual_mileage: 16930,
  insurance_history: 2623,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
