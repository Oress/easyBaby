import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../model/Product';

@Component({
  selector: 'app-item-view',
  templateUrl: './item-view.component.html',
  styleUrls: ['./item-view.component.scss']
})
export class ItemViewComponent implements OnInit {
  @Input() data: Product;

  constructor() {
  }

  ngOnInit() {
  }
}
