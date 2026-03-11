import { Component, computed } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
  constructor(private auth: AuthService) {}

  isLoggedIn()  { return this.auth.isLoggedIn(); }
  isAdmin()     { return this.auth.isAdmin(); }
  isCliente()   { return this.auth.isCliente(); }
  logout()      { this.auth.logout(); }

  username = computed(() => this.auth.currentUser()?.username ?? '');
}