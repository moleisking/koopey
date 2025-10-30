/// <reference types="@angular/localize" />

import { enableProdMode } from "@angular/core";
import { AppModule } from "./app/components/application.module"; // './app/app.module';
import { Environment } from "./environments/environment";
import { platformBrowser } from "@angular/platform-browser";

if (Environment.type === "production" || Environment.type === "stage") {
  enableProdMode();
}

platformBrowser()
  .bootstrapModule(AppModule)
  .catch((err) => console.error(err));
