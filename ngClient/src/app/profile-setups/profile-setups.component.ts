import { Component, OnInit } from '@angular/core';
import { TreeNode } from 'primeng';
import { Category } from '../model/Category';
import { ProdChar } from '../model/prodChar';
import { ProductCharType } from '../model/productCharType';
import { CategoryWebService } from '../services/web/category-web.service';
import { ProdCharWebService } from '../services/web/product-char-web.service';

@Component({
  selector: 'app-profile-setups',
  templateUrl: './profile-setups.component.html',
  styleUrls: ['./profile-setups.component.scss']
})
export class ProfileSetupsComponent implements OnInit {
  showItem: Map<TreeNode, boolean> = new Map();
  categories: TreeNode[] = [];
  seq = -1;

  displayDialog: boolean;
  selectedItem: ProdChar;

  newItem: boolean;
  item: ProdChar;
  chars: ProdChar[] = [];

  types: any[] = [
    { label: 'String', value: ProductCharType.Str },
    { label: 'Number', value: ProductCharType.Number },
    { label: 'Date', value: ProductCharType.Date }];

  constructor(
    private categoryService: CategoryWebService,
    private prodCharService: ProdCharWebService) { }

  ngOnInit(): void {
    this.initCategories();
    this.prodCharService.getAll().subscribe(res => {
      this.chars = res;
    });
  }

  private initCategories() {
    this.categoryService.getAll().subscribe(categories => {
      this.categories = this.constructMenuItems(categories);
      console.log(this.categories);
    });
  }

  private constructMenuItems(dto: Category[]): TreeNode[] {
    const parents = dto.filter(cat => cat.parentId == null);
    return parents.map(par => this.constructMenuItem(par, dto, true));
  }

  private constructMenuItem(item: Category, items: Category[], parent: boolean): TreeNode {
    const menuItem: TreeNode = {
      // label: item.title,
      data: {
        id: item.id,
        parentId: item.parentId,
        title: item.title
      },
      // routerLink: this.cuf.createLink(item),
    };

    const children: Category[] = items.filter(cat => cat.parentId === item.id);
    if (children.length) {
      menuItem.children = children.map(child => this.constructMenuItem(child, items, false));
    }
    return menuItem;
  }

  displayActions(node: any): void {
    this.showItem.set(node, true);
  }

  hideActions(node: TreeNode): void {
    this.showItem.set(node, false);
  }

  addOnChildLevel(node: any, toggler: any, event: any): void {
    // this.showItem.set(node, true);
    const currentNode: TreeNode = node.node;
    if (!currentNode.children) {
      currentNode.children = [];
    }
    currentNode.children.push({
      data: {
        id: this.seq--,
        title: 'New Subcategory ' + currentNode.data.title
      }
    });
    if (toggler && event) {
      if (!currentNode.expanded) {
        toggler.onClick(event);
      } else {
        toggler.onClick(event);
        toggler.onClick(event);
      }
    }

    this.categories = [...this.categories];
  }

  addOnSameLevel(node: any, toggler: any): void {
    if (!node && !toggler) {
      this.categories.push({
        data: {
          id: this.seq--,
          title: 'New Item'
        },
        children: [],
        parent: null
      });
    } else {
      const currentNode: TreeNode = node.node;
      if (currentNode.parent) {
        this.addOnChildLevel({ node: currentNode.parent }, null, null);
      } else {
        this.categories.push({
          data: {
            id: this.seq--,
            title: 'New Item'
          },
          children: [],
          parent: null
        });
      }
    }
    this.categories = [...this.categories];
  }

  editCatEnd(editor, event): void {
    if ((event.type && event.type === 'blur') || event.key === 'Enter') {
      editor.deactivate();
    }
  }

  removeNode(node: any): void {
    const currentNode: TreeNode = node.node;

    if (currentNode.parent) {
      currentNode.parent.children = currentNode.parent.children.filter(val => val !== currentNode);
    } else {
      this.categories = this.categories.filter(val => val !== currentNode);
    }
    this.categories = [...this.categories];
  }

  private fillArray(arr: Category[], item: TreeNode, parent: TreeNode) {
    arr.push(
      {
        id: item.data.id,
        title: item.data.title,
        parentId: parent ? parent.data.id : null
      });
    if (item.children) {
      item.children.forEach(child => this.fillArray(arr, child, item));
    }
  }

  save() {
    const arr: Category[] = [];
    this.categories.forEach(parent => {
      this.fillArray(arr, parent, null);
    });
    this.categoryService.saveCategories(arr).subscribe(res => {
      this.initCategories();
    });
  }


  showDialogToAdd() {
    this.newItem = true;
    this.item = {};
    this.displayDialog = true;
  }


  saveProdChars() {
    const arr: Category[] = [];
    this.categories.forEach(parent => {
      this.fillArray(arr, parent, null);
    });
    this.categoryService.saveCategories(arr).subscribe(res => {
      this.initCategories();
    });
  }

  onRowSelect(event) {
    this.newItem = false;
    this.item = Object.assign({}, event.data);
    this.displayDialog = true;
  }

  addNewProdChar() {
    this.chars.push(this.item);
  }
}
