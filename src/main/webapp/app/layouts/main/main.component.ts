import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet, Router, NavigationEnd } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';
import { AppPageTitleStrategy } from 'app/app-page-title-strategy';
import FooterComponent from '../footer/footer.component';
import PageRibbonComponent from '../profiles/page-ribbon.component';
import SidenavbarComponent from '../sidenavbar/sidenavbar.component';

@Component({
  standalone: true,
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  providers: [AppPageTitleStrategy],
  imports: [RouterOutlet, FooterComponent, PageRibbonComponent, SidenavbarComponent], // Include SidebarComponent

  styles: [
    `
      .sidebar {
        width: 250px; /* Default width */
        flex-shrink: 0; /* Prevent shrinking */
        transition: width 0.3s ease; /* Optional smooth transition */
      }

      .content-container {
        flex-grow: 1;
      }

      /* Optionally, you can create a CSS class for hiding the sidebar */
      .sidebar-hidden {
        width: 0; /* Remove width when on /login */
      }
    `,
  ],
})
export default class MainComponent implements OnInit {
  private router = inject(Router);
  private appPageTitleStrategy = inject(AppPageTitleStrategy);
  private accountService = inject(AccountService);

  constructor() {}
  checkURL(url: string): boolean {
    return this.router.url === url;
  }
  ngOnInit(): void {
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const url = event.urlAfterRedirects;

        if (url === '/login' || url.startsWith('/printsalesinvoice')) {
          this.setSidebarVisibility(false);
        } else {
          this.setSidebarVisibility(true);
        }
      }
    });
  }

  private setSidebarVisibility(isVisible: boolean): void {
    const sidenav = document.querySelector('.sidebar') as HTMLElement;
    if (sidenav) {
      if (isVisible) {
        sidenav.classList.remove('sidebar-hidden'); // Show sidenav
      } else {
        sidenav.classList.add('sidebar-hidden'); // Hide sidenav
      }
    }
  }
}
