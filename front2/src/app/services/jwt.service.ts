import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class JwtService {
    private jwtHelper = new JwtHelperService();

    constructor() { }

    public isValid(token): boolean {
        try {
            this.jwtHelper.decodeToken(token)
            return true;
        } catch (error) {
            return false;
        }
    }
}