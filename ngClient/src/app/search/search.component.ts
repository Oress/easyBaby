import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CategoryUrlFormatter } from '../shared/category-url-formatter';
import { CategoryWebService } from '../services/web/category-web.service';
import { Category } from '../model/Category';
import { ProductWebService } from '../services/web/product-web.service';
import { Product } from '../model/Product';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  @Output() categorySelected: EventEmitter<Category> = new EventEmitter<Category>();
  @Output() productSelected: EventEmitter<Product> = new EventEmitter<Product>();

  Arr = Array;
  allCategories: Category[];
  allCategoryViews: CategoryView[];

  filteredCategories: CategoryView[];

  products: Product[];

  constructor(
    private categoryService: CategoryWebService,
    private productService: ProductWebService,
  ) { }

  ngOnInit() {
    this.categoryService.getAll().subscribe(categories => {
      const parents = categories.filter(cat => cat.parentId == null);
      this.allCategoryViews = [];

      parents.forEach(parent => this.fillCategoryView(parent, categories, this.allCategoryViews));

      this.allCategories = categories;
      this.filteredCategories = this.allCategoryViews;
    });
  }

  private fillCategoryView(item: Category, items: Category[], result: CategoryView[], level: number = 0): void {
    const category: CategoryView = {
      id: item.id,
      title: item.title,
      // routerLink: this.cuf.createLink(item),
      level
    };
    result.push(category);

    const children: Category[] = items.filter(cat => cat.parentId === item.id);

    if (children.length) {
      children.map(child => this.fillCategoryView(child, items, result, level + 1));
    }
  }

  selectCategory(item: CategoryView) {
    this.categorySelected.emit(this.allCategories.find(cat => cat.id === item.id));
  }

  selectProduct(item: Product) {
    this.productSelected.emit(item);
  }

  public search(query: string) {
    if (query) {
      const q = query.toUpperCase();
      this.filteredCategories = this.allCategoryViews.filter(item => item.title.toUpperCase().indexOf(q) !== -1);
      this.productService.search(query).subscribe(res => {
        this.products = res;
      })
    } else {
      this.filteredCategories = this.allCategoryViews;
    }
    console.log('filtered', this.filteredCategories);
  }

  public getClass(item: CategoryView) {
    return 'pl-' + item.level;
  }

}

interface CategoryView {
  id?: number;
  title?: string;
  routerLink?: string;
  children?: CategoryView[];
  level?: number;
}
