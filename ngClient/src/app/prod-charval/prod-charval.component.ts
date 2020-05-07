import {Component, Input, OnInit} from '@angular/core';
import {ProdCharVal} from '../model/prodCharVal';
import {ProdChar} from '../model/prodChar';
import {ProdCharWebService} from '../services/web/product-char-web.service';

@Component({
  selector: 'app-prod-charval',
  templateUrl: './prod-charval.component.html',
  styleUrls: ['./prod-charval.component.scss']
})
export class ProdCharvalComponent implements OnInit {
  @Input() charVals: ProdCharVal[] = [];

  displayDialog: boolean;
  selectedItem: ProdCharVal;

  prodCharsSugg: ProdChar[];

  newItem: boolean;
  item: ProdCharVal;

  constructor(private prodCharWebService: ProdCharWebService) {
  }

  ngOnInit() {
  }

  showDialogToAdd() {
    this.newItem = true;
    this.item = {};
    this.displayDialog = true;
  }

  save() {
    const cars = [...this.charVals];
    if (this.newItem) {
      cars.push(this.item);
    } else {
      cars[this.charVals.indexOf(this.selectedItem)] = this.item;
    }

    this.charVals = cars;
    this.item = null;
    this.displayDialog = false;
  }

  delete() {
    const index = this.charVals.indexOf(this.selectedItem);
    this.charVals = this.charVals.filter((val, i) => i !== index);
    this.item = null;
    this.displayDialog = false;
  }

  onRowSelect(event) {
    this.newItem = false;
    this.item = this.cloneCar(event.data);
    this.displayDialog = true;
  }

  cloneCar(c: ProdCharVal): ProdCharVal {
    const item = {};
    // tslint:disable-next-line:forin
    for (const prop in c) {
      item[prop] = c[prop];
    }
    return item;
  }

  searchProdChars(event: any) {
    this.prodCharWebService.getByTitleLike(event.query)
      .subscribe(data =>
        this.prodCharsSugg = data.filter(item => this.charVals.findIndex(val => val.productCharId === item.id) === -1));
  }

  changeProdChar(prodChar: ProdChar) {
    this.item.productCharName = prodChar.title;
    this.item.productCharType = prodChar.type;
    this.item.productCharId = prodChar.id;
  }
}
