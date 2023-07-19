import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleInsurance, NewVehicleInsurance } from '../vehicle-insurance.model';

export type PartialUpdateVehicleInsurance = Partial<IVehicleInsurance> & Pick<IVehicleInsurance, 'id'>;

export type EntityResponseType = HttpResponse<IVehicleInsurance>;
export type EntityArrayResponseType = HttpResponse<IVehicleInsurance[]>;

@Injectable({ providedIn: 'root' })
export class VehicleInsuranceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-insurances');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleInsurance: NewVehicleInsurance): Observable<EntityResponseType> {
    return this.http.post<IVehicleInsurance>(this.resourceUrl, vehicleInsurance, { observe: 'response' });
  }

  update(vehicleInsurance: IVehicleInsurance): Observable<EntityResponseType> {
    return this.http.put<IVehicleInsurance>(
      `${this.resourceUrl}/${this.getVehicleInsuranceIdentifier(vehicleInsurance)}`,
      vehicleInsurance,
      { observe: 'response' }
    );
  }

  partialUpdate(vehicleInsurance: PartialUpdateVehicleInsurance): Observable<EntityResponseType> {
    return this.http.patch<IVehicleInsurance>(
      `${this.resourceUrl}/${this.getVehicleInsuranceIdentifier(vehicleInsurance)}`,
      vehicleInsurance,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleInsurance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleInsurance[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
