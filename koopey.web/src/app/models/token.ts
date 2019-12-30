const SHA256 = require("crypto-js/sha256");
import { UUID } from 'angular2-uuid';

export class Token {

    public id: string = UUID.UUID();
    public value: number = 0;
    public pubKey: string = '';
    public prvKey: string = '';
    public name: string = '';
    public type: string = '';
    public hash: string;    
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    public static isEmpty(token: Token): Boolean {
        if (token && token.id && token.prvKey && token.type) {
            return false;
        } else {
            return true;
        }
    }   

    public static contains(tokens: Array<Token>, token: Token): boolean {
        if (tokens && tokens.length > 0 && token && token.id && token.type) {
            for (var i = 0; i < tokens.length; i++) {
                if ((tokens[i]) && (tokens[i].id === token.id ||
                    tokens[i].type === token.type)) {
                    return true;
                } else if (i === tokens.length - 1) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }     

    public static create(tokens: Array<Token>, token: Token): Array<Token> {
        if (!Token.contains(tokens, token)) {
            tokens.push(token);
            return tokens;
        } else {
            return tokens;
        }
    }

    public static read(tokens: Array<Token>, token: Token): Token {
        if (tokens && tokens.length > 0) {
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i] &&
                    tokens[i].id == token.id &&
                    tokens[i].type == token.type) {
                    return tokens[i];
                }
            }
        }
    }   

    public static update(tokens: Array<Token>, token: Token): Array<Token> {
        if (tokens && tokens.length > 0) {
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i] &&
                    tokens[i].id == token.id) {
                    tokens[i] = token;
                    return tokens;
                }
            }
        }
    }

    public static delete(tokens: Array<Token>, token: Token): Array<Token> {
        if (tokens && tokens.length > 0) {
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i] &&
                    tokens[i].id == token.id) {
                    tokens.splice(i, 1);
                    return tokens;
                }
            }
        }
    }
}