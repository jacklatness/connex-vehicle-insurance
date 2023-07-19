import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VehicleInsuranceDetailComponent } from './vehicle-insurance-detail.component';

describe('VehicleInsurance Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehicleInsuranceDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VehicleInsuranceDetailComponent,
              resolve: { vehicleInsurance: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(VehicleInsuranceDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load vehicleInsurance on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VehicleInsuranceDetailComponent);

      // THEN
      expect(instance.vehicleInsurance).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
