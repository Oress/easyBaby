import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng';
import { Category } from '../model/Category';
import { CategoryWebService } from '../services/web/category-web.service';
import { CategoryUrlFormatter } from '../shared/category-url-formatter';
import { Product } from '../model/Product';
import { ProductWebService } from '../services/web/product-web.service';

@Component({
  selector: 'app-main-page',
  templateUrl: 'main-page.component.html'
})

export class MainPageComponent implements OnInit {
  products: Product[];

  constructor(private productService: ProductWebService) {
  }

  ngOnInit() {
    this.productService.getMainPage().subscribe(res => {
      this.products = res;
    });
  }
}
