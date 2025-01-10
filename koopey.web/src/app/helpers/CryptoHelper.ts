import sha256 from 'crypto-js/sha256';
export class CryptoHelper {
  public static compareHash(a: any, b: any): boolean {
    return CryptoHelper.toHash(a) == CryptoHelper.toHash(b) ? true : false;
  }

  public static toHash(str: any): string {
    return sha256(str).toString();
  }
}
