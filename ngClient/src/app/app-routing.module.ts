import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartComponent } from './basket/basket.component';
import { CategoryResultComponent } from './category-result/category-result.component';
import { FavouritesComponent } from './favourites/favourites.component';
import { MainPageComponent } from './main-page/main-page.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { PreordersComponent } from './preorders/preorders.component';
import { ProductComponent } from './product/product.component';
import { ProfilePageComponent } from './profile-page-component/profile-page.component';
import { ProfileProductsComponent } from './profile-products/profile-products.component';
import { ProfileSetupsComponent } from './profile-setups/profile-setups.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchComponent } from './search/search.component';
import { CheckoutComponent } from './checkout/checkout.component';


const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'search', component: SearchComponent },
  { path: 'product/:id', component: ProductComponent },
  { path: 'category/:id', component: CategoryResultComponent },
  {
    path: 'profile', component: ProfileComponent, children: [
      { path: '', component: ProfilePageComponent },
      { path: 'cart', component: CartComponent },
      { path: 'fav', component: FavouritesComponent },
      { path: 'orders', component: MyOrdersComponent },
      { path: 'preorders', component: PreordersComponent },
      { path: 'product', component: ProfileProductsComponent },
      // {path: 'inventory', component: InventoryComponent},
      { path: 'setups', component: ProfileSetupsComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
