import {Component, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Cardetail} from '../cardetail';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-car-detail',
  imports: [RouterLink, CommonModule],
  template: `
    <section class="listing">
          <img
            class="listing-photo"
            [src]="cardetail.photo"
            alt="Exterior photo of {{ cardetail.brand }}"
            crossorigin
          />
          <h2 class="listing-heading">{{ cardetail.brand }}</h2>
          <p class="listing-location">{{ cardetail.price }} Euros</p>
          <a [routerLink]="['/details', cardetail.plateNumber]">Learn More</a>
        </section>
      `,
      styleUrls: ['./car-detail.component.css'],
    })
export class CarDetailComponent {
  @Input() cardetail!: Cardetail;
}
