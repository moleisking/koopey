const SHA256 = require("crypto-js/sha256");
import { UUID } from 'angular2-uuid';

export class Tag {
    public id: string = UUID.UUID();
    public type: string = "";
    public hash: string = "";
    public cn: string = "";
    public de: string = "";
    public en: string = "";
    public es: string = "";
    public fr: string = "";
    public it: string = "";
    public pt: string = "";
    public zh: string = "";

    public static getText(tag: Tag, language: string): string {
        if (language == 'de') {
            return tag.de;
        } else if (language == 'cn') {
            return tag.cn;
        } else if (language == 'en') {
            return tag.en;
        } else if (language == 'es') {
            return tag.es;
        } else if (language == 'fr') {
            return tag.fr;
        } else if (language == 'it') {
            return tag.it;
        } else if (language == 'pt') {
            return tag.pt;
        } else if (language == 'zh') {
            return tag.zh;
        } else {
            return tag.en;
        }
    }

    public static contains(tags: Array<Tag>, tag: Tag): Boolean {
        if (tags && tags.length > 0 && tag) {
            for (var i = 0; i < tags.length; i++) {
                //Exclude current tag
                if (tags[i] &&
                    tags[i].id === tag.id &&
                    tags[i].en === tag.en) {
                    //Current item is not unique                     
                    return true;
                } else if (i === tags.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } 
            return false;
        
    }

    public static create(tags: Array<Tag>, tag: Tag): Array<Tag> {
        if (!Tag.contains(tags, tag)) {
            tags.push(tag);
            return tags;
        } else {
            return tags;
        }
    }

    public static read(tags: Array<Tag>, tag: Tag): Tag {
        if (tags && tags.length > 0) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i] &&
                    tags[i].id == tag.id) {
                    return tags[i];
                }
            }
        }
        return new Tag();
    }

    public static update(tags: Array<Tag>, tag: Tag): Array<Tag> {
        if (tags && tags.length > 0) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i] &&
                    tags[i].id == tag.id) {
                    tags[i] = tag;
                    return tags;
                }
            }
        }
        return new Array<Tag>();
    }

    public static delete(tags: Array<Tag>, tag: Tag): Array<Tag> {
        if (tags && tags.length > 0) {
            for (var i = 0; i < tags.length; i++) {
                if (tags[i] &&
                    tags[i].id == tag.id) {
                    tags.splice(i, 1);
                    return tags;
                }
            }
        }
        return new Array<Tag>();
    }
}
