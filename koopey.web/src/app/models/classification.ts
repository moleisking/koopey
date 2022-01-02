import { Asset } from "../models/asset";
import { Base} from "./base/base";
import { Tag } from "../models/tag";

export class Classification extends Base {
    public asset: Asset = new Asset();
    public assetId!: String;
    public tag: Tag = new Tag();
    public tagId!: String;
}