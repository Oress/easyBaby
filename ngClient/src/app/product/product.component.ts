import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { Product } from '../model/Product';
import { ProductImage } from '../model/ProductImage';
import { ProductWebService } from '../services/web/product-web.service';
import { AddToFav, RemoveFromFav, SetCartItem } from '../store/appActions';
import { isFav, selectIsInCart } from '../store/appSelectors';
import { AppState } from '../store/AppState';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  product: Product;
  firstImage: ProductImage;
  images: any[] = [];
  isLiked = false;
  isInCart = false;

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute,
    private productService: ProductWebService,
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = parseInt(params.id, 10);
      this.productService.getById(id).subscribe(res => this.initProduct(res));
      this.store.select(isFav(id)).subscribe(val => this.isLiked = val);
      this.store.select(selectIsInCart(id)).subscribe(val => this.isInCart = val);
    });
  }

  private initProduct(prod: Product) {
    this.product = prod;
    if (prod.images && prod.images.length > 0) {
      this.firstImage = prod.images[0];
      this.images = prod.images.map(val => ({
        source: `/api/product/img?uid=${val.path}`,
        thumbnail: `/api/product/img?uid=${val.path_sm}`,
        title: 'Sopranos 1'
      }));
    } else {
      this.images = [
        {
          source: 'assets/images/no_image.png',
          thumbnail: 'assets/images/no_image.png'
        }
      ];
    }
  }

  toggleLiked() {
    if (!this.isLiked) {
      this.store.dispatch(new AddToFav(this.product.id));
    } else {
      this.store.dispatch(new RemoveFromFav(this.product.id));
    }
  }

  addToCart() {
    this.store.dispatch(new SetCartItem(this.product.id, 1));
  }
}
