
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const APP_ROUTE: Routes = [
    { path: '', redirectTo: 'detail', pathMatch: 'full' },
    { path: 'sb', loadChildren: './view/home/home.module#HomeModule' },
    { path: 'detail', loadChildren: './view/detail/detail.module#DetailModule' },
  //  { path: 'not-found', loadChildren: './pages/not-found/not-found.module#NotFoundModule' },
    //{ path: '**', redirectTo: 'not-found' }
];
@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTE, { useHash: true })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
