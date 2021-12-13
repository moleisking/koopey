import { BaseModel } from "./baseModel";
import { Asset } from "../models/asset";
import { Tag } from "../models/tag";

export class Classification extends BaseModel {
    public asset: Array<Asset> = new Array<Asset>();
    public assetId!: String;
    public tag: Array<Tag> = new Array<Tag>();
    public tagId!: String;
}