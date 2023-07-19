import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VehicleInsuranceFormService } from './vehicle-insurance-form.service';
import { VehicleInsuranceService } from '../service/vehicle-insurance.service';
import { IVehicleInsurance } from '../vehicle-insurance.model';

import { VehicleInsuranceUpdateComponent } from './vehicle-insurance-update.component';

describe('VehicleInsurance Management Update Component', () => {
  let comp: VehicleInsuranceUpdateComponent;
  let fixture: ComponentFixture<VehicleInsuranceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vehicleInsuranceFormService: VehicleInsuranceFormService;
  let vehicleInsuranceService: VehicleInsuranceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VehicleInsuranceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VehicleInsuranceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleInsuranceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vehicleInsuranceFormService = TestBed.inject(VehicleInsuranceFormService);
    vehicleInsuranceService = TestBed.inject(VehicleInsuranceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vehicleInsurance: IVehicleInsurance = { id: 456 };

      activatedRoute.data = of({ vehicleInsurance });
      comp.ngOnInit();

      expect(comp.vehicleInsurance).toEqual(vehicleInsurance);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicleInsurance>>();
      const vehicleInsurance = { id: 123 };
      jest.spyOn(vehicleInsuranceFormService, 'getVehicleInsurance').mockReturnValue(vehicleInsurance);
      jest.spyOn(vehicleInsuranceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleInsurance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleInsurance }));
      saveSubject.complete();

      // THEN
      expect(vehicleInsuranceFormService.getVehicleInsurance).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vehicleInsuranceService.update).toHaveBeenCalledWith(expect.objectContaining(vehicleInsurance));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicleInsurance>>();
      const vehicleInsurance = { id: 123 };
      jest.spyOn(vehicleInsuranceFormService, 'getVehicleInsurance').mockReturnValue({ id: null });
      jest.spyOn(vehicleInsuranceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleInsurance: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleInsurance }));
      saveSubject.complete();

      // THEN
      expect(vehicleInsuranceFormService.getVehicleInsurance).toHaveBeenCalled();
      expect(vehicleInsuranceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicleInsurance>>();
      const vehicleInsurance = { id: 123 };
      jest.spyOn(vehicleInsuranceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleInsurance });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vehicleInsuranceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
