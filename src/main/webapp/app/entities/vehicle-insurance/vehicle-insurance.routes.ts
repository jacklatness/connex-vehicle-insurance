import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleInsuranceComponent } from './list/vehicle-insurance.component';
import { VehicleInsuranceDetailComponent } from './detail/vehicle-insurance-detail.component';
import { VehicleInsuranceUpdateComponent } from './update/vehicle-insurance-update.component';
import VehicleInsuranceResolve from './route/vehicle-insurance-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const vehicleInsuranceRoute: Routes = [
  {
    path: '',
    component: VehicleInsuranceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleInsuranceDetailComponent,
    resolve: {
      vehicleInsurance: VehicleInsuranceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleInsuranceUpdateComponent,
    resolve: {
      vehicleInsurance: VehicleInsuranceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleInsuranceUpdateComponent,
    resolve: {
      vehicleInsurance: VehicleInsuranceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default vehicleInsuranceRoute;
