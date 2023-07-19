import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';

export const vehicleInsuranceResolve = (route: ActivatedRouteSnapshot): Observable<null | IVehicleInsurance> => {
  const id = route.params['id'];
  if (id) {
    return inject(VehicleInsuranceService)
      .find(id)
      .pipe(
        mergeMap((vehicleInsurance: HttpResponse<IVehicleInsurance>) => {
          if (vehicleInsurance.body) {
            return of(vehicleInsurance.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default vehicleInsuranceResolve;
