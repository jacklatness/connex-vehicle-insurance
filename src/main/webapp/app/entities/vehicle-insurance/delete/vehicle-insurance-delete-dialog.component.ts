import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './vehicle-insurance-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VehicleInsuranceDeleteDialogComponent {
  vehicleInsurance?: IVehicleInsurance;

  constructor(protected vehicleInsuranceService: VehicleInsuranceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleInsuranceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
