import { Component, OnInit } from '@angular/core';
import { CategoryWebService } from '../services/web/category-web.service';
import { Category } from '../model/Category';
import { CategoryUrlFormatter } from '../shared/category-url-formatter';
import { MenuItem } from 'primeng';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  private cuf: CategoryUrlFormatter = new CategoryUrlFormatter();
  parents: CategoryView[];
  items: MenuItem[];

  constructor(private categoryService: CategoryWebService) { }


  ngOnInit() {
    this.categoryService.getAll().subscribe(categories => {
      this.items = this.constructMenuItems(categories);
      console.log(this.items);
    });
  }
  private constructMenuItems(dto: Category[]): MenuItem[] {
    const parents = dto.filter(cat => cat.parentId == null);
    return parents.map(par => this.constructMenuItem(par, dto, true));
  }

  private constructMenuItem(item: Category, items: Category[], parent: boolean): MenuItem {
    const menuItem: MenuItem = {
      label: item.title,
      // routerLink: this.cuf.createLink(item),
    };

    const children: Category[] = items.filter(cat => cat.parentId === item.id);
    if (children.length) {
      menuItem.items = children.map(child => this.constructMenuItem(child, items, false));
    }
    return menuItem;
  }

  // ngOnInit() {
  //   this.categoryService.getAll().subscribe(categories => {
  //     this.parents = categories.filter(cat => cat.parentId == null);
  //     console.log(this.parents);
  //   });
  // }

  // private constructMenuItem(item: Category, items: Category[], parent: boolean): CategoryView {
  //   const category: CategoryView = {
  //     title: item.title,
  //     routerLink: this.cuf.createLink(item),
  //   };

  //   const children: Category[] = items.filter(cat => cat.parentId === item.id);
  //   if (children.length) {
  //     category.children = children.map(child => this.constructMenuItem(child, items, false));
  //   }
  //   return category;
  // }
}

interface CategoryView {
  title?: string;
  routerLink?: string;
  children?: CategoryView[];
}
