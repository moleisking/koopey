// This file is required by karma.conf.js and loads recursively all the .spec and framework files

import { provideZonelessChangeDetection } from '@angular/core';
import { getTestBed } from '@angular/core/testing';
import { BrowserTestingModule, platformBrowserTesting } from '@angular/platform-browser/testing'

declare const require: {
  context(path: string, deep?: boolean, filter?: RegExp): {
    keys(): string[];
    <T>(id: string): T;
  };
};

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserTestingModule,
  platformBrowserTesting(),
);

getTestBed().configureTestingModule({
  providers: [provideZonelessChangeDetection()]
});
// Then we find all the tests.
const context = require.context('./', true, /\.spec\.ts$/);
// And load the modules.
context.keys().map(context);
