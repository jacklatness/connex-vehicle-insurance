import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class VehicleInsuranceConfigService {
  private connexApiBaseUrl = 'https://storage.googleapis.com/connex-th/insurance_assignment';

  getConnexApiBaseUrl() {
    return `${this.connexApiBaseUrl}`;
  }
}
