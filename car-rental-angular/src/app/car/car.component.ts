import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RentalService} from '../rental.service';
import {CarDetailComponent} from '../car-detail/car-detail.component';
import {Cardetail} from '../cardetail';

@Component({
  selector: 'app-car',
  imports: [CommonModule, CarDetailComponent],
  template: `
    <section>
          <form>
            <input type="text" placeholder="Filter by brand" #filter>
            <button class="primary" type="button" (click)="filterResults(filter.value)">Search</button>
          </form>
        </section>
        <section class="results">
        <app-car-detail *ngFor="let cardetail of filteredCarList" [cardetail]="cardetail"></app-car-detail>
    </section>
      `,
      styleUrls: ['./car.component.css'],
})
export class CarComponent {
  readonly baseUrl = 'https://angular.dev/assets/images/tutorials/common';
  cardetailList: Cardetail[] = [];
  filteredCarList: Cardetail[] = [];
  rentalService: RentalService = inject(RentalService);
  
  constructor() {
    this.rentalService.getAllCars().then((carList: Cardetail[]) => {
      this.cardetailList = carList;
      this.filteredCarList = carList;
    });
    //this.cardetailList = this.rentalService.getAllCars();
    //this.filteredCarList = this.cardetailList;
  }
  filterResults(text: string) {
    if (!text) {
      this.filteredCarList = this.cardetailList;
      return;
    }
    this.filteredCarList = this.cardetailList.filter(
      cardetail => cardetail?.brand.toLowerCase().includes(text.toLowerCase())
    );
  }
}


