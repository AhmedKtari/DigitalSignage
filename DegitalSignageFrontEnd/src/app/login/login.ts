import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterLink } from "@angular/router";
import { Authservice } from '../Services/authservice';




@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  WrongCredentials = false;
  email: string = '';
  password: string = '';
  ErrorType: string = '';
  constructor(private router: Router ,
              private authentservice : Authservice
  ) {}
  async HandleLogin() {
    if(this.email == '' || this.password == ''){
      this.WrongCredentials = true;
      this.ErrorType = "Please fill in all fields";
      return;
    }
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' 
                
    },
    body: JSON.stringify({ emailRequest: this.email, passwordRequest: this.password }) // ← this is the data going TO the backend
  });

  const data = await response.json();
  
  
  if (response.ok) {
    this.authentservice.authenticatedUsername = String(data.username);
    this.authentservice.authenticate(data.email,data.username);

    this.router.navigate(['/profile/' + String(data.username)]);
    
    
    
  }
  else {
    this.WrongCredentials = true;
    this.ErrorType = String(data.message); // ← this is the data coming FROM the backend
  }
    
  
} 

}
