import { ICustomer } from 'app/shared/model/customer.model';

export interface IAisleDiscount {
  id?: number;
  aisleId?: string | null;
  product?: string | null;
  discount?: string | null;
  customers?: ICustomer[] | null;
}

export const defaultValue: Readonly<IAisleDiscount> = {};
