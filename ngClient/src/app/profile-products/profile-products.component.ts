import {Component, OnInit, ViewChild} from '@angular/core';
import {Product} from '../model/Product';
import {ProductWebService} from '../services/web/product-web.service';
import {LazyLoadEvent, Table} from 'primeng';
import {User} from '../model/user';
import {UserService} from '../services/user.service';
import {AppState} from '../store/AppState';
import {Store} from '@ngrx/store';
import {selectUserData} from '../store/appSelectors';

@Component({
  selector: 'app-products',
  templateUrl: './profile-products.component.html',
  styleUrls: ['./profile-products.component.scss']
})
export class ProfileProductsComponent implements OnInit {
  @ViewChild('dt') table: Table;
  loading = true;
  pageSize = 15;
  totalRecords = 15;
  user: User;

  products: Product[] = [];
  displayDialog = false;

  isNew = false;
  selectedProduct: Product;

  constructor(private productService: ProductWebService,
              private staffService: UserService,
              private store: Store<AppState>) {
  }

  ngOnInit() {
    this.productService.getCount().subscribe(count => this.totalRecords = count);
    this.store.select(selectUserData).subscribe(data => this.user = data);
  }

  loadPage(event: LazyLoadEvent) {
    this.loading = true;

    const pageNum = Math.floor(event.first / this.pageSize);
    this.productService.getPage(pageNum, this.pageSize).subscribe(page => {
      this.products = page;
      this.loading = false;
      const ids = page.map(item => item.addedById);
      this.staffService.loadStaff(ids);
    });
  }

  public getStaffById(id: number): User {
    return this.staffService.getById(id);
  }

  public onProductAdded(product: Product): void {
    this.displayDialog = false;
    location.reload();
  }

  public selectProduct(product: Product): void {
    this.displayDialog = true;
    this.isNew = false;
    this.selectedProduct = product;
  }

  public addProduct(): void {
    this.displayDialog = true;
    this.isNew = true;
    this.selectedProduct = {};
  }
}
