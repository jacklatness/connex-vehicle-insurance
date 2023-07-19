import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IVehicleInsurance } from '../vehicle-insurance.model';

@Component({
  standalone: true,
  selector: 'jhi-vehicle-insurance-detail',
  templateUrl: './vehicle-insurance-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VehicleInsuranceDetailComponent {
  @Input() vehicleInsurance: IVehicleInsurance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
