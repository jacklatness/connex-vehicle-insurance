import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVehicleInsurance, NewVehicleInsurance } from '../vehicle-insurance.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVehicleInsurance for edit and NewVehicleInsuranceFormGroupInput for create.
 */
type VehicleInsuranceFormGroupInput = IVehicleInsurance | PartialWithRequiredKeyOf<NewVehicleInsurance>;

type VehicleInsuranceFormDefaults = Pick<NewVehicleInsurance, 'id'>;

type VehicleInsuranceFormGroupContent = {
  id: FormControl<IVehicleInsurance['id'] | NewVehicleInsurance['id']>;
  name: FormControl<IVehicleInsurance['name']>;
  age: FormControl<IVehicleInsurance['age']>;
  driving_experience: FormControl<IVehicleInsurance['driving_experience']>;
  driver_record: FormControl<IVehicleInsurance['driver_record']>;
  claims: FormControl<IVehicleInsurance['claims']>;
  car_value: FormControl<IVehicleInsurance['car_value']>;
  annual_mileage: FormControl<IVehicleInsurance['annual_mileage']>;
  insurance_history: FormControl<IVehicleInsurance['insurance_history']>;
};

export type VehicleInsuranceFormGroup = FormGroup<VehicleInsuranceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VehicleInsuranceFormService {
  createVehicleInsuranceFormGroup(vehicleInsurance: VehicleInsuranceFormGroupInput = { id: null }): VehicleInsuranceFormGroup {
    const vehicleInsuranceRawValue = {
      ...this.getFormDefaults(),
      ...vehicleInsurance,
    };
    return new FormGroup<VehicleInsuranceFormGroupContent>({
      id: new FormControl(
        { value: vehicleInsuranceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(vehicleInsuranceRawValue.name, {
        validators: [Validators.required],
      }),
      age: new FormControl(vehicleInsuranceRawValue.age, {
        validators: [Validators.required],
      }),
      driving_experience: new FormControl(vehicleInsuranceRawValue.driving_experience, {
        validators: [Validators.required],
      }),
      driver_record: new FormControl(vehicleInsuranceRawValue.driver_record, {
        validators: [Validators.required],
      }),
      claims: new FormControl(vehicleInsuranceRawValue.claims, {
        validators: [Validators.required],
      }),
      car_value: new FormControl(vehicleInsuranceRawValue.car_value, {
        validators: [Validators.required],
      }),
      annual_mileage: new FormControl(vehicleInsuranceRawValue.annual_mileage, {
        validators: [Validators.required],
      }),
      insurance_history: new FormControl(vehicleInsuranceRawValue.insurance_history, {
        validators: [Validators.required],
      }),
    });
  }

  getVehicleInsurance(form: VehicleInsuranceFormGroup): IVehicleInsurance | NewVehicleInsurance {
    return form.getRawValue() as IVehicleInsurance | NewVehicleInsurance;
  }

  resetForm(form: VehicleInsuranceFormGroup, vehicleInsurance: VehicleInsuranceFormGroupInput): void {
    const vehicleInsuranceRawValue = { ...this.getFormDefaults(), ...vehicleInsurance };
    form.reset(
      {
        ...vehicleInsuranceRawValue,
        id: { value: vehicleInsuranceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VehicleInsuranceFormDefaults {
    return {
      id: null,
    };
  }
}
