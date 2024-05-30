/// <reference types="@angular/localize" />

import { enableProdMode } from "@angular/core";
import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";

import { AppModule } from "./app/components/application.module"; // './app/app.module';
import { Environment } from "./environments/environment";

if (Environment.type === "production" || Environment.type === "stage") {
  enableProdMode();
}

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch((err) => console.error(err));
