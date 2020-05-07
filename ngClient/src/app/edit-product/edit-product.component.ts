import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { User } from '../model/user';
import { Product } from '../model/Product';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService, OverlayPanel } from 'primeng';
import { Category } from '../model/Category';
import { CategoryWebService } from '../services/web/category-web.service';
import { ProductWebService } from '../services/web/product-web.service';
import { ProdInvItemComponent } from '../prod-inv-item/prod-inv-item.component';
import { ProductImage } from '../model/ProductImage';
import Inputmask from 'inputmask';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  @ViewChild('iitem') iitem: ProdInvItemComponent;
  @ViewChild('op') op: OverlayPanel;
  @ViewChild('pirce') priceComp: HTMLInputElement;

  @Output() saved: EventEmitter<Product> = new EventEmitter();
  @Input() user: User;
  @Input() product: Product;
  @Input() isNew = false;
  loading = true;

  categoriesSuggestions: Category[];
  allCategories: Category[];

  images: any[] = [];

  selectedImage: ProductImage = null;
  displayFullImage = false;

  userform: FormGroup;
  submitted: boolean;

  constructor(private fb: FormBuilder,
    private messageService: MessageService,
    private productService: ProductWebService,
    private categoryWebService: CategoryWebService) {
  }

  ngOnInit() {
    if (!this.isNew) {
      this.productService.getById(this.product.id).subscribe(data => {
        this.refreshProduct(data);
        this.loading = false;
      });
    } else {
      this.product = {
        images: [],
        inventoryItems: [],
        // categoryIds: []
      };
      this.refreshProduct(this.product);
      this.loading = false;
    }
    this.categoryWebService.getAll().subscribe(data => this.allCategories = data);
    // Inputmask({alias: 'curreny'}).mask(this.priceComp);
  }

  private refreshProduct(data: Product) {
    this.product = data;
    if (this.product.images) {

      this.product.images.forEach(img => {
        const pathSm = `/api/product/img?uid=${img.path_sm}`;
        const path = `/api/product/img?uid=${img.path}`;
        this.images.push({ source: path, thumbnail: pathSm });
      });
    }

    this.userform = this.fb.group({
      title: new FormControl(this.product.title, Validators.required),
      price: new FormControl(this.product.price, Validators.required),
      description: new FormControl(this.product.description),
      categories: new FormControl(this.product.categoryIds
        ? this.allCategories.filter(cat => this.product.categoryIds.includes(cat.id))
        : []),
    });
  }

  onSave() {
    const prodToSave: Product = {};
    prodToSave.id = this.product.id;
    prodToSave.addedById = this.product.addedById;
    prodToSave.price = this.userform.get('price').value;
    prodToSave.title = this.userform.get('title').value;
    prodToSave.description = this.userform.get('description').value;
    prodToSave.categoryIds = this.userform.get('categories').value.map(item => item.id);
    prodToSave.inventoryItems = this.iitem.getItems();
    prodToSave.images = this.product.images;

    this.loading = true;
    this.productService.saveOrUpdate(prodToSave).subscribe(result => {
      this.refreshProduct(result);
      this.loading = false;
      this.saved.emit(result);
    });
  }

  filterCategories(event: any) {
    this.categoriesSuggestions = this.allCategories.filter(cat =>
      this.userform.get('categories').value.findIndex(val => val.id === cat.id) === -1
      && cat.title.toUpperCase().includes(event.query.toUpperCase()));
  }

  transformAmount(event: FocusEvent) {
    // this.formattedAmount = this.currencyPipe.transform(this.formattedAmount, '$');
    // element.target.value = this.formattedAmount;
  }

  beforeUpload(event: any) {
    event.formData.append('prodid', this.product.id.toString());
    console.log(event.formData);
  }

  selectImage(image: ProductImage, event: any, target: any) {
    this.op.toggle(event, target);
    this.selectedImage = image;
    this.displayFullImage = true;
  }

  removeImage(image: ProductImage) {
    this.product.images = this.product.images.filter(img => img.id !== image.id);
  }
}
