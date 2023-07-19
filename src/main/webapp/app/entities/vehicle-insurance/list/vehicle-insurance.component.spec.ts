import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VehicleInsuranceService } from '../service/vehicle-insurance.service';

import { VehicleInsuranceComponent } from './vehicle-insurance.component';

describe('VehicleInsurance Management Component', () => {
  let comp: VehicleInsuranceComponent;
  let fixture: ComponentFixture<VehicleInsuranceComponent>;
  let service: VehicleInsuranceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'vehicle-insurance', component: VehicleInsuranceComponent }]),
        HttpClientTestingModule,
        VehicleInsuranceComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(VehicleInsuranceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleInsuranceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VehicleInsuranceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.vehicleInsurances?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to vehicleInsuranceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getVehicleInsuranceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getVehicleInsuranceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
