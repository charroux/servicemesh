import { Injectable } from '@angular/core';
import {Cardetail} from './cardetail';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  url: string = 'http://localhost:3000/cars';

  /*cardetailList: Cardetail[] = [
    {
      plateNumber: 'AA11BB',
      brand: 'Ferrari',
      price: 1000,
      photo: `assets/ferrari.svg`,
    },
    {
      plateNumber: 'BB22CC',
      brand: 'Porsche',
      price: 2000,
      photo: `assets/porsche.svg`,
    },
  ];*/

  constructor() { }

  async getAllCars(): Promise<Cardetail[]> {
    const data = await fetch(this.url);
    return await data.json() ?? [];
  }

  async getAllCarsByPlateNumber(plateNumber: string): Promise<Cardetail | undefined> {
    const data = await fetch(`${this.url}/${plateNumber}`);
    return await data.json() ?? {};
  }

  /*getAllCars(): Cardetail[] {
    return this.cardetailList;
  }
  getAllCarsByPlateNumber(plateNumber: string): Cardetail | undefined {
    return this.cardetailList.find((cardetail) => cardetail.plateNumber === plateNumber);
  }*/

  submitApplication(firstName: string, lastName: string, email: string) {
    console.log(
      `Homes application received: firstName: ${firstName}, lastName: ${lastName}, email: ${email}.`,
    );
  }
}
