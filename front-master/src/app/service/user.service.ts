import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NewUser } from '../models/newUser';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  addUser(user: NewUser) {
    return this.http.post(`${environment.gatewayurl}/account/api/v1/add`, user);
  }

  findUser(username: string) {
    return this.http.get(`${environment.gatewayurl}/account/api/v1/query?username=${username}`);
  }

}
