import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {RentalService} from '../rental.service';
import {Cardetail} from '../cardetail';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-details',
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  template: `
  <article>
      <img
        class="listing-photo"
        [src]="cardetail?.photo"
        alt="Exterior photo of {{ cardetail?.brand }}"
        crossorigin
      />
      <section class="listing-description">
        <h2 class="listing-heading">{{ cardetail?.brand }}</h2>
        <p class="listing-location">{{ cardetail?.price }}, {{ cardetail?.price }}</p>
      </section>
      <section class="listing-features">
        <h2 class="section-heading">About this housing location</h2>
        <ul>
          <li>Units available: {{ cardetail?.price }}</li>
          <li>Does this location have wifi: {{ cardetail?.price }}</li>
        </ul>
      </section>
      <section class="listing-apply">
        <h2 class="section-heading">Apply now to live here</h2>
        <form [formGroup]="applyForm" (submit)="submitApplication()">
          <label for="first-name">First Name</label>
          <input id="first-name" type="text" formControlName="firstName" />
          <label for="last-name">Last Name</label>
          <input id="last-name" type="text" formControlName="lastName" />
          <label for="email">Email</label>
          <input id="email" type="email" formControlName="email" />
          <button type="submit" class="primary">Apply now</button>
        </form>
      </section>
    </article>
  `,
  styleUrls: ['./details.component.css'],
  styles: ``
})
export class DetailsComponent {
  route: ActivatedRoute = inject(ActivatedRoute);
  rentalService = inject(RentalService);
  cardetail: Cardetail | undefined;
  applyForm = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl('')
  });

  constructor() {
    const cardetailPlateNumber = String(this.route.snapshot.params['plateNumber']);
    this.rentalService.getAllCarsByPlateNumber(cardetailPlateNumber).then(cardetail => {
      this.cardetail = cardetail;
    });
  }

  /*constructor() {
    console.log('oooooookkkkkkkk',);
    const cardetailPlateNumber = String(this.route.snapshot.params['plateNumber']);
    this.cardetail = this.rentalService.getAllCarsByPlateNumber(cardetailPlateNumber);
  }*/
  submitApplication() {
    console.log('oookkkk');
    this.rentalService.submitApplication(
      this.applyForm.value.firstName ?? '',
      this.applyForm.value.lastName ?? '',
      this.applyForm.value.email ?? ''
    );
  }
}
