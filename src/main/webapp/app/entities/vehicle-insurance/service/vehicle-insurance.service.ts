import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { BasePremium, CarModel, IVehicleInsurance, NewVehicleInsurance } from '../vehicle-insurance.model';
import { VehicleInsuranceConfigService } from 'app/core/config/vehicle-insurance-config.service';
import { DATE_FORMAT } from 'app/config/input.constants';
import dayjs from 'dayjs';

export type PartialUpdateVehicleInsurance = Partial<IVehicleInsurance> & Pick<IVehicleInsurance, 'id'>;

type RestOf<T extends IVehicleInsurance | NewVehicleInsurance> = Omit<T, 'birthdate'> & {
  birthdate?: string | null;
};

export type RestVehicleInsurance = RestOf<IVehicleInsurance>;

export type NewRestVehicleInsurance = RestOf<NewVehicleInsurance>;

export type PartialUpdateRestVehicleInsurance = RestOf<PartialUpdateVehicleInsurance>;

export type EntityResponseType = HttpResponse<IVehicleInsurance>;
export type EntityArrayResponseType = HttpResponse<IVehicleInsurance[]>;

@Injectable({ providedIn: 'root' })
export class VehicleInsuranceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-insurances');
  protected connexApiBaseUrl = this.vehicleInsuranceConfigService.getConnexApiBaseUrl();

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
    protected vehicleInsuranceConfigService: VehicleInsuranceConfigService
  ) {}

  getVehicleInsuranceBasePremium() {
    return this.http.get<BasePremium>(`${this.connexApiBaseUrl}/base_premium.json`);
  }

  getCarModelList() {
    return this.http.get(`${this.connexApiBaseUrl}/car_model.json`);
  }

  create(vehicleInsurance: NewVehicleInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInsurance);
    return this.http
      .post<RestVehicleInsurance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vehicleInsurance: IVehicleInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInsurance);
    return this.http
      .put<RestVehicleInsurance>(`${this.resourceUrl}/${this.getVehicleInsuranceIdentifier(vehicleInsurance)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vehicleInsurance: PartialUpdateVehicleInsurance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleInsurance);
    return this.http
      .patch<RestVehicleInsurance>(`${this.resourceUrl}/${this.getVehicleInsuranceIdentifier(vehicleInsurance)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  calculate(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleInsurance>(`${this.resourceUrl}/calculate/${id}`, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVehicleInsurance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVehicleInsurance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVehicleInsuranceIdentifier(vehicleInsurance: Pick<IVehicleInsurance, 'id'>): number {
    return vehicleInsurance.id;
  }

  compareVehicleInsurance(o1: Pick<IVehicleInsurance, 'id'> | null, o2: Pick<IVehicleInsurance, 'id'> | null): boolean {
    return o1 && o2 ? this.getVehicleInsuranceIdentifier(o1) === this.getVehicleInsuranceIdentifier(o2) : o1 === o2;
  }

  addVehicleInsuranceToCollectionIfMissing<Type extends Pick<IVehicleInsurance, 'id'>>(
    vehicleInsuranceCollection: Type[],
    ...vehicleInsurancesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vehicleInsurances: Type[] = vehicleInsurancesToCheck.filter(isPresent);
    if (vehicleInsurances.length > 0) {
      const vehicleInsuranceCollectionIdentifiers = vehicleInsuranceCollection.map(
        vehicleInsuranceItem => this.getVehicleInsuranceIdentifier(vehicleInsuranceItem)!
      );
      const vehicleInsurancesToAdd = vehicleInsurances.filter(vehicleInsuranceItem => {
        const vehicleInsuranceIdentifier = this.getVehicleInsuranceIdentifier(vehicleInsuranceItem);
        if (vehicleInsuranceCollectionIdentifiers.includes(vehicleInsuranceIdentifier)) {
          return false;
        }
        vehicleInsuranceCollectionIdentifiers.push(vehicleInsuranceIdentifier);
        return true;
      });
      return [...vehicleInsurancesToAdd, ...vehicleInsuranceCollection];
    }
    return vehicleInsuranceCollection;
  }

  protected convertDateFromClient<T extends IVehicleInsurance | NewVehicleInsurance | PartialUpdateVehicleInsurance>(
    vehicleInsurance: T
  ): RestOf<T> {
    return {
      ...vehicleInsurance,
      birthdate: dayjs(vehicleInsurance.birthdate).format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restVehicleInsurance: RestVehicleInsurance): IVehicleInsurance {
    return {
      ...restVehicleInsurance,
      birthdate: restVehicleInsurance.birthdate ? dayjs(restVehicleInsurance.birthdate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVehicleInsurance>): HttpResponse<IVehicleInsurance> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVehicleInsurance[]>): HttpResponse<IVehicleInsurance[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
