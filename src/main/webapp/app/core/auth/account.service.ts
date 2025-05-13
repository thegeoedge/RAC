import { inject, Injectable, Signal, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, ReplaySubject, of } from 'rxjs';
import { shareReplay, tap, catchError } from 'rxjs/operators';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Account } from 'app/core/auth/account.model';
import { ApplicationConfigService } from '../config/application-config.service';
import { EmpRoleFunctionPermissionService } from 'app/entities/emp-role-function-permission/service/emp-role-function-permission.service';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { EmpFunctionsService } from 'app/entities/emp-functions/service/emp-functions.service';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private userIdentity = signal<Account | null>(null);
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account> | null;
  emprolefuntion = inject(EmpRoleFunctionPermissionService);
  private http = inject(HttpClient);
  private stateStorageService = inject(StateStorageService);
  private router = inject(Router);
  private applicationConfigService = inject(ApplicationConfigService);
  employees = inject(EmployeeService);
  empfuntions = inject(EmpFunctionsService);
  save(account: Account): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account'), account);
  }
  fetchEmpRoleFunctionPermission(roleId: number): void {
    this.emprolefuntion.query({ 'roleId.equals': roleId }).subscribe({
      next: (res: any) => {
        console.log('Full Response:', res);

        const empRoleFunctionPermission = res.body; // the array of permissions

        if (Array.isArray(empRoleFunctionPermission)) {
          const functionIds = empRoleFunctionPermission.map((item: any) => item.functionId);
          console.log('Function IDs:', functionIds);

          // Now call fetchempfuntion for each functionId
          functionIds.forEach((functionId: number) => {
            this.fetchempfuntion(functionId);
          });
        } else {
          console.log('No permissions found or response is not an array.');
        }
      },
      error: err => {
        console.error('Error fetching data:', err);
      },
    });
  }
  emproles: any[] = [];

  fetchempfuntion(functionId: number): void {
    this.empfuntions.query({ 'functionId.equals': functionId }).subscribe({
      next: (res: any) => {
        console.log('Function Response for ID', functionId, ':', res.body);

        const data = res.body;
        if (Array.isArray(data) && data.length > 0) {
          // Assuming only one function per ID
          const functionName = data[0].functionName;

          if (functionName) {
            this.emproles.push(functionName); // Add to the emproles array
            console.log('Updated emproles:', this.emproles);
          }
        }
        localStorage.setItem('emproles', JSON.stringify(this.emproles));
        console.log('checckkkkkkkk', JSON.parse(localStorage.getItem('emproles') || '[]'));
        console.log('rolessssssss', this.emproles); // Log the updated emproles array
      },
      error: err => {
        console.error('Error fetching function data for ID', functionId, ':', err);
      },
    });
  }

  fetchemp(name: String) {
    this.employees.query({ 'fullName.equals': name }).subscribe({
      next: (res: any) => {
        console.log('Response:', res); // Log the full response

        const empRoleFunctionPermission = res.body[0].id; // The actual data response
        console.log('First itemzzzzzzzzzz:', empRoleFunctionPermission); // Log the first item (if it's an array)
        this.fetchEmpRoleFunctionPermission(empRoleFunctionPermission); // Call the function with the first item's id
      },
      error: err => {
        console.error('Error fetching data:', err); // Log any errors that occur during the API call
      },
    });
  }
  authenticate(identity: Account | null): void {
    this.userIdentity.set(identity);
    this.authenticationState.next(this.userIdentity());

    // Store the id and login in localStorage when the user logs in
    if (identity) {
      //localStorage.setItem('userId', identity.id.toString());
      localStorage.setItem('userLogin', identity.login);
      const storedLogin = localStorage.getItem('userLogin');

      console.log('User logged in:');
      console.log('Loginddddddd:', storedLogin);
      this.fetchemp(storedLogin!);

      this.http.get<{ id: string }>('/api/account').subscribe(userData => {
        console.log('API response:', userData); // Log the full response

        if (userData && userData.id) {
          localStorage.setItem('userId', userData.id); // Store the user id
          const storedUserId = localStorage.getItem('userId');
          console.log('User logged in:');
          console.log('UserIdd:', storedUserId);
          // Fetch data using the stored user id
        } else {
          console.error('User ID is undefined in the response');
        }
      });
    } else {
      localStorage.removeItem('userId');
      localStorage.removeItem('userLogin');
    }

    if (!identity) {
      this.accountCache$ = null;
    }
  }

  trackCurrentAccount(): Signal<Account | null> {
    return this.userIdentity.asReadonly();
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    const userIdentity = this.userIdentity();
    if (!userIdentity) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return userIdentity.authorities.some((authority: string) => authorities.includes(authority));
  }

  identity(force?: boolean): Observable<Account | null> {
    if (!this.accountCache$ || force) {
      this.accountCache$ = this.fetch().pipe(
        tap((account: Account) => {
          this.authenticate(account);
          this.navigateToStoredUrl();
        }),
        shareReplay(),
      );
    }
    return this.accountCache$.pipe(catchError(() => of(null)));
  }

  isAuthenticated(): boolean {
    return this.userIdentity() !== null;
  }

  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>(this.applicationConfigService.getEndpointFor('api/account'));
  }

  private navigateToStoredUrl(): void {
    const previousUrl = this.stateStorageService.getUrl();
    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }
}
