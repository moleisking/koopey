import { MatSnackBar } from '@angular/material/snack-bar';
import { Meta, moduleMetadata, StoryObj } from '@storybook/angular';
import { AppModule } from '@components/application.module';
import { AlertService } from '@services/alert.service';
import { StorybookTranslateModule } from './StorybookTranslateModule';
import { AuthenticationService } from '@services/authentication.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from '@services/message.service';
import { UserService } from '@services/user.service';
import { DashboardComponent } from '@components/dashboard/dashboard.component';

const meta: Meta<DashboardComponent> = {
  component: DashboardComponent,
  decorators: [
    moduleMetadata({
      imports: [StorybookTranslateModule,HttpClientModule],
      declarations: [],
      providers: [AlertService, AuthenticationService, MatSnackBar, MessageService , UserService],
    }),
  ],
};
export default meta;

type Story = StoryObj<DashboardComponent>;

export const Empty: Story = {
  decorators: [
    moduleMetadata({
      imports: [],
      declarations: [],
      providers: [],
    }),
  ],
};

export const Complete: Story = {
  decorators: [
    moduleMetadata({
      imports: [],
      declarations: [],
      providers: [],
    }),
  ],
};