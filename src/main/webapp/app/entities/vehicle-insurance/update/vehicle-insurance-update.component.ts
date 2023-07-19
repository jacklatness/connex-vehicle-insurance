import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { VehicleInsuranceFormService, VehicleInsuranceFormGroup } from './vehicle-insurance-form.service';
import { IVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';

@Component({
  standalone: true,
  selector: 'jhi-vehicle-insurance-update',
  templateUrl: './vehicle-insurance-update.component.html',
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
    this.vehicleInsuranceFormService.resetForm(this.editForm, vehicleInsurance);
  }
}
