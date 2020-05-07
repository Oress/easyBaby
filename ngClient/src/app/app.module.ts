import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MetaReducer, StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { CurrencyMaskModule } from "ng2-currency-mask";
import { storeFreeze } from 'ngrx-store-freeze';
import { ContextMenuModule } from 'ngx-contextmenu';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { ButtonModule, ConfirmationService, DropdownModule, FileUploadModule, InputTextareaModule, InputTextModule, MessageModule, MessageService, PanelModule, ProgressBarModule, TabViewModule } from 'primeng';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ChipsModule } from 'primeng/chips';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { EditorModule } from 'primeng/editor';
import { GalleriaModule } from 'primeng/galleria';
import { InplaceModule } from 'primeng/inplace';
import { InputMaskModule } from 'primeng/inputmask';
import { LightboxModule } from 'primeng/lightbox';
import { MegaMenuModule } from 'primeng/megamenu';
import { MenubarModule } from 'primeng/menubar';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { PasswordModule } from 'primeng/password';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { SidebarModule } from 'primeng/sidebar';
import { SpinnerModule } from 'primeng/spinner';
import { StepsModule } from 'primeng/steps';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { TreeTableModule } from 'primeng/treetable';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './basket/basket.component';
import { CategoriesComponent } from './categories/categories.component';
import { CategoryResultComponent } from './category-result/category-result.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { FavouritesComponent } from './favourites/favourites.component';
import { HeaderComponent } from './header/header.component';
import { InventoryComponent } from './inventory/inventory.component';
import { MainPageComponent } from './main-page/main-page.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { PreordersComponent } from './preorders/preorders.component';
import { ProdCharvalComponent } from './prod-charval/prod-charval.component';
import { ProdInvItemComponent } from './prod-inv-item/prod-inv-item.component';
import { ProductComponent } from './product/product.component';
import { ProfilePageComponent } from './profile-page-component/profile-page.component';
import { ProfileProductsComponent } from './profile-products/profile-products.component';
import { ProfileSetupsComponent } from './profile-setups/profile-setups.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchComponent } from './search/search.component';
import { CategoryWebService } from './services/web/category-web.service';
import { ProdCharWebService } from './services/web/product-char-web.service';
import { ProductWebService } from './services/web/product-web.service';
import { UserWebService } from './services/web/user-web.service';
import { ItemViewComponent } from './shared/item-view/item-view.component';
import { JwtInterceptor } from './shared/JwtInterceptor';
import { SignupComponent } from './signup/signup.component';
import { appReducer } from './store/appReducer';
import { AppState } from './store/AppState';
import { CheckoutComponent } from './checkout/checkout.component';
import { CalendarModule } from 'primeng/calendar';
import {RadioButtonModule} from 'primeng/radiobutton';
import {SlideMenuModule} from 'primeng/slidemenu';
import { PreorderWebService } from './services/web/preorder-web.service';
export const metaReducers: MetaReducer<AppState>[] = !environment.production ? [storeFreeze] : [];
//
// export const reducers: ActionReducerMap<AppState> = {
//   token: tokenReducer,
//   user: userReducer
// };

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MainPageComponent,
    SignupComponent,
    ItemViewComponent,
    CategoriesComponent,
    ProfileComponent,
    ProfilePageComponent,
    CartComponent,
    FavouritesComponent,
    MyOrdersComponent,
    PreordersComponent,
    ProfileProductsComponent,
    InventoryComponent,
    SearchComponent,
    CategoryResultComponent,
    ProdInvItemComponent,
    EditProductComponent,
    ProdCharvalComponent,
    ProfileSetupsComponent,
    ProductComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    StoreModule.forRoot(appReducer),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: environment.production }),
    TooltipModule,
    OverlayPanelModule,
    DialogModule,
    MegaMenuModule,
    HttpClientModule,
    ConfirmDialogModule,
    StepsModule,
    BreadcrumbModule,
    TableModule,
    ProgressBarModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    ToastModule,
    ChipsModule,
    AutoCompleteModule,
    DropdownModule,
    MessageModule,
    PanelModule,
    ReactiveFormsModule,
    InputTextareaModule,
    ProgressSpinnerModule,
    MenubarModule,
    ToolbarModule,
    TabViewModule,
    FileUploadModule,
    LightboxModule,
    TreeTableModule,
    ContextMenuModule.forRoot(),
    InplaceModule,
    SpinnerModule,
    NgxWebstorageModule.forRoot(),
    EditorModule,
    PasswordModule,
    InputMaskModule,
    GalleriaModule,
    SidebarModule,
    ScrollPanelModule,
    CurrencyMaskModule,
    CalendarModule,
    RadioButtonModule,
    SlideMenuModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    CategoryWebService,
    ProductWebService,
    UserWebService,
    MessageService,
    ProdCharWebService,
    ConfirmationService,
    PreorderWebService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
