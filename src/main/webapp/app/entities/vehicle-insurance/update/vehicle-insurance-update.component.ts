import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { VehicleInsuranceFormService, VehicleInsuranceFormGroup } from './vehicle-insurance-form.service';
import { CarModel, IVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';
import dayjs from 'dayjs/esm';

@Component({
  standalone: true,
  selector: 'jhi-vehicle-insurance-update',
  templateUrl: './vehicle-insurance-update.component.html',
  styleUrls: ['./vehicle-insurance-update.component.scss'],
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VehicleInsuranceUpdateComponent implements OnInit {
  isSaving = false;
  vehicleInsurance: IVehicleInsurance | null = null;

  editForm: VehicleInsuranceFormGroup = this.vehicleInsuranceFormService.createVehicleInsuranceFormGroup();

  constructor(
    protected vehicleInsuranceService: VehicleInsuranceService,
    protected vehicleInsuranceFormService: VehicleInsuranceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicleInsurance }) => {
      this.getCarModelList();
      this.vehicleInsurance = vehicleInsurance;
      if (vehicleInsurance) {
        this.updateForm(vehicleInsurance);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicleInsurance = this.vehicleInsuranceFormService.getVehicleInsurance(this.editForm);
    if (vehicleInsurance.id !== null) {
      this.subscribeToSaveResponse(this.vehicleInsuranceService.update(vehicleInsurance));
    } else {
      this.subscribeToSaveResponse(this.vehicleInsuranceService.create(vehicleInsurance));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleInsurance>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vehicleInsurance: IVehicleInsurance): void {
    this.vehicleInsurance = vehicleInsurance;
    this.editForm.controls.birthdate.patchValue('1991-10-10');
    this.vehicleInsuranceFormService.resetForm(this.editForm, vehicleInsurance);
    this.disableSelect();
  }

  protected initEditForm() {
    if (this.editForm.controls.car_category.value !== null) {
      this.getMake(false);
    }

    if (this.editForm.controls.car_make.value !== null) {
      this.getModel(false);
    }

    if (this.editForm.controls.car_model.value !== null) {
      this.getYear(false);
    }
  }

  protected calculateAge(): void {
    const birthdate = this.editForm.controls.birthdate.value;
    if (birthdate) {
      var timeDiff = Math.abs(Date.now() - new Date(birthdate).getTime());
      //Used Math.floor instead of Math.ceil
      //so 26 years and 140 days would be considered as 26, not 27.
      this.editForm.controls.age.patchValue(Math.floor(timeDiff / (1000 * 3600 * 24) / 365));
    }
  }

  carCategory = new Set<string>();
  carMake = new Set<string>();
  carModel = new Set<string>();
  carYear = new Set<number>();
  carModelList: CarModel[] = [];
  getCarModelList() {
    this.disableSelect();
    this.vehicleInsuranceService.getCarModelList().subscribe((response: any) => {
      this.carModelList = response.data.car_Model_Lists.results;

      this.carModelList.forEach((item: any) => {
        this.carCategory.add(item.Category);
      });

      this.initEditForm();
    });
  }

  getMake(onChange: boolean) {
    if (onChange) {
      this.clearSelect(true, true, true);
    }

    const category = this.editForm.controls.car_category.value;
    const makeListByCategory = this.carModelList.filter((itm: CarModel) => itm.Category === category);

    makeListByCategory.forEach((item: any) => {
      this.carMake.add(item.Make);
    });

    this.editForm.controls.car_make.enable();
  }

  getModel(onChange: boolean) {
    if (onChange) {
      this.clearSelect(false, true, true);
    }

    const category = this.editForm.controls.car_category.value;
    const make = this.editForm.controls.car_make.value;
    const modelListByMake = this.carModelList.filter((itm: CarModel) => itm.Category === category && itm.Make === make);

    modelListByMake.forEach((item: any) => {
      this.carModel.add(item.Model);
    });

    this.editForm.controls.car_model.enable();
  }

  getYear(onChange: boolean) {
    if (onChange) {
      this.clearSelect(false, false, true);
    }

    const category = this.editForm.controls.car_category.value;
    const make = this.editForm.controls.car_make.value;
    const model = this.editForm.controls.car_model.value;
    const yearListByMake = this.carModelList.filter(
      (itm: CarModel) => itm.Category === category && itm.Make === make && itm.Model === model
    );

    yearListByMake.forEach((item: any) => {
      this.carYear.add(item.Year);
    });

    this.editForm.controls.car_year.enable();
  }

  disableSelect() {
    if (this.vehicleInsurance?.car_make == null) {
      this.editForm.controls.car_make.disable();
    }

    if (this.vehicleInsurance?.car_model == null) {
      this.editForm.controls.car_model.disable();
    }

    if (this.vehicleInsurance?.car_year == null) {
      this.editForm.controls.car_year.disable();
    }
  }

  clearSelect(clearMake: boolean, clearModel: boolean, clearYear: boolean) {
    if (clearMake) {
      this.carMake = new Set<string>();
      this.editForm.controls.car_make.patchValue(null);
    }

    if (clearModel) {
      this.carModel = new Set<string>();
      this.editForm.controls.car_model.patchValue(null);
      this.editForm.controls.car_model.disable();
    }

    if (clearYear) {
      this.carYear = new Set<number>();
      this.editForm.controls.car_year.patchValue(null);
      this.editForm.controls.car_year.disable();
    }
  }
}
