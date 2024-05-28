import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Meta, moduleMetadata, StoryObj } from '@storybook/angular';
import { AppModule } from 'src/app/components/application/application.module';
import { LoginComponent } from 'src/app/components/authentication/login/login.component';
import { AlertService } from 'src/app/services/alert.service';
import { TranslateModule, TranslateLoader, TranslateService, TranslateStore } from "@ngx-translate/core";
import { StorybookTranslateModule } from './StorybookTranslateModule';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import { TagService } from 'src/app/services/tag.service';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';

const meta: Meta<LoginComponent> = {
    component: LoginComponent,
    decorators: [
        moduleMetadata({
            imports: [MatSnackBarModule,  StorybookTranslateModule,  HttpClientModule],
            declarations: [],
            providers: [AlertService, AuthenticationService, HttpClient , TagService, UserService, ActivatedRoute],
        }),
    ],
};
export default meta;

type Story = StoryObj<LoginComponent>;

export const Empty: Story = {
    
};

export const Complete: Story = {
    args: {
        login: { alias: 'test', password: '12345' },
    },
    decorators: [
        moduleMetadata({
            imports: [],
            declarations: [],
            providers: [],
        }),
    ],
};