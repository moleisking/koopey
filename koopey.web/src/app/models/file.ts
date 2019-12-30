const SHA256 = require("crypto-js/sha256");
import { Config } from "../config/settings";
import { Location } from "../models/location";
import { Tag } from "../models/tag";
import { UUID } from 'angular2-uuid';

export class File {

    public id: string = UUID.UUID();
    public description: string = "";
    public size: number;
    public type: string;
    public name: string;
    public path: string;
    public data: string;
    public hash: string;
    public timeZone: string = Config.default_time_zone;
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    public static isEmpty(file: File): boolean {
        if (file && file.type &&
            file.name &&
            file.size > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static contains(files: Array<File>, file: File): boolean {
        if (files && files.length > 0 && file) {
            for (var i = 0; i < files.length; i++) {
                //Exclude current file
                if (files[i] &&
                    files[i].name === file.name &&
                    files[i].type === file.type &&
                    files[i].size === file.size &&
                    files[i].hash === file.hash) {
                    //Current item is not unique                     
                    return true;
                } else if (i === files.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static create(files: Array<File>, file: File): Array<File> {
        if (!File.contains(files, file)) {
            files.push(file);
            return files;
        } else {
            return files;
        }
    }

    public static read(files: Array<File>, file: File): File {
        if (files && files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                if (files[i] &&
                    files[i].id == file.id) {
                    return files[i];
                }
            }
        }
    }

    public static update(files: Array<File>, file: File): Array<File> {
        if (files && files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                if (files[i] &&
                    files[i].id == file.id) {
                    files[i] = file;
                    return files;
                }
            }
        }
    }

    public static delete(files: Array<File>, file: File): Array<File> {
        if (files && files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                if (files[i] &&
                    files[i].id == file.id) {
                    files.splice(i, 1);
                    return files;
                }
            }
        }
    }
}