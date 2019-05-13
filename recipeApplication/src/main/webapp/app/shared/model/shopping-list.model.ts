import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IShoppingList {
    id?: number;
    ingredients?: IIngredient[];
}

export class ShoppingList implements IShoppingList {
    constructor(public id?: number, public ingredients?: IIngredient[]) {}
}
