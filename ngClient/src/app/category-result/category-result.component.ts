import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng';
import {CategoryWebService} from '../services/web/category-web.service';
import {ActivatedRoute} from '@angular/router';
import {Category} from '../model/Category';
import {CategoryUrlFormatter} from '../shared/category-url-formatter';
import {ProductWebService} from '../services/web/product-web.service';
import {Product} from '../model/Product';

@Component({
  selector: 'app-category-result',
  templateUrl: './category-result.component.html',
  styleUrls: ['./category-result.component.scss']
})
export class CategoryResultComponent implements OnInit {
  public categories: MenuItem[] = [];
  public items: Product[] = [];
  private cuf: CategoryUrlFormatter = new CategoryUrlFormatter();

  constructor(private route: ActivatedRoute,
              private categoryService: CategoryWebService,
              private productService: ProductWebService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params.id;
      console.log('Product id: ', id);
      this.categoryService.getChainByComponent(id.toString()).subscribe(categories => {
        console.log(categories);
        this.constructItems(categories);
      });
      this.productService.getByCategoryId(id.toString()).subscribe(prods => {
        console.log(prods);
        this.items = prods;

      });

    });
  }

  private constructItems(categories: Category[]) {
    const seq: Category[] = [];
    let category = categories.find(cat => cat.parentId === null);
    do {
      seq.push(category);
      category = categories.find(cat => cat.parentId === category.id);
    } while (category);

    this.categories = seq.map(item => ({label: item.title, routerLink: this.cuf.createLink(item)}));
  }


}
