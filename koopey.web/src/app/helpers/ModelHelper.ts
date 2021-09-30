export class ModelHelper {
  public static contains(items: Array<any>, item: any): Boolean {
    if (items.find((currentItem) => currentItem.id === item.id) != undefined) {
      return true;
    } else {
      return false;
    }
  }

  public static count(type: any, items: Array<any>): number {
    return items.filter((item) => {
      item.type === type;
    }).length;
  }

  public static create(items: Array<any>, item: any): Array<any> {
    items.push(item);
    return items;
  }

  public static delete(items: Array<any>, item: any): Array<any> {
    return items.filter(function (value) {
      return value.id === item.id;
    });
  }

  public static edit(items: Array<any>, item: any): Array<any> {
    const elementsIndex = items.findIndex((element) => element.id == item.id);
    return items.splice(elementsIndex, 1);
  }

  public static equals(a: any, b: any): boolean {
    if (!a || !b) {
      return false;
    } else if (JSON.stringify(a.sort()) !== JSON.stringify(b.sort())) {
      return false;
    } else {
      return true;
    }
  }

  public static equalsArray(a: Array<any>, b: Array<any>): boolean {
    if (!a || !b || !Array.isArray(a) || !Array.isArray(b)) {
      return false;
    } else if (a.length != b.length) {
      return false;
    } else if (JSON.stringify(a.sort()) !== JSON.stringify(b.sort())) {
      return false;
    } else {
      return true;
    }
  }

  public static exclude(others: Array<any>, reject: any): Array<any> {
    if (others && others.length > 0) {
      var results: Array<any> = new Array<any>();
      for (var i = 0; i < others.length; i++) {
        if (!ModelHelper.equals(others[i], reject)) {
          results.push(others[i]);
        }
      }
      return results;
    }
    return new Array<any>();
  }

  public static find(items: Array<any>, item: any): any {
    if (typeof item === "object") {
      return items.find(
        (currentItem) =>
          currentItem.id === item.id || currentItem.type === item.type
      );
    } else if (item.length == 36) {
      return items.find((currentItem) => currentItem.id === item.id);
    } else {
      return items.find((currentItem) => currentItem.type === item.type);
    }
  }

  public static is(item: any, type: any): Boolean {
    if (item.type === type) {
      return true;
    } else {
      return false;
    }
  }
}
