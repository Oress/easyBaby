import {Category} from '../model/Category';

export class CategoryUrlFormatter {

  public createLink(cat: Category) {
    return '/category/' + cat.id;
  }
}
