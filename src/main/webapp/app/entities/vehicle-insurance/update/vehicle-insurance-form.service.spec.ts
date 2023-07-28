import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../vehicle-insurance.test-samples';

import { VehicleInsuranceFormService } from './vehicle-insurance-form.service';

describe('VehicleInsurance Form Service', () => {
  let service: VehicleInsuranceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehicleInsuranceFormService);
  });

  describe('Service methods', () => {
    describe('createVehicleInsuranceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVehicleInsuranceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            birthdate: expect.any(Object),
            age: expect.any(Object),
            driving_experience: expect.any(Object),
            driver_record: expect.any(Object),
            claims: expect.any(Object),
            category: expect.any(Object),
            make: expect.any(Object),
            model: expect.any(Object),
            year: expect.any(Object),
            car_value: expect.any(Object),
            annual_mileage: expect.any(Object),
            insurance_history: expect.any(Object),
          })
        );
      });

      it('passing IVehicleInsurance should create a new form with FormGroup', () => {
        const formGroup = service.createVehicleInsuranceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            birthdate: expect.any(Object),
            age: expect.any(Object),
            driving_experience: expect.any(Object),
            driver_record: expect.any(Object),
            claims: expect.any(Object),
            category: expect.any(Object),
            make: expect.any(Object),
            model: expect.any(Object),
            year: expect.any(Object),
            car_value: expect.any(Object),
            annual_mileage: expect.any(Object),
            insurance_history: expect.any(Object),
          })
        );
      });
    });

    describe('getVehicleInsurance', () => {
      it('should return NewVehicleInsurance for default VehicleInsurance initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVehicleInsuranceFormGroup(sampleWithNewData);

        const vehicleInsurance = service.getVehicleInsurance(formGroup) as any;

        expect(vehicleInsurance).toMatchObject(sampleWithNewData);
      });

      it('should return NewVehicleInsurance for empty VehicleInsurance initial value', () => {
        const formGroup = service.createVehicleInsuranceFormGroup();

        const vehicleInsurance = service.getVehicleInsurance(formGroup) as any;

        expect(vehicleInsurance).toMatchObject({});
      });

      it('should return IVehicleInsurance', () => {
        const formGroup = service.createVehicleInsuranceFormGroup(sampleWithRequiredData);

        const vehicleInsurance = service.getVehicleInsurance(formGroup) as any;

        expect(vehicleInsurance).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVehicleInsurance should not enable id FormControl', () => {
        const formGroup = service.createVehicleInsuranceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVehicleInsurance should disable id FormControl', () => {
        const formGroup = service.createVehicleInsuranceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
