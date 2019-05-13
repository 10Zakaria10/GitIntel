import { IShoppingList } from 'app/shared/model/shopping-list.model';

export interface IIngredient {
    id?: number;
    name?: string;
    amount?: string;
    recipeId?: number;
    shoppinglists?: IShoppingList[];
    recipeId?: number;
}

export class Ingredient implements IIngredient {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: string,
        public recipeId?: number,
        public shoppinglists?: IShoppingList[],
        public recipeId?: number
    ) {}
}
