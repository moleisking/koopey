import { HttpClient, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { TranslateModule, TranslateLoader, TranslateService, TranslateStore } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { of } from "rxjs";

const staticTranslateLoader: TranslateLoader = {
  getTranslation(lang: string) {
    return of(require('src/assets/i18n/en.json'));
  }
}

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, './assets/i18n/en.json');
}

@NgModule({
  imports: [
    TranslateModule.forRoot({
      defaultLanguage: "en",
      loader: {
        provide: TranslateLoader,
        //  useValue: staticTranslateLoader
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    TranslateModule],
    providers: [TranslateService, provideHttpClient(withInterceptorsFromDi())]
    
})
export class StorybookTranslateModule {
  constructor(translateService: TranslateService) {
    console.log("Configuring the translation service: ", translateService);
    console.log("Translations: ", translateService.translations);
    translateService.setDefaultLang("en");
    translateService.use("en");
  }
}