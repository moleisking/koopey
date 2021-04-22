export class ModelHelper {
  public static contains(items: Array<any>, id: string): Boolean {
    if (items.find((currentItem) => currentItem.id === id) != undefined) {
      return true;
    } else {
      return false;
    }
  }

  public static create(items: Array<any>, item: any): Array<any> {
    items.push(item);
    return items;
  }

  public static read(items: Array<any>, item: any): any {
    return items.find((currentItem) => currentItem.id === item.id);
  }

  public static update(items: Array<any>, item: any): Array<any> {
    const elementsIndex = items.findIndex((element) => element.id == item.id);
    return items.splice(elementsIndex, 1);
  }

  public static delete(items: Array<any>, item: any): Array<any> {
    return items.filter(function (value) {
      return value.id === item.id;
    });
  }
}
