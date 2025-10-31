import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Meta, moduleMetadata, StoryObj } from '@storybook/angular';
import { AppModule } from '@components/application.module';
import { LoginComponent } from '@components/authentication/login/login.component';
import { AlertService } from '@services/alert.service';
import { TranslateModule, TranslateLoader, TranslateService, TranslateStore } from "@ngx-translate/core";
import { StorybookTranslateModule } from './StorybookTranslateModule';
import { AuthenticationService } from '@services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { TagService } from '@services/tag.service';
import { UserService } from '@services/user.service';
import { ActivatedRoute } from '@angular/router';

const meta: Meta<LoginComponent> = {
    component: LoginComponent,
    decorators: [
        moduleMetadata({
            imports: [MatSnackBarModule,  StorybookTranslateModule,  HttpClient],
            declarations: [],
            providers: [ AuthenticationService, AlertService, HttpClient , TagService, UserService, ActivatedRoute],
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