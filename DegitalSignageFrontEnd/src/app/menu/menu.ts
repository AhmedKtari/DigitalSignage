import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Authservice } from '../Services/authservice';

@Component({
  selector: 'app-menu',
  imports: [RouterLink],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class MenuComponent {
  appName: string = 'MyApp';
  username: string | null = null;
  isMenuOpen = false;
  LogginStatuts: boolean = false;
  
  constructor(
    public authentservice : Authservice ,
    private router: Router
  
  ){
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
   ngOnInit(){
    this.LogginStatuts = this.authentservice.isLoggedIn()   
      
    }
  loggingOut() {
    this.authentservice.logout();
    
  }
    navigateToProfile() {
      console.log('Navigating to profile...');
      this.username = this.authentservice.getAuthenticatedUser() 
      this.router.navigate(['/profile/', this.username]);
      
      
  }
     
}

