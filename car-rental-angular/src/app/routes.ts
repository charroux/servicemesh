import {Routes} from '@angular/router';
import {CarComponent} from './car/car.component';
import {DetailsComponent} from './details/details.component';

const routeConfig: Routes = [
    {
      path: '',
      component: CarComponent,
      title: 'Home page',
    },
    {
      path: 'details/:plateNumber',
      component: DetailsComponent,
      title: 'Home details',
    },
  ];
  export default routeConfig;

