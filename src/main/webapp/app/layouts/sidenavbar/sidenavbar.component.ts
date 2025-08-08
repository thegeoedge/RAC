import { Component, inject, signal, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import NavbarItem from '../navbar/navbar-item.model';
// import NavbarItem from './navbar-item.model';

@Component({
  standalone: true,
  selector: 'jhi-sidebar',
  templateUrl: './sidenavbar.component.html',
  styleUrls: ['./sidenavbar.component.scss'], // Corrected to 'styleUrls' for CSS
  imports: [RouterModule, SharedModule, HasAnyAuthorityDirective],
})
export default class SidenavbarComponent implements OnInit {
  inProduction?: boolean;
  openAPIEnabled?: boolean;
  version = '';
  account = inject(AccountService).trackCurrentAccount();
  entitiesNavbarItems: NavbarItem[] = [];
  isEntitiesExpanded = false;
  isAdminExpanded = false;
  isAccountExpanded = false;

  private loginService = inject(LoginService);
  private profileService = inject(ProfileService);
  private router = inject(Router);

  constructor() {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  checkURL(url: string): boolean {
    return this.router.url === url;
  }
  emproles: string[] = [];
  showJobLink: boolean = false;
  showJobLink2: boolean = false;
  showJobLink3: boolean = false;

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.router.events.subscribe(() => {
      if (this.checkURL('/printsalesinvoice')) {
        const sidenav = document.querySelector('.sidenav') as HTMLElement;
        if (sidenav) {
          sidenav.style.display = 'none'; // Hide the sidenav
        }
      }
      if (this.checkURL('/login')) {
        const sidenav = document.querySelector('.sidenav') as HTMLElement;
        if (sidenav) {
          sidenav.style.display = 'none'; // Hide the sidenav
        }
      } else {
        const sidenav = document.querySelector('.sidenav') as HTMLElement;
        if (sidenav) {
          sidenav.style.display = 'block'; // Show the sidenav
        }
      }
    });
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });
    const emprolesString = localStorage.getItem('emproles');
    this.emproles = emprolesString ? JSON.parse(emprolesString) : [];

    console.log('checckkkkkkkk', this.emproles);
    //Add new inventory item
    this.showJobLink = this.emproles.includes('View job');
    this.showJobLink2 = this.emproles.includes('Search sales invoice');
    this.showJobLink3 = this.emproles.includes('View appointment');
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
