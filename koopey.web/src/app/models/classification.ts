import { Base} from "./base/base";
import { Asset } from "../models/asset";
import { Tag } from "../models/tag";

export class Classification extends Base {
    public asset: Array<Asset> = new Array<Asset>();
    public assetId!: String;
    public tag: Array<Tag> = new Array<Tag>();
    public tagId!: String;
}