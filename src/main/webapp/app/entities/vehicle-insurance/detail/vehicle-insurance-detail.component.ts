import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';

@Component({
  standalone: true,
  selector: 'jhi-vehicle-insurance-detail',
  templateUrl: './vehicle-insurance-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VehicleInsuranceDetailComponent {
  @Input() vehicleInsurance: IVehicleInsurance | null = null;

  basePremium: number = 0;
  factor: any = 0;
  randNumber: number = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected vehicleInsuranceService: VehicleInsuranceService) {}

  ngOnInit(): void {
    if (this.vehicleInsurance !== null) {
      this.calculateVehicleInsurance(this.vehicleInsurance.id);
    }
  }

  calculateVehicleInsurance(id: number) {
    this.vehicleInsuranceService.calculate(id).subscribe(response => {
      this.randNumber = Math.floor(100000 * Math.random());
      this.factor = response.body;
      this.getBasePremium();
    });
  }

  getBasePremium() {
    this.vehicleInsuranceService.getVehicleInsuranceBasePremium().subscribe(response => {
      this.basePremium = response.base_premium;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
